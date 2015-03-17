/**
 * AWT Sample application
 *
 * @author 
 * @version 1.00 15/03/03
 */
 
 /**
 * AWT Sample application
 *
 * @author Dr. Jürg Stettbacher
 * @version 1.00 15/02/26
 */
 
// ============================================================================
//
//      Entropy 
//
// ============================================================================
//
//      Version      1.00
//      Date         2015-02-25
//      Author       J. M. Stettbacher
//
//      System       Java (tested on version 1.7.0_55 on Linux)
//
// ============================================================================
//
//      Build class file:
//      (1) Compile using Eclipse or other.
//      (2) Compile using Makefile. Type on command line:
//          >> make
//      (3) Directly on command line:
//          >> javac Entropy.java
//
//      Execute class file (filename is a valid data file in ASCII format):
//      (1) Run from Eclipse by specifying the filename command line argument.
//      (2) Run from command line:
//          >> java Entropy filename
//
//      Description:
//      Reads symbols from the data file and determines:
//      - number of different character types in file.
//      - total number of characters in file.
//      - probability of each character type.
//      - information of each character type.
//      - entropy of entire file.
//
// ============================================================================


import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map.Entry;


// ----------------------------------------------------------------------------
// Main class:
// ----------------------------------------------------------------------------
public class Entropy {

	// Global hash-map (some kind of an array):
	static HashMap <Integer /*character*/, CharProp> chars = new HashMap<>();
	// Global counter variable:
	static double fileCharactersCount = 0;
	static double fileEntropy = 0;
	

	// Main method. The program starts here.
	public static void main(String[] args) {
		// Print hello message:
		System.out.println("======================================================");
		System.out.println("Starting ComputeMain...");
		
		// ------------------------------------------------------------
		// Check if a valid filename has been supplied on command line:
		// ------------------------------------------------------------
		// Is there a command line argument at all?
		if (args.length <= 0) {
			System.out.println("ERROR: You have to supply a filename on the command line!");
			System.out.println(" ");
			System.exit(0);
		}
		// Yes, there is one:
		String s = args[0];
		File file = new File(s);
		// Check if file with that name exists:
		if (!file.exists()) {
			// Quit program with an error message:
			System.out.println("ERROR: Data file: " + s + " does not exist!");
			System.out.println(" ");
			System.exit(0);
		}
		// Yes, command line argument is okay.
		System.out.println( "Data file " + s + " exists.");
		
		// ------------------------------------------------------------
		// Call each method:
		// ------------------------------------------------------------
		ReadInputTextFileCharacters(s);
		ComputeProbabilities();
		ComputeInformation();
		fileEntropy = ComputeEntropy();
		PrintOutCharProps();
		
		// ------------------------------------------------------------
		// Print goodbye message:
		// ------------------------------------------------------------
		System.out.println("Done.");
		System.out.println("======================================================");
	}
	
	
	// --------------------------------------------------------------------
	// Character propery class:
	// --------------------------------------------------------------------
	static class CharProp {
		int    occurence = 0;
		double probability = 0;
		double information = 0;
	};
	
	
	// --------------------------------------------------------------------
	// Base 2 logarithm:
	// --------------------------------------------------------------------
	static double log2(double d) {
		return Math.log(d)/Math.log(2.0);
	}
	

	// --------------------------------------------------------------------
	// Read character from file and count them:
	// --------------------------------------------------------------------
	static void ReadInputTextFileCharacters(String relativeFilePath) {
	    
	    	System.out.println("Reading file ...");
		
		// Open file and read character by character:
		try (BufferedReader in = new BufferedReader(new FileReader(relativeFilePath))) {
		
			int c;
			while ((c = in.read()) != -1) {
				/* 
				 * ToDo: [1.1] implement computing of the relative frequency of the current character. 
				 * */
			     CharProp charObj;
			     if (chars.containsKey(c)){
			    	 charObj = chars.get(c);
			    	 charObj.occurence++;
			    	 
			     }
			     else{
			    	 charObj = new CharProp();
			    	 charObj.occurence++;
			    	 chars.put(c, charObj);
			     }
		          	
	   		
			
				/* 
				 * ToDo: [1.2] count the characters in the file. 
				 * */
                fileCharactersCount ++;
                
			
			}
		} 
		catch(IOException ioe) {}
	}
	
	
	// --------------------------------------------------------------------
	// Compute probability of each character in hash-map:
	// --------------------------------------------------------------------
	static void ComputeProbabilities() {
		
		System.out.println( "Computing probabilities...");
		/* 
		 * ToDo: [2] implement computing of the probabilities of the existing characters. 
		 * 			 Use the precision 10 after the comma and the constant RoundingMode.HALF_UP
		 * */
		for (CharProp entry : chars.values()){
			
			entry.probability = entry.occurence / fileCharactersCount;
			
			
		}
		
		
	}

	
	// --------------------------------------------------------------------
	// Compute information for each character in hash-map:
	// --------------------------------------------------------------------
	static void ComputeInformation() {
		
		System.out.println( "Computing information...");
		/* 
		 * ToDo: [3] implement computing of the information of the existing characters. 
		 * 			 Use the precision 10 after the comma and the constant RoundingMode.HALF_UP
		 * */
		for (CharProp entry : chars.values ()){
			
			entry.information = log2(1/entry.probability);
			
		}
		
		
	}

	
	// --------------------------------------------------------------------
	// Compute entropy of all characters in hash-map:
	// --------------------------------------------------------------------
	static double ComputeEntropy() {
		
		System.out.println( "Computing entropy...");
		double sum = 0;
		/* 
		 * ToDo: [4] implement computing of the entropy of the existing characters. 
		 * 			 Send the entropy value back as a result.
		 * */
		for(CharProp entry : chars.values()){
			
			sum += entry.information * entry.probability;
			
		}
		
		return sum;
		
		
	}
	
	
	// --------------------------------------------------------------------
	// Print result table with occurence, probability and information:
	// --------------------------------------------------------------------
	static void PrintOutCharProps() {
	
		// Print general statistics
		System.out.println("Character types in file: " + chars.size());
		System.out.println("Number of character in file: " + fileCharactersCount);
		System.out.println("Entropy of file: " + fileEntropy);
		System.out.println(" ");
		
		// Print character statistics:
		String chr = "";
		for ( int c : chars.keySet() ) {
			
			if ( Character.isWhitespace(c) ) {
				chr = "(" + c + ")";
			} else {
				chr = "" + (char) c;
			}
			System.out.format("  %5s : o=%8d  p=%1.10f  i=%-2.10f%n", 
				chr, chars.get(c).occurence, chars.get(c).probability, chars.get(c).information);
		}
	}
}

// ============================================================================
// ============================================================================