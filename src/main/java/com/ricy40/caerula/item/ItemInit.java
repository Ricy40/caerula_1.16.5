package com.ricy40.caerula.item;

import com.ricy40.caerula.Caerula;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Caerula.MOD_ID);

    public static final RegistryObject<Item> CAERULA = ITEMS.register("caerula", () -> new Item(new Item.Properties()));
}
