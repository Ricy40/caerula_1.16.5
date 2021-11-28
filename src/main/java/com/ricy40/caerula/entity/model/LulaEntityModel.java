package com.ricy40.caerula.entity.model;

import com.ricy40.caerula.Caerula;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LulaEntityModel extends AnimatedGeoModel {

    public LulaEntityModel() {
    }

    @Override
    public ResourceLocation getModelLocation(Object entity) {
        return new ResourceLocation(Caerula.MOD_ID, "geo/entity/lula_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity) {
        return new ResourceLocation(Caerula.MOD_ID, "textures/entity/lula_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object entity) {
        return new ResourceLocation(Caerula.MOD_ID, "animations/gremlin/lula_entity.animation.json");
    }
}