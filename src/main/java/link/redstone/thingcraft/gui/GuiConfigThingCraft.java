package link.redstone.thingcraft.gui;

import link.redstone.thingcraft.ThingCraft;
import link.redstone.thingcraft.proxy.CommonProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class GuiConfigThingCraft extends GuiConfig {
    public GuiConfigThingCraft(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(CommonProxy.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                ThingCraft.MODID, false, false, "ThingCraft");
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }
}
