package link.redstone.thingcraft.proxy;

import link.redstone.thingcraft.gui.GuiConfigThingCraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.FMLConfigGuiFactory.FMLConfigGuiScreen;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    private void registerBlockRenderers() {

    }
}
