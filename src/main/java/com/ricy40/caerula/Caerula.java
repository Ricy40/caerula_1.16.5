package com.ricy40.caerula;

import com.ricy40.caerula.entity.EntityTypeInit;
import com.ricy40.caerula.entity.render.LulaEntityGeoRenderer;
import com.ricy40.caerula.world.biome.BiomeInit;
import com.ricy40.caerula.block.BlockInit;
import com.ricy40.caerula.item.ItemInit;
import com.ricy40.caerula.world.biome.ConfiguredFeaturesInit;
import com.ricy40.caerula.world.biome.FeaturesInit;
import com.ricy40.caerula.world.gen.CaerulaBiomeGeneration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Caerula.MOD_ID)
public class Caerula {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "caerula";
    public static final ItemGroup CAERULA_GROUP = new CaerulaGroup("caerulatab");

    public Caerula() {
        GeckoLib.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::registerRenderers);

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        FeaturesInit.FEATURES.register(bus);
        EntityTypeInit.ENTITY_TYPES.register(bus);
        BiomeInit.BIOMES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

        RenderTypeLookup.setRenderLayer(BlockInit.RED_SEAGRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.TALL_RED_SEAGRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BUSH_CORAL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DEAD_BUSH_CORAL.get(), RenderType.cutout());

        //ConfiguredFeaturesInit.registerConfiguredFeatures();

        event.enqueueWork(() -> {
            CaerulaBiomeGeneration.generateBiomes();
        });

    }

    public void registerRenderers(final FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LULA.get(), manager -> new LulaEntityGeoRenderer(manager));
    }


    public static class CaerulaGroup extends ItemGroup {

        public CaerulaGroup(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.CAERULA.get().getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            super.fillItemList(items);
        }
    }


}
