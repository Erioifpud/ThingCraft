package link.redstone.thingcraft.proxy;

import link.redstone.thingcraft.block.BlockChannel;
import link.redstone.thingcraft.gui.GuiConfigThingCraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.FMLConfigGuiFactory;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class CommonProxy {
    public static Configuration config;
    public static String apiKey;
    public static BlockChannel channel;

    public void init(FMLInitializationEvent event) {
        registerEventListeners();
    }

    public void preInit(FMLPreInitializationEvent event) {
        initConfig(event);
        registerBlocks();
    }

    private void registerBlocks() {
        channel = new BlockChannel();
    }

    private void initConfig(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        apiKey = config.get(Configuration.CATEGORY_GENERAL, "Apikey", "-1").getString();
        config.save();
    }

    private void registerEventListeners() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof FMLConfigGuiFactory.FMLConfigGuiScreen) {
            event.setGui(new GuiConfigThingCraft(null));
        }
    }
}
