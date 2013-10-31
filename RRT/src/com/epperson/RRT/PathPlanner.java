package com.epperson.RRT;

import java.util.ArrayList;

import org.omg.CORBA.Object;

import net.sf.javaml.core.kdtree.KDTree;

import com.epperson.state.Point3D;
import com.epperson.state.State;
import com.epperson.state.Velocity;
import com.epperson.tree.Tree;

public class PathPlanner {
	private State root;
	private State[] roots;
	private MiscMethods tools = new MiscMethods();
	
	public PathPlanner() {
		/* Do Nothing */
	}

	public PathPlanner(double x, double y, double z) {
		root = new State(new Point3D(x,y,z));
	}
	
	public PathPlanner(double[] x, double[] y, double[] z, int size) {
		for(int i=0;i<size;i++) {
			roots[size] = new State(new Point3D(x[i],y[i],z[i]));
		}
	}
	
	/**
	 * Build function for Rapidly Exploring Random Tree. Takes in the number of iterations to run and a Tree object.
	 * It returns true if goal was met.
	 * @param iterations
	 * @param tree
	 * @return
	 */
	public Tree rrt(int iterations) {
		/* 1: Initialize tree
		 * 2: Select randomState
		 * 3: Find nearestState
		 * 4: Extend towards randomState
		 * 5: Check extendState
		 * 6: Add extendState as child of nearestState
		 * 7: Rinse and Repeat till goalFound
		 */
		Tree tree = new Tree();
		KDTree kdtree = new KDTree(3);
		kdtree.insert(root.getP3D().toArray(), root);

		tree.add(root);
		State randomState;
		State nearestState;
		State newState;
		
		for(int i=0;i<iterations;i++) {
			randomState = tools.createRandom();
			
			nearestState = (State) kdtree.nearest(randomState.getP3D().toArray());	
			newState = tools.extend2(nearestState, randomState,.1);
			tree.add(newState, nearestState);
			kdtree.insert(newState.getP3D().toArray(), newState);
		}
		return tree;
	}
	
	public ArrayList<Tree> multiRRT(int iterations, int numTrees) {
		/* 1: Initialize tree
		 * 2: Select randomState
		 * 3: Find nearestState
		 * 4: Extend towards randomState
		 * 5: Check extendState
		 * 6: Add extendState as child of nearestState
		 * 7: Rinse and Repeat till goalFound
		 */
		ArrayList<Tree> trees = new ArrayList<Tree>();
		ArrayList<KDTree> kdtrees = new ArrayList<KDTree>();
		
		for(int i=0;i<numTrees;i++) {
			trees.add(new Tree());
			trees.get(i).add(roots[i]);
		    kdtrees.add(new KDTree(3));
		    kdtrees.get(i).insert(roots[i].getP3D().toArray(), roots[i]);
		}

		State randomState;
		State nearestState;
		State newState;
		
		for(int i=0;i<iterations;i++) {
			for(int j=0;j<numTrees;j++) {
				randomState = tools.createRandom();
				nearestState = (State) kdtrees.get(j).nearest(randomState.getP3D().toArray());
				//collisionCheck();
				newState = tools.extend2(nearestState, randomState,.1);
				trees.get(j).add(newState, nearestState);
				kdtrees.get(j).insert(newState.getP3D().toArray(), newState);
			}
		}
		return trees;		
	}
	
	public Tree rrtStar(int iterations, Tree tree) {
		/* 1: Initiate tree
		 * 2: Select randomState
		 * 3: Find nearestState
		 * 4: Extend towards randomState
		 * 5: Add newState as child of nearestState
		 * 6: Find nearStates of newState
		 * 7: Rewire all states in near
		 * 8: Rinse and Repeat till goalFound
		 */
		tree.add(root);
		State randomState;
		State nearestState;
		State newState;
		
		for(int i=0;i<iterations;i++) {
			/*2*/ randomState = tools.createRandom();
			/*3*/ nearestState =tools.nearestState(tree, randomState);
			/*4*/ newState = tools.extend2(nearestState, randomState,.1);
			/*5*/ //tree.add(newState, nearestState);
			/*6-7*/ tree = tools.rewire(tree, tools.nearStates(newState,1.5,tree), nearestState, newState);
		}
		return tree;
	}
		
}
