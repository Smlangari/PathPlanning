package com.epperson.state;

/**
 * Simple Class for a 3 Dimensional Point
 * @author Epperson
 *
 */
public class Point3D {
	private double X;
	private double Y;
	private double Z;
	
	public Point3D(double x, double y, double z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	
	public double getX() {
		return this.X;
	}
	
	public double getY() {
		return this.Y;
	}
	
	public double getZ() {
		return this.Z;
	}
	
	public void setX(double x)
	{
		this.X = x;
	}
	
	public void setY(double y)
	{
		this.Y = y;
	}
	
	public void setZ(double z)
	{
		this.Z = z;
	}
	
	public double[] toArray() {
		double[] arr = new double[3];
		arr[0] = X;
		arr[1] = Y;
		arr[2] = Z;
		return arr;
	}
	
	public String toString() {
		return X+","+Y+","+Z;
	}
	
	public boolean equals(Point3D p) {
		return (this.X == p.getX() && this.Y == p.getY() && this.Z == p.getZ());
	}
}
