package com.ricy40.caerula.entity;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.entity.entities.LulaEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Caerula.MOD_ID);

    public static final RegistryObject<EntityType<LulaEntity>> LULA = ENTITY_TYPES.register("lula",
            () -> EntityType.Builder.of(LulaEntity::new, EntityClassification.WATER_CREATURE)
                    .sized(0.688f, 0.125f)
                    .build(new ResourceLocation(Caerula.MOD_ID, "lula").toString()));

    public static void register (IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

}
