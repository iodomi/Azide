package xyz.azide.command.impl;

import net.minecraft.util.EnumChatFormatting;
import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.util.game.ChatUtil;

import java.util.Arrays;

public class Help extends Command {
    public Help() {
        super("Help", "Makes a list of all commands", new String[]{"help", "list"});
    }

    @Override
    public void invoke(String[] args) {
        for (Command command : Azide.getSingleton().getCommandManager().getCommands()) {
            ChatUtil.addChatMessage(command.getName() + " " + EnumChatFormatting.GRAY + Arrays.toString(command.getAlias()) + EnumChatFormatting.RESET + " - " + command.getDescription());
        }
    }
}
