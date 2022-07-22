package lemoncraft.evolxtion.spaceextensions.tiles;

import cpw.mods.fml.relauncher.Side;
import lemoncraft.evolxtion.spaceextensions.blocks.SEBlocks;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.tile.TileEntityElectricFurnace;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashSet;
import java.util.Set;

public class TileAdvancedElectricFurnace extends TileBaseElectricBlockWithInventory implements ISidedInventory {
    
    public static int PROCESS_TIME_REQUIRED = 130; // to do
    
    @NetworkedField(
            targetSide = Side.CLIENT
    )
    public int processTimeRequired;
    
    @NetworkedField(
            targetSide = Side.CLIENT
    )
    public int processTicks;
    
    private ItemStack[] containingItems;
    public final Set<EntityPlayer> playersUsing;
    private boolean initialised;
    
    public TileAdvancedElectricFurnace() {
        this.processTimeRequired = PROCESS_TIME_REQUIRED;
        this.processTicks = 0;
        this.containingItems = new ItemStack[3];
        this.playersUsing = new HashSet();
        this.initialised = true;
        this.storage.setMaxExtract(100.0F);
        this.storage.setCapacity(100000.0F);
        this.setTier3();
    }
    
    private void setTier3() {
        this.storage.setCapacity(100000.0F);
        this.processTimeRequired = 50; // to do
        this.setTierGC(3);
    }
    
    @Override
    public void updateEntity() {
        if (!this.initialised) {
            int metadata = this.getBlockMetadata();
            Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
            if (b == GCBlocks.machineBase) {
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, SEBlocks.advArcFurnace, 4, 2);
            } else if (metadata >= 8) {
                this.setTier3();
            }
            
            this.initialised = true;
        }
        
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            if (this.canProcess()) {
                if (this.hasEnoughEnergyToRun) {
                    if (this.tierGC == 3) {
                        this.processTimeRequired = 15 / (1 + this.poweredByTierGC); // to do
                    }
                    
                    if (this.processTicks == 0) {
                        this.processTicks = this.processTimeRequired;
                    } else if (--this.processTicks <= 0) {
                        this.smeltItem();
                        this.processTicks = this.canProcess() ? this.processTimeRequired : 0;
                    }
                } else if (this.processTicks > 0 && this.processTicks < this.processTimeRequired && this.worldObj.rand.nextInt(4) == 0) {
                    ++this.processTicks;
                }
            } else {
                this.processTicks = 0;
            }
        }
        
    }
    
    public boolean canProcess() {
        if (this.containingItems[1] != null && FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]) != null) {
            if (this.containingItems[2] != null) {
                if (!this.containingItems[2].isItemEqual(FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]))) {
                    return false;
                }
            
                if (this.containingItems[2].stackSize + 1 > 64) {
                    return false;
                }
            }
        
            return true;
        } else {
            return false;
        }
    }
    
    public void smeltItem() {
        if (this.canProcess()) {
            ItemStack resultItemStack = FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]);
            ItemStack var10000;
            String nameSmelted;
            if (this.containingItems[2] == null) {
                this.containingItems[2] = resultItemStack.copy();
                if (this.tierGC > 1) {
                    nameSmelted = this.containingItems[1].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw") || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard"))) {
                        var10000 = this.containingItems[2];
                        var10000.stackSize += resultItemStack.stackSize;
                    }
                }
            } else if (this.containingItems[2].isItemEqual(resultItemStack)) {
                var10000 = this.containingItems[2];
                var10000.stackSize += resultItemStack.stackSize;
                if (this.tierGC > 1) {
                    nameSmelted = this.containingItems[1].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw") || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard"))) {
                        var10000 = this.containingItems[2];
                        var10000.stackSize += resultItemStack.stackSize;
                    }
                }
            }
        
            --this.containingItems[1].stackSize;
            if (this.containingItems[1].stackSize <= 0) {
                this.containingItems[1] = null;
            }
        }
    
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        if (this.storage.getEnergyStoredGC() > 16000.0F) {
            this.setTier3();
            this.initialised = true;
        } else {
            this.initialised = false;
        }
    
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        this.writeStandardItemsToNBT(par1NBTTagCompound);
    }
    
    protected ItemStack[] getContainingItems() {
        return this.containingItems;
    }
    
    @Override
    public String getInventoryName() {
        return GCCoreUtil.translate("tile.advArcFurnace.0.name");
    }
    
    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else {
            return slotID == 1 ? FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null : slotID == 0 && ItemElectricBase.isElectricItem(itemStack.getItem());
        }
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1, 2};
    }
    
    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }
    
    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3) {
        return slotID == 2;
    }
    
    @Override
    public boolean shouldUseEnergy() {
        return this.canProcess();
    }
    
}
