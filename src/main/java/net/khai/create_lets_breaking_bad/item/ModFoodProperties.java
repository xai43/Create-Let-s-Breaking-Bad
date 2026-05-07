package net.khai.create_lets_breaking_bad.item;

import net.khai.create_lets_breaking_bad.item.custom.PowderProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties RADISH = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 0.35f)
            .build();

    public static final FoodProperties CYANIDE = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.25f)
            .alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 700), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 70), 1f)
            .build();

    public static final PowderProperties METH_PROPS = new PowderProperties.Builder()
            .grams(0.5f)
            .maxDose(2.5f)
            .shaderThreshold(1.0f) // Шейдер включится после 1 грамма
            .doseShader("minecraft:shaders/post/meth.json") // Пример ванильного шейдера (радужный размыв)
            .deathKey("meth_overdose") // Кастомный JSON смерти
            .maxDoseEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.POISON, 70), 1f)
            .alwaysEdible()
            .build();

    public static final PowderProperties CYANIDE_PROPS = new PowderProperties.Builder()
            .grams(1.0f)
            .maxDose(1.0f)
            .alwaysEdible()
            .maxDoseEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.POISON, 70), 1f)
            .build();

// ".alwaysEdible()"    В 1.21.1 его можно и при полной сытости кушать
}
