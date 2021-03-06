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
package forestry.arboriculture.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.WoodType;
import forestry.core.items.ItemForestryBlock;

public class ItemWoodBlock extends ItemForestryBlock {

	public ItemWoodBlock(Block block) {
		super(block);
	}

	public static int getTypeFromMeta(int damage) {
		return damage & 3;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {

		if (this.getBlock() instanceof IWoodTyped) {
			IWoodTyped block = (IWoodTyped) getBlock();
			int meta = itemstack.getItemDamage();
			WoodType woodType = block.getWoodType(meta);
			if (woodType == null)
				return null;
			return "tile.for." + block.getBlockKind() + "." + woodType.ordinal();
		}
		return super.getUnlocalizedName(itemstack);
	}

}
