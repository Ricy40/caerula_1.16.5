package com.ricy40.caerula.entity.render;

import com.ricy40.caerula.entity.entities.LulaEntity;
import com.ricy40.caerula.entity.model.LulaEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LulaEntityGeoRenderer extends GeoEntityRenderer<LulaEntity> {
    public LulaEntityGeoRenderer(EntityRendererManager renderManager) {
        super(renderManager, new LulaEntityModel());
        this.shadowRadius = 0.3F;
    }
}