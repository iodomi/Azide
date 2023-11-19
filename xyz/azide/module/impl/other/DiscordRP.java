package xyz.azide.module.impl.other;

import org.lwjgl.input.Keyboard;
import xyz.azide.Azide;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.discord.DiscordUtil;
import xyz.azide.value.impl.KeyValue;

public class DiscordRP extends Module {
    public DiscordRP() {
        super("DiscordRP", "Client status on your discord profile", ModuleCategory.OTHER);
        setKeybind(new KeyValue("DiscordRP", null, Keyboard.KEY_R));
        onEnable = () -> Azide.getSingleton().getDiscordUtil().update("Playing Client", null);
        onDisable = () -> Azide.getSingleton().getDiscordUtil().shutdown();
    }
}
