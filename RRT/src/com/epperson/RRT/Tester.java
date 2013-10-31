package com.epperson.RRT;

import java.io.IOException;
import java.util.ArrayList;

import com.epperson.state.Point3D;
import com.epperson.state.State;
import com.epperson.tree.Tree;

public class Tester {
hello
	public static void main(String[] args) throws IOException {
		/*
		 * PathPlanner planner = new PathPlanner(0,0,0);
		 
		
		Tree tree = new Tree();
		MiscMethods tools = new MiscMethods();
		
		double[] x = {0.0, 1.0, 2.0};
		double[] y = {0.0, 1.0, 2.0};
		double[] z = {0.0, 1.0, 2.0};
		PathPlanner multiPlanner = new PathPlanner(x,y,z,3);
		
		
	    Long time = System.nanoTime();
	    Tree graph = planner.rrtStar(1000, tree);
        ArrayList<Tree> graphs = multiPlanner.multiRRT(1000,1);
	    Long time2 = System.nanoTime();
		System.out.println((time2-time)*.000000001);
	    graph.writeToFile();
		
		/*
		//Cost Testing
		ArrayList<State> states = new ArrayList<State>();
		for(int i=0;i<10;i++) {
			states.add(new State(new Point3D(i,i,i)));
		}
		tree.add(states.get(0));
		tree.add(states.get(1), states.get(0));
		tree.add(states.get(2), states.get(1));
		tree.add(states.get(3), states.get(2));
		tree.add(states.get(4), states.get(3));
		tree.add(states.get(5), states.get(2));
		tree.add(states.get(6), states.get(3));
		tree.changeParent(states.get(5), states.get(1));
		*/
	    
		//ArrayList<State> pre = tree.preOrderTraversal();
		//System.out.println("Size: "+pre.size());
		
	}

}