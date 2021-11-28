package com.ricy40.caerula.events;

import com.ricy40.caerula.entity.EntityTypeInit;
import com.ricy40.caerula.entity.entities.LulaEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CaerulaEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.LULA.get(), LulaEntity.setCustomAttributes().build());
    }
}