package xyz.azide.util.game;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import xyz.azide.Azide;
import xyz.azide.trait.Util;

/**
 * @author plusbox
 * @since 10/23/2023
 */
public final class ChatUtil implements Util {
    private ChatUtil() {

    }

    public static void addChatMessage(final String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + Azide.getName() + EnumChatFormatting.GRAY + " > " + EnumChatFormatting.RESET + message));
    }

    public static void addErrorMessage(final String message) {
        addChatMessage(EnumChatFormatting.RED + "(ERROR) " + message);
    }
}