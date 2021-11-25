package com.ricy40.caerula.world.dimension;

import com.ricy40.caerula.Caerula;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionInit {

    public static final RegistryKey<World> CAERULA_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(Caerula.MOD_ID, "caerula_world"));

}