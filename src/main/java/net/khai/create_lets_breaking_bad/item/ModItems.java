package net.khai.create_lets_breaking_bad.item;

import net.khai.create_lets_breaking_bad.Create_Lets_Breaking_Bad;
import net.khai.create_lets_breaking_bad.item.custom.PowderItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Create_Lets_Breaking_Bad.MOD_ID);

    public static final DeferredItem<Item> RADISH = ITEMS.register("radish",
            () -> new Item(new Item.Properties()
                    .food(ModFoodProperties.RADISH)
                    ));

    public static final DeferredItem<Item> METH = ITEMS.register("meth",
            () -> new PowderItem(new Item.Properties()
                    .food(ModFoodProperties.METH_PROPS.getFoodProperties()),
                    ModFoodProperties.METH_PROPS));

    public static final DeferredItem<Item> CYANIDE = ITEMS.register("cyanide",
            () -> new PowderItem(new Item.Properties()
                    .food(ModFoodProperties.CYANIDE_PROPS.getFoodProperties())
                    .stacksTo(16),
                    ModFoodProperties.CYANIDE_PROPS));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
