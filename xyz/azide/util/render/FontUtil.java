package xyz.azide.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.azide.trait.Util;

import java.awt.*;
import java.io.InputStream;

/**
 * Forked from:
 * https://github.com/Godwhitelight/FontRenderer/blob/main/FontUtil.java"
 * Modified by @Severanced
 */

public class FontUtil implements Util {
    public static Font getFontFromTTF(ResourceLocation location, int size, int type) {
        try {
            final InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();

            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(size, type);

            inputStream.close();
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", type, size);
        }
    }
}