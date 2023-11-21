package xyz.azide.util.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import xyz.azide.trait.Initializable;
import xyz.azide.trait.Util;

public class DiscordUtil implements Initializable, Util {
    private static boolean running = true;
    private static long created = 0;

    @Override
    public void initialize() {
        created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> {
        }).build();
        DiscordRPC.discordInitialize("1175788563997139096", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public static void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public static void update(String firstLine) {
        running = true;

        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(null);

        b.setBigImage("azide", "");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
