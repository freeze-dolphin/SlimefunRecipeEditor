package me.Freeze_Dolphin.sfre;

import java.io.File;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	public static Plugin plug;
	public static FileConfiguration cfg;
	
	@Override
	public void onEnable() {

		plug = this;
		cfg = plug.getConfig();
		
		File datad = this.getDataFolder();
		String datadp = datad.getPath();

		if (!(new File(datadp + "/config.yml").exists())) {
			U.scolor("&c&l配置文件未找到, 正在加载默认配置文件...");
			this.saveDefaultConfig();
		}
		
		if (!(new File(datadp + "/recipes.yml").exists())) {
			this.saveResource("recipes.yml", true);
		}
		
		if (!(new File(datadp + "/material.enum").exists())) {
			this.saveResource("material.enum", true);
		}
		
		if (cfg.getBoolean("enable")) {
			try {
				Editor.main();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	public static void debug(String msg) {
		if (Main.plug.getConfig().getBoolean("debug")) {
			plug.getServer().getConsoleSender().sendMessage("\n" + ChatColor.translateAlternateColorCodes('&', "&c&l[&4&lDEBUG&c&l]&c: &r" + msg));
		}
	}
	
}
