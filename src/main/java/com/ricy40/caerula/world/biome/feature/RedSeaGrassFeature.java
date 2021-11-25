package com.ricy40.caerula.world.biome.feature;

import com.mojang.serialization.Codec;
import com.ricy40.caerula.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class RedSeaGrassFeature extends Feature<ProbabilityConfig> {
    public RedSeaGrassFeature(Codec<ProbabilityConfig> probability) {
        super(probability);
    }

    public boolean place(ISeedReader seedReader, ChunkGenerator chunkGen, Random rand, BlockPos ppos, ProbabilityConfig probability) {
        boolean flag = false;
        int i = rand.nextInt(8) - rand.nextInt(8);
        int j = rand.nextInt(8) - rand.nextInt(8);
        int k = seedReader.getHeight(Heightmap.Type.OCEAN_FLOOR, ppos.getX() + i, ppos.getZ() + j);
        BlockPos blockpos = new BlockPos(ppos.getX() + i, k, ppos.getZ() + j);
        if (seedReader.getBlockState(blockpos).is(Blocks.WATER)) {
            boolean flag1 = rand.nextDouble() < (double)probability.probability;
            BlockState blockstate = flag1 ? BlockInit.TALL_RED_SEAGRASS.get().defaultBlockState() : BlockInit.RED_SEAGRASS.get().defaultBlockState();
            if (blockstate.canSurvive(seedReader, blockpos)) {
                if (flag1) {
                    BlockState blockstate1 = blockstate.setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos blockpos1 = blockpos.above();
                    if (seedReader.getBlockState(blockpos1).is(Blocks.WATER)) {
                        seedReader.setBlock(blockpos, blockstate, 2);
                        seedReader.setBlock(blockpos1, blockstate1, 2);
                    }
                } else {
                    seedReader.setBlock(blockpos, blockstate, 2);
                }

                flag = true;
            }
        }

        return flag;
    }
}
