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
package forestry.energy.gadgets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import forestry.core.TemperatureState;
import forestry.core.config.Defaults;
import forestry.core.gadgets.Engine;
import forestry.core.gadgets.TileBase;
import forestry.core.utils.DamageSourceForestry;

public class EngineClockwork extends Engine {

	private final static float WIND_EXHAUSTION = 0.05f;
	private final static float WIND_TENSION_BASE = 0.5f;
	private final static int WIND_DELAY = 10;
	
	private static final int ENGINE_CLOCKWORK_HEAT_MAX = 300000;
	private static final float ENGINE_CLOCKWORK_ENERGY_PER_CYCLE = 0.25f;
	private static final float ENGINE_CLOCKWORK_WIND_MAX = 8f;

	private static final DamageSourceForestry damageSourceEngineClockwork = new DamageSourceForestry("engine.clockwork");
	
	private float tension = 0.0f;
	private short delay = 0;
	
	public EngineClockwork() {
		super(ENGINE_CLOCKWORK_HEAT_MAX, 1000, 50);
	}
	
	@Override
	public void openGui(EntityPlayer player, TileBase tile) {
		
		if(!(player instanceof EntityPlayerMP))
			return;
		
		if(tension <= 0)
			tension = WIND_TENSION_BASE;
		else if(tension < ENGINE_CLOCKWORK_WIND_MAX + WIND_TENSION_BASE) {
			tension += (ENGINE_CLOCKWORK_WIND_MAX + WIND_TENSION_BASE - tension) / (ENGINE_CLOCKWORK_WIND_MAX  + WIND_TENSION_BASE) * WIND_TENSION_BASE;
		} else
			return;
		
		player.addExhaustion(WIND_EXHAUSTION);
		if(tension > ENGINE_CLOCKWORK_WIND_MAX + (0.1*WIND_TENSION_BASE))
			player.attackEntityFrom(damageSourceEngineClockwork, 6);
		tension = tension > ENGINE_CLOCKWORK_WIND_MAX + WIND_TENSION_BASE ? ENGINE_CLOCKWORK_WIND_MAX + WIND_TENSION_BASE : tension;
		delay = WIND_DELAY;
		sendNetworkUpdate();
	}

	/* LOADING & SAVING */
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		tension = nbttagcompound.getFloat("Wound");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("Wound", tension);
	}
	
	@Override
	public boolean isActivated() {
		return true;
	}

	@Override
	public int dissipateHeat() {
		return 0;
	}

	@Override
	public int generateHeat() {
		return 0;
	}

	@Override
	public boolean mayBurn() {
		return true;
	}

	@Override
	public void burn() {
		
		heat = (int)(tension * 10000);
		
		if(delay > 0) {
			delay--;
			return;
		}
		
		if(!isBurning())
			return;
		
		if(tension > 0.01f)
			tension *= 0.9995f;
		else
			tension = 0;
		addEnergy(ENGINE_CLOCKWORK_ENERGY_PER_CYCLE * tension);
	}

	@Override
	public boolean isBurning() {
		return tension > 0;
	}

	@Override
	public TemperatureState getTemperatureState() {
		double scaled = (heat / 10000) / ENGINE_CLOCKWORK_WIND_MAX;

		if (scaled < 0.20)
			return TemperatureState.COOL;
		else if (scaled < 0.45)
			return TemperatureState.WARMED_UP;
		else if (scaled < 0.65)
			return TemperatureState.OPERATING_TEMPERATURE;
		else if (scaled < 0.85)
			return TemperatureState.RUNNING_HOT;
		else
			return TemperatureState.OVERHEATING;
	}

	@Override
	public float getPistonSpeed() {
		if(delay > 0)
			return 0;
		
		float fromClockwork = (tension / ENGINE_CLOCKWORK_WIND_MAX) * Defaults.ENGINE_PISTON_SPEED_MAX;
		return fromClockwork >= 0.01f ? fromClockwork : storedEnergy > 0 ? 0.01f : 0;  
	}
	
	@Override public void getGUINetworkData(int i, int j) {}
	@Override public void sendGUINetworkData(Container containerEngine, ICrafting iCrafting) {}

}