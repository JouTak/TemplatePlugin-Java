package org.joutak.joutaktemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;


@Slf4j
public final class JouTakTemplate extends JavaPlugin {

    @Getter
    private static JouTakTemplate instance;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
