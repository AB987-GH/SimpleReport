package dev.ab.simplereport;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.ab.simplereport.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class ReportCmd implements CommandExecutor {
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("This command is only for player use!");
            return false;
        }

        Player player = (Player) sender;

        if (!(player.hasPermission("cmd.report.use"))){
            player.sendMessage(Chat.format("&cYou do not have permission to use this command!"));
        }

        if (args.length < 2){
            player.sendMessage(Chat.format("&cUsage: /report <player> <reason>"));
            return false;
        }

        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            long timeLeft = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
            player.sendMessage(Chat.format("&cYou have used this command recently, please wait " + TimeUnit.MILLISECONDS.toSeconds(timeLeft) + "&c seconds."));
            return false;
        } else {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
                Player target = Bukkit.getPlayer(args[0]);
                String reportReason = StringUtils.join(args, " ", 1, args.length);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("cmd.report.alert")) {
                        p.sendMessage(Chat.format("&4[R] &c" + player.getName() + " &7reported &c" + target.getName() + "&7 for &c" + reportReason));
                    }
                }
                    player.sendMessage(Chat.format("&aYour report against " + target.getName() + " has been logged for staff review. It will be addressed as soon as possible."));
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 30000);

                } else {
                player.sendMessage(Chat.format("&cThat player does not exist or is not online!"));
            }

            }





        return false;
    }
}
