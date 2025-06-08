package org.joutak.joutaktemplate.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.joutak.joutaktemplate.data.PlayerPass;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GivePassCommand implements CommandExecutor {
    private final JavaPlugin javaPlugin;
    private final File jsonFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public GivePassCommand(JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
        this.jsonFile = new File(javaPlugin.getDataFolder(), "passes.json");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length != 1){
            sender.sendMessage(ChatColor.RED + "Использование команды /givepass <количество дней>");
            return false;
        }

        int days;
        try
        {
           days = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            sender.sendMessage(ChatColor.RED + "Введите целое число ");
            return false;
        }

        if(!jsonFile.exists()){
            sender.sendMessage(ChatColor.RED+"Файл passes.json не найден");
            return false;
        }

        try(Reader reader = new FileReader(jsonFile)) {
            Type listType = new TypeToken<List<PlayerPass>>() {
            }.getType();
            List<PlayerPass> passes = gson.fromJson(reader, listType);

            LocalDate now = LocalDate.now();

            for (PlayerPass pass : passes) {
                LocalDate passUntil = LocalDate.parse(pass.getPassValidUntil(), formatter);
                LocalDate newValidUntil = passUntil.isAfter(now) ? passUntil.plusDays(days) : now.plusDays(days);

                pass.setPassValidUntil(newValidUntil.format(formatter));
                pass.setLastPaymentDate(now.format(formatter));
            }

            try (Writer writer = new FileWriter(jsonFile)) {
                gson.toJson(passes, writer);
            }

            sender.sendMessage(ChatColor.GREEN + "Проходки продлены на " + days + " дня(ей)");
            return true;
        }
        catch (IOException | DateTimeParseException e){
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Ошибка при обработке файла.");
            return false;
        }
    }
}
