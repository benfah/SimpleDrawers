package me.benfah.simpledrawers.api.drawer;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum DrawerType implements StringIdentifiable
{
	
	FULL("full"), HALF("half");
	
	public static EnumProperty<DrawerType> DRAWER_TYPE = EnumProperty.of("drawer_type", DrawerType.class);
	
	private String name;
	
	private DrawerType(String name)
	{
		this.name = name;
	}
	
	@Override
	public String asString()
	{
		return name;
	}
	
}
