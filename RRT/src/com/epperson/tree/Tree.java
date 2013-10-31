package com.epperson.tree;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.epperson.RRT.MiscMethods;
import com.epperson.state.State;

/**
 * Simple Arraylist tree class that provides the data structure to build the RRT
 * @author Epperson
 */
public class Tree {

    private ArrayList<State> vertices;
    private ArrayList<ArrayList<State>> children;
    private ArrayList<State> parents;
	MiscMethods tool = new MiscMethods();

    
    public Tree(){
        vertices = new ArrayList<State>();
        children = new ArrayList<ArrayList<State>>();
        parents  = new ArrayList<State>();
    }        
    
    public void add(State s) {
    	if(isEmpty()) {
    		add(s, null);
    	}
    	else {
        	add(s, vertices.get(0));
    	}
    }    

	public void add(State n, State parent){
        if(!vertices.contains(n)){
            vertices.add(n);
            int index = vertices.indexOf(n);
            parents.add(index, parent);
            children.add(index, new ArrayList<State>());

            if(parent!=null) {
                addChild(parent, n);
            }
        }
        else System.out.println("Duplicate");
    }

    public void addChild(State parent, State child)	{
        int index = vertices.indexOf(parent);          
        if(!children.get(index).contains(child)) {
            child.updateCost(tool.distance(parent, child));
            children.get(index).add(child);     
        }
        else System.out.println("Duplicate");
    }
    
    public ArrayList<State> getChildren(State n){
        int index = vertices.indexOf(n);
        if(index == -1) return null;
        else if(children.get(index).isEmpty()) return null;
        else return children.get(index);
    }
    
    public State getParent(State n){
        int index = vertices.indexOf(n);
        return parents.get(index);
    }

    /*
     * ToDo. Lots of testing to see why theres gaps in the graph
     */
    public void changeParent(State s, State newParent) {
    
        State oldParent = getParent(s);
        System.out.println("before: "+children.get(vertices.indexOf(oldParent)).size());
        int index = vertices.indexOf(oldParent);
        System.out.println(children.get(index).remove(s));

        index = vertices.indexOf(s);
        parents.set(index, newParent);
        
        s.updateCost(newParent.cost()+tool.distance(newParent, s)); 
        addChild(newParent, s);
        System.out.println("After: "+children.get(vertices.indexOf(oldParent)).size()+"\n\n");
    }
 
    
    public State getState(int index){
        
        if(index == -1) return null;
        
        if(index < vertices.size())
            return vertices.get(index);
        else
            return null;
    }
    
    public String toString() {
    	System.out.println("Parent Size: "+parents.size());
		for(int i=0;i<parents.size();i++) {
			System.out.println("Parents: "+parents.get(i));	
		}
		
		System.out.println("\n\nChildren Size: "+parents.size());
		for(int i=0;i<children.size();i++) {
			System.out.println("Children: "+children.get(i));
		}
		return null;
    }

    public void writeToFile() throws IOException {
    	FileWriter outputStream = new FileWriter("/home/matt/points.txt");
        outputStream.write(""+vertices.size()+"\n");
        for(int i=0;i<vertices.size();i++) {
        	if(getParent(vertices.get(i)) != null) {
            	//System.out.println("child: "+vertices.get(i).toString()+" ---- Parent: "+getParent(vertices.get(i)).toString());
        		outputStream.write(vertices.get(i).toString()+"\n"+getParent(vertices.get(i)).toString()+"\n");        		
        	}
        }
        
        outputStream.close();
    }

    public int getSize(){
        return vertices.size();
    }
    
    private boolean isEmpty() {
		if(vertices.size()<1) {
			return true;
		}
		return false;
	}

    public ArrayList<State> preOrderTraversal() {
    	return preOrderTraversal(0, new ArrayList<State>());
    }
    
    private ArrayList<State> preOrderTraversal(int nodeIndex, ArrayList<State> list) {
		if(vertices.get(nodeIndex) != null) {
			System.out.println(vertices.get(nodeIndex));
			list.add(vertices.get(nodeIndex));
		}
		ArrayList<State> childs = children.get(nodeIndex);
		for(int i = 0; i < childs.size(); i++) {
			preOrderTraversal(vertices.indexOf(childs.get(i)), list);
		}
		return list;
	}


}