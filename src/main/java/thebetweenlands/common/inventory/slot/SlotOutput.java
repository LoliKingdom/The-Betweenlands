package thebetweenlands.common.inventory.slot;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thebetweenlands.api.item.ICorrodible;
import thebetweenlands.common.inventory.container.ContainerFishTrimmingTable;
import thebetweenlands.common.inventory.container.ContainerPurifier;
import thebetweenlands.common.registries.AdvancementCriterionRegistry;
import thebetweenlands.common.tile.TileEntityFishTrimmingTable;

public class SlotOutput extends Slot {
    private Container container;

    public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition, Container container) {
        super(inventoryIn, index, xPosition, yPosition);
        this.container = container;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        if (container instanceof ContainerPurifier && stack.getItem() instanceof ICorrodible && thePlayer instanceof EntityPlayerMP)
            AdvancementCriterionRegistry.PURIFY_TOOL.trigger((EntityPlayerMP) thePlayer);
        //temp item test
        if (container instanceof ContainerFishTrimmingTable && getSlotIndex() == 4) {
            ((TileEntityFishTrimmingTable) inventory).getItems().set(1, ItemStack.EMPTY);
            ((TileEntityFishTrimmingTable) inventory).getItems().set(2, ItemStack.EMPTY);
            ((TileEntityFishTrimmingTable) inventory).getItems().set(3, ItemStack.EMPTY);
        }
        return super.onTake(thePlayer, stack);
    }
}