package thebetweenlands.api.rune.gui;

import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public interface IRuneContainerContext {
	public IRuneChainAltarContainer getRuneChainAltarContainer();

	public IRuneChainAltarGui getRuneChainAltarGui();

	public int getRuneIndex();

	public NBTTagCompound getData();

	public void setData(NBTTagCompound nbt);

	public void addSlot(Slot slot);
}
