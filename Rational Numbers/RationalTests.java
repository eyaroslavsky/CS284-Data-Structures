import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RationalTests {
    @Test
     void testAddition() {
	RationalNumber a = new RationalNumber(1,2);
	RationalNumber b = new RationalNumber(1,3);
	RationalNumber ans = new RationalNumber(5,6);
	//RationalNumber test = new RationalNumber(5,0);

	RationalNumber c = new RationalNumber(4,6);
	assertTrue(c.getNum() == 4 && c.getDenom() == 6);
	assertTrue(RationalNumber.gcd(4,6) == 2);
	c.simplify();
	assertTrue(c.getNum() == 2 && c.getDenom() == 3);

	a.add(b); //a should equal 5/6
	assertTrue(a.isEqual(ans));

	a.multiply(b);
	assertTrue(a.isEqual(new RationalNumber(5,18)));

	RationalNumber str3 = new RationalNumber("101");
	assertTrue(str3.getNum() == 5 && str3.getDenom() == 1);
	
	RationalNumber str = new RationalNumber("101.01_101");
	assertTrue(str.getNum() == 38 && str.getDenom() == 7);

	//RationalNumber str2 = new RationalNumber(".01_101");
	//assertTrue(str2.getNum() == 3 && str2.getDenom() == 7);
    }
}
