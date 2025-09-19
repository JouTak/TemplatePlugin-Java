package ru.joutak.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;


@Slf4j
public final class EmptyPlugin extends JavaPlugin {

    @Getter
    private static EmptyPlugin instance;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
