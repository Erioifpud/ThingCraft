package link.redstone.thingcraft.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import link.redstone.thingcraft.bean.Channel;
import link.redstone.thingcraft.proxy.CommonProxy;
import link.redstone.thingcraft.util.ChatUtils;
import link.redstone.thingcraft.util.RequestUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static link.redstone.thingcraft.proxy.CommonProxy.apiKey;

/**
 * Created by Erioifpud on 16/9/6.
 */
public class GuiListChn extends GuiScreen {
    private GuiSlotChnList chnList;
    private GuiScrollingList chnInfo;
    private int selected = -1;
    private Channel selectedChn;
    private int listWidth;
    private ArrayList<Channel> chns;
    //private GuiTextField apiKey;
    private GuiButton deleteBtn;
    //private GuiButton confirmBtn;

    public GuiListChn() {
        this.chns = new ArrayList<Channel>();
        if (!apiKey.equals("-1")) {
            try {
                String json = RequestUtils.get("https://api.thingspeak.com/channels.json", String.format("api_key=%s", apiKey));
                Gson gson = new Gson();
                Type chnType = new TypeToken<ArrayList<Channel>>() {
                }.getType();
                List<Channel> channels = gson.fromJson(json, chnType);
                for (int i = 0; i < channels.size(); i++) {
                    this.chns.add(channels.get(i));
                }
            } catch (IOException ex) {
                ChatUtils.error(ex.toString());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void initGui() {
        //super.initGui();
        int slotHeight = 35;
        for (Channel bc : chns) {
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(bc.getName()) + 10);
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(String.valueOf(bc.getId())) + 5 + slotHeight);
        }
        listWidth = Math.min(listWidth, 150);
        //listWidth = 150;
        this.chnList = new GuiSlotChnList(this, chns, listWidth, slotHeight);
        //buttonList.add(new GuiButton(20, 10, height - 49, listWidth, 20, "Confirm"));
        deleteBtn = new GuiButton(21, 10, height - 29, listWidth, 20, "Delete (Hold ctrl)");
        buttonList.add(deleteBtn);
        //apiKey = new GuiTextField(0, getFontRenderer(), 12, height - 88 + 4 + 17, listWidth - 4, 14);
        //apiKey.setFocused(true);
        //apiKey.setCanLoseFocus(true);
        updateCache();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.chnList.drawScreen(mouseX, mouseY, partialTicks);
        if (this.chnInfo != null) {
            this.chnInfo.drawScreen(mouseX, mouseY, partialTicks);
        }
        int left = ((this.width - this.listWidth - 38) / 2) + this.listWidth + 30;
        this.drawCenteredString(this.fontRendererObj, "Channel List", left, 16, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        //apiKey.drawTextBox();
    }

    @Override
    public void handleMouseInput() throws IOException {
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        super.handleMouseInput();
        if (this.chnInfo != null) {
            this.chnInfo.handleMouseInput(mouseX, mouseY);
        }
        this.chnList.handleMouseInput(mouseX, mouseY);
    }

    public void selectChnIndex(int index) {
        if (index == this.selected) {
            return;
        }
        this.selected = index;
        this.selectedChn = (index >= 0 && index <= chns.size()) ? chns.get(selected) : null;
        updateCache();
    }

    public boolean chnIndexSelected(int index) {
        return index == selected;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        //apiKey.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        //apiKey.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (GuiScreen.isCtrlKeyDown()) {
            deleteBtn.enabled = true;
        } else {
            deleteBtn.enabled = false;
        }
        //apiKey.updateCursorCounter();
    }

    private void reloadChns() {
        ArrayList<Channel> chns = chnList.getChns();
        chns.clear();
        try {
            String json = RequestUtils.get("https://api.thingspeak.com/channels.json", String.format("api_key=%s", CommonProxy.apiKey));
            Gson gson = new Gson();
            Type chnType = new TypeToken<ArrayList<Channel>>() {
            }.getType();
            List<Channel> channels = gson.fromJson(json, chnType);
            for (int i = 0; i < channels.size(); i++) {
                chns.add(channels.get(i));
            }
        } catch (IOException ex) {
            ChatUtils.error(ex.toString());
            ex.printStackTrace();
        }
        this.chns = chns;

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 20:
                reloadChns();
                break;
            case 21:
                if (selectedChn != null) {
                    //ChatUtils.message(selectedChn.getId());
                    RequestUtils.delete(String.format("https://api.thingspeak.com/channels/%d", selectedChn.getId()), String.format("api_key=%s", CommonProxy.apiKey));
                    reloadChns();
                }
                break;
        }
        super.actionPerformed(button);
    }

