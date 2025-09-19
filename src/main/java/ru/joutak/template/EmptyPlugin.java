package ru.joutak.template;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmptyPlugin extends JavaPlugin {
    @Getter
    private static EmptyPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info(
                String.format("Плагин %s версии %s включен!", getPluginMeta().getName(), getPluginMeta().getVersion())
        );
    }

    @Override
    public void onDisable() {

    }
}
