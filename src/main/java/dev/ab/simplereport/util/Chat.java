package dev.ab.simplereport.util;

import org.bukkit.ChatColor;

public class Chat {

    public static String format (String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
