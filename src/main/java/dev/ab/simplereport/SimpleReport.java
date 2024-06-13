package dev.ab.simplereport;

import org.bukkit.plugin.java.JavaPlugin;
import dev.ab.*;

public final class SimpleReport extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("report").setExecutor(new ReportCmd());
        System.out.println("Enabled SimpleReport v1.0! Developed by AB987");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        System.out.println("Disabled SimpleReport v1.0!");
        // Plugin shutdown logic
    }
}
