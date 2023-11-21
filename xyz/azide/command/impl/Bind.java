package xyz.azide.command.impl;

import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.module.Module;
import xyz.azide.util.game.ChatUtil;
import xyz.azide.value.impl.KeyValue;

public class Bind extends Command {
    public Bind() {
        super("Bind", "Manages the module keybind setting and getting", new String[]{"bind", "keybind", "key"});
    }

    @Override
    public void invoke(String[] args) {
        try {
            if (args.length < 1) {
                ChatUtil.addErrorMessage("Incorrect use of arguments, try: .bind module <key>");
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                for (Module m : Azide.getSingleton().getModuleManager().getModules()) {
                    if (!m.getKeybind().getValue().equals(0)) {
                        ChatUtil.addChatMessage(m.getName() + ": " + EnumChatFormatting.AQUA + Keyboard.getKeyName(m.getKeybind().getValue()));
                    }
                }
                return;
            }

            final Module module = Azide.getSingleton().getModuleManager().getModule(args[0]);

            if (module == null) {
                ChatUtil.addErrorMessage("Could not find module named: " + EnumChatFormatting.AQUA + args[0]);
                return;
            }

            module.setKeybind(new KeyValue("Keybinding", Keyboard.getKeyIndex(args[1].toUpperCase())));
            ChatUtil.addChatMessage("Changed " + module.getName() + " key to: " + Keyboard.getKeyName(module.getKeybind().getValue()));

        } catch (ArrayIndexOutOfBoundsException e) {
            ChatUtil.addErrorMessage("Incorrect use of arguments...");
        }
    }
}
