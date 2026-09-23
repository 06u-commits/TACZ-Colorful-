package com.tacz.guns.client.gui.components.refit;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.util.TritiumColorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import java.awt.*;

public class TritiumHSVSliderGroup {
    private final Inventory inventory;
    private final int gunItemIndex;

    private final TritiumColorSlider hueSlider;
    private final TritiumColorSlider saturationSlider;

    public TritiumHSVSliderGroup(int x, int y, int width, int height, Inventory inventory, int gunItemIndex) {
        this.inventory = inventory;
        this.gunItemIndex = gunItemIndex;

        int color = getColor();
        float[] hsb = Color.RGBtoHSB((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, null);

        hueSlider = new TritiumColorSlider(x, y, width, height, this, hsb[0]);
        saturationSlider = new TritiumColorSlider(x, y + 2 + height, width, height, this, hsb[1]);
    }

    public TritiumColorSlider getHueSlider() {
        return hueSlider;
    }

    public TritiumColorSlider getSaturationSlider() {
        return saturationSlider;
    }

    public void apply() {
        ItemStack gun = inventory.getItem(gunItemIndex);
        if (gun.getItem() instanceof IGun iGun) {
            int rgb_new = Color.HSBtoRGB((float) hueSlider.getValue(), (float) saturationSlider.getValue(), 1f);
            iGun.setTritiumColor(gun, rgb_new);
        }
    }

    private int getColor() {
        if (inventory == null) {
            return 0x00FF00;
        }
        ItemStack gun = inventory.getItem(gunItemIndex);
        if (gun.getItem() instanceof IGun) {
            return TritiumColorUtil.getTritiumColor(gun);
        }
        return 0x00FF00;
    }

    public static class TritiumColorSlider extends ForgeSlider {
        private final TritiumHSVSliderGroup parent;

        public TritiumColorSlider(int x, int y, int width, int height, TritiumHSVSliderGroup parent, double current) {
            super(x, y, width, height, Component.empty(), Component.empty(), 0, 1, current, 0.01, 0, true);
            this.parent = parent;
        }

        @Override
        protected void applyValue() {
            parent.apply();
        }
    }
}
