package link.redstone.thingcraft.block;

import link.redstone.thingcraft.ThingCraft;
import link.redstone.thingcraft.gui.GuiChannel;
import link.redstone.thingcraft.tile.TileEntityField;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class BlockChannel extends Block {
    private final String name = "channel";

    public BlockChannel() {
        super(Material.IRON);
        setUnlocalizedName(name);
        setCreativeTab(ThingCraft.thingCraftTab);
        GameRegistry.registerBlock(this, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiChannel());
        return true;
    }

    public String getName() {
        return name;
    }
}
