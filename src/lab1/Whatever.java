package lab1;

import java.util.Scanner;

public class Whatever {
	private static Order ord=new Order();
	private static String ordinput= new String();
	public static boolean expressionOrCommandInput(){
		Scanner in = new Scanner(System.in);
		ordinput = in.nextLine();
		while(ordinput.length() == 0)
			ordinput = in.nextLine();
		if(ordinput.equals("end")){//结束的标志
			in.close();
			return false;
		}
		return true;
	}
	public static void outputResult(String result){
		System.out.println(result);
	}
	public static void main(String[] args){
		while(expressionOrCommandInput()==true){
			ord.setOrder(ordinput);
			if(ord.run()==0){
				outputResult("Error!");
			}
			else
				outputResult(ord.print());
		}	
	}
}

