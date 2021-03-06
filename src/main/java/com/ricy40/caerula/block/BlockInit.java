package com.ricy40.caerula.block;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.block.flora.RedSeaGrassBlock;
import com.ricy40.caerula.block.flora.TallRedSeaGrassBlock;
import com.ricy40.caerula.item.ItemInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Caerula.MOD_ID);

    public static final RegistryObject<Block> RED_SEAGRASS = registerBlock("red_seagrass", () -> new RedSeaGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> TALL_RED_SEAGRASS = registerBlock("tall_red_seagrass", () -> new TallRedSeaGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> DEAD_BUSH_CORAL = registerBlock("dead_bush_coral", () -> new DeadCoralPlantBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak()));
    public static final RegistryObject<Block> BUSH_CORAL = registerBlock("bush_coral", () -> new CoralPlantBlock(DEAD_BUSH_CORAL.get(), AbstractBlock.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block > void registerBlockItem(String name, RegistryObject<T> block) {
        if (name != "tall_red_seagrass") {
            ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(Caerula.CAERULA_GROUP)));
        }
    }
}
