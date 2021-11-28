package com.ricy40.caerula.events;

import com.ricy40.caerula.Caerula;
import com.ricy40.caerula.entity.EntityTypeInit;
import com.ricy40.caerula.entity.entities.LulaEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Caerula.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaerulaEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.LULA.get(), LulaEntity.setCustomAttributes().build());
    }
}