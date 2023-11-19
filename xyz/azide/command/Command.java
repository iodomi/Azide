package xyz.azide.command;

public abstract class Command {
    private final String description;
    private final String[] alternatives;

    public Command(final String description, final String[] alternatives) {
        this.description = description;
        this.alternatives = alternatives;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAlternatives() {
        return alternatives;
    }

    public abstract void invoke(String[] args);
}
