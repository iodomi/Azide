package xyz.azide.util.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import xyz.azide.trait.Initializable;
import xyz.azide.trait.Util;

public class DiscordUtil implements Initializable, Util {
    private boolean running = true;
    private long created = 0;

    @Override
    public void initialize(){
        this.created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> {}).build();
        DiscordRPC.discordInitialize("1175788563997139096", handlers, true);

        new Thread("Discord RPC Callback"){
            @Override
            public void run(){
                while(running){
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    public void shutdown(){
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine){
        running = true;
        initialize();

        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);

        b.setBigImage("azide", "");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
