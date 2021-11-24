package com.ricy40.caerula.core.init;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.content.blocks.flora.RedSeaGrassBlock;
import com.ricy40.caerula.content.blocks.flora.TallRedSeaGrassBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Caerula.MOD_ID);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static final RegistryObject<Block> RED_SEAGRASS = registerBlock("red_seagrass", () -> new RedSeaGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> TALL_RED_SEAGRASS = registerBlock("tall_red_seagrass", () -> new TallRedSeaGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));


    private static <T extends Block > void registerBlockItem(String name, RegistryObject<T> block) {
        if (name != "tall_red_seagrass") {
            ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(),
                    new Item.Properties().tab(Caerula.CAERULA_GROUP)));
        }
    }
}
