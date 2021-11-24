package com.ricy40.caerula.data.client;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.ricy40.caerula.core.init.BlockInit;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CaerulaLootTableProvider extends LootTableProvider
{

    public CaerulaLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(CaerulaBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((a ,b) -> LootTableManager.validate(validationtracker, a ,b));
    }

    public static class CaerulaBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {

            this.add(BlockInit.RED_SEAGRASS.get(), BlockLootTables::createShearsOnlyDrop);
            this.add(BlockInit.TALL_RED_SEAGRASS.get(), createDoublePlantShearsDrop(BlockInit.RED_SEAGRASS.get()));

        }

        private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
        private static LootTable.Builder createDoublePlantShearsDrop(Block blockIn) {
            return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS).add(ItemLootEntry.lootTableItem(blockIn).apply(SetCount.setCount(ConstantRange.exactly(2)))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockInit.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }
}

