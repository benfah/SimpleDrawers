package me.benfah.simpledrawers;

import me.benfah.simpledrawers.init.SDBlockEntities;
import me.benfah.simpledrawers.init.SDBlocks;
import me.benfah.simpledrawers.init.SDItems;
import me.benfah.simpledrawers.init.SDTags;
import me.benfah.simpledrawers.models.border.Border;
import net.fabricmc.api.ModInitializer;

public class SimpleDrawersMod implements ModInitializer
{

	public static final String MOD_ID = "simpledrawers";

	@Override
	public void onInitialize()
	{
		Border.init();
		
		SDBlocks.init();
		SDBlockEntities.init();
		SDItems.init();
		SDTags.init();
	}

}
