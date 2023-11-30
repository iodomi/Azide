package xyz.azide.command;

public abstract class Command {
    private final String name, description;
    private final String[] alias;

    public Command(final String name, final String description, final String[] alias) {
        this.name = name;
        this.description = description;
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public abstract void invoke(final String[] args);
}

