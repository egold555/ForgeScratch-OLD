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

@Mod(modid = Mod_project.MOD_ID, name=Mod_project.MOD_NAME, version="1.0")
public class Mod_project {
	public static final String MOD_NAME = "project";
	public static final String MOD_ID = "sf_" + MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
	
	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.iron_axe;
		}
		
	};
	
    static Mcblock_change_me mcblock_change_me;

    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModHelpers.addTranslation(CREATIVE_TAB, MOD_NAME);
        mcblock_change_me = new Mcblock_change_me();

	}
    
    





    public class Mcblock_change_me extends BlockBase {
        public Mcblock_change_me() {
            super(BLOCK_ID, CREATIVE_TAB, "change_me", Material.wood);
        }

        @Override
        public int quantityDropped(Random r){
            return Math.max(0,(int)10);
        }
    }

}
