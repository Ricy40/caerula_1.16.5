package com.ricy40.caerula.world.biome;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

public class ConfiguredFeaturesInit {

    public static final ConfiguredFeature<?, ?> RED_SEAGRASS_FIELDS_FEATURE = register("red_seagrass_fields_feature", Feature.SEAGRASS.configured(new ProbabilityConfig(0.3F)).count(80).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
