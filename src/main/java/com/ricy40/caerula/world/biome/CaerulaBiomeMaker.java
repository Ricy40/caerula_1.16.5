package com.ricy40.caerula.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.function.Supplier;

public class CaerulaBiomeMaker {

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.2460909F - lvt_1_1_ + 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    private static BiomeGenerationSettings.Builder baseCaerulaGeneration(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> sbc) {
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).surfaceBuilder(sbc);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addWaterTrees(biomegenerationsettings$builder);
        return biomegenerationsettings$builder;
    }

    private static Biome shallowCaerulaOceanBiome(MobSpawnInfo.Builder mobSpawninfo, int waterColor, int waterFogColor, float depth, BiomeGenerationSettings.Builder biomeGenSettings) {

        return (new Biome.Builder())
                .precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.OCEAN)
                .depth(depth).scale(0.1F)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(12638463)
                        .skyColor(getSkyColorWithTemperatureModifier(0.5F))
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(mobSpawninfo.build())
                .generationSettings(biomeGenSettings.build()).build();
    }

    public static Biome makeRedGrassFieldsBiome() {
        MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).addSpawn(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.PUFFERFISH, 15, 1, 3));
        DefaultBiomeFeatures.warmOceanSpawns(mobspawninfo$builder, 10, 4);
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = baseCaerulaGeneration(ConfiguredSurfaceBuilders.FULL_SAND)
                //.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.WARM_OCEAN_VEGETATION)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeaturesInit.RED_SEAGRASS_FIELDS_FEATURE)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEA_PICKLE);
        DefaultBiomeFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
        return shallowCaerulaOceanBiome(mobspawninfo$builder, 4445678, 270131, 5.0F, biomegenerationsettings$builder);

    }

}
