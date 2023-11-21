package xyz.azide.command.impl;

import xyz.azide.command.Command;
import xyz.azide.module.impl.visual.Overlay;
import xyz.azide.util.game.ChatUtil;

public class ClientName extends Command {
    public ClientName() {
        super("ClientName", "Lets you change the name displayed as the watermark", new String[]{"clientname"});
    }

    @Override
    public void invoke(String[] args) {
        try {
            final Overlay overlay = new Overlay();
            final StringBuilder string = new StringBuilder();

            for (final String arg : args) {
                String tempString = arg;
                tempString = tempString.replace('&', '§');
                string.append(tempString).append(" ");
            }

            overlay.setWatermarkName(string.toString().trim());
            ChatUtil.addChatMessage("Client name was set to: " + overlay.getWatermarkName());
        } catch (ArrayIndexOutOfBoundsException e) {
            ChatUtil.addErrorMessage("Incomplete arguments");
        }
    }
}
