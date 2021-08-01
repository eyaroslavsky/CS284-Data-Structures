/*
 * DrumString.java
 *
 */

package assign3;
import java.lang.Math;


public class DrumString extends InstString{

   
    public DrumString(double frequency) {
	this.r = new RingBuffer((int) (Math.round(44100 / frequency)));
	for (int x = 0; x < ((int) (Math.round(44100 / frequency))); x++) {
	    this.r.enqueue(0);
	}
    }

    public DrumString(double[] init) {
	this.r = new RingBuffer(init.length);
	for (int x = 0; x < init.length; x++) {
	    this.r.enqueue(init[x]);
	}
    }

    public void pluck() {
	int size = this.r.getSize();
	while (!this.r.isEmpty()) {
	    this.r.dequeue();
	}
	for (int x = 0; x < size; x++) {
	    if (Math.sin(x/((double)(size))) > 0) {
		this.r.enqueue(1.0);
	    }
	    else {
		this.r.enqueue(-1.0);
	    }
	}
    }
    
    public void tic() {
	double deq = this.r.dequeue();
	if (Math.random() >= .4) {
	    if (Math.random() >= .5) {
		this.r.enqueue((deq + this.r.peek()) / 2 * .996);
	    }
	    else {
		this.r.enqueue((deq + this.r.peek()) / 2 * .996 * -1);
	    }
	}
	else {
	    if (Math.random() >= .5) {
		this.r.enqueue(deq);
	    }
	    else {
		this.r.enqueue(-1 * deq);
	    }
	}
	this.times++;
    }
}
