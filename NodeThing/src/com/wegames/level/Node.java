/**
 * 
 */
package com.wegames.level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Neptune
 *
 */
public class Node {
	public int x, y;
	
	public static List<Node> nodes = new ArrayList<Node>();
	
	int maxDistance=400;
	
	Map<Integer, Integer> rawSupply = new HashMap<Integer, Integer>();
	Map<Integer, Integer> productionHarvesting = new HashMap<Integer, Integer>();
	
	Map<Integer, Integer> supply = new HashMap<Integer, Integer>();
	Map<Integer, Integer> demand = new HashMap<Integer, Integer>();
	Map<Integer, Integer> excess = new HashMap<Integer, Integer>();
	Map<Integer, Integer> productionExcess = new HashMap<Integer, Integer>();
	
	List<Node> nearbyNodes=new ArrayList<Node>();
	
	public double getDistance(Node node) {
		int dx = node.x-x;
		int dy = node.y-y;
		
		return Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
	}
	
	public static List<Node> getNearbyNodes(Node target) {
		List<Node> result = new ArrayList<Node>();
		for (int i = 0; i < nodes.size(); i++) {
			if(target.getDistance(nodes.get(i))<=target.maxDistance) result.add(nodes.get(i));
		}
		
		result.sort(new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				if(target.getDistance(o1)<target.getDistance(o2)) return 1;
				else if(target.getDistance(o1)>target.getDistance(o2)) return-1;
				else return 0;
			}
		});
		
		return result;
	}
	
	private void generateResources() {
		Random random = new Random();
		int standardSize = random.nextInt(100)/8;
		for (int i = 0; i < Resource.resources.size(); i++) {
			if(!Resource.resources.get(i).isRaw()) continue;
			int amount = (int) ((random.nextDouble()/2+1.5)*standardSize*1000*Resource.resources.get(i).rarity);
			rawSupply.put(i, amount);
		}
	}
	
	public void calculateResources() {
		for (int i = 0; i < Resource.resources.size(); i++) {
			if(supply.get(i)==null&&demand.get(i)==null) {
				return;
			} else if(supply.get(i)==null&&demand.get(i)!=null) {
				excess.put(i, -demand.get(i));
			} else if(supply.get(i)!=null&&demand.get(i)==null) {
				excess.put(i, supply.get(i));
			} else {
				excess.put(i, supply.get(i)-demand.get(i));
			}
		}
	}
	int toUpdate=0;
	public void tick() {
		if(toUpdate<1800) {
			toUpdate++;
		} else {
			toUpdate=0;
			calculateResources();
		}
	}
	public Node() {
		nodes.add(this);
		generateResources();
	}
}
