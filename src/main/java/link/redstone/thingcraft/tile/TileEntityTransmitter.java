package link.redstone.thingcraft.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Erioifpud on 16/9/7.
 */
public class TileEntityTransmitter extends TileEntity {
    public static final String ENTITY_NAME = TileEntityTransmitter.class.getSimpleName();
    private int channelId = -1;
    private int fieldId = -1;
    private int result = -1;

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("channel", this.channelId);
        compound.setInteger("field", this.fieldId);
        compound.setInteger("result", this.result);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.channelId = compound.getInteger("channel");
        this.fieldId = compound.getInteger("field");
        this.result = compound.getInteger("result");
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        //worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
    }

}
