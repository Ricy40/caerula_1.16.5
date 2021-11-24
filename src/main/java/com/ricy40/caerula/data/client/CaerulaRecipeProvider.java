package com.ricy40.caerula.data.client;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.core.init.BlockInit;
import com.ricy40.caerula.core.init.ItemInit;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class CaerulaRecipeProvider extends RecipeProvider {
    public CaerulaRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RED_SEAGRASS.get()), Items.RED_DYE, 0.1f, 200).unlockedBy("has", has(BlockInit.RED_SEAGRASS.get())).save(consumer);

        //ShapelessRecipeBuilder.shapeless(ItemInit.CAERULA.get(), 9)
        //        .requires(BlockInit.EXAMPLE_BLOCK.get())
        //        .unlockedBy("has", has(ItemInit.EXAMPLE_ITEM.get()))
        //        .save(consumer);

        //ShapedRecipeBuilder.shaped(BlockInit.EXAMPLE_STAIRS.get())
        //        .define('#', BlockInit.EXAMPLE_STAIRS.get())
        //        .define('0', Items.AIR)
        //        .pattern("#00")
        //        .pattern("##0")
        //        .pattern("###")
        //        .unlockedBy("has", has(BlockInit.EXAMPLE_BLOCK.get()))
        //        .save(consumer);

        //Smelting... numbers are xp, time
        //
        //CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.EXAMPLE_BLOCK.get()), ItemInit.EXAMPLE_ITEM.get(), 1.0f, 200)
        //        .unlockedBy("has", has(ItemInit.EXAMPLE_ITEM.get()))
        //        .save(consumer, modId("example_smelting"));

        //CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.EXAMPLE_BLOCK.get()), ItemInit.EXAMPLE_ITEM.get(), 1.0f, 100)
        //        .unlockedBy("has", has(ItemInit.EXAMPLE_ITEM.get()))
        //        .save(consumer, modId("example_smelting"));
    }

    private ResourceLocation modId(String path) {
        return new ResourceLocation(Caerula.MOD_ID, path);
    }
}
