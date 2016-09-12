package link.redstone.thingcraft.block;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import link.redstone.thingcraft.ThingCraft;
import link.redstone.thingcraft.bean.Channel;
import link.redstone.thingcraft.gui.GuiTransmitter;
import link.redstone.thingcraft.proxy.CommonProxy;
import link.redstone.thingcraft.tile.TileEntityTransmitter;
import link.redstone.thingcraft.tile.TileEntityTransmitterRenderer;
import link.redstone.thingcraft.util.ChatUtils;
import link.redstone.thingcraft.util.RequestUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sun.audio.AudioPlayer.player;

/**
 * Created by Erioifpud on 16/9/7.
 */
public class BlockTransmitter extends BlockContainer {
    private final String name = "transmitter";

    public BlockTransmitter() {
        super(Material.IRON);
        setUnlocalizedName(name);
        setCreativeTab(ThingCraft.thingCraftTab);
        GameRegistry.registerBlock(this, name);
        //GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        UpdateTask ut = new UpdateTask(worldIn, pos);
        ut.start();

        /*TileEntityTransmitter t = (TileEntityTransmitter) worldIn.getTileEntity(pos);
        if (t != null && worldIn.isBlockPowered(pos)) {
            try {
                String json = RequestUtils.get("https://api.thingspeak.com/channels.json", String.format("api_key=%s", CommonProxy.apiKey));
                Gson gson = new Gson();
                Type chnType = new TypeToken<ArrayList<Channel>>() {
                }.getType();
                List<Channel> channels = gson.fromJson(json, chnType);
                Channel channel = null;
                String writeKey = "";
                for (Channel c : channels) {
                    if (c.getId() == t.getChannelId()) {
                        channel = c;
                        writeKey = c.getApi_keys().get(0).getApi_key();
                        break;
                    }
                }
                if (channel != null) {
                    String link = "https://api.thingspeak.com/update";
                    URL url = new URL(link);
                    StringBuilder sb = new StringBuilder();
                    sb.append("api_key=").append(writeKey).append("&field").append(t.getFieldId()).append("=").append(t.getResult());
                    RequestUtils.post(url, sb.toString());
                }
            } catch (MalformedURLException ex) {
                ChatUtils.error(ex.toString());
                ex.printStackTrace();
            } catch (IOException ex) {
                ChatUtils.error(ex.toString());
                ex.printStackTrace();
            }
        }*/
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTransmitter();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (!worldIn.isRemote && tileentity instanceof TileEntityTransmitter) {
            TileEntityTransmitter t = (TileEntityTransmitter) worldIn.getTileEntity(pos);
            if (GuiScreen.isCtrlKeyDown()) {
                if (heldItem != null) {
                    ((TileEntityTransmitter) tileentity).setStack(heldItem);
                } else {
                    ((TileEntityTransmitter) tileentity).setStack(null);
                }
            } else {
                Minecraft.getMinecraft().displayGuiScreen(new GuiTransmitter(t));
            }
            worldIn.notifyBlockOfStateChange(pos, worldIn.getBlockState(pos).getBlock());
            return true;
        } else {
            return false;
        }
    }

    /*public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }*/

    private class UpdateTask extends Thread {
        private World world;
        private BlockPos pos;

        UpdateTask(World world, BlockPos pos) {
            this.world = world;
            this.pos = pos;
        }

        public void run() {
            TileEntityTransmitter t = (TileEntityTransmitter) world.getTileEntity(pos);
            if (t != null && world.isBlockPowered(pos)) {
                try {
                    String json = RequestUtils.get("https://api.thingspeak.com/channels.json", String.format("api_key=%s", CommonProxy.apiKey));
                    //String json = ThingSpeak.getChannelList(String.format("api_key=%s", CommonProxy.apiKey));
                    Gson gson = new Gson();
                    Type chnType = new TypeToken<ArrayList<Channel>>() {
                    }.getType();
                    List<Channel> channels = gson.fromJson(json, chnType);
                    Channel channel = null;
                    String writeKey = "";
                    for (Channel c : channels) {
                        if (c.getId() == t.getChannelId()) {
                            channel = c;
                            writeKey = c.getApi_keys().get(0).getApi_key();
                            break;
                        }
                    }
                    if (channel != null) {
                        //String link = "https://api.thingspeak.com/update";
                        //URL url = new URL(link);
                        StringBuilder sb = new StringBuilder();
                        sb.append("api_key=").append(writeKey).append("&field").append(t.getFieldId()).append("=").append(t.getResult());
                        //RequestUtils.post(url, sb.toString());
                        RequestUtils.http("https://api.thingspeak.com/update", "POST", sb.toString());
                        ChatUtils.message(this.getName() + " finished");
                        world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F, 0.6F);
                    }
                } catch (IOException ex) {
                    ChatUtils.error(ex.toString());
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

}
