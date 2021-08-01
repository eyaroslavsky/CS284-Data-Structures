/*
 * Piano.java
 */

package assign3;


public class Piano extends Instrument{

    private static InstString[][] pStrings; //2D array of strings
    
    public Piano(int numNotes){
	this.pStrings = new PianoString[numNotes][3];
	for (int row = 0; row < this.pStrings.length; row++) {
	    this.pStrings[row][0] = new PianoString(440.0 * Math.pow(2,(row-24)/12.0));
	    this.pStrings[row][1] = new PianoString(440.0 * Math.pow(2,(row-24)/12.0) + .45);
	    this.pStrings[row][2] = new PianoString(440.0 * Math.pow(2,(row-24)/12.0) - .45);
	}
    }
  
    public void playNote(int index){
	this.pStrings[index][0].pluck();
	this.pStrings[index][1].pluck();
	this.pStrings[index][2].pluck();
    }
    
    public double ringNotes(){
	double sum = 0.0;
	for (int row = 0; row < this.pStrings.length; row++) {
	    for (int col = 0; col < this.pStrings[0].length; col++) {
		this.pStrings[row][col].tic();
		sum += this.pStrings[row][col].sample();
	    }
	}
	return sum;
    }
			    

}
