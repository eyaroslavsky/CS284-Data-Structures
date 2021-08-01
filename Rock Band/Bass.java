/*
 * Bass.java
 */
package assign3;

public class Bass extends Instrument{


    public Bass(int numNotes){
	this.strings = new GuitarString[numNotes];

	for (int x = 0; x < this.strings.length; x++) {
	    this.strings[x] = new GuitarString(440.0 * Math.pow(2,(x-48)/12.0));
	}
    }		    
}
