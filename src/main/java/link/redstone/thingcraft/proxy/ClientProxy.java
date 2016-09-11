package link.redstone.thingcraft.proxy;

import link.redstone.thingcraft.gui.GuiConfigThingCraft;
import link.redstone.thingcraft.tile.TileEntityTransmitter;
import link.redstone.thingcraft.tile.TileEntityTransmitterRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
        registerBlockRenderers();
    }

    private void registerBlockRenderers() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CommonProxy.transmitter), 0, new ModelResourceLocation(CommonProxy.transmitter.getName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmitter.class, new TileEntityTransmitterRenderer());
    }
}
