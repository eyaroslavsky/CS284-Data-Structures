/*
 * InstString.java
 *
 */

package assign3;

public abstract class InstString{

    public RingBuffer r;
    public int times = 0;
    
    /* To be implemented by subclasses*/
    public abstract void pluck();
    public abstract void tic();

    public double sample(){
	return r.peek();
    }

    public int time(){
	return this.times;
    }

}
