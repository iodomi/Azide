package xyz.azide.command.impl;

import net.minecraft.entity.player.EntityPlayer;
import xyz.azide.command.Command;
import xyz.azide.util.game.ChatUtil;

import static xyz.azide.trait.Util.mc;

public class Teleport extends Command {
    public Teleport() {
        super("Teleport", "Teleports you to your desired location or player", new String[]{"teleport", "tp"});
    }

    @Override
    public void invoke(String[] args) {
        try {
            if (args.length == 3) {
                final double x = Double.parseDouble(args[0]);
                final double y = Double.parseDouble(args[1]);
                final double z = Double.parseDouble(args[2]);

                mc.addScheduledTask(() -> mc.thePlayer.setPosition(x, y, z));
                ChatUtil.addChatMessage("Teleported you to given position: " + x + ", " + y + ", " + z);
            } else {
                final EntityPlayer player = mc.theWorld.playerEntities.stream().filter(entity -> entity.getGameProfile().getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                if (player != null) {
                    final double x = player.posX;
                    final double y = player.posY;
                    final double z = player.posZ;

                    mc.addScheduledTask(() -> mc.thePlayer.setPosition(x, y, z));
                    ChatUtil.addChatMessage("Teleported you to given player position: " + x + ", " + y + ", " + z);
                } else {
                    ChatUtil.addErrorMessage("Could not find the given player!");
                }
            }
        }catch (IndexOutOfBoundsException e){
            ChatUtil.addErrorMessage("Incomplete arguments");
        }
    }
}
