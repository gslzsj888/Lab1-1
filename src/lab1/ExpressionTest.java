package lab1;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTest {

	//@Before
	//public void setUp() throws Exception {
	//}
	
	@Test
	public void testS1() {
		//Ç°Çý
		Expression expression = new Expression();
		String inputExpression = "2*x+y^4";
		Order o = new Order();
		o.setOrder(inputExpression);
		o.judge();
		expression.E(o.getOrder());
		//²âÊÔ
		o.setOrder("!simplify x=1 y=2");
		o.judge();
		expression.S(o.getArg_name(), o.getValue());
		String result;
		result = expression.toString();
		assertEquals("16+2", result);
	}
	

	@Test
	public void testS2() {
		//Ç°Çý
		Expression expression = new Expression();
		String inputExpression = "99+3*xxx-77*yooz*z-y";
		Order o = new Order();
		o.setOrder(inputExpression);
		o.judge();
		expression.E(o.getOrder());
		//²âÊÔ
		o.setOrder("!simplify xxx=2 yooz=1 y=7");
		o.judge();
		expression.S(o.getArg_name(), o.getValue());
		String result;
		result = expression.toString();
		assertEquals("92+6-77*z", result);
	}
	
	@Test
	public void testS3() {
		//Ç°Çý
		Expression expression = new Expression();
		String inputExpression = "x+y";
		Order o = new Order();
		o.setOrder(inputExpression);
		o.judge();
		expression.E(o.getOrder());
		//²âÊÔ
		o.setOrder("!simplify x 1");
		o.judge();
		int result=o.judge();
		assertEquals(0,result);
	}
	
	@Test
	public void testS4() {
		//Ç°Çý
		Expression expression = new Expression();
		String inputExpression = "xx+3*zzz";
		Order o = new Order();
		o.setOrder(inputExpression);
		o.judge();
		expression.E(o.getOrder());
		//²âÊÔ
		o.setOrder("!simplify x=1");
		int result=o.judge();
		assertEquals(0,result);
	}
	
	@Test
	public void testS5() {
		//Ç°Çý
		Expression expression = new Expression();
		String inputExpression = "2*a*cx*bx+xx^2";
		Order o = new Order();
		o.setOrder(inputExpression);
		o.judge();
		expression.E(o.getOrder());
		//²âÊÔ
		o.setOrder("!simplify x=1");
		int result=o.judge();
		assertEquals(0,result);

	}
	
	
}

