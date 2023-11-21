package xyz.azide.command.impl;

import net.minecraft.util.EnumChatFormatting;
import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.module.Module;
import xyz.azide.util.game.ChatUtil;

import static xyz.azide.trait.Util.mc;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles modules for you", new String[]{"toggle"});
    }

    @Override
    public void invoke(String[] args) {
        try {
            if (args.length < 1) {
                ChatUtil.addErrorMessage("Not enough arguments");
                return;
            }

            final Module module = Azide.getSingleton().getModuleManager().getModule(args[0]);

            if (module == null) {
                ChatUtil.addErrorMessage("Could not find module named: " + EnumChatFormatting.AQUA + args[0]);
                return;
            }
            module.toggle();
            ChatUtil.addChatMessage(module.getName() + (module.isEnabled() ? " was enabled" : " was disabled"));
        } catch (ArrayIndexOutOfBoundsException e) {
            ChatUtil.addErrorMessage("Incomplete arguments");
        }
    }
}