package azide.xyz;

import org.lwjgl.opengl.Display;

public final class Azide {
    private static final Azide SINGLETON;
    private static final String NAME, VERSION;

    private Azide() {

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

    public void init() {
        Display.setTitle(NAME + " " + VERSION);
    }

    static {
        SINGLETON = new Azide();
        NAME = "Azide";
        VERSION = "0.1";
    }
}
