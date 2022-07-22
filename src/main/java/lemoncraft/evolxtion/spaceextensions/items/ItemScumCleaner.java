package lemoncraft.evolxtion.spaceextensions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemScumCleaner extends Item {
    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int s, float hx, float hy, float hz) {
        if (entityPlayer != null) {
//            if(!world.isRemote && world.getBlock(x, y, z) == Ic2Items.steamgenerator)
        }
        return false;
    }
    
}
