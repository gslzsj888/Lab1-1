/**
 * @Title: Whatever.java 
 * @Package com.lab1 
 * @Description: TODO 
 * @author Shuqq 
 * @date 2016年10月16日 下午7:10:32
 * 
 */
package com.lab1;

import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

/** 
 * @ClassName: Whatever 
 * @Description: TODO 
 * @author Shuqq 
 *  
 */
public class Whatever {
	/** 
	 * Title:  
	 * Description:   
	 */  
	protected Whatever() {
		
	}
	/** 
	 * @Title: main 
	 * @Description: TODO
	 * @param args   
	 * @throws 
	 */  
	public static void main(String[] args) {
		Expression exp = new Whatever().new Expression();
		Order ord = new Whatever().new Order();
		while (ord.input() != 0) {
			if (ord.judge(exp) == 0) {
				System.out.println("Error!");
				continue;
			}
			exp.run(ord);
			ord.arg_name = new String[0];
			ord.value = new Vector<Integer>();
			ord.typ = 2;
			exp.run(ord);
			exp.print();
		}
	}

	/** 
	 * @ClassName: Object 
	 * @Description: TODO 
	 * @author Shuqq 
	 *  
	 */  
	class Object {
		int typ, num, e, sign;
		// 0 for destroy,1 for numbers,2 for arguments,3 for +,4 for -,5 for *,6
		// for /;
		String argName;
	}

	/** 
	 * @ClassName: Expression 
	 * @Description: TODO 
	 * @author Shuqq 
	 *  
	 */  
	class Expression {
		Vector<Object> obj = new Vector<Object>();

		/** 
		 * @Title: find 
		 * @Description: TODO
		 * @param str
		 * @return int   
		 * @throws 
		 */  
		int find(final String str) {
			for (int i = 0; i < obj.size(); i++) {
				if (obj.get(i).typ == 2 && obj.get(i).argName.equals(str)) {
					return i;
				}
			}
			return -1;
		}

