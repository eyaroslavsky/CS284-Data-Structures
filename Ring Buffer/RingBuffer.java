/*****************************************************************************
Edward Yaroslavsky eyarosla 3/15/19 I pledge my honor that I have abided by the Stevens Honor System.
 *
 *  This is a template file for RingBuffer.java. It lists the constructors and
 *  methods you need, along with descriptions of what they're supposed to do.
 *  
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least commment out the ones whose return type is non-void).
 *
 *****************************************************************************/


public class RingBuffer {
    private int first;            // index of first item in buffer
    private int last;             // index of last item in buffer
    private int size;             // current number of items of buffer
    private double[] buffer;

    // create an empty buffer, with given max capacity
    public RingBuffer(int capacity) {
	this.first = 0;
	this.last = 0;
        this.size = 0;
	this.buffer = new double[capacity];
    }

    // return number of items currently in the buffer
    public int getSize() {
	return size;
    }

    // is the buffer empty (size equals zero)?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // is the buffer full (size equals array capacity)?
    public boolean isFull() {
        return this.size == this.buffer.length;
    }

    // add item x to the end
    public void enqueue(double x) {
        if (isFull()) { throw new RuntimeException("Ring buffer overflow"); }
        this.buffer[this.last] = x;
	if (this.last == this.buffer.length - 1) {
	    this.last = 0;
	}
	else {
	    this.last++;
	}
	this.size++;
    }

    // delete and return item from the front
    public double dequeue() {
        if (isEmpty()) { throw new RuntimeException("Ring buffer underflow"); }
        double x = this.buffer[this.first];
	if (this.first == this.buffer.length - 1) {
	    this.first = 0;
	}
	else {
	    this.first++;
	}
	this.size--;
	return x;
    }

    // return (but do not delete) item from the front
    public double peek() {
        if (isEmpty()) { throw new RuntimeException("Ring buffer underflow"); }
        return this.buffer[this.first];
    }

    // a simple test of the constructor and methods in RingBuffer
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(N);
        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }
		double t = buffer.dequeue();
		buffer.enqueue(t);
		System.out.println("Size after wrap-around is " 
				   + buffer.getSize());
        while (buffer.getSize() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }
}



