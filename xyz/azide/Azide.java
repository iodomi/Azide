package xyz.azide;

import org.lwjgl.opengl.Display;
import xyz.azide.event.bus.EventBus;
import xyz.azide.module.ModuleManager;
import xyz.azide.trait.IInitializable;
import xyz.azide.trait.IManager;
import xyz.azide.value.ValueManager;

/**
 * @author Severanced and plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class Azide implements IInitializable {
    private static final Azide SINGLETON;
    private static final String NAME, VERSION, BUILD;
    private final EventBus eventBus;
    private final IManager moduleManager, valueManager;

    private Azide() {
        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        valueManager = new ValueManager();
    }

    @Override
    public void initialize() {
        moduleManager.initialize();
        valueManager.initialize();

        Display.setTitle(NAME + " " + VERSION + "(" + BUILD + ")");
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

    static {
        SINGLETON = new Azide();
        NAME = "Azide";
        VERSION = "1.0";
        BUILD = "Development";
    }
}
