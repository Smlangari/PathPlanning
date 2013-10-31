package com.epperson.tree;

public class BinaryTree<T> {
	private Node root;
	
	private class Node {
		public T elem;
		public Node left, right;
			
		public Node(T elem) {
			this.elem = elem;
		}        //Tree graph = rrt.rrt(5000, tre
		
	}
	public void insert(T elem) {
		insert(elem, root);
	}
	
	private void insert(T elem, Node next) {
		
	}


}