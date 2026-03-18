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
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Objects;

import static gregapi.data.CS.*;

public class MultiTileEntitySculptingTable extends TileEntityBase07Paintable {
    public static int sMoldSculptingPanelStart;

    public int mShape = 0;
    public int mBlueprintShape = 0;
    public boolean mGUIUpdateNeeded = F;

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setInteger("gt.sculptingtable.shape", mShape);
        aNBT.setInteger("gt.sculptingtable.shape.blueprint", mBlueprintShape);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mShape = aNBT.getInteger("gt.sculptingtable.shape");
        mBlueprintShape = aNBT.getInteger("gt.sculptingtable.shape.blueprint");
    }

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

    @Override public boolean interceptClick(int aGUIID, Slot_Base aSlot, int aSlotIndex, int aInvSlot, EntityPlayer aPlayer, boolean aShiftclick, boolean aRightclick, int aMouse, int aShift) {
        mGUIUpdateNeeded = T;
        if (aInvSlot == 10 || aInvSlot == 2) return T;
        return F;
    }

    @Override
    public ItemStack slotClick(int aGUIID, Slot_Base aSlot, int aSlotIndex, int aInvSlot, EntityPlayer aPlayer, boolean aShiftclick, boolean aRightclick, int aMouse, int aShift) {
        if (aInvSlot == 10 && !aShiftclick && !aRightclick) {
            int tIndex = aSlotIndex - sMoldSculptingPanelStart;
            if (tIndex>=0 && tIndex<25) {
                if (mBlueprintShape != 0) return null;
                ItemStack tChisel = slot(0), tMold = slot(1);
                if (tChisel == null || tMold == null) return null;
                int tMoldShape = tMold.stackTagCompound == null ? 0 : tMold.stackTagCompound.getInteger("gt.mold");
                if ((tMoldShape & B[tIndex]) != 0) return null;
                mShape ^= B[tIndex];
                mGUIUpdateNeeded = T;
            }
            else if (tIndex == 25) {
                ItemStack tMold = slot(1);
                if (tMold == null) return null;
                int tMoldShape = tMold.stackTagCompound == null ? 0 : tMold.stackTagCompound.getInteger("gt.mold");
                mBlueprintShape = tMoldShape | mShape;
                mGUIUpdateNeeded = T;
            }
            else if (tIndex == 26) {
                mBlueprintShape = 0;
                mShape = 0;
                mGUIUpdateNeeded = T;
            }
        }
        if (aInvSlot == 2 && !aRightclick) {
            if (mShape == 0 && mBlueprintShape == 0) return null;
            ItemStack tOutput = slot(2), tInput = slot(1), tChisel = slot(0), tStack = aPlayer.inventory.getItemStack();
            if (tOutput == null || tInput == null) return null;
            if (tStack != null && (tStack.getItem() != tOutput.getItem() || !Objects.equals(tStack.stackTagCompound, tOutput.stackTagCompound))) return null;
            ItemStack tMoldSculpted = tOutput.copy();
            if (aShiftclick) {
                int tMaxCount = tInput.stackSize;
                for (int i=1;i<=tMaxCount;i++) {
                    if (slot(0) == null) break;
                    tInput.stackSize -= 1;
                    if (tInput.stackSize <= 0) setInventorySlotContents(1, null);
                    tChisel.damageItem(100, aPlayer);
                    tMoldSculpted.stackSize = i;
                }
            }
            else {
                tInput.stackSize -= 1;
                if (tInput.stackSize <= 0) setInventorySlotContents(1, null);
                tChisel.damageItem(100, aPlayer);
                tMoldSculpted.stackSize = 1;
            }
            tMoldSculpted.stackSize += tStack == null ? 0 : tStack.stackSize;
            aPlayer.inventory.setItemStack(tMoldSculpted);
            return aPlayer.inventory.getItemStack();
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlotGUI(int aSlot, ItemStack aStack) {
        switch (aSlot) {
            case 0: {
                if (ST.valid(aStack) && ToolsGT.contains(TOOL_chisel, aStack)) return T;
                break;
            }
            case 1: {
                if (ST.valid(aStack) && aStack.getItem() instanceof MultiTileEntityItemInternal) {
                    MultiTileEntityContainer tContainer = ((MultiTileEntityItemInternal) aStack.getItem()).mBlock.mMultiTileEntityRegistry.getNewTileEntityContainer(aStack);
                    if (tContainer != null && tContainer.mTileEntity instanceof MultiTileEntityMold) return T;
                }
                break;
            }
        }

        return F;
    }

    public class MultiTileEntityGUICommonSculptingTable extends ContainerCommon {
        public Slot_Render[] mPanelSlots;
        public int mShape = 0;
        public int mBlueprintShape = 0;
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
            sMoldSculptingPanelStart = inventorySlots.size();
            for (int j=2;j<81;j+=16) { //Mold Sculpting Panel
                for (int i=28;i<107;i+=16) {
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

        @Override @SuppressWarnings("unchecked")
        public void detectAndSendChanges() {
            MultiTileEntitySculptingTable tTileEntity = (MultiTileEntitySculptingTable) mTileEntity;
            if (mShape != tTileEntity.mShape || mBlueprintShape != tTileEntity.mBlueprintShape) tTileEntity.mGUIUpdateNeeded = T;
            if (!tTileEntity.mGUIUpdateNeeded) return;
            tTileEntity.mGUIUpdateNeeded = F;
            mShape = tTileEntity.mShape;
            mBlueprintShape = tTileEntity.mBlueprintShape;
            for (ICrafting tUpdate : (List<ICrafting>) crafters) {
                tUpdate.sendProgressBarUpdate(this, 0, tTileEntity.mShape);
                tUpdate.sendProgressBarUpdate(this, 1, tTileEntity.mBlueprintShape);
            }
            if (slot(0) == null || slot(1) == null) {
                tTileEntity.mShape = 0;
                setInventorySlotContents(2, null);
            }
            else {
                ItemStack tMoldSculpted = slot(1).copy();
                tMoldSculpted.stackSize = 0;
                NBTTagCompound tMoldNBT = slotNBT(1);
                tMoldSculpted.stackTagCompound = tMoldNBT == null ? new NBTTagCompound() : (NBTTagCompound) tMoldNBT.copy();
                tMoldSculpted.stackTagCompound.setInteger("gt.mold", mBlueprintShape == 0 ? mShape | tMoldSculpted.stackTagCompound.getInteger("gt.mold") : mBlueprintShape);
                setInventorySlotContents(2, tMoldSculpted);
            }
            super.detectAndSendChanges();
        }

        @SideOnly(Side.CLIENT) @Override
        public void updateProgressBar(int aID, int aValue) {
            switch (aID) {
                case 0: mShape = aValue; break;
                case 1: mBlueprintShape = aValue; break;
            }
            if (mBlueprintShape == 0) {
                ItemStack tChisel = ((MultiTileEntitySculptingTable) mTileEntity).slot(0);
                ItemStack tMold = ((MultiTileEntitySculptingTable) mTileEntity).slot(1);
                if (tChisel == null || tMold == null) {
                    for (int i = 0; i < 25; i++)
                        mPanelSlots[i].setTooltip(LH.SCULPTINGTABLE_PANEL_SLOT_NONE, LH.Chat.RED);
                    return;
                }
                int tMoldShape = tMold.stackTagCompound == null ? 0 : tMold.stackTagCompound.getInteger("gt.mold");
                for (int i = 0; i < 25; i++) {
                    if ((tMoldShape & B[i]) != 0) mPanelSlots[i].setTooltip(LH.SCULPTINGTABLE_PANEL_SLOT_SCULPTED, LH.Chat.RED);
                    else mPanelSlots[i].setTooltip(LH.SCULPTINGTABLE_PANEL_SLOT, LH.Chat.WHITE);
                }
            }
            else {
                for (int i = 0; i < 25; i++) mPanelSlots[i].setTooltip(LH.SCULPTINGTABLE_PANEL_SLOT_LOCKED, LH.Chat.RED);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public class MultiTileEntityGUIClientSculptingTable extends ContainerClient {
        public MultiTileEntityGUIClientSculptingTable(InventoryPlayer aInventoryPlayer, MultiTileEntitySculptingTable aTileEntity, int aGUIID) {
            super(new MultiTileEntityGUICommonSculptingTable(aInventoryPlayer, aTileEntity, aGUIID), RES_PATH_GUI + "machines/SculptingTable.png");
        }

        @Override
        protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
            super.drawGuiContainerBackgroundLayer2(par1, par2, par3);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            MultiTileEntityGUICommonSculptingTable tContainer = (MultiTileEntityGUICommonSculptingTable) mContainer;
            ItemStack tChisel = ((MultiTileEntitySculptingTable) mContainer.mTileEntity).slot(0);
            ItemStack tMold = ((MultiTileEntitySculptingTable) mContainer.mTileEntity).slot(1);
            if (tChisel == null || tMold == null) return;
            int tMoldShape = tMold.stackTagCompound == null ? 0 : tMold.stackTagCompound.getInteger("gt.mold");
            for (int i=0;i<25;i++) {
                if ((tContainer.mShape & B[i]) != 0) drawDarkSlot(i, x, y);
                if ((tMoldShape & B[i])!=0) drawRedSlot(i, x, y);
                if ((tContainer.mBlueprintShape & B[i])!=0) drawBlueSlot(i, x, y);
            }
        }

        protected void drawRedSlot(int aIndex, int aGUIX, int aGUIY) {
            int tX = 28 + (aIndex % 5) * 16;
            int tY = 2 + (aIndex / 5) * 16;
            drawTexturedModalRect(tX + aGUIX, tY + aGUIY, 112, 32, 16, 16);
        }
        protected void drawDarkSlot(int aIndex, int aGUIX, int aGUIY) {
            int tX = 28 + (aIndex % 5) * 16;
            int tY = 2 + (aIndex / 5) * 16;
            drawTexturedModalRect(tX + aGUIX, tY + aGUIY, 176, 16, 16, 16);
        }
        protected void drawBlueSlot(int aIndex, int aGUIX, int aGUIY) {
            int tX = 28 + (aIndex % 5) * 16;
            int tY = 2 + (aIndex / 5) * 16;
            drawTexturedModalRect(tX + aGUIX, tY + aGUIY, 176,  0, 16, 16);
        }
    }
}
