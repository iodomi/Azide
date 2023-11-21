package xyz.azide.command.impl;

import net.minecraft.client.Minecraft;
import xyz.azide.command.Command;
import xyz.azide.util.game.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Name extends Command {
    public Name() {
        super("Name", "Copies player name to clipboard", new String[]{ "name" });
    }

    @Override
    public void invoke(String[] args) {
        final StringSelection string = new StringSelection(Minecraft.getMinecraft().thePlayer.getName());
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.setContents(string, null);
        ChatUtil.addChatMessage("Copied your name to clipboard.");
    }
}
