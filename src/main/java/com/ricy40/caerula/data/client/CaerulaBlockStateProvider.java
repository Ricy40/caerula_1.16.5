package com.ricy40.caerula.data.client;

import com.ricy40.caerula.Caerula;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CaerulaBlockStateProvider<T extends ModelBuilder<T>> extends BlockStateProvider {

    public CaerulaBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Caerula.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        //simpleBlock(BlockInit.EXAMPLEBLOCK.get());

    }
}