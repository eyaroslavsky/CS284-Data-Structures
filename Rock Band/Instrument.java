/*
 * Instrument.java
 */
package assign3;

public abstract class Instrument {
    
    protected InstString[] strings;
    
    public void playNote(int i) {
	this.strings[i].pluck();
    }

    public double ringNotes() {
	double sum = 0;
	for (int x = 0; x < this.strings.length; x++) {
	    this.strings[x].tic();
	    sum += this.strings[x].sample();
	}
	return sum;
    }

}
