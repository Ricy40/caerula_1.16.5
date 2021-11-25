package com.ricy40.caerula.world.biome;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.world.biome.feature.RedSeaGrassFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeaturesInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Caerula.MOD_ID);

    public static final RegistryObject<RedSeaGrassFeature> RED_SEAGRASS = register("red_seagrass", () -> new RedSeaGrassFeature(ProbabilityConfig.CODEC));

    public static void register(final IEventBus modEventBus) {
        FEATURES.register(modEventBus);
    }
}