    Minecraft getMinecraftInstance() {
        return mc;
    }

    FontRenderer getFontRenderer() {
        return fontRendererObj;
    }

    private void updateCache() {
        chnInfo = null;
        if (selectedChn == null) {
            return;
        }
        List<String> lines = new ArrayList<String>();
        lines.add(selectedChn.getName());
        lines.add(String.format("ID: %s", selectedChn.getId()));
        lines.add(String.format("Desc: %s", selectedChn.getDescription()));
        lines.add(String.format("Date: %s", selectedChn.getCreated_at()));
        lines.add(String.format("Elevation: %s", selectedChn.getElevation()));
        lines.add(String.format("Last entry: %d", selectedChn.getLast_entry_id()));
        lines.add(String.format("Latitude: %f", selectedChn.getLatitude()));
        lines.add(String.format("Longitude: %f", selectedChn.getLongitude()));
        lines.add(String.format("Metadata: %s", selectedChn.getMetadata()));
        if (selectedChn.getApi_keys().size() >= 2) {
            lines.add(String.format("Write key: %s", selectedChn.getApi_keys().get(0).getApi_key()));
            lines.add(String.format("Read key: %s", selectedChn.getApi_keys().get(1).getApi_key()));
        }

        chnInfo = new Info(this.width - this.listWidth - 30, lines);
    }

    private class Info extends GuiScrollingList {
        private List<ITextComponent> lines = null;

        public Info(int width, List<String> lines) {
            super(GuiListChn.this.getMinecraftInstance(),
                    width,
                    GuiListChn.this.height,
                    32, GuiListChn.this.height - 88 + 4,
                    GuiListChn.this.listWidth + 20, 60,
                    GuiListChn.this.width,
                    GuiListChn.this.height);
            this.lines = resizeContent(lines);
            this.setHeaderInfo(true, getHeaderHeight());
        }

        @Override
        protected int getSize() {
            return 0;
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick) {

        }

        @Override
        protected boolean isSelected(int index) {
            return false;
        }

        @Override
        protected void drawBackground() {

        }

        @Override
        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {

        }

        private List<ITextComponent> resizeContent(List<String> lines) {
            List<ITextComponent> ret = new ArrayList<ITextComponent>();
            for (String line : lines) {
                if (line == null) {
                    ret.add(null);
                    continue;
                }
                ITextComponent chat = ForgeHooks.newChatWithLinks(line, false);
                ret.addAll(GuiUtilRenderComponents.splitText(chat, this.listWidth - 8, GuiListChn.this.fontRendererObj, false, true));
            }
            return ret;
        }

        private int getHeaderHeight() {
            int height = 0;
            height += (lines.size() * 10);
            if (height < this.bottom - this.top - 8) {
                height = this.bottom - this.top - 8;
            }
            return height;
        }

        protected void drawHeader(int entryRight, int relativeY, Tessellator tess) {
            int top = relativeY;
            for (ITextComponent line : lines) {
                if (line != null) {
                    GlStateManager.enableBlend();
                    GuiListChn.this.fontRendererObj.drawStringWithShadow(line.getFormattedText(), this.left + 4, top, 0xFFFFFF);
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                }
                top += 10;
            }
        }

        @Override
        protected void clickHeader(int x, int y) {
            int offset = y;
            if (offset <= 0) {
                return;
            }
            int lineIdx = offset / 10;
            if (lineIdx >= lines.size()) {
                return;
            }
            ITextComponent line = lines.get(lineIdx);
            if (line != null) {
                int k = -4;
                for (ITextComponent part : line) {
                    if (!(part instanceof TextComponentString)) {
                        continue;
                    }
                    k += GuiListChn.this.fontRendererObj.getStringWidth(((TextComponentString) part).getText());
                    if (k >= x) {
                        GuiListChn.this.handleComponentClick(part);
                        break;
                    }
                }
            }
        }
    }
}
