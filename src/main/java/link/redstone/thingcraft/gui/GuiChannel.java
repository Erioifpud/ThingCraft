package link.redstone.thingcraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class GuiChannel extends GuiScreen {
    //private GuiButton createChn, updateChn, deleteChn;
    public GuiChannel() {

    }

    @Override
    public void initGui() {
        buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 - 20, 100, 20, "Create Channel"));
        buttonList.add(new GuiButton(1, width / 2 - 50, height / 2, 100, 20, "List Channel"));
        buttonList.add(new GuiButton(2, width / 2 - 50, height / 2 + 20, 100, 20, "Delete Channel"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (!button.enabled) {
            return;
        }
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiCreateChn());
                break;
            case 1:
                mc.displayGuiScreen(new GuiListChn());
                break;
            case 2:
                //delete
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
