package inversionszahl;

import java.lang.StringBuilder;

/**
 * Takes in space separated ints from args, sorts, and returns how times the values were inverted
 * to achieve sortedness.
 * @param int int int ... (space separated)
 * @author brianmelton
 *
 */

public class Inversionszahl {
	
	int[] input;
	
	public Inversionszahl(int[] input){
		this.input = input;
	}
	
	public static void main(String[] args) {
		Inversionszahl a = new Inversionszahl(stringArrayToIntArray(args));
		System.out.println("The input array was: " + a.printArray(a.input));
		int inversionzahl = a.sortAndReturnInversionzahl(a.input, new int[a.input.length], 0, a.input.length-1, 0); //check this
		System.out.print("The inversion count was: " + inversionzahl + "\nThe sorted array is: ");
		System.out.println("Sorted array: " + a.printArray(a.input));

	}
	
	private String printArray(int[] a){
		StringBuilder sB = new StringBuilder();
		sB.append('{');
		for (int i = 0; i < a.length; i++){
			sB.append(a[i]);
			if ( i < a.length-1) sB.append(", ");
		}
		sB.append('}');
		return sB.toString();
	}
	
	public static int[] stringArrayToIntArray(String[] sArray){
		int[] t = new int[sArray.length];
		for (int i = 0; i < sArray.length; i++){
			t[i] = Integer.parseInt(sArray[i]);
		}
		return t;
	}
	
	private int sortAndReturnInversionzahl(int[] inputArray, int[] tempArray, int first, int last, int inversionzahl){
		if ( (last - first +1) <= 1) //if size == 1 -> list is sorted (base case)
    		return 0;
    	
    	int mid = ( ( first + last +1) /2 );
    	
    	inversionzahl = sortAndReturnInversionzahl(inputArray, tempArray, first, mid-1, inversionzahl); //split list -- first 'half' with smaller bias (mid-1)
    	inversionzahl = sortAndReturnInversionzahl(inputArray, tempArray, mid, last, inversionzahl); //split list -- top 'half' with bigger bias
    	
    	inversionzahl = merge( inputArray, tempArray, first, mid, last, inversionzahl); //combine sorted sub-lists
    	return inversionzahl;
		
	}
	
	private int merge(int[] in, int[] temp, int first, int mid, int last, int inversionzahl){
    	int scannerOne = 0; //walks the lower half
    	int scannerTwo = mid; //walks the upper half
    	int insertionPoint = first; //where to insert into input array
    	
    	System.arraycopy(in, first, temp, 0, mid-first); //fill temp array with contents of lower half of input array
    	
    	while( ( scannerOne < (mid - first) ) && scannerTwo <= last){ //while either half has items to traverse
    		if (temp[scannerOne] <= in[scannerTwo]){
    			in[insertionPoint] =  temp[scannerOne];
    			scannerOne++;
    		}
    		else{
    			in[insertionPoint] = in[scannerTwo];
    			System.out.println(scannerTwo - insertionPoint + " swaps occurring ---" +  
    					" the number @ postion["+scannerTwo+"]"+ " is being switched into position"+
    					"["+insertionPoint+"]");
    			/*
    			 * *********************************************************
    			 * ******HERE IS WHERE THE INVERSIONZAHL IS CALCULATED******
    			 * *********************************************************
    			 * 
    			 * When elements are pulled from the upper-partition of the
    			 * array to be sorted this is an 'inversion' - the distance
    			 * the element is being traversed through the array is the #
    			 * of inversions occurring.
    			 * 
    			 */
    			
    			inversionzahl += scannerTwo - insertionPoint;
    			scannerTwo++;
    		}
    		insertionPoint++;
    	}
    	
    	while( scannerOne < (mid - first) ){
    		in[insertionPoint] = temp[scannerOne];
    		scannerOne++;
    		insertionPoint++;
    	}
    	
    	return inversionzahl;
    }

}
