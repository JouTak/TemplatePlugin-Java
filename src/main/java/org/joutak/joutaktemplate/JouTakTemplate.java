package org.joutak.joutaktemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;
import org.joutak.joutaktemplate.commands.GivePassCommand;
import org.joutak.joutaktemplate.data.PlayerJoinListener;


@Slf4j
public final class JouTakTemplate extends JavaPlugin {

    @Getter
    private static JouTakTemplate instance;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        getCommand("givepass").setExecutor(new GivePassCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        log.info("Плагин JouTakTemplate успешно включен.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("Плагин JouTekTemplate отключён");
    }

}
