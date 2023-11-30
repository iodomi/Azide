package xyz.azide.command.api;

import org.reflections.Reflections;
import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventChat;
import xyz.azide.trait.Manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public final class CommandManager implements Manager {
    private final HashMap<Class<? extends Command>, Command> classCommandMap = new HashMap<>();

    @Register
    private final Consumer<EventChat.Send> onChatSend = event -> {
        final String prefix = ".";

        if (event.getMessage().startsWith(prefix)) {
            for (final Command command : getCommands()) {
                final String[] aliases = command.getAlias();
                for (final String alias : aliases) {
                    if (event.getMessage().contains(prefix + alias)) {
                        final String[] arguments = event.getMessage().split(" ");
                        command.invoke(Arrays.copyOfRange(arguments, 1, arguments.length));
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    };

    @Override
    public void initialize() {
        Azide.getSingleton().getEventBus().register(this);

        for (final Class<? extends Command> clazz : new Reflections().getSubTypesOf(Command.class)) {
            try {
                classCommandMap.put(clazz, clazz.getDeclaredConstructor().newInstance());
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Collection<Command> getCommands() {
        return classCommandMap.values();
    }
}
