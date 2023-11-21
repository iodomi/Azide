package xyz.azide;

import org.lwjgl.opengl.Display;
import xyz.azide.command.api.CommandManager;
import xyz.azide.event.api.bus.EventBus;
import xyz.azide.module.api.ModuleManager;
import xyz.azide.trait.Initializable;
import xyz.azide.trait.Manager;
import xyz.azide.value.api.ValueManager;

import java.io.File;

/**
 * @author Severanced and plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class Azide implements Initializable {
    private static final Azide SINGLETON;
    private static final String NAME, VERSION, BUILD;
    private final EventBus eventBus;
    private final Manager moduleManager, valueManager, commandManager;
    private static final File DIR;

    private Azide() {
        eventBus = new EventBus();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        valueManager = new ValueManager();
    }

    @Override
    public void initialize() {
        commandManager.initialize();
        moduleManager.initialize();
        valueManager.initialize();

        if (!DIR.exists()) {
            DIR.mkdir();
        }

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

    public CommandManager getCommandManager() {
        return (CommandManager) commandManager;
    }

    static {
        SINGLETON = new Azide();
        NAME = "Azide";
        VERSION = "1.0";
        BUILD = "Development";
        DIR = new File("azide");
    }
}
