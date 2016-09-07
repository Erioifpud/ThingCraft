package link.redstone.thingcraft.gui;

import link.redstone.thingcraft.ThingCraft;
import link.redstone.thingcraft.proxy.CommonProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class GuiConfigThingCraft extends GuiConfig {
    public GuiConfigThingCraft(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(CommonProxy.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                ThingCraft.MODID, false, false, "ThingCraft");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new DummyConfigElement.DummyCategoryElement("name", "lang", GeneralEntry.class));
        return list;
    }

    public static class GeneralEntry extends GuiConfigEntries.CategoryEntry {

        public GeneralEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
            super(owningScreen, owningEntryList, configElement);
        }

        @Override
        protected GuiScreen buildChildScreen() {
            return new GuiConfig(owningScreen, new ConfigElement(CommonProxy.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), ThingCraft.MODID, "Test", false, false, "Test2");

        }
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
