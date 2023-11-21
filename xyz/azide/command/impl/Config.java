package xyz.azide.command.impl;

import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.config.api.ConfigManager;

public final class Config extends Command {

    public Config() {
        super("Config", "Manages client configuration settings", new String[]{"config", "cfg"});
    }

    @Override
    public void invoke(String[] args) {
        if (args.length < 3) {
            final ConfigManager config = new ConfigManager();
            if (args.length > 1) {
                config.create(args[1]);
            }

            switch (args[0]) {
                case "save":
                    config.save();
                    break;
                case "load":
                    config.load();
                    break;
                case "delete":
                    config.delete();
                    break;
                case "list":
                    config.list();
            }
        }
    }
}
