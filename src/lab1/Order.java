package lab1;

import java.util.Vector;
import java.util.regex.Pattern;

public class Order{
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String[] getArg_name() {
		return arg_name;
	}
	public void setArg_name(String[] arg_name) {
		this.arg_name = arg_name;
	}
	public int getTyp() {
		return typ;
	}
	public void setTyp(int typ) {
		this.typ = typ;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Vector<Integer> getValue() {
		return value;
	}
	public void setValue(Vector<Integer> value) {
		this.value = value;
	}
	
	
	private Expression exp=new Expression();
	private String order;
	private String[] arg_name;
	private int typ;
	private String dir;
	private Vector<Integer> value=new Vector<Integer>();
	
	public int run(){
		if(judge()==0)
			return 0;
		if(typ==1){
			exp.E(order);
		}
		else if(typ==2){
			exp.S(arg_name,value);
		}
		else if(typ==3){
			exp.D(dir);
		}
		arg_name=new String[0];
		value=new Vector<Integer>();
		exp.S(arg_name,value);
		return 1;
	}
	
	public String print(){
		return exp.toString();
	}
	
	public int judge(){	
		String[] temp=order.split(" ");
		order="";
		for(int i=0;i<temp.length;i++){
			if(i==0)
				order=order+temp[i];
			else
				order=order+temp[i];
		}	
		if(order.charAt(0)=='!'){				
	        if(order.length()>8 && order.substring(1, 9).equals("simplify")){//ÅÐ¶Ï»¯¼ò
	       		Pattern pat1=Pattern.compile("([a-z]*\\=[0-9]*)*");
	       		if(!pat1.matcher(order.substring(9)).matches())
	       			return 0;
	       		else
	       		{
	       			typ=2;
	       			String temp2=order.substring(9);
	       			String temp3="";
	       			int mark=0;
	       			for(int i=0;i<temp2.length()-1;i++){
	       				if(Character.isDigit(temp2.charAt(i)) && Character.isLetter(temp2.charAt(i+1)))
	       				{
	       					temp3=temp3+temp2.substring(mark, i+1)+"=";
	       					mark=i+1;
	       				}
	       			}
	       			temp3=temp3+temp2.substring(mark);
	       			temp=temp3.split("=");
	       			temp2="";
	       			value=new Vector<Integer>();
	       			for(int i=0;i<temp.length;i++)
	       			{
	       				if(i==0)
	       					temp2=temp[i];
	       				else if(i%2==0)
	       					temp2=temp2+" "+temp[i];
	       				else
	       					value.add(Integer.parseInt(temp[i]));
	       			}
	       			arg_name=temp2.split(" ");
	       			for(int i=0;i<arg_name.length;i++)
	       				if(exp.find(arg_name[i])==-1)
	       					return 0;
	       			return 1;
	       		}
	        }
	        else if(order.length()>4 && order.substring(1, 4).equals("d/d")){
	        	Pattern pat2=Pattern.compile("[a-z]*");
	       		if(!pat2.matcher(order.substring(4)).matches() || exp.find(order.substring(4))==-1)
	       			return 0;
	       		else
	       		{
	       			dir=order.substring(4);
	       			typ=3;
	       			return 1;
	       		}
	        }
	        else
	        	return 0;
		}
		else{
			String temp2;
	        for(int i=0;i<order.length()-1;i++){
	        	if(order.charAt(i)=='^' && !(Character.isDigit(order.charAt(i+1))))
	        		return 0;
	        }
	      	temp2=order;
	        temp2=temp2.replaceAll("\\+", "\\$");
	        temp2=temp2.replaceAll("\\-", "\\$");
	        temp2=temp2.replaceAll("\\*", "\\$");
	        temp2=temp2.replaceAll("\\/", "\\$");
	        temp2=temp2.replaceAll("\\^", "\\$");
	        for(int i=0;i<order.length()-1;i++){
	       		if(order.charAt(i)=='$' && order.charAt(i+1)=='$')
	       			return 0;
	       	}
	       	temp=temp2.split("\\$");
	       	for(int i=0;i<temp.length;i++){
	       		Pattern pat1=Pattern.compile("[0-9]*");
	       		Pattern pat2=Pattern.compile("[a-z]*");
	       		if(!(pat1.matcher(temp[i]).matches() || pat2.matcher(temp[i]).matches()))
        			return 0;
        	}
	       	typ=1;
	       	return 1;
		}
	}
}	