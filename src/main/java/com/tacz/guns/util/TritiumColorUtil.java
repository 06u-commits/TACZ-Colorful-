package com.tacz.guns.util;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.pojo.display.TritiumConfig;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TritiumColorUtil {
    public static int getTritiumColor(ItemStack stack, @NotNull TritiumConfig defaultConfig) {
        if (stack == null) {
            return defaultConfig.getDefaultColor();
        }

        if (stack.getItem() instanceof IGun gun) {
            if (gun.hasCustomTritiumColor(stack)) {
                return gun.getTritiumColor(stack);
            } else {
                return defaultConfig.getDefaultColor();
            }
        }

        return defaultConfig.getDefaultColor();
    }

    public static int getTritiumColor(ItemStack stack) {
        if (stack == null) {
            return 0x00FF00;
        }

        if (stack.getItem() instanceof IGun gun) {
            if (gun.hasCustomTritiumColor(stack)) {
                return gun.getTritiumColor(stack);
            } else {
                return TimelessAPI.getGunDisplay(stack)
                        .map(GunDisplayInstance::getTritiumConfig)
                        .map(TritiumConfig::getDefaultColor)
                        .orElse(0x00FF00);
            }
        }

        return 0x00FF00;
    }
}
