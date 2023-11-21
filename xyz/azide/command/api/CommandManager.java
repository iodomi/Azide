package xyz.azide.command.api;

import org.reflections.Reflections;
import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventChat;
import xyz.azide.trait.Manager;
import xyz.azide.util.game.ChatUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public final class CommandManager implements Manager {
    private final HashMap<Class<? extends Command>, Command> classCommandMap = new HashMap<>();

    @Register
    private final Consumer<EventChat.Send> onChatSend = event -> {
        final String prefix = ".";
        boolean exists = false;

        if (event.getMessage().startsWith(prefix)) {
            for (final Command command : getCommands()) {
                final String[] aliases = command.getAlias();
                for (final String alias : aliases) {
                    if (event.getMessage().contains(prefix + alias)) {
                        final String[] arguments = event.getMessage().split(" ");
                        command.invoke(Arrays.copyOfRange(arguments, 1, arguments.length));
                        event.setCancelled(true);
                        exists = true;
                        break;
                    }
                }
            }
        }
        if (!exists){
            ChatUtil.addErrorMessage("This command does not exist");
        }
    };

    @Override
    public void initialize() {
        Azide.getSingleton().getEventBus().register(this);

        final Set<Class<? extends Command>> classSet = new Reflections().getSubTypesOf(Command.class);

        if (!classSet.isEmpty()) {
            for (final Class<? extends Command> clazz : classSet) {
                try {
                    classCommandMap.put(clazz, clazz.getDeclaredConstructor().newInstance());
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Collection<Command> getCommands() {
        return classCommandMap.values();
    }

    public Command getCommand(final Class<? extends Command> command) {
        return classCommandMap.get(command);
    }
}
