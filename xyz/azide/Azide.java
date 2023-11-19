package xyz.azide;

import org.lwjgl.opengl.Display;
import xyz.azide.event.bus.EventBus;
import xyz.azide.module.api.ModuleManager;
import xyz.azide.trait.Initializable;
import xyz.azide.trait.Manager;
import xyz.azide.trait.Util;
import xyz.azide.util.discord.DiscordUtil;
import xyz.azide.value.api.ValueManager;

/**
 * @author Severanced and plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class Azide implements Initializable {
    private static final Azide SINGLETON;
    private static final String NAME, VERSION, BUILD;
    private final EventBus eventBus;
    private final Manager moduleManager, valueManager;
    private final DiscordUtil discordUtil;

    private Azide() {
        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        valueManager = new ValueManager();
        discordUtil = new DiscordUtil();
    }

    @Override
    public void initialize() {
        moduleManager.initialize();
        valueManager.initialize();
        discordUtil.initialize();

        Display.setTitle(NAME + " " + VERSION + " (" + BUILD + ")");
    }

    public static Azide getSingleton() {
        return SINGLETON;
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return (ModuleManager) moduleManager;
    }

    public ValueManager getValueManager() {
        return (ValueManager) valueManager;
    }

    public DiscordUtil getDiscordUtil(){
        return discordUtil;
    }

    static {
        SINGLETON = new Azide();
        NAME = "Azide";
        VERSION = "1.0";
        BUILD = "Development";
    }
}
