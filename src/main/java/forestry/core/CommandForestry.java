/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core;

import java.util.List;

import forestry.core.utils.StringUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;

import forestry.plugins.Plugin;
import forestry.core.config.Version;
import forestry.core.proxy.Proxies;
import forestry.core.utils.CommandMC;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.PluginManager;
import net.minecraft.util.StatCollector;

public class CommandForestry extends CommandMC {

	@Override
	public int compareTo(Object arg0) {
		return this.getCommandName().compareTo(((ICommand) arg0).getCommandName());
	}

	@Override
	public String getCommandName() {
		return "forestry";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {

		if (arguments.length <= 0)
			throw new WrongUsageException(StringUtil.localizeAndFormat("chat.help", this.getCommandUsage(sender)));

		if (arguments[0].matches("version")) {
			commandVersion(sender, arguments);
			return;
		} else if (arguments[0].matches("plugins")) {
			commandPlugins(sender, arguments);
			return;
		} else if (arguments[0].matches("help")) {
			sendChatMessage(sender, StringUtil.localizeAndFormat("chat.command.help.0", this.getCommandName()));
			sendChatMessage(sender, StringUtil.localize("chat.command.help.1"));
			sendChatMessage(sender, StringUtil.localize("chat.command.help.2"));
			sendChatMessage(sender, StringUtil.localize("chat.command.help.3"));
			sendChatMessage(sender, StringUtil.localize("chat.command.help.4"));
			return;
		}

		throw new WrongUsageException(this.getCommandUsage(sender));
	}

	private void commandVersion(ICommandSender sender, String[] arguments) {
		String colour = Version.isOutdated() ? "\u00A7c" : "\u00A7a";

		sendChatMessage(sender, String.format(colour + StringUtil.localize("chat.version"), Version.getVersion(),
				Proxies.common.getMinecraftVersion(), Version.getRecommendedVersion()));
		if (Version.isOutdated())
			for (String updateLine : Version.getChangelog())
				sendChatMessage(sender, "\u00A79" + updateLine);
	}

	private void commandPlugins(ICommandSender sender, String[] arguments) {

		if (arguments.length <= 1)
			listPluginsForSender(sender);
		else if (arguments[1].matches("info"))
			listPluginInfoForSender(sender, arguments);

	}

	private void listPluginsForSender(ICommandSender sender) {

		String pluginList = "";

		for (PluginManager.Module pluginModule : PluginManager.getLoadedModules()) {
			if (!pluginList.isEmpty())
				pluginList += ", ";
			pluginList += makeListEntry(pluginModule.instance());
		}

		sendChatMessage(sender, pluginList);
	}

	private void listPluginInfoForSender(ICommandSender sender, String[] arguments) {

		if (arguments.length < 3)
			throw new WrongUsageException("/" + getCommandName() + " plugins info <plugin-name>");

		ForestryPlugin found = null;
		for (PluginManager.Module pluginModule : PluginManager.getLoadedModules()) {
			Plugin info = pluginModule.instance().getClass().getAnnotation(Plugin.class);
			if (info == null)
				continue;

			if ((info.pluginID().equalsIgnoreCase(arguments[2]) || info.name().equalsIgnoreCase(arguments[2]))) {
				found = pluginModule.instance();
				break;
			}
		}

		if (found == null)
			throw new CommandException(StringUtil.localizeAndFormat("chat.plugins.error", arguments[2]));

		String entry = "\u00A7c";
		if (found.isAvailable())
			entry = "\u00A7a";
		Plugin info = found.getClass().getAnnotation(Plugin.class);
		if (info != null) {
			sendChatMessage(sender, entry + "Plugin: " + info.name());
			if (!info.version().isEmpty())
				sendChatMessage(sender, "\u00A79Version: " + info.version());
			if (!info.author().isEmpty())
				sendChatMessage(sender, "\u00A79Author(s): " + info.author());
			if (!info.url().isEmpty())
				sendChatMessage(sender, "\u00A79URL: " + info.url());
			if (!info.unlocalizedDescription().isEmpty())
				sendChatMessage(sender, StatCollector.translateToLocal(info.unlocalizedDescription()));
		}

	}

	private String makeListEntry(ForestryPlugin plugin) {
		String entry = "\u00A7c";
		if (plugin.isAvailable())
			entry = "\u00A7a";

		Plugin info = plugin.getClass().getAnnotation(Plugin.class);
		if (info != null) {
			entry += info.pluginID();
			if (!info.version().isEmpty())
				entry += " (" + info.version() + ")";
		} else
			entry += "???";

		return entry;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer)
			return Proxies.common.isOp((EntityPlayer) sender);
		else
			return sender.canCommandSenderUseCommand(4, getCommandName());
	}

}
