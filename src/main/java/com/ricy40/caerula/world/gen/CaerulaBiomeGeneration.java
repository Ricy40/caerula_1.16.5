package com.ricy40.caerula.world.gen;

import com.ricy40.caerula.world.biome.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CaerulaBiomeGeneration {
    public static void generateBiomes() {
        addBiome(BiomeInit.RED_SEAGRASS_FIELDS.get(), BiomeManager.BiomeType.WARM, 20, BiomeDictionary.Type.OCEAN);
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES,
                Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)));

        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }
}
