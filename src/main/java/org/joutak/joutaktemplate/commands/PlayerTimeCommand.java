package org.joutak.joutaktemplate.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.joutak.joutaktemplate.utils.MessageUtils.createTextMessage;
import static org.joutak.joutaktemplate.utils.TimeUtils.getTimeInfo;

public class PlayerTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length > 0) {
                sender.sendMessage(createTextMessage("Ошибка! Команда используется без аргументов", NamedTextColor.RED));
                sender.sendMessage(createTextMessage("Используйте: /playertime", NamedTextColor.YELLOW));
                return true;
            }

            Player player = (Player) sender;
            sender.sendMessage(getTimeInfo(player));
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            if (args.length != 1) {
                sender.sendMessage(createTextMessage("Использование: /playertime <ник_игрока>", NamedTextColor.RED));
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(createTextMessage("Ошибка! Игрок " + args[0] + " не найден или не в сети.", NamedTextColor.RED));
                return true;
            }

            sender.sendMessage(getTimeInfo(target));
            return true;
        }

        return false;
    }

}