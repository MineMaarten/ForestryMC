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
package forestry.mail.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import forestry.core.network.PacketIds;
import forestry.core.proxy.Proxies;
import forestry.mail.POBoxInfo;
import forestry.mail.network.PacketPOBoxInfo;
import forestry.api.mail.IMailAddress;

public class ProxyMail {

	public void clearMailboxInfo() {
	}

	public void resetMailboxInfo() {
	}

	public void setPOBoxInfo(World world, IMailAddress address, POBoxInfo info) {
		EntityPlayer player = Proxies.common.getPlayer(world, address.getPlayerProfile());
		if (player != null)
			Proxies.net.sendToPlayer(new PacketPOBoxInfo(PacketIds.POBOX_INFO, info), player);
	}
}
