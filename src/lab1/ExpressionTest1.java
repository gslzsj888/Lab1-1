package lab1;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTest1 {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test1(){
		Expression expre=new Expression();
	 	String dir="aaa";
	 	Vector<Object> obj=new Vector<Object>();
	 	expre.setObj(obj);
	 	expre.D(dir);
		String result=expre.toString();
		assertEquals("0",result);
	}
	
	
	@Test
	public void test2(){
	 	Expression  expre=new Expression();
	 	String dir="aaa";
	 	Vector<Object> obj=new Vector<Object>();
		Object temp=new Object();
	 	temp.typ=1;
	 	temp.e=1;
	 	temp.sign=3;
	 	temp.num=5;
	 	obj.add(temp);
	 	expre.setObj(obj);
	 	expre.D(dir);
		String result=expre.toString();
		assertEquals("0",result);
	}
	
	@Test
	public void test3(){
		Expression expre=new Expression();
	 	String dir="aaa";
	 	Vector<Object> obj=new Vector<Object>();
		Object temp=new Object();
	 	temp.typ=1;
	 	temp.e=1;
	 	temp.sign=3;
	 	temp.num=5;
	 	obj.add(temp);
	 	temp=new Object();
	 	temp.typ=1;
	 	temp.e=1;
	 	temp.sign=3;
	 	temp.num=6;
	 	obj.add(temp);
	 	expre.setObj(obj);
	 	expre.D(dir);
		String result=expre.toString();
		assertEquals("0",result);
	}
	
	@Test
	public void test4(){
	 	Expression expre=new Expression();
	 	String dir="aaa";
	 	Vector<Object> obj = new Vector<Object>();
	 	Object temp=new Object();
	 	temp.typ=2;
	 	temp.e=1;
	 	temp.sign=3;
	 	temp.argName="bbb";
	 	obj.add(temp);
	 	expre.setObj(obj);
	 	expre.D(dir);
		String result=expre.toString();
		assertEquals("0",result);
	}

	@Test
	public void test5(){
	 	Expression expre=new Expression();
	 	String dir="aaa";
	 	Vector<Object> obj = new Vector<Object>();
	 	Object temp=new Object();
	 	temp.typ=2;
	 	temp.e=3;
	 	temp.sign=3;
	 	temp.argName="aaa";
	 	obj.add(temp);
	 	expre.setObj(obj);
	 	expre.D(dir);
		String result=expre.toString();
		assertEquals("aaa^2*3",result);
	}

	
	
}



