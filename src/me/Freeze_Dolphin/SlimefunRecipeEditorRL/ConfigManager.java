package me.Freeze_Dolphin.SlimefunRecipeEditorRL;

import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private final File cfile = new File(Main.plug.getDataFolder(), "config.yml");

	public static YamlConfiguration cfg;

	public ConfigManager() {
		if (!cfile.exists()) {
			cfile.getParentFile().mkdirs();

			try {
				cfile.createNewFile();
			} catch (IOException ex) { 
				ex.printStackTrace(); 
				Main.plug.getLogger().severe("加载配置文件 '" + cfile.getPath() + "' 时出错");
			}

		}

		cfg = YamlConfiguration.loadConfiguration(cfile);

		if (cfg.getInt("version") != 100) {
			setConfig("debug", false);
			setConfig("messages.no-permissions", ChatColor.translateAlternateColorCodes('&', "&c你不能使用这个!"));
			setConfig("messages.reload-successfully", ChatColor.translateAlternateColorCodes('&', "&a重载成功!"));
			setConfig("messages.reload-wrongly", ChatColor.translateAlternateColorCodes('&', "&c重载失败! 请查看控制台以获取更多信息"));
			setConfig("messages.only-player", ChatColor.translateAlternateColorCodes('&', "&c仅玩家可使用这个!"));
			setConfig("messages.air", ChatColor.translateAlternateColorCodes('&', "&c手上物品不可为空气!"));
			setConfig("messages.not-slimefun", ChatColor.translateAlternateColorCodes('&', "&c手上必须拿着Slimefun物品!"));
			setConfig("gui.title", ChatColor.translateAlternateColorCodes('&', "&8合成表设定: &c{id}"));
			setConfig("gui.all-null", ChatColor.translateAlternateColorCodes('&', "&c合成表不可全空"));
			setConfig("io.error-when-saving", ChatColor.translateAlternateColorCodes('&', "&c保存合成表时发生错误, 请查看控制台以获取更多信息"));
			try {
				cfg.save(cfile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void setConfig(String key, Object defaultValue) {

		if (cfg.contains(key)) {
			return;
		}

		cfg.set(key, defaultValue);
	}

}