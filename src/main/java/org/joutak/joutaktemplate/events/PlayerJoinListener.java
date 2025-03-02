package org.joutak.joutaktemplate.events;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.joutak.joutaktemplate.JouTakTemplate;
import org.joutak.joutaktemplate.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        LocalDateTime joinTime = LocalDateTime.now();
        JouTakTemplate.getInstance().getPlayerJoinTimes().put(playerUUID, joinTime);

        String formattedTime = TimeUtils.formatTime(joinTime);

        log.info("Игрок {} зашел на сервер в {}", event.getPlayer().getName(), formattedTime);

        event.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, event.getPlayer().getLocation(), 30, 0.5, 1, 0.5);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

        ItemStack reward = new ItemStack(Material.GOLDEN_APPLE, 1);
        event.getPlayer().getInventory().addItem(reward);
        event.getPlayer().sendMessage("§6Вы получили бонус за вход: §e1 Золотое яблоко!");
        event.getPlayer().sendMessage("§aВы зашли на сервер в §e" + formattedTime);
    }
}
