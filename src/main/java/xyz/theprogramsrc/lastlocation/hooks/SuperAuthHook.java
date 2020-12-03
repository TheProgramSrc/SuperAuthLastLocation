package xyz.theprogramsrc.lastlocation.hooks;

import org.bukkit.Bukkit;
import xyz.theprogramsrc.lastlocation.LastLocation;
import xyz.theprogramsrc.superauth.spigot.SuperAuth;

public class SuperAuthHook {

    public SuperAuthHook(LastLocation main){
        LastLocationAction lastLocationAction = new LastLocationAction(main);
        Bukkit.getPluginManager().registerEvents(lastLocationAction, main);
        SuperAuth.registerAction(main, lastLocationAction);
    }
}
