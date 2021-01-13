package xyz.theprogramsrc.lastlocation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.theprogramsrc.lastlocation.hooks.SuperAuthHook;

import java.io.File;
import java.io.IOException;

public class LastLocation extends JavaPlugin {

    @Override
    public void onLoad() {
        if(Bukkit.getPluginManager().getPlugin("SuperAuth") == null){
            Bukkit.getConsoleSender().sendMessage(c("&cThis Plugin was designed as extension of &bSuperAuth &7https://songoda.com/marketplace/products/255"));
        }else{
            if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
            File config = new File(this.getDataFolder(), "config.yml");
            if(!config.exists()){
                try{
                    config.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("SuperAuth") != null){
            new SuperAuthHook(this);
        }
    }

    @Override
    public void onDisable() {

    }

    public static String c(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
