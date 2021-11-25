package com.ricy40.caerula.block.flora;

import com.ricy40.caerula.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TallRedSeaGrassBlock extends TallSeaGrassBlock {

    public TallRedSeaGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader blockReader, BlockPos pos, BlockState state) {
        return new ItemStack(BlockInit.RED_SEAGRASS.get());
    }
}
