package org;

public abstract class Shape {
	
	public abstract void draw();
	
	public abstract void erase();
	
	
	public static Shape factory(String type) {
		if (type.equals("Circle")) {
			return new Cicrle();
		} if (type.equals("Square")) {
			return new Square();
		}
		
		throw new RuntimeException("Bad shape creation: " + type);
	}
		

}
