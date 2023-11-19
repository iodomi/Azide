package xyz.azide.command.api;

import xyz.azide.Azide;
import xyz.azide.command.Command;
import xyz.azide.trait.Initializable;

import java.util.ArrayList;

public final class CommandManager extends ArrayList<Command> implements Initializable {
    @Override
    public void initialize(){
        Azide.getSingleton().getEventBus().register(this);
    }


}
