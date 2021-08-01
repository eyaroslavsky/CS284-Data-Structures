/*
 * Drum.java
 */
package assign3;

public class Drum extends Instrument{

    public Drum(int numNotes){
	this.strings = new DrumString[numNotes];

	for (int x = 0; x < this.strings.length; x++) {
	    this.strings[x] = new DrumString(440.0 * Math.pow(2,(x-24)/12.0));
	}
    }		    
}
