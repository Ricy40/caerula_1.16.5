package com.ricy40.caerula.world.biome;

import com.ricy40.caerula.Caerula;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

public class ConfiguredFeaturesInit {

    public static final ConfiguredFeature<?, ?> RED_SEAGRASS_FIELDS_FEATURE = FeaturesInit.RED_SEAGRASS_FEATURE.get().configured(new ProbabilityConfig(0.3F)).count(80).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(Caerula.MOD_ID, "red_seagrass_fields_feature"), RED_SEAGRASS_FIELDS_FEATURE);

    }
}
