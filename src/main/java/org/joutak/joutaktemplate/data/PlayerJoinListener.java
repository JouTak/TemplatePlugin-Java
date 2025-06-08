package org.joutak.joutaktemplate.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private final File jsonFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PlayerJoinListener(JavaPlugin plugin) {
        this.jsonFile = new File(plugin.getDataFolder(), "passes.json");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String nickname = event.getPlayer().getName();

        List<PlayerPass> passes = new ArrayList<>();

        if (jsonFile.exists()) {
            try (Reader reader = new FileReader(jsonFile)) {
                Type listType = new TypeToken<List<PlayerPass>>() {}.getType();
                passes = gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean exists = passes.stream().anyMatch(p -> p.getNickname().equalsIgnoreCase(nickname));
        if (!exists) {
            LocalDate now = LocalDate.now();
            PlayerPass newPass = new PlayerPass();
            newPass.setNickname(nickname);
            newPass.setLastPaymentDate(now.format(formatter));
            newPass.setPassValidUntil(now.format(formatter));
            passes.add(newPass);

            // Сохранить обратно
            try (Writer writer = new FileWriter(jsonFile)) {
                gson.toJson(passes, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
