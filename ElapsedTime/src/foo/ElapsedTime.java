package foo;

import java.util.Date;

public class ElapsedTime {
	
	public static void main(String[] args) {
		
		long lStartTime = new Date().getTime(); //start time
		
		String sArray1[]  = createArray(); // method execute
		
		long lEndTime = new Date().getTime(); // end time
		
		long difference = lEndTime - lStartTime;
		
		System.out.println("Elapsed time in milliseconds: " + difference);
		System.out.println("Elapsed time in seconds: " + difference/1000);
		
	}
	
	
	
	static String[] createArray(){
		String[] sArray = new String[10000000];
		for (int i = 0; i < sArray.length; i++) {
			sArray[i] = "Array " + i;
		}
		
		return sArray;
	}

}
