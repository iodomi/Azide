package xyz.azide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import xyz.azide.command.api.CommandManager;
import xyz.azide.config.api.ConfigManager;
import xyz.azide.event.api.bus.EventBus;
import xyz.azide.module.api.ModuleManager;
import xyz.azide.shader.api.ShaderManager;
import xyz.azide.trait.Initializable;
import xyz.azide.trait.Manager;
import xyz.azide.ui.font.api.FontManager;
import xyz.azide.value.api.ValueManager;

import java.io.File;

/**
 * @author Severanced and plusbox
 * @version 1.0
 * @since 11/14/2023
 */
public final class Azide implements Initializable {
    private static final Azide SINGLETON;
    private static final String NAME, VERSION, BUILD;
    private static final File DIR;
    private final Manager moduleManager, valueManager, commandManager, configManager, fontManager, shaderManager;
    private final EventBus eventBus;
    private final Logger logger;

    private Azide() {
        eventBus = new EventBus();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        valueManager = new ValueManager();
        configManager = new ConfigManager();
        fontManager = new FontManager();
        shaderManager = new ShaderManager();
        logger = LogManager.getLogger(Azide.class);
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

    public static File getDirectory() {
        return DIR;
    }

    @Override
    public void initialize() {
        fontManager.initialize();
        commandManager.initialize();
        moduleManager.initialize();
        valueManager.initialize();
        configManager.initialize();
        shaderManager.initialize();

        Display.setTitle(NAME + " " + VERSION + " (" + BUILD + ")");

        logger.info("Azide was injected.");
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

    public ConfigManager getConfigManager() {
        return (ConfigManager) configManager;
    }

    public FontManager getFontManager() {
        return (FontManager) fontManager;
    }

    public ShaderManager getShaderManager() {
        return (ShaderManager) shaderManager;
    }

    static {
        SINGLETON = new Azide();
        NAME = "Azide";
        VERSION = "1.0";
        BUILD = "Development";
        DIR = new File("azide");
    }
}
