package com.ricy40.caerula.content.blocks.flora;

import com.ricy40.caerula.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RedSeaGrassBlock extends SeaGrassBlock {

    public RedSeaGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockState blockstate = BlockInit.TALL_RED_SEAGRASS.get().defaultBlockState();
        BlockState blockstate1 = blockstate.setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockpos = pos.above();
        if (worldIn.getBlockState(blockpos).is(Blocks.WATER)) {
            worldIn.setBlock(pos, blockstate, 2);
            worldIn.setBlock(blockpos, blockstate1, 2);
        }

    }
}
