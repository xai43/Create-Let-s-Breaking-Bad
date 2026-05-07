package net.khai.create_lets_breaking_bad.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PowderProperties {
    private final float grams, maxDose, shaderThreshold;
    private final String deathKey;
    private final ResourceLocation shaderLocation;
    private final List<EffectEntry> effects;
    private final FoodProperties food;

    public record EffectEntry(MobEffectInstance effect, float probability) {}

    public PowderProperties(float g, float md, float st, String dk, ResourceLocation sl, List<EffectEntry> e, FoodProperties f) {
        this.grams = g; this.maxDose = md; this.shaderThreshold = st;
        this.deathKey = dk; this.shaderLocation = sl; this.effects = e; this.food = f;
    }

    public float getGrams() { return grams; }
    public float getMaxDose() { return maxDose; }
    public float getShaderThreshold() { return shaderThreshold; }
    public String getDeathKey() { return deathKey; }
    public ResourceLocation getShaderLocation() { return shaderLocation; }
    public List<EffectEntry> getMaxDoseEffects() { return effects; }
    public FoodProperties getFoodProperties() { return food; }

    public static class Builder {
        private float grams, maxDose, shaderThreshold = 999f;
        private String deathKey = "powder_overdose";
        private ResourceLocation shaderLocation;
        private final List<EffectEntry> effects = new ArrayList<>();
        private final FoodProperties.Builder foodBuilder = new FoodProperties.Builder();

        public Builder grams(float g) { this.grams = g; return this; }
        public Builder maxDose(float md) { this.maxDose = md; return this; }
        public Builder shaderThreshold(float st) { this.shaderThreshold = st; return this; }
        public Builder deathKey(String dk) { this.deathKey = dk; return this; }
        public Builder doseShader(String path) { this.shaderLocation = ResourceLocation.parse(path); return this; }

        public Builder maxDoseEffect(MobEffectInstance e, float p) {
            this.effects.add(new EffectEntry(e, p));
            return this;
        }

        public Builder nutrition(int n) { this.foodBuilder.nutrition(n); return this; }
        public Builder saturation(float s) { this.foodBuilder.saturationModifier(s); return this; }
        public Builder alwaysEdible() { this.foodBuilder.alwaysEdible(); return this; }
        public Builder effect(Supplier<MobEffectInstance> e, float p) {
            this.foodBuilder.effect(e, p);
            return this;
        }

        public PowderProperties build() {
            return new PowderProperties(grams, maxDose, shaderThreshold, deathKey, shaderLocation, effects, foodBuilder.build());
        }
    }
}
