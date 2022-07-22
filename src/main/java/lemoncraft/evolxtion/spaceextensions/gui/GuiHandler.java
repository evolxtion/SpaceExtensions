package lemoncraft.evolxtion.spaceextensions.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lemoncraft.evolxtion.spaceextensions.inventory.ContainerAdvElectricFurnace;
import lemoncraft.evolxtion.spaceextensions.tiles.TileAdvancedElectricFurnace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.inventory.ContainerElectricFurnace;
import micdoodle8.mods.galacticraft.core.tile.TileEntityElectricFurnace;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);
        if (playerBase == null) {
            player.addChatMessage(new ChatComponentText("Space Extensions player instance null server-side. This is a bug."));
            return null;
        } else {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileAdvancedElectricFurnace) {
                    return new ContainerAdvElectricFurnace(player.inventory, (TileAdvancedElectricFurnace)tile);
                }
            }
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? this.getClientGuiElement(ID, player, world, new Vector3((double)x, (double)y, (double)z)) : null;
    }
    
    @SideOnly(Side.CLIENT)
    private Object getClientGuiElement(int ID, EntityPlayer player, World world, Vector3 position) {
        EntityClientPlayerMP playerClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);
        TileEntity tile = world.getTileEntity(position.intX(), position.intY(), position.intZ());
        if (tile != null) {
            if (tile instanceof TileAdvancedElectricFurnace) {
                return new GuiAdvancedElectricFurnace(player.inventory, (TileAdvancedElectricFurnace) tile);
            }
        }
        return null;
    }
    
}
