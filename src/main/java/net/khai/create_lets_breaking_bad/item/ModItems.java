package net.khai.create_lets_breaking_bad.item;

import net.khai.create_lets_breaking_bad.Create_Lets_Breaking_Bad;
import net.khai.create_lets_breaking_bad.item.custom.CyanideItem;
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

    public static final DeferredItem<Item> CYANIDE = ITEMS.register("cyanide",
            () -> new CyanideItem(new Item.Properties()
                    .food(ModFoodProperties.CYANIDE)
                    .stacksTo(16)
                    ));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
