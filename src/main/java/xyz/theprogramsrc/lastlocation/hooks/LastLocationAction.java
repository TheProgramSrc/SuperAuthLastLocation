package xyz.theprogramsrc.lastlocation.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.theprogramsrc.lastlocation.LastLocation;
import xyz.theprogramsrc.superauth.api.actions.SuperAuthAction;

import java.util.LinkedHashMap;
import java.util.UUID;

public class LastLocationAction implements SuperAuthAction, Listener {

    private final LastLocation main;
    private final LinkedHashMap<UUID, Location> locs;

    public LastLocationAction(LastLocation main){
        this.main = main;
        this.locs = new LinkedHashMap<>();
    }

    @Override
    public String getPrefix() {
        return "lastlocation";
    }

    @Override
    public void onExecute(Player player, String argument, boolean before, boolean register){
        if(argument.equalsIgnoreCase("register")){
            this.locs.put(player.getUniqueId(), player.getLocation());
        }else if (argument.equalsIgnoreCase("back")){
            if(this.locs.containsKey(player.getUniqueId())){
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, () -> player.teleport(this.locs.get(player.getUniqueId())));
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        this.locs.remove(e.getPlayer().getUniqueId());
    }
}
