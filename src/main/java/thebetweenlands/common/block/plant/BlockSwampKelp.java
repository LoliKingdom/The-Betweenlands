package thebetweenlands.common.block.plant;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import thebetweenlands.common.registries.ItemRegistry;

public class BlockSwampKelp extends BlockStackablePlantUnderwater {
	public BlockSwampKelp() {
		this.setLightLevel(0.2F);
		this.setHardness(0.1F);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemRegistry.SWAMP_KELP_ITEM;
	}
	
	@Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return ImmutableList.of();
	}
}