package net.khai.create_lets_breaking_bad.item.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CyanideItem extends Item {
    public CyanideItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                handleCyanideLogic(player, level);
            }
        }
        return result;
    }

    private void handleCyanideLogic(Player player, Level level) {
        long currentTime = level.getGameTime();
        var data = player.getPersistentData();

        long lastEatTime = data.getLong("last_cyanide_time");
        int count = data.getInt("cyanide_eat_count");

        if (lastEatTime != 0 && (currentTime - lastEatTime) < 12000) {
            count++;
        } else {
            count = 1;
        }

        if (count >= 2) {
            player.removeAllEffects();
            ResourceKey<net.minecraft.world.damagesource.DamageType> damageTypeKey =
                    ResourceKey.create(Registries.DAMAGE_TYPE,
                            ResourceLocation.fromNamespaceAndPath("create_lets_breaking_bad", "cyanide_overdose"));

            level.registryAccess().registry(Registries.DAMAGE_TYPE).ifPresent(reg -> {
                reg.getHolder(damageTypeKey).ifPresent(holder -> {
                    player.hurt(new DamageSource(holder), Float.MAX_VALUE);
                });
            });

            data.putInt("cyanide_eat_count", 0);
            data.putLong("last_cyanide_time", 0);
        } else {
            data.putInt("cyanide_eat_count", count);
            data.putLong("last_cyanide_time", currentTime);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.create_lets_breaking_bad.cyanide.desc"));
    }
}
