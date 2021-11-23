package com.ricy40.caerula.data;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.data.client.CaerulaBlockStateProvider;
import com.ricy40.caerula.data.client.CaerulaLootTableProvider;
import com.ricy40.caerula.data.client.CaerulaModelProvider;
import com.ricy40.caerula.data.client.CaerulaRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Caerula.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new CaerulaBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new CaerulaModelProvider(gen, existingFileHelper));

        gen.addProvider(new CaerulaLootTableProvider(gen));
        gen.addProvider(new CaerulaRecipeProvider(gen));

    }
}