package com.noobanidus.combustible;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWoodenFurnace extends FurnaceBase {
  protected static boolean keepInventory = true;

  protected BlockWoodenFurnace(boolean isBurning) {
    super(isBurning);
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Combustible.furnaceItem;
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

      if (tileentity instanceof TileEntityWoodenFurnace) {
        InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityWoodenFurnace) tileentity);
        worldIn.updateComparatorOutputLevel(pos, this);
      }

      worldIn.removeTileEntity(pos);
    }
  }

  @Override
  public ItemStack getItem (World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(Combustible.furnaceItem);
  }

  @Override
  public Item getItemDropped (IBlockState state, Random rand, int fortune) {
    return Combustible.furnaceItem;
  }
}
