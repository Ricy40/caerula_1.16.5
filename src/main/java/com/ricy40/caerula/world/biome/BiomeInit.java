package com.ricy40.caerula.world.biome;


import com.ricy40.caerula.Caerula;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BiomeInit {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Caerula.MOD_ID);

    public static final RegistryObject<Biome> RED_SEAGRASS_FIELDS = BIOMES.register("red_seagrass_fields", () -> CaerulaBiomeMaker.makeRedGrassFieldsBiome());

}