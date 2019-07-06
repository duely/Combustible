package com.noobanidus.combustible;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Combustible.MODID)
public class CombustibleConfig {
  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(Combustible.MODID)) {
      ConfigManager.sync(Combustible.MODID, Config.Type.INSTANCE);
    }
  }

  @Config.Comment("Set to false to prevent wooden furnaces from randomly setting themselves on fire [default true]")
  @Config.Name("Furnaces are combustible")
  public static boolean combustibleFurnaces = true;

  @Config.Comment("The random chance per tick that the furnace will catch on fire (if combustibleFurnaces is set to true) [default 1800, 0.05%]")
  @Config.Name("Furnace combustion chance")
  public static int combustionChance = 1800;

  @Config.Comment("Set to true to instead use the furnace multiplier against the default furnace cook time instead of a static value [default false]")
  @Config.Name("Use multiplier for cook time")
  public static boolean useCookMultiplier = false;

  @Config.Comment("The default rate at which an item takes to be smelted [default 400]")
  @Config.Name("Furnace cook time (per item)")
  public static int cookTime = 400;

  @Config.Comment("The multiplier to be applied to the default result of getCookTime [default 2]")
  @Config.Name("Furnace cook time (multiplier)")
  public static int cookMultiplier = 2;

  @Config.Comment("Set to true to make the furnace flammable (i.e., it can burn) [default true]")
  @Config.Name("Is flammable")
  public static boolean isFlammable = true;

  @Config.Comment("The flammability of the furance (i.e., how quickly it will burn and be destroyed; requires Is Flammable to be true) [default 5]")
  @Config.Name("Flammability")
  public static int flammability = 5;

  @Config.Comment("The \"encouragement\" of this block, (i.e., the speed of fire spreading) [default 5]")
  @Config.Name("Encouragement")
  public static int encouragement = 5;

  @Config.Comment("Set to true to extend the range of fire placement by 1 (a 5x5 cube around the furnace) [default false]")
  @Config.Name("Increase fire placement range")
  public static boolean increaseFirePlacementRange = false;
}
