package link.redstone.thingcraft.gui;

import link.redstone.thingcraft.proxy.CommonProxy;
import link.redstone.thingcraft.util.ChatUtils;
import link.redstone.thingcraft.util.RequestUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import scala.collection.mutable.StringBuilder;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class GuiCreateChn extends GuiScreen {
    /**
     * 0:apiKey
     * 1:desc
     * 2:elevation
     * 3:latitude
     * 4:longitude
     * 5:metadata
     * 6:name
     * 7:publicFlag
     * 8:tags
     * 9:url
     */
    private GuiAdvTextField[] items = new GuiAdvTextField[17];
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        for (int i = 0; i < 8; ++i) {
            String t = String.format("field%d", i);
            int id = 9 + i;
            items[id] = new GuiAdvTextField(id, fontRendererObj, width / 2, 10 + 20 * i, 100, 20, t, t);
        }
        //items[0] = new GuiAdvTextField(0, fontRendererObj, 10, 10, 100, 20, "api_key", "api key(required)");
        items[0] = new GuiAdvTextField(1, fontRendererObj, 10, 10, 100, 20, "desc", "description");
        items[1] = new GuiAdvTextField(2, fontRendererObj, 10, 30, 100, 20, "elevation", "elevation");
        items[2] = new GuiAdvTextField(3, fontRendererObj, 10, 50, 100, 20, "latitude", "latitude");
        items[3] = new GuiAdvTextField(4, fontRendererObj, 10, 70, 100, 20, "longitude", "longitude");
        items[4] = new GuiAdvTextField(5, fontRendererObj, 10, 90, 100, 20, "metadata", "metadata");
        items[5] = new GuiAdvTextField(6, fontRendererObj, 10, 110, 100, 20, "name", "name");
        items[6] = new GuiAdvTextField(7, fontRendererObj, 10, 130, 100, 20, "public_flag", "public?");
        items[7] = new GuiAdvTextField(8, fontRendererObj, 10, 150, 100, 20, "tags", "tags");
        items[8] = new GuiAdvTextField(9, fontRendererObj, 10, 170, 100, 20, "url", "url");
        buttonList.add(new GuiButton(18, width / 2, 170, 100, 20, "Confirm"));
        buttonList.add(new GuiButton(19, width / 2, 190, 100, 20, "Cancel"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 18:
                StringBuilder sb = new StringBuilder("");
                for (GuiAdvTextField tf : items) {
                    sb.append(tf.getKey()).append("=").append(tf.getText()).append("&");
                }
                sb.append("api_key").append("=").append(CommonProxy.apiKey);
                try {
                    RequestUtils.post(new URL("https://api.thingspeak.com/channels"), sb.toString());
                } catch (Exception ex) {
                    ChatUtils.error(ex.toString());
                    ex.printStackTrace();
                }
                mc.displayGuiScreen(null);
                break;
            case 19:
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (int i = 0; i < items.length; i++) {
            items[i].drawTextBox();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < items.length; i++) {
            items[i].mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        for (int i = 0; i < items.length; i++) {
            items[i].updateCursorCounter();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (int i = 0; i < items.length; i++) {
            items[i].textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
