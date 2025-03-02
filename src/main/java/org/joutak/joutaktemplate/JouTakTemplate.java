package org.joutak.joutaktemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;
import org.joutak.joutaktemplate.commands.PlayerTimeCommand;
import org.joutak.joutaktemplate.events.PlayerJoinListener;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Getter
public final class JouTakTemplate extends JavaPlugin {

    @Getter
    private static JouTakTemplate instance;

    // Хранение времени входа игроков
    private final HashMap<UUID, LocalDateTime> playerJoinTimes = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("playertime").setExecutor(new PlayerTimeCommand());
        log.info("JouTakTemplate плагин запущен!");
    }

    @Override
    public void onDisable() {
        log.info("JouTakTemplate плагин отключен.");
    }

}
