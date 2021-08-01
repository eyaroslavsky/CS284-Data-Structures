/*
 * GuitarString.java
 *
 */

package assign3;
import java.lang.Math;

public class GuitarString extends InstString{

    public GuitarString(){};
   
    public GuitarString(double frequency) {	
	this.r = new RingBuffer((int) (Math.round(44100 / frequency)));
	for (int x = 0; x < ((int) (Math.round(44100 / frequency))); x++) {
	    this.r.enqueue(0);
	}
    }

    public GuitarString(double[] init) {
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
	    double rand = Math.random();
	    rand -= .5;
	    this.r.enqueue(rand);
	}
    }
    
    public void tic() {
	double deq = this.r.dequeue();
	double val = (deq + this.r.peek()) / 2.0 * .996;
	this.r.enqueue(val);
	this.times++;
    }

    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	double[] samples = {.2,.4,.5,.3,-.2,.4,.3,.0,-.1,-.3};
	GuitarString testString = new GuitarString(samples);
	for (int i = 0; i < N; i++) {
	    int t = testString.time();
	    double sample = testString.sample();
	    System.out.printf("%6d %8.4f\n",t,sample);
	    testString.tic();
	}
    }
}
