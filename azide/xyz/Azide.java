package azide.xyz;

import org.lwjgl.opengl.Display;

public final class Azide {
    private static final Azide SINGLETON;
    public static final String NAME = "Azide", VERSION = "1.0";

    private Azide() {

    }

    public static Azide getSingleton() {
        return SINGLETON;
    }

    public void init() {
        Display.setTitle(NAME + " " + VERSION);
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    static {
        SINGLETON = new Azide();
    }
}
