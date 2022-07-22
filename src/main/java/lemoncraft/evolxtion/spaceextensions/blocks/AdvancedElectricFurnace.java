package lemoncraft.evolxtion.spaceextensions.blocks;

import lemoncraft.evolxtion.spaceextensions.SpaceExtensions;
import lemoncraft.evolxtion.spaceextensions.tiles.TileAdvancedElectricFurnace;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockMachineTiered;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class AdvancedElectricFurnace extends BlockMachineTiered {
    
    private IIcon iconMachineT3;
    private IIcon iconMachineSideT3;
    private IIcon iconInputT3;
    private IIcon iconElectricFurnaceT3;
    
    public AdvancedElectricFurnace(String assetName) {
        super(assetName);
        this.setBlockTextureName(SpaceExtensions.TEXTURE_PREFIX + "machine");
        this.setBlockName(assetName);
    }
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        
        this.iconMachineT3 = iconRegister.registerIcon(SpaceExtensions.TEXTURE_PREFIX + "machine");
        this.iconMachineSideT3 = iconRegister.registerIcon(SpaceExtensions.TEXTURE_PREFIX + "machine_side");
        this.iconInputT3 = iconRegister.registerIcon(SpaceExtensions.TEXTURE_PREFIX + "machine_input");
        this.iconElectricFurnaceT3 = iconRegister.registerIcon(SpaceExtensions.TEXTURE_PREFIX + "electricFurnaceT3");
        
    }
    
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
        return GalacticraftCore.galacticraftBlocksTab;
    }
    
    @Override
    public int getRenderType() {
        return GalacticraftCore.proxy.getBlockRender(GCBlocks.machineTiered);
//        return super.getRenderType();
    }
    
    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 4;
        int metaside = (metadata & 3) + 2;
        if (type == 0) {
            if (side != 0 && side != 1) {
                if (side == (metaside ^ 1)) {
                    return this.iconInputT3;
                }
            } else {
                return metadata >= 8 ? this.iconMachineT3 : this.blockIcon;
            }
        } else {
            return this.getIcon(side, metadata);
        }
        return null;
    }
    
    @Override
    public IIcon getIcon(int side, int metadata) {
        int metaside = (metadata & 3) + 2;
        if (side != 0 && side != 1) {
            if ((metadata & 4) == 4) {
                if (side == metaside) {
                    return this.iconInputT3;
                } else if ((metaside != 2 || side != 4) && (metaside != 3 || side != 5) && (metaside != 4 || side != 3) && (metaside != 5 || side != 2)) {
                    return this.iconMachineSideT3;
                } else {
                    return this.iconElectricFurnaceT3;
                }
            } else if (side == (metaside ^ 1)) {
                return this.iconInputT3;
            }
        } else {
            return metadata >= 8 ? this.iconMachineT3 : this.blockIcon;
        }
        return null;
    }
   
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        int metadata = world.getBlockMetadata(x, y, z);
        int angle = MathHelper.floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int change = 0;
        switch (angle) {
            case 0:
                change = 3;
                break;
            case 1:
                change = 1;
                break;
            case 2:
                change = 2;
                break;
            case 3:
                change = 0;
        }
        
        world.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
        
    }
    
    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 3;
        int change = 0;
        switch (original) {
            case 0:
                change = 3;
                break;
            case 1:
                change = 2;
                break;
            case 2:
                change = 0;
                break;
            case 3:
                change = 1;
        }
        
        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileAdvancedElectricFurnace) {
            ((TileAdvancedElectricFurnace) te).updateFacing();
        }
        
        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
        return true;
    }
    
    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        if (!par1World.isRemote) {
            par5EntityPlayer.openGui(SpaceExtensions.instance, -1, par1World, x, y, z);
        }
    
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        int tier = metadata / 8 + 1;
        return (TileEntity)new TileAdvancedElectricFurnace();
    }
    
    @Override
    public ItemStack getEnergyStorageModule() {
        return null;
    }
    
    @Override
    public ItemStack getEnergyStorageCluster() {
        return null;
    }
    
    @Override
    public ItemStack getElectricFurnace() {
        return null;
    }
    
    @Override
    public ItemStack getElectricArcFurnace() {
        return new ItemStack(this, 1, 12);
    }
    
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List){
        par3List.add(this.getElectricArcFurnace());
    }
    
    @Override
    public String getShiftDescription(int meta) {
        int tier = meta >= 8 ? 2 : 1;
        switch (meta & 4) {
            case 0:
                return GCCoreUtil.translate("tile.energyStorageModuleTier" + tier + ".description");
            case 4:
                return GCCoreUtil.translate("tile.electricFurnaceTier" + tier + ".description");
            default:
                return "";
        }
    }
    
}