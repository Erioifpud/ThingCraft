package link.redstone.thingcraft.proxy;

import link.redstone.thingcraft.ThingCraft;
import link.redstone.thingcraft.block.BlockChannel;
import link.redstone.thingcraft.block.BlockTransmitter;
import link.redstone.thingcraft.gui.GuiConfigThingCraft;
import link.redstone.thingcraft.tile.TileEntityTransmitter;
import link.redstone.thingcraft.tile.TileEntityTransmitterRenderer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.FMLConfigGuiFactory;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class CommonProxy {
    public static Configuration config;
    public static String apiKey;
    public static BlockChannel channel;
    public static BlockTransmitter transmitter;

    public void init(FMLInitializationEvent event) {
        registerEventListeners();
    }

    public void preInit(FMLPreInitializationEvent event) {
        initConfig(event);
        registerBlocks();
        registerTileEntities();
    }

    private void registerBlocks() {
        channel = new BlockChannel();
        transmitter = new BlockTransmitter();
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityTransmitter.class, TileEntityTransmitter.ENTITY_NAME);
    }

    private void initConfig(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    private void registerEventListeners() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(ThingCraft.MODID)) {
            syncConfig();
        }
    }

    private static void syncConfig(){
        apiKey = config.get(Configuration.CATEGORY_GENERAL, "Apikey", "-1").getString();
        if(config.hasChanged())
            config.save();
    }
}
