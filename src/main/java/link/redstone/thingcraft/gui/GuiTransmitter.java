package link.redstone.thingcraft.gui;

import link.redstone.thingcraft.tile.TileEntityTransmitter;
import link.redstone.thingcraft.util.ChatUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * Created by Erioifpud on 16/9/8.
 */
public class GuiTransmitter extends GuiScreen {
    private GuiAdvTextField channel;
    private GuiAdvTextField field;
    private GuiAdvTextField result;
    private TileEntityTransmitter tile;

    public GuiTransmitter(TileEntityTransmitter tile) {
        this.tile = tile;
    }

    @Override
    public void initGui() {
        super.initGui();
        channel = new GuiAdvTextField(0, fontRendererObj, width / 2 - 50, height / 2 - 70, 100, 20, "", "Channel id");
        field = new GuiAdvTextField(1, fontRendererObj, width / 2 - 50, height / 2 - 50, 100, 20, "", "Field id");
        result = new GuiAdvTextField(2, fontRendererObj, width / 2 - 50, height / 2 - 30, 100, 20, "results", "Value");
        buttonList.add(new GuiButton(10, width / 2 - 50, height / 2 + 10, 100, 20, "Confirm"));
        buttonList.add(new GuiButton(11, width / 2 - 50, height / 2 + 30, 100, 20, "Cancel"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        channel.drawTextBox();
        field.drawTextBox();
        result.drawTextBox();
        drawCenteredString(this.fontRendererObj, String.format("Channel:%d Field:%d Value:%d", tile.getChannelId(), tile.getFieldId(), tile.getResult()), width / 2, 16, 0xFFFFFF);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        channel.textboxKeyTyped(typedChar, keyCode);
        field.textboxKeyTyped(typedChar, keyCode);
        result.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        channel.mouseClicked(mouseX, mouseY, mouseButton);
        field.mouseClicked(mouseX, mouseY, mouseButton);
        result.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 10:
                try {
                    tile.setChannelId(channel.getText().isEmpty() ?  tile.getChannelId() : Integer.parseInt(channel.getText()));
                    tile.setFieldId(field.getText().isEmpty() ?  tile.getFieldId() : Integer.parseInt(field.getText()));
                    tile.setResult(result.getText().isEmpty() ?  tile.getResult() : Integer.parseInt(result.getText()));
                    mc.displayGuiScreen(null);
                } catch (NumberFormatException ex) {
                    ChatUtils.error(ex.toString());
                    ex.printStackTrace();
                    mc.displayGuiScreen(null);
                }
                break;
            case 11:
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        channel.updateCursorCounter();
        field.updateCursorCounter();
        result.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
