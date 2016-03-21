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
	public String name="noname";
	public double rarity=1.0;
	
	public static List<Resource> resources = new ArrayList<Resource>();
	
	//Format: RECOURCE_NAME:RESOURCE_RARITY
	Resource wood = new Resource("Wood:5.0");
	
	/**
	 * 
	 */
	public Resource(String data) {
		String[] sData = data.split(":");
		if(sData.length<1) {
			System.err.println("No Data Given for Resource!");
			return;
		}
		name=sData[1];
		if(sData.length>=2) rarity=Double.parseDouble(sData[1]);
		resources.add(this);
	}
}
