//Edward Yaroslavsky eyarosla 2/16/19 section D
//I pledge my honor that I have abided by the Stevens Honor System.

public class RationalNumber extends java.lang.Number {
    private int numerator;
    private int denominator;

    /**
       Constructor that takes a numerator and denominator as parameters.
     */
    public RationalNumber(int numerator, int denominator) {
	if (denominator == 0) {
	    throw new IllegalArgumentException("Can't pass 0 as a denominator");
	}
	this.numerator = numerator;
	this.denominator = denominator;
    }

    /**
       Constructor that takes a String as a parameter. 
       Splits up the 3 sections of the binary string and converts each into a RationalNumber to be added.
    */
    public RationalNumber(String s) {
        String[] arr = s.split("[._]");
	int charValue = 0;
	int periodValue = 0;

	for (int x = 0; x < arr[0].length(); x++) {
	    if (arr[0].substring(arr[0].length()-1-x,arr[0].length()-x).equals("1")) {
		charValue += Math.pow(2,x);
	    }
	}
        RationalNumber sum = new RationalNumber(charValue,1);

	if (arr.length > 1) {
	    for (int y = 0; y < arr[1].length(); y++) {
		if (arr[1].substring(y,y+1).equals("1")) {
		    sum.add(new RationalNumber(1,(int)Math.pow(2,y+1)));
		}
	    }
	}
	
	if (arr.length > 2) {
	    for (int z = 0; z < arr[2].length(); z++) {
		if (arr[2].substring(arr[2].length()-1-z,arr[2].length()-z).equals("1")) {
		    periodValue += Math.pow(2,z);
		}
	    }
	    int denom = ((int)Math.pow(2,arr[2].length())-1)*((int)Math.pow(2,arr[1].length()));
	    sum.add(new RationalNumber(periodValue,denom));
	}
		
	this.numerator = sum.getNum();
	this.denominator = sum.getDenom();
	simplify();
    }

    //Getter for the numerator.
    public int getNum() {
	return this.numerator;
    }

    //Getter for the denominator.
    public int getDenom() {
	return this.denominator;
    }

    /**
       Returns the double value of a fraction truncated to 3 decimal places.
     */
    @Override
    public double doubleValue() {
	double ans = (double) this.numerator / this.denominator;
        ans = Math.round(ans * 1000) / 1000.0;
	return ans;
    }

    /**
       Returns the float value of a fraction truncated to 3 decimal places.
     */
    @Override
    public float floatValue() {
	float ans = (float) this.numerator / this.denominator;
	ans = Math.round(ans * 1000) / (float) 1000;
	return ans;
    }

    /**
       Returns the int value of a fraction.
     */
    @Override
    public int intValue() {
        return this.numerator / this.denominator;
    }

    /**
       Returns the long value of a fraction.
     */
    @Override
    public long longValue() {
	return this.numerator / (long) this.denominator;
    }

    /**
       Returns the greatest common denominator of two RationalNumber Objects.
     */
    public static int gcd(int p, int q) {
	int gcd = p;
	for (int x = 1; x <= q; x++) {
	    if (p % x == 0 && q % x == 0) {
		gcd = x;
	    }
	}
	return gcd;
    }

    /**
       Simplifies the RationalNumber fraction.
     */
    public void simplify() {
	int num = gcd(this.numerator, this.denominator);
	this.numerator = this.numerator / num;
	this.denominator = this.denominator / num;
    }

    /**
       Adds two RationalNumbers together.
     */
    public void add(RationalNumber other) {
	this.numerator = (this.numerator * other.getDenom()) + (this.denominator * other.getNum());
	this.denominator = this.denominator * other.getDenom();
	simplify();
    }

    /**
       Multiplies two RationalNumbers together.
     */
    public void multiply(RationalNumber other) {
	this.numerator = this.numerator * other.getNum();
	this.denominator = this.denominator * other.getDenom();
    }

    /**
       Checks for equality between two RationalNumber Objects.
     */
    public boolean isEqual(RationalNumber other) {
	return this.numerator == other.getNum() && this.denominator == other.getDenom();
    }
}
