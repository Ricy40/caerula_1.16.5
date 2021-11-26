package com.ricy40.caerula.world.biome;

import com.ricy40.caerula.Caerula;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CaerulaConfiguredSurfaceBulders {

    public static ConfiguredSurfaceBuilder<?> RED_SEAGRASS_FIELDS_SURFACE = register("red_seagrass_fields_surface",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                    Blocks.SAND.defaultBlockState(),
                    Blocks.SAND.defaultBlockState(),
                    Blocks.SAND.defaultBlockState()
            )));

    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Caerula.MOD_ID, name), csb);
    }

}
