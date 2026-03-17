package gregtech.tileentity.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.LH;
import gregapi.gui.*;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityItemInternal;
import gregapi.data.CS.ToolsGT;
import gregapi.util.ST;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import static gregapi.data.CS.*;

public class MultiTileEntitySculptingTable extends TileEntityBase07Paintable {

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aSideShouldBeRendered) {
        switch (aRenderPass) {
            case 0:box(aBlock, PX_P[0], PX_P[7], PX_P[0], PX_P[16], PX_P[12], PX_P[16]); return T;
            case 1:box(aBlock, PX_P[1], PX_P[0], PX_P[1], PX_P[2], PX_P[7], PX_P[2]); return T;
            case 2:box(aBlock, PX_N[1], PX_P[0], PX_P[1], PX_N[2], PX_P[7], PX_P[2]); return T;
            case 3:box(aBlock, PX_P[1], PX_P[0], PX_N[1], PX_P[2], PX_P[7], PX_N[2]); return T;
            case 4:box(aBlock, PX_N[1], PX_P[0], PX_N[1], PX_N[2], PX_P[7], PX_N[2]); return T;
        }
        return F;
    }

    public static IIconContainer
    sTextureBorder  = new Textures.BlockIcons.CustomIcon("machines/tools/sculpting_table/colored/border"),
    sTextureLeg     = new Textures.BlockIcons.CustomIcon("machines/tools/sculpting_table/colored/leg"),
    sOverlaySide    = new Textures.BlockIcons.CustomIcon("machines/tools/sculpting_table/overlay/side"),
    sOverlaySurface = new Textures.BlockIcons.CustomIcon("machines/tools/sculpting_table/overlay/surface");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (aRenderPass == 0) {
            return SIDE_TOP == aSide ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureBorder, mRGBa), BlockTextureDefault.get(sOverlaySurface)) : SIDE_BOTTOM == aSide ? BlockTextureDefault.get(sTextureBorder, mRGBa) : BlockTextureMulti.get(BlockTextureDefault.get(sTextureBorder, mRGBa), BlockTextureDefault.get(sOverlaySide));
        }
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureLeg, mRGBa), BlockTextureDefault.get(sOverlaySide));
    }
    @Override public AxisAlignedBB getCollisionBoundingBoxFromPool() {return box(PX_P[0], PX_P[0] ,PX_P[0], PX_P[16], PX_P[12], PX_P[16]);}
    @Override public AxisAlignedBB getSelectedBoundingBoxFromPool() {return box(PX_P[0], PX_P[0] ,PX_P[0], PX_P[16], PX_P[12], PX_P[16]);}
    @Override public int getLightOpacity() {return LIGHT_OPACITY_WATER;}
    @Override public boolean isSurfaceSolid         (byte aSide) {return F;}
    @Override public boolean isSurfaceOpaque2       (byte aSide) {return F;}
    @Override public boolean isSideSolid2           (byte aSide) {return F;}
    @Override public boolean allowCovers            (byte aSide) {return F;}
    @Override public boolean attachCoversFirst      (byte aSide) {return F;}
    @Override public boolean checkObstruction(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {return F;}
    @Override public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {return 5;}
    @Override public boolean canDrop(int aSlot) {return T;}
    @Override public String getTileEntityName() {return "gt.multitileentity.sculptingtable";}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return F;}

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        return !isServerSide() || openGUI(aPlayer);
    }
    @Override
    public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {
        return new ItemStack[11];
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new MultiTileEntityGUICommonSculptingTable(aPlayer.inventory, this, aGUIID);}
    @SideOnly(Side.CLIENT) @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new MultiTileEntityGUIClientSculptingTable(aPlayer.inventory, this, aGUIID);}

    public class MultiTileEntityGUICommonSculptingTable extends ContainerCommon {
        public Slot_Render[] mPanelSlots;
        public MultiTileEntityGUICommonSculptingTable(InventoryPlayer aInventoryPlayer, MultiTileEntitySculptingTable aTileEntity, int aGUIID) {
            super(aInventoryPlayer, aTileEntity, aGUIID);
        }
        @Override
        public int addSlots(InventoryPlayer aPlayerInventory) {
            if (mPanelSlots == null) mPanelSlots = new Slot_Render[25]; //This called by super class' construction method
            addSlotToContainer(new Slot_Normal(mTileEntity, 0 , 8, 8).setTooltip(LH.SCULPTINGTABLE_CHISEL_SLOT, LH.Chat.WHITE));//Chisel Slot
            addSlotToContainer(new Slot_Normal(mTileEntity, 1, 8, 30).setTooltip(LH.SCULPTINGTABLE_MOLD_SLOT, LH.Chat.WHITE));//Mold Slot
            addSlotToContainer(new Slot_Holo(mTileEntity, 2, 8, 60, F, F, 1).setTooltip(LH.SCULPTINGTABLE_MOLD_OUTPUT_SLOT, LH.Chat.WHITE));//Mold Output Slot

            int index = 0;
            for (int i=28;i<107;i+=16) { //Mold Sculpting Panel
                for (int j=2;j<81;j+=16) {
                    Slot_Render tSlot = (Slot_Render) new Slot_Render(mTileEntity, 10 , i, j).setTooltip(LH.SCULPTINGTABLE_PANEL_SLOT_NONE, LH.Chat.RED);
                    addSlotToContainer(tSlot);
                    mPanelSlots[index] = tSlot;
                    index++;
                }
            }
            addSlotToContainer(new Slot_Render(mTileEntity, 10, 112, 8).setTooltip(LH.SCULPTINGTABLE_BLUEPRINT_SAVE_SLOT, LH.Chat.WHITE));//Mold Blueprint Save
            addSlotToContainer(new Slot_Render(mTileEntity, 10, 112, 32).setTooltip(LH.SCULPTINGTABLE_BLUEPRINT_CLEAR_SLOT, LH.Chat.WHITE));//Mold Blueprint Clear
            return super.addSlots(aPlayerInventory);
        }

        @Override
        public int getSlotCount() {
            return 3;
        }
    }
    public boolean isItemValidForSlotGUI(int aSlot, ItemStack aStack) {
        switch (aSlot) {
        case 0: {
            if (ST.valid(aStack) && ToolsGT.contains(TOOL_chisel, aStack)) return true;
            break;
        }
        case 1: {
            if (ST.valid(aStack) && aStack.getItem() instanceof MultiTileEntityItemInternal) {
                MultiTileEntityContainer tContainer = ((MultiTileEntityItemInternal) aStack.getItem()).mBlock.mMultiTileEntityRegistry.getNewTileEntityContainer(aStack);
                if (tContainer != null && tContainer.mTileEntity instanceof MultiTileEntityMold) return true;
            }
            break;
        }
        }
        return false;
    }
    @SideOnly(Side.CLIENT)
    public class MultiTileEntityGUIClientSculptingTable extends ContainerClient {
        public MultiTileEntityGUIClientSculptingTable(InventoryPlayer aInventoryPlayer, MultiTileEntitySculptingTable aTileEntity, int aGUIID) {
            super(new MultiTileEntityGUICommonSculptingTable(aInventoryPlayer, aTileEntity, aGUIID), RES_PATH_GUI + "machines/SculptingTable.png");
        }
    }
}
