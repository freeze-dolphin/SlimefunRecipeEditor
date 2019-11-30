package me.Freeze_Dolphin.sfre.api;

import me.Freeze_Dolphin.sfre.Main;
import me.Freeze_Dolphin.sfre.U;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class C {

	public static ItemStack str2item(String configString) {
		ItemStack rt = null;
		
		if (configString.equalsIgnoreCase("null")) return null;
		
		try {

			String[] split = configString.split("\\{");
			String type = split[0];
			String include = split[1].split("\\}")[0];

			if (type.equalsIgnoreCase("MC")) {	// 判断是粘液科技物品还是原版物品

				rt = new ItemStack(Material.getMaterial(include));

			} else if (type.equalsIgnoreCase("SF")) {

				rt = SlimefunItem.getItem(include);

			} else {
				rt = errorItem();
			}

		} catch (Exception ex) {
			rt = errorItem();
			Main.debug("item exception");
		}

		return rt;
	}

	public static RecipeType str2rt(String configString) {
		RecipeType rt = null;

		try {

			String[] split = configString.split("\\{");
			String type = split[0];
			String include = split[1].split("\\}")[0];

			if (type.equalsIgnoreCase("RT")) {
				switch (include) {
				case "ANCIENT_ALTAR": 
					rt = RecipeType.ANCIENT_ALTAR;
					break;
				case "ARMOR_FORGE":
					rt = RecipeType.ARMOR_FORGE;
					break;
				case "COMPRESSOR":
					rt = RecipeType.COMPRESSOR;
					break;
				case "ENHANCED_CRAFTING_TABLE":
					rt = RecipeType.ENHANCED_CRAFTING_TABLE;
					break;
				case "FURNACE":
					rt = RecipeType.FURNACE;
					break;
				case "GOLD_PAN":
					rt = RecipeType.GOLD_PAN;
					break;
				case "GRIND_STONE":
					rt = RecipeType.GRIND_STONE;
					break;
				case "HEATED_PRESSURE_CHAMBER":
					rt = RecipeType.HEATED_PRESSURE_CHAMBER;
					break;
				case "JUICER":
					rt = RecipeType.JUICER;
					break;
				case "MAGIC_WORKBENCH":
					rt = RecipeType.MAGIC_WORKBENCH;
					break;
				case "MOB_DROP":
					rt = RecipeType.MOB_DROP;
					break;
				case "MULTIBLOCK":
					rt = RecipeType.MULTIBLOCK;
					break;
				case "NULL":
					rt = RecipeType.NULL;
					break;
				case "ORE_CRUSHER":
					rt = RecipeType.ORE_CRUSHER;
					break;
				case "ORE_WASHER":
					rt = RecipeType.ORE_WASHER;
					break;
				case "OVEN":
					rt = RecipeType.OVEN;
					break;
				case "PRESSURE_CHAMBER":
					rt = RecipeType.PRESSURE_CHAMBER;
					break;
				case "SHAPED_RECIPE":
					rt = RecipeType.SHAPED_RECIPE;
					break;
				case "SHAPELESS_RECIPE":
					rt = RecipeType.SHAPELESS_RECIPE;
					break;
				case "SMELTERY":
					rt = RecipeType.SMELTERY;
					break;
				default:
					rt = errorRecipe();
					break;
				}
			} else {
				U.scolor("&c出现错误! 在该写&6合成方式&c的位置出现了奇怪的东西! 请修改配置文件!");
			}

		} catch (Exception ex) {
			rt = errorRecipe();
			Main.debug("recipetype exception");
		}

		return rt;
	}


	private static final ItemStack errorItem() {
		U.scolor("&c出现错误! 读取到一个未知的&6物品&c, 请检查配置文件是否编写正确!");
		return new CustomItem(Material.COBBLESTONE, "&c&l错误!", 0, new String[] { "", "&f出现了一个错误, 请联系管理员检查服务器配置文件!" });
	}

	private static final RecipeType errorRecipe() {
		U.scolor("&c出现错误! 读取到一个未知的&6合成方式&c, 请检查配置文件是否编写正确!");
		return new RecipeType(new CustomItem(Material.COBBLESTONE, "&c&l错误!", 0, new String[] { "", "&f出现了一个错误, 请联系管理员检查服务器配置文件!" }));
	}

}
