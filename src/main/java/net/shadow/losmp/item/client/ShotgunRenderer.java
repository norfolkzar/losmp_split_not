package net.shadow.losmp.item.client;

import net.shadow.losmp.item.custom.ShotgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<ShotgunItem> {
    public ShotgunRenderer() {
        super(new ShotgunModel());
    }
}
