package xyz.azide.config.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.azide.Azide;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleManager;
import xyz.azide.trait.Json;
import xyz.azide.trait.Manager;
import xyz.azide.util.game.ChatUtil;
import xyz.azide.value.Value;
import xyz.azide.value.impl.*;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.Objects;

public final class ConfigManager implements Json, Manager {
    private final File directory = new File("azide/config");
    private String name;
    private File config;

    public void create(final String name) {
        this.name = name;
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.config = new File(directory, name + ".json");
    }

    public void save() {
        final JsonObject json = new JsonObject();
        final ModuleManager moduleManager = Azide.getSingleton().getModuleManager();

        for (final Module m : moduleManager.getModules()) {
            final JsonObject properties = new JsonObject();
            properties.addProperty("toggled", m.isEnabled());

            for (final Value<?> value : m.getValues()) {
                final String name = value.getName();
                Object valueProperty = null;

                switch (value.getClass().getSimpleName()) {
                    case "KeyValue":
                        valueProperty = ((KeyValue) value).getValue();
                        break;
                    case "BooleanValue":
                        valueProperty = ((BooleanValue) value).getValue();
                        break;
                    case "EnumValue":
                        valueProperty = ((EnumValue) value).getFormattedValue();
                        break;
                    case "NumberValue":
                        valueProperty = ((NumberValue) value).getValue().doubleValue();
                        break;
                    case "StringValue":
                        valueProperty = ((StringValue) value).getValue();
                        break;
                    case "ColorValue":
                        final Color color = ((ColorValue) value).getValue();
                        valueProperty = color.getRed() + "_" + color.getGreen() + "_" + color.getBlue() + "_" + color.getAlpha();
                        break;
                }

                if (valueProperty != null) {
                    properties.addProperty(name, valueProperty.toString());
                }
            }

            json.add(m.getName(), properties);
        }

        try {
            final FileWriter writer = new FileWriter(config);
            writer.write(json.toString());
            writer.close();
            ChatUtil.addChatMessage("Config was successfully saved");
        } catch (IOException e) {
            ChatUtil.addErrorMessage("Could not save config");
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            if (!config.exists()) {
                ChatUtil.addErrorMessage("This config does not exist");
                return;
            }

            final BufferedReader reader = new BufferedReader(new FileReader(config));
            final JsonObject json = (JsonObject) parser.parse(reader);

            reader.close();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                final Module m = Azide.getSingleton().getModuleManager().getModule(entry.getKey());
                if (m != null) {
                    final JsonObject properties = (JsonObject) entry.getValue();
                    boolean toggled = properties.get("toggled").getAsBoolean();
                    m.setEnabled(toggled);

                    for (final Value<?> value : m.getValues()) {
                        if (value instanceof KeyValue) {
                            ((KeyValue) value).setValue(properties.get(value.getName()).getAsInt());
                        } else if (value instanceof BooleanValue) {
                            ((BooleanValue) value).setValue(properties.get(value.getName()).getAsBoolean());
                        } else if (value instanceof EnumValue) {
                            final EnumValue enumValue = (EnumValue) value;
                            enumValue.setValue(Enum.valueOf(enumValue.getValue().getClass(), properties.get(value.getName()).getAsString()));
                        } else if (value instanceof NumberValue) {
                            ((NumberValue) value).setValue(properties.get(value.getName()).getAsDouble());
                        } else if (value instanceof StringValue) {
                            ((StringValue) value).setValue(properties.get(value.getName()).getAsString());
                        } else if (value instanceof ColorValue) {
                            final String[] colors = properties.get(value.getName()).getAsString().split("_");
                            ((ColorValue) value).setValue(new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]), Integer.parseInt(colors[3])));
                        }
                    }
                }
            }
            ChatUtil.addChatMessage("Config was successfully loaded");
        } catch (IOException e) {
            ChatUtil.addErrorMessage("Could not load config");
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        if (!config.exists()) {
            ChatUtil.addErrorMessage("This config does not exist");
            return;
        }
        config.delete();
        ChatUtil.addChatMessage("Config was successfully deleted");
    }

    public void makeDir(final File directory) {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void list() {
        if (!directory.exists()) {
            ChatUtil.addChatMessage("Config directory does not exist");
            return;
        }

        if (directory.listFiles() == null || Objects.requireNonNull(directory.listFiles()).length < 1) {
            ChatUtil.addChatMessage("No configs were found");
        } else {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                ChatUtil.addChatMessage(file.getName().replace(".json", ""));
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public void initialize() {
        makeDir(Azide.getDirectory());
    }
}