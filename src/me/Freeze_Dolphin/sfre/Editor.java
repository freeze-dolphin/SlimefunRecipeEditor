package me.Freeze_Dolphin.sfre;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.Freeze_Dolphin.sfre.api.C;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Editor {

	public static List<List<String>> recipes = new ArrayList<>();

	public static void main() {

		for (String s : Main.cfg.getStringList("recipes")) {

			File recipe_file = new File(Main.plug.getDataFolder(), s);
			FileConfiguration rcfg = YamlConfiguration.loadConfiguration(recipe_file);

			for (String key : rcfg.getKeys(false)) {
				recipes.add(rcfg.getStringList(key));
			}

			Main.debug(recipes.toString());

			for (List<String> ls : recipes) {
				
				String[] s1 = ls.get(1).split("\\|");
				String[] s2 = ls.get(2).split("\\|");
				String[] s3 = ls.get(3).split("\\|");
				
				Main.debug(s1[0] + s1[1] + s1[2] + s2[0] + s2[1] + s2[2] + s3[0] + s3[1] + s3[2]);
				
				ItemStack[] itemRecipe = new ItemStack[] 
						{
						C.str2item(s1[0]), C.str2item(s1[1]), C.str2item(s1[2]),
						C.str2item(s2[0]), C.str2item(s2[1]), C.str2item(s2[2]),
						C.str2item(s3[0]), C.str2item(s3[1]), C.str2item(s3[2])
						};
				SlimefunItem.getByID(ls.get(0)).setRecipe(itemRecipe);
				SlimefunItem.getByID(ls.get(0)).setRecipeType(C.str2rt(ls.get(4)));

			}

		}

	}

}
