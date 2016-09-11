package link.redstone.thingcraft.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Erioifpud on 16/9/7.
 */
public class TileEntityTransmitter extends TileEntity {
    public static final String ENTITY_NAME = TileEntityTransmitter.class.getSimpleName();
    private int channelId = -1;
    private int fieldId = -1;
    private int result = -1;
    private ItemStack stack = new ItemStack(Items.REDSTONE);

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("channel", this.channelId);
        compound.setInteger("field", this.fieldId);
        compound.setInteger("result", this.result);
        if (stack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.channelId = compound.getInteger("channel");
        this.fieldId = compound.getInteger("field");
        this.result = compound.getInteger("result");
        if (compound.hasKey("item")) {
            stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("item"));
        } else {
            stack = null;
        }
    }

    //@SideOnly(Side.CLIENT)
    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    //@SideOnly(Side.CLIENT)
    public int getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    //@SideOnly(Side.CLIENT)
    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the client you can write
        // a more optimal NBT here.
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        // Here we get the packet from the server and read it into our client side tile entity
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        // getUpdateTag() is called whenever the chunkdata is sent to the
        // client. In contrast getUpdatePacket() is called when the tile entity
        // itself wants to sync to the client. In many cases you want to send
        // over the same information in getUpdateTag() as in getUpdatePacket().
        return writeToNBT(new NBTTagCompound());
    }

    public void updateVal() {
        markDirty();
        if (worldObj != null) {
            IBlockState state = worldObj.getBlockState(getPos());
            worldObj.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        updateVal();
    }

    public ItemStack getStack() {
        return stack;
    }
}
