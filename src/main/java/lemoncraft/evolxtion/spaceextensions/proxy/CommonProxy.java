package lemoncraft.evolxtion.spaceextensions.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import lemoncraft.evolxtion.spaceextensions.SpaceExtensions;
import lemoncraft.evolxtion.spaceextensions.blocks.SEBlocks;
import lemoncraft.evolxtion.spaceextensions.gui.GuiHandler;
import lemoncraft.evolxtion.spaceextensions.tiles.TileAdvancedElectricFurnace;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		SEBlocks.init();
		this.registerTileEntities();
	}
	
	public void init(FMLInitializationEvent e) {
	
		
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(SpaceExtensions.instance, new GuiHandler());
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileAdvancedElectricFurnace.class, "Advanced Electric Furnace");
	}
	
//	public int getBlockRender(Block blockID) {
//		return -1;
//	}
	
}
