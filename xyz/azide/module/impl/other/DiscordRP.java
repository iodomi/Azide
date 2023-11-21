package xyz.azide.module.impl.other;

import org.lwjgl.input.Keyboard;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.discord.DiscordUtil;
import xyz.azide.value.impl.KeyValue;

public class DiscordRP extends Module {
    private final DiscordUtil discordUtil = new DiscordUtil();
    public DiscordRP() {
        super("DiscordRP", "Client status on your discord profile", ModuleCategory.OTHER);
        onEnable = () -> {
            discordUtil.initialize();
            DiscordUtil.update("Playing Client");
        };
        onDisable = DiscordUtil::shutdown;
    }
}
