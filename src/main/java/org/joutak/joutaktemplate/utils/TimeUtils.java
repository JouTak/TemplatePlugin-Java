package org.joutak.joutaktemplate.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.joutak.joutaktemplate.JouTakTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.joutak.joutaktemplate.utils.MessageUtils.createTextMessage;

public class TimeUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatTime(LocalDateTime time) {
        return time.format(FORMATTER);
    }

    public static Component getTimeInfo(Player target) {
        UUID playerUUID = target.getUniqueId();
        LocalDateTime joinTime = JouTakTemplate.getInstance().getPlayerJoinTimes().get(playerUUID);

        if (joinTime == null) {
            return createTextMessage("Нет данных о входе игрока " + target.getName(), NamedTextColor.RED);
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(joinTime, currentTime);
        long minutes = duration.toMinutes();
        long seconds = duration.toSecondsPart();

        return formatTimeMessage(target.getName(), currentTime, joinTime, minutes, seconds);
    }

    private static Component formatTimeMessage(String playerName, LocalDateTime currentTime, LocalDateTime joinTime, long minutes, long seconds) {
        return createTextMessage("Текущее серверное время: ", NamedTextColor.GREEN)
                .append(createTextMessage(TimeUtils.formatTime(currentTime), NamedTextColor.YELLOW))
                .append(Component.newline())
                .append(createTextMessage("Время входа игрока " + playerName + ": ", NamedTextColor.GREEN))
                .append(createTextMessage(TimeUtils.formatTime(joinTime), NamedTextColor.YELLOW))
                .append(Component.newline())
                .append(createTextMessage("Разница с текущим временем: ", NamedTextColor.GREEN))
                .append(createTextMessage(minutes + " мин " + seconds + " сек", NamedTextColor.YELLOW));
    }
}
