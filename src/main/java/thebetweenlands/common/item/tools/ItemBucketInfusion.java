package thebetweenlands.common.item.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import thebetweenlands.api.aspect.Aspect;
import thebetweenlands.api.aspect.DiscoveryContainer;
import thebetweenlands.api.aspect.IAspectType;
import thebetweenlands.api.aspect.ItemAspectContainer;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.herblore.aspect.AspectManager;
import thebetweenlands.common.herblore.elixir.ElixirRecipe;
import thebetweenlands.common.herblore.elixir.ElixirRecipes;
import thebetweenlands.common.item.ITintedItem;
import thebetweenlands.common.registries.ItemRegistry;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.*;

public class ItemBucketInfusion extends Item implements ITintedItem, ItemRegistry.IMultipleItemModelDefinition {

    public ItemBucketInfusion() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if (hasTag(stack)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("infused") && stack.getTagCompound().hasKey("ingredients") && stack.getTagCompound().hasKey("infusionTime")) {
                int infusionTime = stack.getTagCompound().getInteger("infusionTime");
                String infusionTimeSeconds = BigDecimal.valueOf(infusionTime / 20.0F).setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString();
                list.add(TextFormatting.GREEN + "Infusion time: " + TextFormatting.RESET + infusionTimeSeconds);
                list.add(TextFormatting.GREEN + "Ingredients:");
                // The properties will be retrieved in the Alembic's TE logic
                NBTTagList nbtList = (NBTTagList) stack.getTagCompound().getTag("ingredients");
                Map<ItemStack, Integer> stackMap = new LinkedHashMap<ItemStack, Integer>();
                for (int i = 0; i < nbtList.tagCount(); i++) {
                    ItemStack ingredient = new ItemStack(nbtList.getCompoundTagAt(i));
                    boolean contained = false;
                    for (Map.Entry<ItemStack, Integer> stackCount : stackMap.entrySet()) {
                        if (ItemStack.areItemStacksEqual(stackCount.getKey(), ingredient)) {
                            stackMap.put(stackCount.getKey(), stackCount.getValue() + 1);
                            contained = true;
                        }
                    }
                    if (!contained) {
                        stackMap.put(ingredient, 1);
                    }
                }
                for (Map.Entry<ItemStack, Integer> stackCount : stackMap.entrySet()) {
                    ItemStack ingredient = stackCount.getKey();
                    int count = stackCount.getValue();
                    if (!ingredient.isEmpty()) {
                        list.add((count > 1 ? (count + "x ") : "") + ingredient.getDisplayName());
                        List<Aspect> ingredientAspects = AspectManager.get(TheBetweenlands.proxy.getClientWorld()).getDiscoveredAspects(AspectManager.getAspectItem(ingredient), DiscoveryContainer.getMergedDiscoveryContainer(FMLClientHandler.instance().getClientPlayerEntity()));
                        if (ingredientAspects.size() >= 1) {
                            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                                for (Aspect aspect : ingredientAspects) {
                                    list.add("  - " + aspect.type.getName() + " (" + aspect.getDisplayAmount() * count + ")");
                                }
                            }
                        }
                    }
                }
            } else {
                list.add("This Infusion Contains Nothing");
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.setTagCompound(new NBTTagCompound());
    }

    private boolean hasTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
            return false;
        }
        return true;
    }

    public ElixirRecipe getInfusionElixirRecipe(ItemStack stack) {
        return ElixirRecipes.getFromAspects(this.getInfusingAspects(stack));
    }

    public List<IAspectType> getInfusingAspects(ItemStack stack) {
        List<IAspectType> infusingAspects = new ArrayList<IAspectType>();
        if (hasTag(stack)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("infused") && stack.getTagCompound().hasKey("ingredients") && stack.getTagCompound().hasKey("infusionTime")) {
                NBTTagList nbtList = (NBTTagList) stack.getTagCompound().getTag("ingredients");
                for (int i = 0; i < nbtList.tagCount(); i++) {
                    ItemStack ingredient = new ItemStack(nbtList.getCompoundTagAt(i));
                    ItemAspectContainer container = ItemAspectContainer.fromItem(ingredient, AspectManager.get(TheBetweenlands.proxy.getClientWorld()));
                    for (Aspect aspect : container.getAspects()) {
                        infusingAspects.add(aspect.type);
                    }
                    //infusingAspects.addAll(AspectManager.get(TheBetweenlands.proxy.getClientWorld()).getDiscoveredAspectTypes(AspectManager.getAspectItem(ingredient), null));
                }
            }
        }
        return infusingAspects;
    }

    public int getInfusionTime(ItemStack stack) {
        if (hasTag(stack)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("infused") && stack.getTagCompound().hasKey("ingredients") && stack.getTagCompound().hasKey("infusionTime")) {
                return stack.getTagCompound().getInteger("infusionTime");
            }
        }
        return 0;
    }

    @Override
    public int getColorMultiplier(ItemStack stack, int tintIndex) {
        if(tintIndex == 1) {
            ElixirRecipe recipe = this.getInfusionElixirRecipe(stack);
            int infusionTime = this.getInfusionTime(stack);
            //Infusion liquid
            if(recipe != null) {
                if(infusionTime > recipe.idealInfusionTime + recipe.infusionTimeVariation) {
                    float[] failedColor = recipe.getRGBA(recipe.infusionFailedColor);
                    return this.getColorFromRGBA(failedColor[0], failedColor[1], failedColor[2], failedColor[3]);
                } else if(infusionTime > recipe.idealInfusionTime - recipe.infusionTimeVariation
                        && infusionTime < recipe.idealInfusionTime + recipe.infusionTimeVariation) {
                    float[] finishedColor = recipe.getRGBA(recipe.infusionFinishedColor);
                    return this.getColorFromRGBA(finishedColor[0], finishedColor[1], finishedColor[2], finishedColor[3]);
                } else {
                    float startR = 0.2F;
                    float startG = 0.6F;
                    float startB = 0.4F;
                    float startA = 0.9F;
                    float[] targetColor = recipe.getRGBA(recipe.infusionGradient);
                    int targetTime = recipe.idealInfusionTime - recipe.infusionTimeVariation;
                    float infusingPercentage = (float)infusionTime / (float)targetTime;
                    float interpR = startR + (targetColor[0] - startR) * infusingPercentage;
                    float interpG = startG + (targetColor[1] - startG) * infusingPercentage;
                    float interpB = startB + (targetColor[2] - startB) * infusingPercentage;
                    float interpA = startA + (targetColor[3] - startA) * infusingPercentage;
                    return this.getColorFromRGBA(interpR, interpG, interpB, interpA);
                }
            } else {
                return this.getColorFromRGBA(0.8F, 0.0F, 0.8F, 1.0F);
            }
        }
        return 0xFFFFFFFF;
    }

    private int getColorFromRGBA(float r, float g, float b, float a) {
        return ((int) (a * 255.0F) << 24) | ((int) (r * 255.0F) << 16) | ((int) (g * 255.0F) << 8) | ((int) (b * 255.0F));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        try {
            switch (stack.getItemDamage()) {
                case 0:
                    return "item.thebetweenlands.bl_bucket_infusion.weedwood";
                case 1:
                    return "item.thebetweenlands.bl_bucket_infusion.syrmorite";
            }
        } catch (Exception e) {
        }
        return "item.thebetweenlands.unknown";
    }

    @Override
    public Map<Integer, ResourceLocation> getModels() {
        Map<Integer, ResourceLocation> models = new HashMap<>();
        models.put(0, new ResourceLocation(getRegistryName().toString() + "_weedwood"));
        models.put(1, new ResourceLocation(getRegistryName().toString() + "_syrmorite"));
        return models;
    }

    public static ItemStack getEmptyBucket(ItemStack stack) {
        return new ItemStack(ItemRegistry.BL_BUCKET, 1, stack.getMetadata());
    }
}