package me.Freeze_Dolphin.sfre;

import java.util.Random;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class U {

	public static final String prefix = "&e[&6SlimefunRecipeEditor&e]";

	public static final String cprefix = color("&e[&6SlimefunRecipeEditor&e]");

	public static final String prefixs = "&e[&6SlimefunRecipeEditor&e] ";

	public static final String cprefixs = color("&e[&6SlimefunRecipeEditor&e] ");

	public static ItemStack mat(Material material) {
		return new ItemStack(material);
	}
	
	public static ItemStack sfi(String id) {
		return SlimefunItem.getItem(id);
	}

	public static boolean chance(int integer, int chance) {
		return (CSCoreLib.randomizer().nextInt(integer) < chance);
	}
	
	public static int lrandom(int integer, int chance, int highest) {
		int rt = 0;
		if (chance(integer, chance)) { rt = random(highest, 0); }
		return rt;
	}
	
	public static int random(int highest, int lowest) {
		return new Random().nextInt(highest - lowest + 1) + lowest;
	}

	public static ItemStack[] midr(ItemStack item) {
		return new ItemStack[] { null, null, null, null, item, null, null, null, null };
	}

	public static ItemStack[] allr(ItemStack item) {
		return new ItemStack[] { item, item, item, item, item, item, item, item, item };
	}
	
	public static ItemStack[] halfr2(ItemStack item1, ItemStack item2) {
		return new ItemStack[] { item1, item2, item1, item2, item1, item2, item1, item2, item1 };
	}
	
	public static ItemStack[] halfr3(ItemStack item1, ItemStack item2, ItemStack item3) {
		return new ItemStack[] { item1, item2, item1, item2, item3, item2, item1, item2, item1 };
	}
	
	public static ItemStack[] firsts(ItemStack item) {
		return new ItemStack[] { item, null, null, null, null, null, null, null, null };
	}
	
	public static void scolor(String msg) {
		Main.plug.getServer().getConsoleSender().sendMessage(cprefixs + color(msg));
	}

	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
