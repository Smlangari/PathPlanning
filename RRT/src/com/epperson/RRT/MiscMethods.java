package com.epperson.RRT;

import java.util.ArrayList;
import java.util.Random;

import com.epperson.state.Point3D;
import com.epperson.state.State;
import com.epperson.tree.Tree;

/**
 * This class is here to provide miscellaneous methods needed to create and maintain RRT. Notes - May consider
 * creating my own tree data structure in which case some of these can be moved into there
 * @author Epperson
 *
 */
public class MiscMethods {
	public static final double maxLength = 1.0;
	public static final double size = 5;
	public static final double width = size, length = size, height = 0;
	public static final int numDimensions = 3;
	
	
	/**
	 * This function takes a tree and sorts through its States to find a the closestState
	 * to the random State and returns that state.
	 * @param tree
	 * @param randomState
	 * @return
	 */
	public State nearestState(Tree tree, State randomState) {
		State nearestState = null;
		double dist = 9999;
		double tempDist = 9999;

		for(int i=0;i<tree.getSize();i++) {
			State state = tree.getState(i);
			tempDist = distance(randomState, state);
			if(tempDist < dist) {
				dist = tempDist;
				nearestState = state;
			}	
		}
		return nearestState;
	}

	
	public ArrayList<State> nearStates(State newState, double gamma, Tree tree) {
		ArrayList<State> nearStates = new ArrayList<State>();
	    double ballRadius = gamma * Math.pow( Math.log((double)(tree.getSize() + 1.0))/((double)(tree.getSize() + 1.0)), 1.0/((double)numDimensions) );

	    for(int i=0;i<tree.getSize();i++) {
	    	if(distance(newState, tree.getState(i))<ballRadius && !(tree.getState(i).equals(newState))) {
	    		nearStates.add(tree.getState(i));
	    	}
	    }
	    return nearStates;
	}
	
	public Tree rewire(Tree tree, ArrayList<State> nearStates, State nearest, State newState) {
		/* 1) First check if the nearestState is the cheapest parent for newState
		* if it is add to tree, if not then add the cheapest as parent
		* 2) Next go thro each node in near and decide if its cheaper for its parent to be newNode
		* if its is then change its parent to new Node 
		*/
		State cheapestParent = nearest;
		
		for(int i=0;i<nearStates.size();i++) {
			if(nearStates.get(i).cost()+distance(nearStates.get(i), newState)<cheapestParent.cost()+distance(cheapestParent, newState)) {
				cheapestParent = nearStates.get(i); 
			}
		} 
		tree.add(newState, nearest);
		
		for(int i=0;i<nearStates.size();i++) {
			if(nearStates.get(i).cost()>newState.cost()+distance(nearStates.get(i),newState)) {
				//System.out.println("rewiring");
				tree.changeParent(nearStates.get(i), newState);
			}
		}
		return tree;
	}
	
	
	
	/**
	 * ToDo FIX THIS METHOD
	 * @param tree
	 * @param extendState
	 * @param radius
	 * @return
	 */
	public ArrayList<State> nearestStates(Tree tree, State extendState, double radius) {
		ArrayList<State> nearestStates = new ArrayList<State>();
		State tempState = null;
				
		for(int i=0;i<tree.getSize();i++) {
			tempState = tree.getState(i);
			if(distance(tempState, extendState)<radius) {
				nearestStates.add(tempState);
			}
		}
		
		return nearestStates;
	}
		
	/**
	 * This function basically takes a state close to the goal were aiming for
	 * and returns a list of the nodes from the path back to the root
	 * @param tree
	 * @param nearestGoal
	 * @return
	 */
	public ArrayList<State> createPath(Tree tree, State nearestGoal) {
		ArrayList<State> path = new ArrayList<State>();
		State state = nearestGoal;
		path.add(state);
	
		while(true) {
			state = tree.getParent(state);
			if(state == null) {
				return path;
			}
			path.add(state);
		}		
	}
	
