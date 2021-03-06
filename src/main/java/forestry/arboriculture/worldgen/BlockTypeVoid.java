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
package forestry.arboriculture.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.core.worldgen.BlockType;

public class BlockTypeVoid extends BlockType {

	public BlockTypeVoid() {
		super(Blocks.air, 0);
	}

	@Override
	public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		if (world.getTileEntity(x, y, z) != null)
			world.removeTileEntity(x, y, z);
	}

}
