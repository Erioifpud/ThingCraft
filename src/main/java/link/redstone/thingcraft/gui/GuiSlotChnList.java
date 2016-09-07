package link.redstone.thingcraft.gui;

import link.redstone.thingcraft.bean.Channel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.util.ArrayList;

/**
 * Created by Erioifpud on 16/9/6.
 */
public class GuiSlotChnList extends GuiScrollingList {
    private GuiListChn parent;
    private ArrayList<Channel> chns;

    public GuiSlotChnList(GuiListChn parent, ArrayList<Channel> chns, int listWidth, int slotHeight) {
        super(parent.getMinecraftInstance(), listWidth, parent.height, 32, parent.height - 88 + 4, 10, slotHeight, parent.width, parent.height);
        this.parent = parent;
        this.chns = chns;
    }

    @Override
    protected int getSize() {
        return chns.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        this.parent.selectChnIndex(index);
    }

    @Override
    protected boolean isSelected(int index) {
        return this.parent.chnIndexSelected(index);
    }

    @Override
    protected void drawBackground() {
        this.parent.drawDefaultBackground();
    }

    @Override
    protected int getContentHeight() {
        return (this.getSize()) * 35 + 1;
    }

    ArrayList<Channel> getChns() {
        return chns;
    }

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int height, Tessellator tess) {
        Channel bc = chns.get(slotIdx);
        String name = StringUtils.stripControlCodes(bc.getName() == null ? "" : bc.getName());
        String id = StringUtils.stripControlCodes(String.valueOf(bc.getId()));
        String desc = StringUtils.stripControlCodes(bc.getDescription() == null ? "" : bc.getDescription());
        FontRenderer font = this.parent.getFontRenderer();
        font.drawString(font.trimStringToWidth(name,    listWidth - 10), this.left + 3 , top +  2, 0xFFFFFF);
        font.drawString(font.trimStringToWidth(id, listWidth - (5 + height)), this.left + 3 , top + 12, 0xCCCCCC);
        font.drawString(font.trimStringToWidth(desc, listWidth - (5 + height)), this.left + 3 , top + 22, 0xCCCCCC);

    }
}