	/**
	 * This takes two 3D points and returns the distance between them.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double distance(State s1, State s2) {
		Point3D p1 = s1.getP3D(), p2 = s2.getP3D();
		return Math.sqrt( Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY()-p2.getY(),2) + Math.pow(p1.getZ()-p2.getZ(),2));
	}
	
	/**
	 * This takes two 3D points and returns the distance between them.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double distance(Point3D p1, Point3D p2) {
		return Math.sqrt( Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY()-p2.getY(),2) + Math.pow(p1.getZ()-p2.getZ(),2) );
	}
	
	/**
	 * ToDo
	 * @return
	 */
	public State createRandom() {
		Random generator = new Random();		
	    return new State( new Point3D(generator.nextDouble()*width-(size/2.0), generator.nextDouble()*length-(size/2.0), generator.nextDouble()*(height)) );

	}
	
	/**
	 * 
	 */
	public State extend2(State nearestState, State randomState, double length) {
		Point3D nearest = nearestState.getP3D();
		Point3D random =  randomState.getP3D();
		
		if(distance(nearest,random)<length) {
			return randomState;
		}
		
		double vectorLength = distance(nearest, random)*(length*100);
		
		
		Point3D vector = new Point3D( ((random.getX()-nearest.getX())/vectorLength),
				                      ((random.getY()-nearest.getY())/vectorLength),
				                      ((random.getZ()-nearest.getZ())/vectorLength)  );
		
		//System.out.println(vector);
		//System.out.println("Distance: "+distance(vector, new Point3D(0,0,0)));
	
		State newState = new State(new Point3D(vector.getX()+nearest.getX(), 
							                   vector.getY()+nearest.getY(), 
							                   vector.getZ()+nearest.getZ()));
		
		//System.out.println(distance(nearest,newState.getP3D()));
		return newState;
	}
	
	/**
	 * 
	 * @param nearestState
	 * @param randomState
	 * @return
	 */
	public State extend(State nearestState, State randomState)
	{
		// Get points from States
		Point3D nearest = nearestState.getP3D(); 
		Point3D random = randomState.getP3D();
		
		// Create Point to be returned by function
		Point3D newPoint = new Point3D(nearest.getX(), nearest.getY(), nearest.getZ());

		// Helper arrays for algorithm
		double[] dist = new double[3];
		double[] newDist = new double[3];

		// Initialize array values
		newDist[0] = nearest.getX();
		newDist[1] = nearest.getY();
		newDist[2] = nearest.getZ();
		
		dist[0] = random.getX()-nearest.getX();
		dist[1] = random.getY()-nearest.getY();
		dist[2] = random.getZ()-nearest.getZ();
		
		// Compute the distance between two point
		double distance = distance(nearest, random);
		//System.out.println("Distance: "+distance);
		
		// Set the distance to integrate by and then round that number off
		double incrementTotal = distance/.01;
        int numSegments = (int) Math.floor(incrementTotal);
		//System.out.println("Increment total: "+incrementTotal);
		
		// Figure out distance to increment each value by
		for(int i=0;i<3;i++) {
			dist[i] /= incrementTotal;
		}
		
		// Enter a for loop which increments the values along there line until it hits a certain length
        for(int i=0;i<numSegments;i++) {
        	Point3D p = new Point3D(newDist[0], newDist[1],newDist[2]);
        	// If the distance is at maxLength then return the new Point
        	if(distance(nearest, p)>=maxLength) {
        		//System.out.println(p.getX()+" Y: "+p.getY()+" Z: "+p.getZ());
        		return new State(p);
        	}
        	for(int j=0;j<3;j++) {
        		newDist[j] += dist[j];
        	}
        }
        newPoint.setX(newDist[0]);
        newPoint.setY(newDist[1]);
        newPoint.setZ(newDist[2]);
        
		return new State(newPoint);
	}

	private boolean collisionChecker(State randomState) {
		// TODO Auto-generated method stub
		return false;
	}
}
