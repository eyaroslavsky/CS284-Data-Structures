/*
 * PianoString.java
 *
 */

package assign3;
import java.lang.Math;

public class PianoString extends InstString {
  
    public PianoString(double frequency) {
	this.r = new RingBuffer((int) (Math.round(44100 / frequency)));
	for (int x = 0; x < ((int) (Math.round(44100 / frequency))); x++) {
	    this.r.enqueue(0);
	}
    }

    public PianoString(double[] init) {
	this.r = new RingBuffer(init.length);
	for (int x = 0; x < init.length; x++) {
	    this.r.enqueue(init[x]);
	}
    }
   
    public void pluck() {
	int size = this.r.getSize();
	int index = 0;
	double[] temp = new double[size];
        while (!this.r.isEmpty()) {;
	    temp[index] = this.r.dequeue();
	    index++;
	}
	for (int x = 0; x < size; x++) {
	    if (x < (7.0/16 * size) || x > (9.0/16 * size)) {
		this.r.enqueue(0);
		//System.out.println(temp[x]);
	    }
	    else if (x >= (7.0/16 * size) && x <= (9.0/16 * size)) {
		this.r.enqueue(.25 * Math.sin(8 * Math.PI * ((((double)(x))/size) - (7.0/16))));
	    }
	}
    }

   
    public void tic() {	
	double deq = this.r.dequeue();
	double val = (deq + this.r.peek()) / 2 * .996;
	this.r.enqueue(val);
	this.times++;
    }

    /* public static void main(String[] args) {
	PianoString test = new PianoString(440.0);
	test.pluck();
	}*/

}
