package com.epperson.state;


/**
 * This class will act as nodes inside of the tree. However, it will contain important state information such
 * as position, velocity, etc..
 * @author Epperson
 *
 */
public class State {
	private Point3D p3d;
	private Velocity vel;
	private double cost = 1;

/**
 * Point only constructor
 * @param p3d
 */
	public State(Point3D p3d) {
		this.p3d = p3d;
	}
	
/**
 * Point and Vel Constructor
 * @param p3d
 * @param vel
 */
	public State(Point3D p3d, Velocity vel) {
		this.p3d = p3d;
		this.vel = vel;
	}
	
	public void updateCost(State parentState) {
		cost += parentState.cost();
		//System.out.println("Cost at Point "+p3d.toString()+" : "+cost);
	}
	
	public void updateCost(double cost) {
		this.cost += cost;
	}
	
	public double cost() {
		return cost;
	}
	
	/**
	 * Getter for State Position
	 * @return
	 */
	public Point3D getP3D() {
		return p3d;
	}

	/**
	 * Getter for State Velocity
	 * @return
	 */
	public Velocity getVel() {
		return vel;
	}
	
	/**
	 * returns a String represntation of state
	 */
	public String toString() {
		//return "X: "+p3d.getX()+" Y: "+p3d.getY()+" Z: "+p3d.getZ()+"\n";
		return ""+p3d.getX()+","+p3d.getY()+","+p3d.getZ();
	}

	public boolean equals(State s) {
			if(!p3d.equals(s.getP3D())) {
				return false;
			}
		return true;
	}
}
	
