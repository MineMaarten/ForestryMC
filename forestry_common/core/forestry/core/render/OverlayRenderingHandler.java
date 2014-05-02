/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.core.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class OverlayRenderingHandler implements ISimpleBlockRenderingHandler {

	protected static final double OVERLAY_SHIFT = 0.001;

	protected int determineMixedBrightness(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, int mixedBrightness) {
		return renderer.renderMinY > 0.0D ? mixedBrightness : block.getMixedBrightnessForBlock(world, x, y, z);
	}

	protected void renderBottomFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness,
			float r, float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x, y - 1, z, 0))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y - 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceYNeg(block, x, y - OVERLAY_SHIFT, z, textureIndex);

	}

	protected void renderTopFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness, float r,
			float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x, y + 1, z, 1))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y + 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceYPos(block, x, y + OVERLAY_SHIFT, z, textureIndex);

	}

	protected void renderEastFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness, float r,
			float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x, y, z - 1, 2))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z - 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceZNeg(block, x, y, z - OVERLAY_SHIFT, textureIndex);

	}

	protected void renderWestFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness, float r,
			float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x, y, z + 1, 3))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z + 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceZPos(block, x, y, z + OVERLAY_SHIFT, textureIndex);

	}

	protected void renderNorthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness,
			float r, float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x - 1, y, z, 4))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x - 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceXNeg(block, x - OVERLAY_SHIFT, y, z, textureIndex);

	}

	protected void renderSouthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, IIcon textureIndex, int mixedBrightness,
			float r, float g, float b) {

		if (!renderer.renderAllFaces && !block.shouldSideBeRendered(world, x + 1, y, z, 5))
			return;

		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x + 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceXPos(block, x + OVERLAY_SHIFT, y, z, textureIndex);

	}

}