package xyz.azide.module.api;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public enum ModuleCategory {
    COMBAT,
    MOVEMENT,
    PLAYER,
    VISUAL,
    OTHER;

    private final String name;

    ModuleCategory() {
        final String name = name();
        this.name = name.charAt(0) + name.substring(1).toLowerCase();
    }

    public String getName() {
        return name;
    }
}
