	package org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



public class ShapeFactory {
	
	public static void main(String[] args) {
		List<Shape> shapes = new ArrayList<Shape>();
		
		Iterator it = Arrays.asList(
		new String[] {"Circle", "Square", "Square", "Square", "Circle", "Circle", "Square", "Tri"}).iterator();
		
		while (it.hasNext()) 
			shapes.add(Shape.factory((String) it.next()));
		
		it = shapes.iterator();
		
		
		while(it.hasNext()) {
			Shape s = (Shape) it.next();
			s.draw();
			s.erase();
		}
				
	}

}
