/**
 * 
 */
package com.wegames.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Neptune
 *
 */
public class Node {
	public int x, y;
	
	
	
	Map<Resource, Integer> supply = new HashMap<Resource, Integer>();
	Map<Resource, Integer> demand = new HashMap<Resource, Integer>();
	Map<Resource, Integer> excess = new HashMap<Resource, Integer>();
	
	public double getDistance(Node node) {
		int dx = node.x-x;
		int dy = node.y-y;
		
		return Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
	}
	
	public void generateResources() {
		//TODO Create method to generate resource amount in certain node.
	}
	
	public void calculateResources() {
		for
	}
	
	public void tick() {
		
	}
}
