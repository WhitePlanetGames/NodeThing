/**
 * 
 */
package com.wegames.level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neptune
 *
 */
public class Resource {
	public int id = -1;
	public String name = "noname";
	public double rarity = 0.0;

	public static List<Resource> resources = new ArrayList<Resource>();

	/**
	 * Format: RECOURCE_NAME:RESOURCE_RARITY
	 */
	public static Resource WOOD_RAW = new Resource("Wood:20.0");
	public static Resource IRON_RAW = new Resource("Iron:8.0");
	public static Resource GOLD_RAW = new Resource("Gold:0.");
	public static Resource COPPER_RAW = new Resource("Copper:4.0");
	public static Resource TIN_RAW = new Resource("Tin:4.0");

	/**
	 * 
	 */
	public Resource(String data) {
		String[] sData = data.split(":");
		if (sData.length < 1) {
			System.err.println("No Data Given for Resource!");
			return;
		}
		name = sData[1];
		if (sData.length >= 2)
			rarity = Double.parseDouble(sData[1]);
		id = resources.size();
		resources.add(this);
	}

	public boolean isRaw() {
		return !new Double(0.0).equals(rarity);
	}
}