		/** 
		 * @Title: run 
		 * @Description: TODO
		 * @param ord   
		 * @throws 
		 */  
		void run(Order ord) {
			if (ord.typ == 1) {
				obj = new Vector<Object>();
				String temp2 = "+" + ord.order.replaceAll("\\+", "\\$\\+");
				temp2 = temp2.replaceAll("\\-", "\\$\\-");
				temp2 = temp2.replaceAll("\\*", "\\$\\*");
				temp2 = temp2.replaceAll("\\/", "\\$\\/");

				String[] temp = temp2.split("\\$");
				for (int i = 0; i < temp.length; i++) {
					Object newMem = new Object();
					if (temp[i].charAt(0) == '+') {
						newMem.sign = 3;
					} else if (temp[i].charAt(0) == '-') {
						newMem.sign = 4;
					} else if (temp[i].charAt(0) == '*') {
						newMem.sign = 5;
					} else if (temp[i].charAt(0) == '/') {
						newMem.sign = 6;
					}
					temp[i] = temp[i].substring(1);
					Pattern pat1 = Pattern.compile("[a-z]*\\^[0-9]*");
					Pattern pat2 = Pattern.compile("[0-9]*\\^[0-9]*");
					if (pat1.matcher(temp[i]).matches()) {
						newMem.argName = temp[i].split("\\^")[0];
						newMem.e = Integer.parseInt(temp[i].split("\\^")[1]);
						newMem.typ = 2;
					} else if (pat2.matcher(temp[i]).matches()) {
						newMem.num = Integer.parseInt(temp[i].split("\\^")[0]);
						newMem.e = Integer.parseInt(temp[i].split("\\^")[1]);
						newMem.typ = 1;
					} else {
						newMem.e = 1;
						if (Character.isDigit(temp[i].charAt(0))) {
							newMem.num = Integer.parseInt(temp[i]);
							newMem.typ = 1;
						} else {
							newMem.argName = temp[i];
							newMem.typ = 2;
						}
					}
					obj.add(newMem);
				}
			} else if (ord.typ == 2) {
				Vector<Object> tempMem = new Vector<Object>();
				Vector<Object> tempMem2 = new Vector<Object>();
				Object newMem;

				for (int i = 0; i < obj.size(); i++) {
					int ind = -1;
					if (obj.get(i).typ == 2) {
						for (int j = 0; j < ord.arg_name.length; j++) {
							if (obj.get(i).argName.equals(ord.arg_name[j])) {
								ind = j;
							}
						}
					}
					if (ind != -1) {
						newMem = obj.get(i);
						newMem.typ = 1;
						//不论哪个都越界，估计是vector没读进去
						newMem.num = ord.value.elementAt(ind);  
						obj.set(i, newMem);
					}
					if (obj.get(i).e == 0) {
						newMem = new Object();
						newMem.e = 1;
						newMem.typ = 1;
						newMem.num = 1;
						newMem.sign = obj.get(i).sign;
						obj.set(i, newMem);
					}
					if (obj.get(i).typ == 1) {
						int k = obj.get(i).num;
						newMem = obj.get(i);
						for (int j = 1; j < newMem.e; j++) {
							newMem.num *= k;
						}
						newMem.e = 1;
						obj.set(i, newMem);
					}
				}
				newMem = new Object();
				newMem.typ = 1;
				newMem.num = 0;
				newMem.e = 1;
				newMem.sign = 3;
				tempMem2.add(newMem);
				int l = 0;
				int r = 0;
				while (l < obj.size()) {
					r = l + 1;
					while (r < obj.size() && obj.get(r).sign >= 5) {
						++r;
					}
					if (r == l + 1) {
						if (obj.get(l).typ == 1) {
							if (obj.get(l).sign == 3) {
								newMem = tempMem2.get(0);
								newMem.num += obj.get(l).num;
							} else if (obj.get(l).sign == 4) {
								newMem = tempMem2.get(0);
								newMem.num -= obj.get(l).num;
							}
						} else {
							tempMem2.add(obj.get(l));
						}
						l = r;
						continue;
					}
					tempMem = new Vector<Object>();
					newMem = new Object();
					newMem.typ = 1;
					newMem.e = 1;
					newMem.sign = obj.get(l).sign;
					if (obj.get(l).typ == 1) {
						newMem.num = obj.get(l).num;
					} else {
						newMem.num = 1;
					}
					tempMem.add(newMem);
					newMem = new Object();
					newMem.typ = 1;
					newMem.e = 1;
					newMem.sign = 6;
					newMem.num = 1;
					tempMem.add(newMem);
					if (obj.get(l).typ == 2) {
						newMem = new Object();
						newMem.typ = 2;
						newMem.e = obj.get(l).e;
						newMem.sign = 5;
						newMem.argName = obj.get(l).argName;
						tempMem.add(newMem);
					}
					for (int i = l + 1; i < r; i++) {
						if (obj.get(i).typ == 1) {
							if (obj.get(i).sign == 5) {
								newMem = tempMem.get(0);
								newMem.num *= obj.get(i).num;
							} else if (obj.get(i).sign == 6) {
								newMem = tempMem.get(1);
								newMem.num *= obj.get(i).num;
							}
						} else {
							tempMem.add(obj.get(i));
						}
					}
					l = r;
					if (tempMem.get(0).num == 0) {
						continue;
					}
					for (int i = 0; i < tempMem.size(); i++) {
						if (i == 1 && tempMem.get(i).num == 1) {
							continue;
						}
						tempMem2.add(tempMem.get(i));
					}
				}
				obj = tempMem2;
			} else if (ord.typ == 3) {
				int flag = 0;

				Vector<Object> tempMem = new Vector<Object>();
				Vector<Object> tempMem2 = new Vector<Object>();
				Object newMem;
				int l = 0;
				int r = 0;
				while (l < obj.size()) {
					r = l + 1;
					while (r < obj.size() && obj.get(r).sign >= 5) {
						++r;
					}
					flag = 0;
					// System.out.println("ord.typ==3 flag:"+flag+"r:"+r);
					tempMem = new Vector<Object>();
					for (int i = l; i < r; i++) {
						if (obj.get(i).typ == 1) {
							tempMem.add(obj.get(i));
						} else {
							if (obj.get(i).argName.equals(ord.dir)) {
								flag = 1;
								newMem = obj.get(i);
								newMem.e--;
								tempMem.add(newMem);
								newMem = new Object();
								newMem.typ = 1;
								newMem.num = obj.get(i).e + 1;
								newMem.sign = 5;
								newMem.e = 1;
								tempMem.add(newMem);
							} else {
								tempMem.add(obj.get(i));
							}
						}
					}
					if (flag == 1) {
						for (int i = 0; i < tempMem.size(); i++) {
							tempMem2.add(tempMem.get(i));
						}
					}
					l = r;

				}
				obj = tempMem2;
			}
		}

