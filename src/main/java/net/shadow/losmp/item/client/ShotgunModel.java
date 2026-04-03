package net.shadow.losmp.item.client;

import net.minecraft.util.Identifier;
import net.shadow.losmp.Losmp;
import net.shadow.losmp.item.custom.ShotgunItem;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {
    @Override
    public Identifier getModelResource(ShotgunItem item) {
        return new Identifier(Losmp.MOD_ID, "geo/shotgun.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShotgunItem item) {
        return new Identifier(Losmp.MOD_ID, "textures/item/shotgun.png");
    }

    @Override
    public Identifier getAnimationResource(ShotgunItem item) {
        return new Identifier(Losmp.MOD_ID, "animations/shotgun.json");
    }
}