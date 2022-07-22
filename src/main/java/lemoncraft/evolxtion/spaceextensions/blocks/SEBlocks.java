package lemoncraft.evolxtion.spaceextensions.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lemoncraft.evolxtion.spaceextensions.items.ItemAdvBlockMachine;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;

public class SEBlocks {

	public static Block advArcFurnace;
	
	public static void init() {
		
		advArcFurnace = new AdvancedElectricFurnace("advArcFurnace");
		
		GCCoreUtil.registerGalacticraftBlock("advArcFurnace", advArcFurnace, 12);
		
		SEBlocks.registerBlocks();
		
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(advArcFurnace, ItemAdvBlockMachine.class, advArcFurnace.getUnlocalizedName());
	}
	
}
