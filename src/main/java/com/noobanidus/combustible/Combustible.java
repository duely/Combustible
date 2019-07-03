package com.noobanidus.combustible;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = Combustible.MODID, name = Combustible.MODNAME, version = Combustible.VERSION)
@SuppressWarnings("WeakerAccess")
public class Combustible {
  public static final String MODID = "combustible";
  public static final String MODNAME = "Combustible";
  public static final String VERSION = "GRADLE:VERSION";
  public static final CreativeTabs TAB = new CreativeTabs(CreativeTabs.getNextID(), "Combustible") {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(furnaceItem);
    }
  };

  public final static Logger LOG = LogManager.getLogger(MODID);

  @Mod.Instance(MODID)
  public static Combustible instance;

  public static Block furnace = new BlockWoodenFurnace(false).setRegistryName(MODID, "wooden_furnace").setCreativeTab(TAB).setTranslationKey("wooden_furnace");
  public static Block furnace_lit = new BlockWoodenFurnace(true).setRegistryName("wooden_furnace_lit").setTranslationKey("wooden_furnace");
  public static Item furnaceItem = new ItemBlock(furnace).setRegistryName(MODID, "wooden_furnace").setCreativeTab(TAB).setTranslationKey("wooden_furnace");

  @Mod.EventHandler
  public static void preInit (FMLPreInitializationEvent event) {
  }

  @Mod.EventHandler
  public static void loadComplete(FMLLoadCompleteEvent event) {
    LOG.info("Combustible loaded.");
  }

  @SubscribeEvent
  public static void onBlockRegistration (RegistryEvent.Register<Block> event) {
    event.getRegistry().register(furnace);
    event.getRegistry().register(furnace_lit);
    GameRegistry.registerTileEntity(TileEntityWoodenFurnace.class, new ResourceLocation(MODID, "wooden_furnace"));
  }

  @SubscribeEvent
  public static void onItemRegistration(RegistryEvent.Register<Item> event) {
    event.getRegistry().register(furnaceItem);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void onModelRegistration(ModelRegistryEvent event) {
    ModelLoader.setCustomModelResourceLocation(furnaceItem, 0, new ModelResourceLocation(new ResourceLocation(MODID, "wooden_furnace"), "inventory"));
  }
}
