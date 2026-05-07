package net.khai.create_lets_breaking_bad.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber
public class PowderItem extends Item {
    private final PowderProperties powderProps;
    public static final Map<UUID, Map<Item, Float>> PLAYER_DOSES = new HashMap<>();
    private static final Map<UUID, Long> LAST_USAGE_TIME = new HashMap<>();

    public PowderItem(Properties properties, PowderProperties powderProps) {
        super(properties);
        this.powderProps = powderProps;
    }

    public PowderProperties getPowderProps() {
        return powderProps;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            UUID uuid = player.getUUID();
            PLAYER_DOSES.putIfAbsent(uuid, new HashMap<>());
            Map<Item, Float> doses = PLAYER_DOSES.get(uuid);

            float newDose = doses.getOrDefault(this, 0f) + powderProps.getGrams();
            doses.put(this, newDose);
            LAST_USAGE_TIME.put(uuid, level.getGameTime());

            if (newDose >= powderProps.getMaxDose()) {
                // Эффекты
                for (PowderProperties.EffectEntry entry : powderProps.getMaxDoseEffects()) {
                    if (player.getRandom().nextFloat() <= entry.probability()) {
                        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(entry.effect()));
                    }
                }

                // Логика смерти
                ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE,
                        ResourceLocation.fromNamespaceAndPath("create_lets_breaking_bad", powderProps.getDeathKey()));

                var holder = player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolder(key);

                if (holder.isPresent()) {
                    // Создаем сообщение вручную, чтобы %2$s заработал
                    Component deathMsg = Component.translatable("death.attack." + powderProps.getDeathKey(),
                            player.getDisplayName(), stack.getHoverName());

                    player.hurt(new DamageSource(holder.get()), Float.MAX_VALUE);
                    // Рассылаем наше правильное сообщение всем
                    player.getServer().getPlayerList().broadcastSystemMessage(deathMsg, false);
                } else {
                    player.die(player.damageSources().generic());
                }
                doses.put(this, 0f);
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }


    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        long currentTime = event.getServer().overworld().getGameTime();
        PLAYER_DOSES.forEach((uuid, doses) -> {
            Long lastTime = LAST_USAGE_TIME.get(uuid);
            if (lastTime != null && (currentTime - lastTime) >= 12000) {
                doses.clear();
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.powder.info", stack.getHoverName(), powderProps.getGrams()).withStyle(ChatFormatting.AQUA));
    }
}
