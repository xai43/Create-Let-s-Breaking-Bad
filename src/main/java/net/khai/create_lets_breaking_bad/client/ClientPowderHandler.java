package net.khai.create_lets_breaking_bad.client;

import net.khai.create_lets_breaking_bad.item.custom.PowderItem;
import net.khai.create_lets_breaking_bad.item.custom.PowderProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientPowderHandler {
    private static boolean shaderActive = false;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player != null && player.level().isClientSide) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            Map<Item, Float> doses = PowderItem.PLAYER_DOSES.get(player.getUUID());
            boolean shouldActivate = false;
            net.minecraft.resources.ResourceLocation effectLoc = null;

            if (doses != null) {
                for (Map.Entry<Item, Float> entry : doses.entrySet()) {
                    if (entry.getKey() instanceof PowderItem pItem) {
                        PowderProperties props = pItem.getPowderProps();
                        if (entry.getValue() >= props.getShaderThreshold()) {
                            shouldActivate = true;
                            effectLoc = props.getShaderLocation();
                            break;
                        }
                    }
                }
            }

            if (shouldActivate && !shaderActive && effectLoc != null) {
                mc.gameRenderer.loadEffect(effectLoc);
                shaderActive = true;
            } else if (!shouldActivate && shaderActive) {
                mc.gameRenderer.shutdownEffect();
                shaderActive = false;
            }
        }
    }
}