		/** 
		 * @Title: print 
		 * @Description: TODO
		 * @param    
		 * @throws 
		 */  
		void print() {
			int flag = 0;
			for (int i = 0; i < obj.size(); i++) {
				if (i == 0 && obj.get(i).typ == 1 && obj.get(i).num == 0) {
					continue;
				}
				if (flag == 1 && obj.get(i).sign == 3) {
					System.out.print("+");
				} else if (obj.get(i).sign == 4) {
					System.out.print("-");
				} else if (obj.get(i).sign == 5) {
					System.out.print("*");
				} else if (obj.get(i).sign == 6) {
					System.out.print("/");
				}
				if (obj.get(i).typ == 1) {
					System.out.print(obj.get(i).num);
				} else {
					System.out.print(obj.get(i).argName);
				}
				if (obj.get(i).e != 1) {
					System.out.print("^" + obj.get(i).e);
				}
				flag = 1;
			}
			System.out.println();
			// System.out.println(obj.size());
			return;
		}
	}

	/** 
	 * @ClassName: Order 
	 * @Description: TODO 
	 * @author Shuqq 
	 *  
	 */  
	class Order {
		private String order;
		private String[] arg_name;
		private int typ;
		private String dir;
		private Vector<Integer> value = new Vector<Integer>();

		/** 
		 * @Title: input 
		 * @Description: TODO
		 * @return int   
		 * @throws 
		 */  
		@SuppressWarnings("resource")
		int input() {
			Scanner in = new Scanner(System.in);
			order = in.nextLine();
			while (order.length() == 0) {
				order = in.nextLine();
			}
			if (order.equals("end")) { // 结束的标�?
				in.close();
				return 0;
			}
			return 1;
		}

		/** 
		 * @Title: judge 
		 * @Description: TODO
		 * @param exp
		 * @return int
		 * @throws 
		 */  
		int judge(final Expression exp) {
			String[] temp = order.split(" ");
			order = "";
			for (int i = 0; i < temp.length; i++) {
				if (i == 0) {
					order = order + temp[i];
				} else {
					order = order + temp[i];
				}
			}
			if (order.charAt(0) == '!') {
				if (order.length() > 8 && order.substring(1, 9).equals("simplify")) { // 判断化简
					Pattern pat1 = Pattern.compile("([a-z]*\\=[0-9]*)*");
					if (!pat1.matcher(order.substring(9)).matches()) {
						return 0;
					} else {
						typ = 2;
						String temp2 = order.substring(9);
						String temp3 = "";
						int mark = 0;
						for (int i = 0; i < temp2.length() - 1; i++) {
							if (Character.isDigit(temp2.charAt(i)) 
									&& Character.isLetter(temp2.charAt(i + 1))) {
								temp3 = temp3 + temp2.substring(mark, i + 1) + "=";
								mark = i + 1;
							}
						}
						temp3 = temp3 + temp2.substring(mark);
						temp = temp3.split("=");
						temp2 = "";
						value = new Vector<Integer>();
						for (int i = 0; i < temp.length; i++) {
							if (i == 0) {
								temp2 = temp[i];
							} else if (i % 2 == 0) {
								temp2 = temp2 + " " + temp[i];
							} else {
								value.add(Integer.parseInt(temp[i]));
							}
						}
						arg_name = temp2.split(" ");
						for (int i = 0; i < arg_name.length; i++) {
							if (exp.find(arg_name[i]) == -1) {
								return 0;
							}
						}
						return 1;
					}
				} else if (order.length() > 4 && order.substring(1, 4).equals("d/d")) {
					Pattern pat2 = Pattern.compile("[a-z]*");
					if (!pat2.matcher(order.substring(4)).matches() || exp.find(order.substring(4)) == -1) {
						return 0;
					} else {
						dir = order.substring(4);
						typ = 3;
						return 1;
					}
				} else {
					return 0;
				}
			} else {
				String temp2;
				for (int i = 0; i < order.length() - 1; i++) {
					if (order.charAt(i) == '^' && !(Character.isDigit(order.charAt(i + 1)))) {
						return 0;
					}
				}
				temp2 = order;
				temp2 = temp2.replaceAll("\\+", "\\$");
				temp2 = temp2.replaceAll("\\-", "\\$");
				temp2 = temp2.replaceAll("\\*", "\\$");
				temp2 = temp2.replaceAll("\\/", "\\$");
				temp2 = temp2.replaceAll("\\^", "\\$");
				for (int i = 0; i < order.length() - 1; i++) {
					if (order.charAt(i) == '$' && order.charAt(i + 1) == '$') {
						return 0;
					}
				}
				temp = temp2.split("\\$");
				for (int i = 0; i < temp.length; i++) {
					Pattern pat1 = Pattern.compile("[0-9]*");
					Pattern pat2 = Pattern.compile("[a-z]*");
					if (!(pat1.matcher(temp[i]).matches() || pat2.matcher(temp[i]).matches())) {
						return 0;
					}
				}
				typ = 1;
				return 1;
			}
		}
	}
}
