package link.redstone.thingcraft.gui;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class GuiFactoryThingCraft implements IModGuiFactory {
    public GuiFactoryThingCraft() {

    }

    @Override
    public void initialize(Minecraft instance) {

    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiConfigThingCraft.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

}
