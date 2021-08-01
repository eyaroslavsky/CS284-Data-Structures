/*
 * Guitar.java
 */
package assign3;

public class Guitar extends Instrument{


    public Guitar(int numNotes){
	this.strings = new GuitarString[numNotes];
	
	for (int x = 0; x < this.strings.length; x++) {
	    this.strings[x] = new GuitarString(440.0 * Math.pow(2,(x-24)/12.0));
	}
    }		    
}
