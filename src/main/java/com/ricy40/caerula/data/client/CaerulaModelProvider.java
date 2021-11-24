package com.ricy40.caerula.data.client;

import com.ricy40.caerula.Caerula;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CaerulaModelProvider extends ItemModelProvider
{
    public CaerulaModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Caerula.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //withExistingParent("exampleblock", modLoc("block/exampleblock"));

        //ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        //builder(itemGenerated, "example_item");

    }
    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}