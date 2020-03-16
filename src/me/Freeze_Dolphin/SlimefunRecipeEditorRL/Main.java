package me.Freeze_Dolphin.SlimefunRecipeEditorRL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	public static Plugin plug;
	public static FileConfiguration cfg;

	public static Map<UUID, Boolean> closeInv = new HashMap<>();

	public static File recipesFile;
	public static YamlConfiguration recipes;

	@Override
	public void onEnable() {
		plug = this;
		cfg = this.getConfig();

		recipesFile = new File(plug.getDataFolder(), "recipes.yml");
		if (!recipesFile.exists()) {
			recipesFile.getParentFile().mkdirs();
			try {
				recipesFile.createNewFile();
			} catch (IOException ex) { 
				ex.printStackTrace(); 
				Main.plug.getLogger().severe("加载配置文件 '" + recipesFile.getPath() + "' 时出错");
			}
		}
		recipes = YamlConfiguration.loadConfiguration(recipesFile);

		new ConfigManager();

		this.getCommand("sfre").setExecutor(new CommandExec());

		for (String key : recipes.getKeys(false)) {
			ItemStack[] r = new ItemStack[] {null, null, null, null, null, null, null, null, null}; 
			int a = 0;
			while (a < 9) {
				if (recipes.getItemStack(key + "." + a) == null) {
					a++;
					continue;
				}
				r[a] = recipes.getItemStack(key + "." + a);
				a++;
			}
			debug(r.toString());
			SlimefunItem.getByID(key).setRecipe(r);
		}

	}

	public static void debug(String msg) {
		if (cfg.getBoolean("debug")) {
			plug.getLogger().info(msg);
		}
	}

}
