package org.joutak.joutaktemplate.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.joutak.joutaktemplate.JouTakTemplate;
import org.joutak.joutaktemplate.utils.TimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length > 0) {
                sender.sendMessage("§cОшибка! Команда используется без аргументов");
                sender.sendMessage("§eИспользуйте: /playertime");
                return true;
            }

            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            LocalDateTime joinTime = JouTakTemplate.getInstance().getPlayerJoinTimes().get(playerUUID);

            if (joinTime == null) {
                player.sendMessage("§cНет данных о вашем входе.");
                return true;
            }

            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(joinTime, currentTime);
            long minutes = duration.toMinutes();
            long seconds = duration.toSecondsPart();

            player.sendMessage("§aТекущее серверное время: §e" + TimeUtils.formatTime(currentTime));
            player.sendMessage("§aВаше время входа: §e" + TimeUtils.formatTime(joinTime));
            player.sendMessage("§aВы на сервере уже §e" + minutes + " мин " + seconds + " сек");

            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            if (args.length != 1) {
                sender.sendMessage("§cИспользование: /playertime <ник_игрока>");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cОшибка! Игрок " + args[0] + " не найден или не в сети.");
                return true;
            }

            UUID playerUUID = target.getUniqueId();
            LocalDateTime joinTime = JouTakTemplate.getInstance().getPlayerJoinTimes().get(playerUUID);

            if (joinTime == null) {
                sender.sendMessage("§cНет данных о входе игрока " + target.getName());
                return true;
            }

            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(joinTime, currentTime);
            long minutes = duration.toMinutes();
            long seconds = duration.toSecondsPart();

            sender.sendMessage("§aТекущее серверное время: §e" + TimeUtils.formatTime(currentTime));
            sender.sendMessage("§aВремя входа игрока " + target.getName() + ": §e" + TimeUtils.formatTime(joinTime));
            sender.sendMessage("§aРазница с текущим временем: §e" + minutes + " мин " + seconds + " сек");

            return true;
        }

        return false;
    }
}
