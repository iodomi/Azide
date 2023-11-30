package xyz.azide.trait;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public interface Json {
    JsonParser parser = new JsonParser();
    Gson gson = new Gson();
}