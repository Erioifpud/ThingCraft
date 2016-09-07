package link.redstone.thingcraft;

import link.redstone.thingcraft.block.BlockChannel;
import link.redstone.thingcraft.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Erioifpud on 16/9/5.
 */
@Mod(modid = ThingCraft.MODID, version = ThingCraft.VERSION, guiFactory = "link.redstone.thingcraft.gui.GuiFactoryThingCraft")
public class ThingCraft {
    @Mod.Instance
    public static ThingCraft instance;
    @SidedProxy(clientSide = "link.redstone.thingcraft.proxy.ClientProxy", serverSide = "link.redstone.thingcraft.proxy.CommonProxy")
    public static ClientProxy proxy;
    public static final String MODID = "thingcraft";
    public static final String VERSION = "0.1";

    public ThingCraft() {
        instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    public static final CreativeTabs thingCraftTab = new CreativeTabs("ThingCraft") {
        @Override
        public Item getTabIconItem() {
            return Items.REPEATER;
        }
    };
}
