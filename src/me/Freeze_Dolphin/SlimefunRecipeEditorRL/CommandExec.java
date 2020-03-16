package me.Freeze_Dolphin.SlimefunRecipeEditorRL;

import java.io.IOException;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuCloseHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuOpeningHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CommandExec implements CommandExecutor {

	private static String[] cmdInfo = new String[] 
			{ "SlimefunRecipeEditor v1.0.0", "made by Freeze_Dolphin", "", "/sfre 为手中物品配置合成", "/sfre reload 重载配置" };

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

		if (!cmd.getName().equalsIgnoreCase("sfre")) return true;
		
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(Main.cfg.getString("messages.only-player"));
			return true;
		} else if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("slimefunrecipeeditor.use")) {
					ItemStack i = p.getInventory().getItemInMainHand();
					if (i.getType().equals(Material.AIR) || i == null) {
						p.sendMessage(Main.cfg.getString("messages.air"));
					} else {
						if (SlimefunItem.getByItem(i) != null) {
							// TODO open config gui for hand item

							openSettingMenu(p, SlimefunItem.getByItem(i));

						} else {
							p.sendMessage(Main.cfg.getString("messages.not-slimefun"));
						}
					}
				} else {
					p.sendMessage(Main.cfg.getString("messages.no-permissions"));
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (p.hasPermission("slimefunrecipeeditor.reload")) {
					try {
						Main.plug.reloadConfig();
						p.sendMessage(Main.cfg.getString("messages.reload-successfully"));
					} catch (Exception ex) {
						p.sendMessage(Main.cfg.getString("messages.reload-wrongly"));
					}
				} else {
					p.sendMessage(Main.cfg.getString("messages.no-permissions"));
				}
			} else {
				if (p.hasPermission("slimefunrecipeeditor.use")) {
					for (String s : cmdInfo) {
						p.sendMessage(s);
					}
				} else {
					p.sendMessage(Main.cfg.getString("messages.no-permissions"));
				}
			}
		}

		return true;

	}

	private static int[] border = 
		{
		0, 1, 2,
		6, 7, 8,
		9, 10, 11,
		15, 16, 17, 
		18, 19, 20, 
		24, 25, 26
		};

	private static int[] newRecipe = 
		{
		3, 4, 5,
		12, 13, 14,
		21, 22, 23
		};

	@SuppressWarnings("deprecation")
	private static void openSettingMenu(Player p, SlimefunItem sfi) {
		ChestMenu cm = new ChestMenu(Main.cfg.getString("gui.title").replaceAll("\\{id\\}", sfi.getID()));

		cm.setEmptySlotsClickable(true);
		cm.setPlayerInventoryClickable(true);
		
		for (int i : border) {
			cm.addItem(i, new CustomItem(new MaterialData(org.bukkit.Material.STAINED_GLASS_PANE, (byte) 7), ""));
			cm.addMenuClickHandler(i, new MenuClickHandler() {

				@Override
				public boolean onClick(Player arg0, int arg1, ItemStack arg2,
						ClickAction arg3) {
					return false;
				}});
		}
		
		cm.replaceExistingItem(10, sfi.getRecipeType().toItem());
		cm.replaceExistingItem(19, sfi.getItem());

		cm.replaceExistingItem(16, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 5), "&a保存并关闭"));
		cm.addMenuClickHandler(16, new MenuClickHandler() {
			
			@Override
			public boolean onClick(Player arg0, int arg1, ItemStack arg2,
					ClickAction arg3) {
				Main.closeInv.put(arg0.getUniqueId(), true);
				p.closeInventory();
				return false;
			}
		});
		
		cm.replaceExistingItem(25, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 14), "&c取消"));
		cm.addMenuClickHandler(25, new MenuClickHandler() {

			@Override
			public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
				p.closeInventory();
				return true;
			}
		});

		cm.addMenuOpeningHandler(new MenuOpeningHandler() {

			@Override
			public void onOpen(Player arg0) {
				int n = 0;
				for (int i : newRecipe) {
					cm.replaceExistingItem(i, sfi.getRecipe()[n]);
					n++;
				}
			}
		});

		cm.addMenuCloseHandler(new MenuCloseHandler() {

			@Override
			public void onClose(Player arg0) {
				
				if (!Main.closeInv.containsKey(arg0.getUniqueId())) {
					return;
				}
				
				Main.closeInv.remove(arg0.getUniqueId());
				Main.debug(p.getName() + " is modifing the recipe table for " + sfi.getID());
				
				int nullN = 0;
				int rl = 0;
				ItemStack[] r = new ItemStack[] { null, null, null, null, null, null, null, null, null };
				for (int i : newRecipe) {

					if (cm.getItemInSlot(i) == null) {
						nullN++;
					}

					if (nullN >= 9) {
						p.sendMessage(Main.cfg.getString("gui.all-null"));
						return;
					}
					
					r[rl] = cm.getItemInSlot(i);
					rl++;
				}
				
				sfi.setRecipe(r);
				
				int a = 0;
				for (ItemStack ai : r) {
					Main.recipes.set(sfi.getID() + "." + a, ai);
					a++;
				}
				
				try {
					Main.recipes.save(Main.recipesFile);
				} catch (IOException e) {
					p.sendMessage(Main.cfg.getString("io.error-when-saving"));
					e.printStackTrace();
					Main.plug.getLogger().severe("保存配置文件 '" + Main.recipesFile.getPath() + "' 时出错");
				}
			}});
		
		cm.open(p);
		
	}

}
