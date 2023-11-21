package xyz.azide.command.impl;

import xyz.azide.command.Command;
import xyz.azide.util.game.ChatUtil;

import static xyz.azide.trait.Util.mc;

public class Say extends Command {
    public Say() {
        super("Say", "Sends message in the chat for you", new String[]{ "say" });
    }

    @Override
    public void invoke(String[] args) {
        try{
            final String message = args[0];

            mc.thePlayer.sendChatMessage(message);
        } catch (ArrayIndexOutOfBoundsException e) {
            ChatUtil.addErrorMessage("Incomplete arguments");
        }
    }
}
