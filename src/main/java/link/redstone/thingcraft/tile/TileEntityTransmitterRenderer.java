package link.redstone.thingcraft.tile;

import link.redstone.thingcraft.ThingCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static link.redstone.thingcraft.ThingCraft.mc;
import static link.redstone.thingcraft.ThingCraft.tessellator;
import static link.redstone.thingcraft.ThingCraft.vertexbuffer;
import static net.minecraft.client.renderer.GlStateManager.depthMask;
import static net.minecraft.client.renderer.GlStateManager.disableBlend;

/**
 * Created by Erioifpud on 16/9/8.
 */
@SideOnly(Side.CLIENT)
public class TileEntityTransmitterRenderer extends TileEntitySpecialRenderer<TileEntityTransmitter> {
    private static final ResourceLocation TOP_TEXTURE = new ResourceLocation("textures/blocks/stone_slab_top.png");

    public void renderTileEntityAt(TileEntityTransmitter te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
        drawTexture(te, x, y, z);
        renderInfo(te, x, y, z);
        renderItem(te, x + 0.5, y + 17f / 32, z + 0.5);
    }

    private void renderInfo(TileEntityTransmitter te, double x, double y, double z) {
        double d = mc.thePlayer.getDistanceSqToCenter(te.getPos());
        if (d <= 5 * 5) {
            float f = mc.getRenderManager().playerViewY;
            float f1 = mc.getRenderManager().playerViewX;
            boolean flag1 = mc.getRenderManager().options.thirdPersonView == 2;
            String channelId = String.valueOf(te.getChannelId());
            String fieldId = String.valueOf(te.getFieldId());
            String result = String.valueOf(te.getResult());
            renderText(mc, mc.fontRendererObj, channelId, fieldId, result, (float) x + 0.5f, (float) y + 1.75f, (float) z + 0.5f, f, f1, flag1, false);
        }
    }


    private void renderText(Minecraft mc, FontRenderer fontRenderer, String text, String text2, String text3, float x, float y, float z, float viewY, float viewX, boolean thirdPerson, boolean isSneaking) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (thirdPerson ? -1 : 1) * viewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        depthMask(false);

        if (!isSneaking) {
            GlStateManager.disableDepth();
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRenderer.getStringWidth(text) / 2;
        int j = fontRenderer.getStringWidth(text2) / 2;
        int k = fontRenderer.getStringWidth(text3) / 2;
        //GlStateManager.disableTexture2D();
        ThingCraft.vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double) (-i - 1), (double) (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (-i - 1), (double) (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (i + 1), (double) (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (i + 1), (double) (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double) (-j - 1), (double) (9), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (-j - 1), (double) (18), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (j + 1), (double) (18), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (j + 1), (double) (9), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double) (-k - 1), (double) (19), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (-k - 1), (double) (28), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (k + 1), (double) (28), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double) (k + 1), (double) (19), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        if (!isSneaking) {
            //fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 553648127);
            GlStateManager.enableDepth();
        }

        depthMask(true);
        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 0xffffff);
        fontRenderer.drawString(text2, -fontRenderer.getStringWidth(text2) / 2, 10, 0xffffff);
        fontRenderer.drawString(text3, -fontRenderer.getStringWidth(text3) / 2, 20, 0xffffff);
        GlStateManager.enableLighting();
        disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void drawTexture(TileEntityTransmitter te, double x, double y, double z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.color(241f / 255, 196f / 255, 15f / 255, 1.0F);
        mc.getTextureManager().bindTexture(TOP_TEXTURE);
        //top
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1, 0.5, 1).tex(0, 0).endVertex();
        vertexbuffer.pos(1, 0.5, 0).tex(0, 1).endVertex();
        vertexbuffer.pos(0, 0.5, 0).tex(1, 1).endVertex();
        vertexbuffer.pos(0, 0.5, 1).tex(1, 0).endVertex();
        tessellator.draw();
        //bottom
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0, 0.5, 1).tex(1, 0).endVertex();
        vertexbuffer.pos(0, 0.5, 0).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.5, 0).tex(0, 1).endVertex();
        vertexbuffer.pos(1, 0.5, 1).tex(0, 0).endVertex();
        tessellator.draw();
        //draw piston side
        //north
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1, 0.5, 0).tex(0, 0).endVertex();
        vertexbuffer.pos(1, 0, 0).tex(0, 1).endVertex();
        vertexbuffer.pos(0, 0, 0).tex(1, 1).endVertex();
        vertexbuffer.pos(0, 0.5, 0).tex(1, 0).endVertex();
        tessellator.draw();
        //south
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0, 0.5, 1).tex(0, 0).endVertex();
        vertexbuffer.pos(0, 0, 1).tex(0, 1).endVertex();
        vertexbuffer.pos(1, 0, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.5, 1).tex(1, 0).endVertex();
        tessellator.draw();
        //east
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1, 0.5, 1).tex(0, 0).endVertex();
        vertexbuffer.pos(1, 0, 1).tex(0, 1).endVertex();
        vertexbuffer.pos(1, 0, 0).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.5, 0).tex(1, 0).endVertex();
        tessellator.draw();
        //west
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0, 0.5, 0).tex(0, 0).endVertex();
        vertexbuffer.pos(0, 0, 0).tex(0, 1).endVertex();
        vertexbuffer.pos(0, 0, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(0, 0.5, 1).tex(1, 0).endVertex();
        tessellator.draw();
        //end piston side
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderItem(TileEntityTransmitter itemFrame, double x, double y, double z) {
        ItemStack itemstack = itemFrame.getStack();
        if (itemstack != null) {
            //EntityItem entityitem = new EntityItem(itemFrame.getWorld(), 0.0D, 0.0D, 0.0D, itemstack);
            //Item item = entityitem.getEntityItem().getItem();
            //entityitem.getEntityItem().stackSize = 1;
            //entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.disableLighting();
            GlStateManager.rotate(90, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(1F, 1F, 1F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            mc.getRenderItem().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }


}
