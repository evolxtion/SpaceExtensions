package lemoncraft.evolxtion.spaceextensions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.ItemBlockMachine;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAdvBlockMachine extends ItemBlockMachine {
    
    public ItemAdvBlockMachine(Block block) {
        super(block);
    }
    
    @Override
    public int getMetadata(int damage) {
        return damage;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return ClientProxyCore.galacticraftItem;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int index = 0;
        return this.field_150939_a.getUnlocalizedName() + "." + index;
    }
    
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) {
            int typenum = stack.getItemDamage() & 12;
            if (player instanceof EntityPlayerSP) {
                if (this.field_150939_a == GCBlocks.machineBase && typenum == 12) {
                    ClientProxyCore.playerClientHandler.onBuild(1, (EntityPlayerSP)player);
                } else if (this.field_150939_a == GCBlocks.machineBase2 && typenum == 4) {
                    ClientProxyCore.playerClientHandler.onBuild(2, (EntityPlayerSP)player);
                }
            }
            
        }
    }
    
    @Override
    public String getUnlocalizedName() {
        return this.field_150939_a.getUnlocalizedName() + ".0";
    }
}
