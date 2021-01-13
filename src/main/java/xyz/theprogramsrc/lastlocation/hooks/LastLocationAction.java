package xyz.theprogramsrc.lastlocation.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.theprogramsrc.lastlocation.LastLocation;
import xyz.theprogramsrc.superauth.api.actions.SuperAuthAction;

import java.io.File;
import java.io.IOException;

public class LastLocationAction implements SuperAuthAction, Listener {

    private final LastLocation main;
    private final FileConfiguration config;

    public LastLocationAction(LastLocation main){
        this.main = main;
        this.config = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "config.yml"));
    }

    private void saveCfg(){
        try{
            this.config.save(new File(this.main.getDataFolder(), "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getPrefix() {
        return "lastlocation";
    }

    @Override
    public void onExecute(Player player, String argument, boolean before, boolean register){
        if(argument.equalsIgnoreCase("register")){
            this.config.set(player.getUniqueId().toString(), this.str(player.getLocation()));
            this.saveCfg();
        }else if (argument.equalsIgnoreCase("back")){
            if(this.config.contains(player.getUniqueId().toString())){
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, () -> {
                    Location loc = this.loc(this.config.getString(player.getUniqueId().toString()));
                    if(loc != null){
                        player.teleport(loc);
                        this.config.set(player.getUniqueId().toString(), null);
                        this.saveCfg();
                    }
                });
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        this.config.set(e.getPlayer().getUniqueId().toString(), null);
        this.saveCfg();
    }

    private String str(Location loc){
        return String.format("%s;%s;%s;%s;%s;%S", loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    private Location loc(String str){
        if(str == null) return null;
        String[] s = str.split(";");
        if(s.length < 6){
            return null;
        }else{
            return new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
        }
    }
}
