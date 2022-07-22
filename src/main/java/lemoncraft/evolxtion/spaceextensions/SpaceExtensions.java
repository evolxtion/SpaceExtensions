package lemoncraft.evolxtion.spaceextensions;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lemoncraft.evolxtion.spaceextensions.proxy.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SpaceExtensions.MOD_ID,
	name = SpaceExtensions.NAME,
	version = SpaceExtensions.VERSION,
	dependencies = "required-after:GalacticraftCore; required-after:GalacticraftMars")
public class SpaceExtensions {

	public static final String MOD_ID = "spaceextensions";
	public static final String NAME = "Space Extensions";
	public static final String VERSION = "1.0.0";
	
	public static final String ASSET_PREFIX = "spaceextensions";
	public static final String TEXTURE_PREFIX = ASSET_PREFIX + ":";
 
	public static final Logger logger = LogManager.getLogger("SpaceExtensions");
	
    @Instance(SpaceExtensions.MOD_ID)
    public static SpaceExtensions instance;
	
    @SidedProxy(clientSide = "lemoncraft.evolxtion.spaceextensions.proxy.ClientProxy",
                serverSide = "lemoncraft.evolxtion.spaceextensions.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
    	
        proxy.preInit(e);
        SpaceExtensions.logger.info("Space Extensions successfully pre-initialized!");
        
    }

    @EventHandler
    public void init(FMLInitializationEvent e){
    	
    	SpaceExtensions.proxy.init(e);
        SpaceExtensions.logger.info("Space Extensions successfully initialized!");
        
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e){
    	
    	SpaceExtensions.proxy.postInit(e);
        SpaceExtensions.logger.info("Space Extensions successfully post-initialized!");
        
    }

}
