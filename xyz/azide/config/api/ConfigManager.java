package xyz.azide.config.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.azide.Azide;
import xyz.azide.module.Module;
import xyz.azide.trait.Json;
import xyz.azide.util.game.ChatUtil;
import xyz.azide.value.Value;
import xyz.azide.value.impl.*;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.Objects;

public final class ConfigManager implements Json {
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
        this.config = new File(directory, name + ".json");

        try {
            JsonObject json = new JsonObject();
            for (Module m : Azide.getSingleton().getModuleManager().getModules()) {
                JsonObject properties = new JsonObject();
                properties.addProperty("toggled", m.isEnabled());
                for (Value<?> value : m.getValues()) {
                    final String name = value.getName();
                    if (value instanceof KeyValue) {
                        properties.addProperty(name, ((KeyValue) value).getValue());
                    } else if (value instanceof BooleanValue) {
                        properties.addProperty(name, ((BooleanValue) value).getValue());
                    } else if (value instanceof EnumValue) {
                        properties.addProperty(name, ((EnumValue) value).getValue().toString());
                    } else if (value instanceof NumberValue) {
                        properties.addProperty(name, ((NumberValue) value).getValue().doubleValue());
                    } else if (value instanceof StringValue) {
                        properties.addProperty(name, ((StringValue) value).getValue());
                    } else if (value instanceof ColorValue) {
                        final Color color = ((ColorValue) value).getValue();
                        properties.addProperty(name, color.getRed() + "_" + color.getGreen() + "_" + color.getBlue() + color.getAlpha());
                    }
                }
                json.add(m.getName(), properties);
            }
            PrintWriter printer = new PrintWriter(new FileWriter(config));
            printer.println(gson.toJson(json));
            printer.close();
            ChatUtil.addChatMessage("Config was successfully saved");
        } catch (IOException e) {
            ChatUtil.addErrorMessage("Could not save config");
            throw new RuntimeException(e);
        }
    }

    public void load() {
        this.config = new File(directory, name + ".json");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(config));
            JsonObject json = (JsonObject) parser.parse(reader);
            reader.close();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                Module m = Azide.getSingleton().getModuleManager().getModule(entry.getKey());
                if (m != null) {
                    JsonObject properties = (JsonObject) entry.getValue();
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
        this.config = new File(directory, name + ".json");

        if (!config.exists()) {
            ChatUtil.addErrorMessage("This config does not exist");
            return;
        }
        config.delete();
        ChatUtil.addChatMessage("Config was successfully deleted");
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
}