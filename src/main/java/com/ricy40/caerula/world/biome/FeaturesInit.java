package com.ricy40.caerula.world.biome;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.world.biome.feature.RedSeaGrassFeature;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class FeaturesInit {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Caerula.MOD_ID);

    public static final RegistryObject<RedSeaGrassFeature> RED_SEAGRASS_F = FEATURES.register("red_seagrass_f",() -> new RedSeaGrassFeature(ProbabilityConfig.CODEC));

}