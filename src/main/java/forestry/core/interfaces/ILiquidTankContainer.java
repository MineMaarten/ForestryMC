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
package forestry.core.interfaces;

import forestry.core.fluids.TankManager;
import forestry.core.fluids.tanks.StandardTank;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fluids.IFluidHandler;

public interface ILiquidTankContainer extends IFluidHandler {

	TankManager getTankManager();
	void getGUINetworkData(int messageId, int data);
	void sendGUINetworkData(Container container, ICrafting iCrafting);

}
