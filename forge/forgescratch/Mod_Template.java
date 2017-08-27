package org.golde.forge.scratchforge.mods;

import java.util.Random;

import org.golde.forge.scratchforge.base.BlockBase;
import org.golde.forge.scratchforge.base.ModHelpers;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = Mod_Template.MOD_ID, name=Mod_Template.MOD_NAME, version="1.0")
public class Mod_Template {
	public static final String MOD_NAME = "Mod Template";
	public static final String MOD_ID = "sf_" + MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
	
	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.iron_axe;
		}
		
	};
	
    /*Variables*/
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModHelpers.addTranslation(CREATIVE_TAB.getTranslatedTabLabel(), MOD_NAME);
        /*Constructor calls*/
	}
    
    /*Classes*/
}
