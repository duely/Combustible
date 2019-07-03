package com.noobanidus.combustible;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWoodenFurnace extends BlockFurnace {
  private final boolean isBurning;
  protected static boolean keepInventory = true;

  protected BlockWoodenFurnace(boolean isBurning) {
    super(isBurning);
    this.isBurning = isBurning;
    setSoundType(SoundType.WOOD);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    if (CombustibleConfig.isFlammable) {
      return CombustibleConfig.flammability;
    }
    return super.getFlammability(world, pos, face);
  }

  @Override
  public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return CombustibleConfig.isFlammable;
  }

  @Override
  public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
    if (CombustibleConfig.isFlammable) {
      return CombustibleConfig.encouragement;
    }
    return super.getFireSpreadSpeed(world, pos, face);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityWoodenFurnace();
  }

  public static void setState(boolean active, World worldIn, BlockPos pos) {
    IBlockState iblockstate = worldIn.getBlockState(pos);
    TileEntity tileentity = worldIn.getTileEntity(pos);
    BlockWoodenFurnace.keepInventory = true;

    if (active) {
      worldIn.setBlockState(pos, Combustible.furnace_lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      worldIn.setBlockState(pos, Combustible.furnace_lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    } else {
      worldIn.setBlockState(pos, Combustible.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      worldIn.setBlockState(pos, Combustible.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    }

    BlockWoodenFurnace.keepInventory = false;

    if (tileentity != null) {
      tileentity.validate();
      worldIn.setTileEntity(pos, tileentity);
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!BlockWoodenFurnace.keepInventory) {
      TileEntity tileentity = worldIn.getTileEntity(pos);

      if (tileentity instanceof TileEntityFurnace) {
        InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace) tileentity);
        worldIn.updateComparatorOutputLevel(pos, this);
      }

      worldIn.removeTileEntity(pos);
    }
  }
}
