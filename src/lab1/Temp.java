/**
 * @Title: Temp.java 
 * @Package com.lab1 
 * @Description: TODO 
 * @author Shuqq 
 * @date 2016年10月16日 下午7:08:51
 * 
 */
package com.lab1;

import java.util.Scanner;
/** 
 * @ClassName: Temp 
 * @Description: TODO 
 * @author Shuqq 
 *  
 */
public class Temp {
	public static void main(final String[] args) {
		Expression myExpression = new Expression(); //表达式对象
		Scanner in = new Scanner(System.in);
		while (true) {
			//Scanner in = new Scanner(System.in);
			String preOrder = new String(in.nextLine());
		
			if (!preOrder.equals("") && preOrder != null) {
				if (preOrder.equals("end")) { //结束的标志־
					in.close();
					break;
				}
				
				Order inputOrder = new Order(); //新建命令对象
				inputOrder.init(preOrder); //调用命令初始化方法
				int type = inputOrder.judge(myExpression); //调用命令类型判断方法
				
				if (type == 0) {
					System.out.println("Error!");
					continue;
				} else if (type == 1) {
					myExpression.substitution(inputOrder);
					myExpression.adjust(inputOrder);
					
					//拆分了S方法
					StringBuffer temp = new StringBuffer();
					temp = myExpression.parenthese(myExpression.expression);  //括号
					temp = myExpression.involution(myExpression.expression, temp); //乘方
					temp = myExpression.muldiv(myExpression.expression, temp); //乘除
					myExpression.adddel( myExpression.expression, temp);    //加减
					
					for (int i = 0; i < inputOrder.cnt_argue; i++) {
						for (int j = 0; j < inputOrder.arguement_namelen[i]; j++) {
							System.out.print(inputOrder.arguement_name[i]);
						}
						System.out.println(" " + inputOrder.arguement[i]);
					}
				} else if (type == 2) {
					myExpression.D(inputOrder);
					System.out.println(inputOrder.derivation);
				}
				System.out.println(myExpression.getExpression());
			} else {
				in.close();
				System.out.println("Error!");
			}
			
		}
	}
}

class Order {
	final int MAX_ARGUEMENT_NUMBER = 1001; //最大变量个数
	StringBuilder simp = new StringBuilder("simplify");
	
	int orderLength ;
	StringBuffer order = new StringBuffer();

	StringBuffer[] arguement_name = new StringBuffer[MAX_ARGUEMENT_NUMBER]; //变量名
	int[] arguement = new int[MAX_ARGUEMENT_NUMBER]; //变量替换值
	int[] arguement_namelen = new int[MAX_ARGUEMENT_NUMBER]; //变量名长度
	int cnt_argue; //当前的变量数目

	StringBuffer derivation = new StringBuffer();

	int derivation_len;

	public int getOrderLength() {
		return orderLength;
	}

	public void setOrderLength(int orderLength) {
		this.orderLength = orderLength;
	}

	public StringBuffer getOrder() {
		return order;
	}

	public void setOrder(StringBuffer order) {
		this.order = order;
	}

	public StringBuffer[] getArguement_name() {
		return arguement_name;
	}

	public void setArguement_name(StringBuffer[] arguement_name) {
		this.arguement_name = arguement_name;
	}

	public int[] getArguement() {
		return arguement;
	}

	public void setArguement(int[] arguement) {
		this.arguement = arguement;
	}
 
	public int[] getArguement_namelen() {
		return arguement_namelen;
	}

	public void setArguement_namelen(int[] arguement_namelen) {
		this.arguement_namelen = arguement_namelen;
	}

	public int getCnt_argue() {
		return cnt_argue;
	}

	public void setCnt_argue(int cnt_argue) {
		this.cnt_argue = cnt_argue;
	}

	public StringBuffer getDerivation() {
		return derivation;
	}

	public void setDerivation(StringBuffer derivation) {
		this.derivation = derivation;
	}

	public int getDerivation_len() {
		return derivation_len;
	}

	public void setDerivation_len(int derivation_len) {
		this.derivation_len = derivation_len;
	}
	
	public void init(String preOrder) {

		for (int i = 0; i < preOrder.length(); i++) {
			if (preOrder.charAt(i) >= 'A' && preOrder.charAt(i) <= 'Z') {
				this.order.append((char) 
						((int) preOrder.charAt(i) + (int) 'a' - (int) 'A'));
			} else if (preOrder.charAt(i) != ' ') {
				this.order.append(preOrder.charAt(i));
			}
		} //大小写转换，除去空格
		
		//this.order.append(preOrder.toLowerCase().replaceAll("\\s", ""));
		this.orderLength = order.length();
		
		// System.out.println("order initial"+this.order);
		
		for (int i = 0; i < MAX_ARGUEMENT_NUMBER; i++) {
			this.arguement_name[i] = new StringBuffer();
		}
	}
	
	public int judge(Expression expression) {
		if (this.order.charAt(0) == '!') {
			if (this.order.charAt(1) == 's') { //判断化简
				if (this.orderLength < 9 || !this.order.substring(1, 9).equals(simp.toString())) 
					return 0;
				
				/*for (int i = 0; i < 8; i++) {
					if (i + 1 >= this.orderLength || this.order.charAt(i + 1) != simp.charAt(i))
						return 0;
				}*/
				int p = 9;
				while (p < this.orderLength) {

					StringBuffer temp = new StringBuffer();
					if (!Character.isLetter(this.order.charAt(p))) 
						return 0;
					//if (!(this.order.charAt(p) <= 'z' && this.order.charAt(p) >= 'a'))
						//return 0;
					while (p <this.orderLength && Character.isLetter(this.order.charAt(p))) {
					//while (p < this.orderLength && this.order.charAt(p) <= 'z' && this.order.charAt(p) >= 'a') {
						temp.append(this.order.charAt(p));
						p++;
					}

					if (find(temp, temp.length(), expression.expression, 
							expression.expression_len) != 1) {
						return 0;
					}
					this.arguement_name[this.cnt_argue].append(temp);
					this.arguement_namelen[this.cnt_argue] = temp.length();
					if (p >= this.orderLength || this.order.charAt(p++) != '=') {
						return 0;
					}
					this.arguement[this.cnt_argue] = 0;
					while (p < this.orderLength && Character.isDigit(this.order.charAt(p))) {
					//while (p < this.orderLength && this.order.charAt(p) <= '9' && this.order.charAt(p) >= '0') {
						this.arguement[this.cnt_argue] *= 10;
						this.arguement[this.cnt_argue] += this.order.charAt(p) - '0';
						++p;
					}
					++(this.cnt_argue);
				}
				return 1;
			} else {  //判断求导
				if (this.order.charAt(1) != 'd' || this.order.charAt(2) != '/' 
						|| this.order.charAt(3) != 'd') {
					return 0;
				}
				this.derivation_len = 0;
				for (int i = 4; i < this.orderLength; i++) {
					if (!Character.isLetter(this.order.charAt(i))) {
					//if (!(this.order.charAt(i) >= 'a' && this.order.charAt(i) <= 'z')) {
						return 0;
					}
					this.derivation.append(this.order.charAt(i));
					this.derivation_len++;
				}
				if (find(this.derivation, this.derivation_len, 
						expression.expression, expression.getExpression_len()) != 1) {
					return 0;
				}
				return 2;
			}
		} else {
			int bracelet = 0;
			StringBuffer temp = new StringBuffer();
			int i = 0;
			/*
			 * System.out.println("orderLength"+orderLength);
			 * System.out.println("orderLength"+this.orderLength);
			 */
			while (i < this.orderLength) {
				if (this.order.charAt(i) == '(') {
					bracelet++;
					temp.append(order.charAt(i));
					++i;
				} else if (this.order.charAt(i) == ')') {
					bracelet--;
					temp.append(order.charAt(i));
					++i;
					if (bracelet < 0)
						return 0;
				} else if (i < this.orderLength && this.order.charAt(i) >= 'a' && this.order.charAt(i) <= 'z') {
					while (i < this.orderLength && this.order.charAt(i) <= 'z' && this.order.charAt(i) >= 'a') {
						temp.append(this.order.charAt(i));
						i++;
					}
					if (i < this.orderLength - 1 && this.order.charAt(i) == '^'
							&& !(this.order.charAt(i + 1) <= '9' && this.order.charAt(i + 1) >= '0')) {
						return 0; //如果阶数不是整数，则非法
					} else if (i < this.orderLength && (this.order.charAt(i) == '+' 
							|| this.order.charAt(i) == '-' || this.order.charAt(i) == '*' 
							|| this.order.charAt(i) == '/' || this.order.charAt(i) == '^')) {
						temp.append(this.order.charAt(i));
						i++;
					} else if (i < this.orderLength && this.order.charAt(i) != ')')
					{
						return 0;
					}
				} else if (this.order.charAt(i) >= '0' && this.order.charAt(i) <= '9') {
					while (i < this.orderLength && this.order.charAt(i) <= '9' && this.order.charAt(i) >= '0') {
						temp.append(this.order.charAt(i));
						i++;
					}
					if (i < this.orderLength - 1 && this.order.charAt(i) == '^'
							&& !(this.order.charAt(i + 1) <= '9' && this.order.charAt(i + 1) >= '0'))
						return 0;
					else if (i < this.orderLength && ((this.order.charAt(i) <= 'z' && this.order.charAt(i) >= 'a')
							|| this.order.charAt(i) == '('))
						temp.append('*'); //补'*'号
					else if (i < this.orderLength && (this.order.charAt(i) == '+' || this.order.charAt(i) == '-'
							|| this.order.charAt(i) == '*' || this.order.charAt(i) == '/'
							|| this.order.charAt(i) == '^')) {
						temp.append(this.order.charAt(i));
						i++;
					} else if (i < this.orderLength && this.order.charAt(i) != ')' && this.order.charAt(i) != '(')
						return 0;
				}
			}
			if (this.order.charAt(this.orderLength - 1) == '+' || this.order.charAt(this.orderLength - 1) == '-'
					|| this.order.charAt(this.orderLength - 1) == '*' || this.order.charAt(this.orderLength - 1) == '/'
					|| this.order.charAt(this.orderLength - 1) == '^')
				if (bracelet != 0)
					return 0;
			expression.setExpression_len(temp.length());
			expression.setExpression(temp);
			return 3;
		}
	}
	
	public int find(StringBuffer a, int lena, StringBuffer b, int lenb) {
		for (int i = 0; i <= lenb - lena; i++) {
			int flag = 1;
			for (int j = 0; j < lena; j++) {
				if (b.charAt(i + j) != a.charAt(j)) {
					flag = 0;
					break;
				}
			}
			
			if (flag == 1 && (i - 1 < 0 || !(b.charAt(i - 1) <= 'z' && b.charAt(i - 1) >= 'a'))
					&& ((i + lena) >= lenb || !(b.charAt(i + lena) <= 'z' && b.charAt(i + lena) >= 'a'))) {
				return 1;
			}
		}	
		return 0;
	}
	
	public int find2(StringBuffer a, int lena) {
		for (int i = 0; i < cnt_argue; i++) {
			if (lena == arguement_namelen[i]) {
				if (arguement_name[i].subSequence(0, arguement_name[i].length()).equals(a.subSequence(0, a.length()))) {
					return i;
				} //替换值不能为0
			}
		}
		return -1;
	}
}

class Expression {
	StringBuffer expression = new StringBuffer();
	int expression_len;

	public StringBuffer getExpression() {
		return expression;
	}

	public void setExpression(StringBuffer expression) {
		this.expression = expression;
	}

	public int getExpression_len() {
		return expression_len;
	}

	public void setExpression_len(int expression_len) {
		this.expression_len = expression_len;
	}

	public void E(Order order) {

	}

	public void D(Order order) { //求导

	}
	
	public void substitution(Order order) {
		StringBuffer temp2 = new StringBuffer();
		StringBuffer temp1 = new StringBuffer();
		for (int i = 0; i < this.expression_len; i++) {
			if (this.expression.charAt(i) >= 'a' && this.expression.charAt(i) <= 'z') {
				//StringBuffer temp1 = new StringBuffer();
				while (i < this.expression_len && this.expression.charAt(i) >= 'a'
						&& this.expression.charAt(i) <= 'z') {
					temp1.append(this.expression.charAt(i));
					i++;
				}
				i--;
				int index = order.find2(temp1, temp1.length());
				int len1 = temp1.length();
				if (index == -1) {
					temp2.append(temp1);
				} else {
					len1 = 0;
					long k = order.arguement[index];
					while (k > 0) {
						if (temp1.length() - 1 < len1)
							temp1.append("$");
						temp1.setCharAt(len1, (char) (k % 10 + '0'));
						len1++;
						k /= 10;
					}
					for (int j = len1 - 1; j >= 0; j--)
						temp2.append(temp1.charAt(j));
					
				}
				continue;
			}
			temp1.setLength(0);             //////////////
			temp2.append(this.expression.charAt(i));
		}
		this.expression.delete(0, this.expression_len);
		this.expression.append(temp2);
		this.expression_len = temp2.length();
		System.out.println("substitusion " + this.expression);
		//return;
	}
	
	public void adjust(Order order) {

		StringBuffer temp1 = new StringBuffer();
		StringBuffer temp2 = new StringBuffer();
		int len1 = 0;
		int len2 = 0;
		for (int i = 0; i < this.expression_len; i++) {
			if (this.expression.charAt(i) == '*') {
				if (!((this.expression.charAt(i - 1) >= 'a' && this.expression.charAt(i - 1) <= 'z')
						&& (this.expression.charAt(i + 1) >= '0' && this.expression.charAt(i + 1) <= '9')))
					continue;
				int p1 = i - 1;
				int p2 = i + 1;
				len1 = 0;
				while (p1 >= 0 && this.expression.charAt(p1) >= 'a' && this.expression.charAt(p1) <= 'z') {
					if (temp1.length() - 1 < len1)
						temp1.append("$");
					temp1.setCharAt(len1, this.expression.charAt(p1));
					len1++;
					p1--;
				}
				len2 = 0;
				while (p2 < this.expression_len && this.expression.charAt(p2) >= '0'
						&& this.expression.charAt(p2) <= '9') {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, this.expression.charAt(p2));
					len2++;
					p2++;
				}
				if ((p1 >= 0 && (this.expression.charAt(p1) == '^'))
						|| (p2 < this.expression_len && (this.expression.charAt(p2) == '^')))
					continue;
				char sign = this.expression.charAt(i);

				// this.expression.replace(p1+1, p1+len1, temp2.substring(0,
				// len1-1));

				for (int j = p1 + 1; j <= p1 + len1; j++) {
					if (expression.length() - 1 < j)
						expression.append("$");
					this.expression.setCharAt(j, temp2.charAt(j - p1 - 1));
				}

				if (expression.length() - 1 < p1 + len1 + 1)
					expression.append("$");
				this.expression.setCharAt(p1 + len1 + 1, sign);

				for (int j = p1 + len1 + 2; j < p2; j++) {
					--len1;
					if (expression.length() - 1 < j)
						expression.append("$");
					this.expression.setCharAt(j, temp1.charAt(len1));
				}

				i = p2 - 1;
			}
		}
		for (int i = 0; i < this.expression_len; i++) {
			if (this.expression.charAt(i) == '+' || this.expression.charAt(i) == '-') {
				if (!((this.expression.charAt(i - 1) >= 'a' && this.expression.charAt(i - 1) <= 'z')
						&& (this.expression.charAt(i + 1) >= '0' && this.expression.charAt(i + 1) <= '9')))
					continue;
				int p1 = i - 1;
				int p2 = i + 1;
				len1 = 0;
				while (p1 >= 0 && this.expression.charAt(p1) >= 'a' && this.expression.charAt(p1) <= 'z') {
					if (temp1.length() - 1 < len1)
						temp1.append("$");
					temp1.setCharAt(len1, this.expression.charAt(p1));
					len1++;
					p1--;
				}
				len2 = 0;
				while (p2 < this.expression_len && this.expression.charAt(p2) >= '0'
						&& this.expression.charAt(p2) <= '9') {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, this.expression.charAt(p2));
					len2++;
					p2++;
				}
				if ((p1 >= 0 && (this.expression.charAt(p1) == '*' || this.expression.charAt(p1) == '/'
						|| this.expression.charAt(p1) == '^'))
						|| (p2 < this.expression_len && (this.expression.charAt(p2) == '*'
								|| this.expression.charAt(p2) == '/' || this.expression.charAt(p2) == '^')))
					continue;

				// char sign=this.expression.charAt(i);

				for (int j = p1 + 1; j <= p1 + len1; j++) {
					if (expression.length() - 1 < j)
						expression.append("$");
					this.expression.setCharAt(j, temp2.charAt(j - p1 - 1));
				}

				i = p2 - 1;
			}
		}
		System.out.println("adjust " + this.expression);
	}
	
	public StringBuffer parenthese(StringBuffer expression) {
		StringBuffer temp2 = new StringBuffer();
		int bracelet = 0;
	
		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '(') {
				StringBuffer temp1 = new StringBuffer();
				bracelet = 1;
				i++;
				while (bracelet != 0) {
					if (expression.charAt(i) == ')')
						bracelet--;
					
					if (bracelet == 0)
						break;
					temp1.append(expression.charAt(i));
					i++;
				}
				parenthese(temp1);
				int flag = 0;
				for (int j = 1; j < temp1.length(); j++) {
					if (!(temp1.charAt(j) >= '0' && temp1.charAt(j) <= '9'))
						flag = 1;
				}
				if (flag == 1)
					temp2.append('(');
				temp2.append(temp1);

				if (flag == 1)
					temp2.append(')');
				continue;
			}
			temp2.append(expression.charAt(i));
		}
		expression_len = temp2.length();
		expression.delete(0, expression.length());
		expression.append(temp2);
		System.out.println("1:" + expression);
		return temp2;
	}
	
	public StringBuffer involution(StringBuffer expression, StringBuffer temp2) {
		int len2 = 0;
		temp2.delete(0, temp2.length());
		
		for (int i = 0; i < expression_len; i++) {
			if (expression.charAt(i) == '^') {
				if (!(expression.charAt(i - 1) >= '0' && expression.charAt(i) <= '9')) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, expression.charAt(i));
					len2++;
					continue;
				}
				int p = len2 - 1;
				while (p >= 0 && temp2.charAt(p) >= '0' && temp2.charAt(p) <= '9')
					p--;
				long k1 = 0;
			    long k2 = 0;
				for (int j = p + 1; j < len2; j++)
					k1 = k1 * 10 + temp2.charAt(j) - '0';
				len2 = p + 1;
				++i;
				while (i < expression_len && expression.charAt(i) >= '0' && expression.charAt(i) <= '9')
					k2 = k2 * 10 + expression.charAt(i) - '0';
				i--;
				long k = 1;
				for (int j = 1; j <= k2; j++) {
					k *= k1;
				}

				StringBuffer temp1 = new StringBuffer();
				while (k > 0) {
					temp1.append((char) (k % 10 + '0'));
					k /= 10;
				}
				if (temp1.length() == 0)
					temp1.append('0');
				for (int j = temp1.length() - 1; j >= 0; j--) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, temp1.charAt(j));
					len2++;
				}

				continue;
			}
			if (temp2.length() - 1 < len2)
				temp2.append("$");
			temp2.setCharAt(len2, expression.charAt(i));
			len2++;
		}
		expression_len = len2;
		/*
		 * for(int i=0;i<expression_len;i++) { if(expression.length()-1<i)
		 * expression.append("$"); expression.setCharAt(i,temp2.charAt(i));
		 * 
		 * }
		 */
		expression_len = temp2.length();
		expression.delete(0, expression.length());
		expression.append(temp2);
		System.out.println("2:" + expression);
		return temp2;
	}
	
	public StringBuffer muldiv(StringBuffer expression, StringBuffer temp2) {
		int len2 = 0;
		temp2.delete(0, temp2.length());

		N1: for (int i = 0; i < expression_len; i++) {
			if (expression.charAt(i) == '*' || expression.charAt(i) == '/') {
				int sign;
				if (expression.charAt(i) == '*')
					sign = 1;
				else
					sign = 2;
				if (!(expression.charAt(i - 1) >= '0' && expression.charAt(i - 1) <= '9'
						&& expression.charAt(i + 1) >= '0' && expression.charAt(i + 1) <= '9')) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, expression.charAt(i));
					len2++;
					continue N1;
				}
				System.out.println("3.1:" + temp2);
				int p1 = i - 1;
				int p2 = i + 1;
				while (p1 >= 0 && expression.charAt(p1) >= '0' && expression.charAt(p1) <= '9')
					p1--;
				while (p2 < expression_len && expression.charAt(p2) >= '0' && expression.charAt(p2) <= '9')
					p2++;

				if (((p1 >= 0 && expression.charAt(p1) == '^')
						|| (p2 < expression_len && expression.charAt(p2) == '^'))) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, expression.charAt(i));
					len2++;
					continue N1;
				}
				System.out.println("3.2:" + temp2);
				int p = len2 - 1;
				while (p >= 0 && temp2.charAt(p) >= '0' && temp2.charAt(p) <= '9')
					p--;
				long k1 = 0;
				long k2 = 0;
				for (int j = p + 1; j < len2; j++)
					k1 = k1 * 10 + temp2.charAt(j) - '0';

				len2 = p + 1;
				++i;
				while (i < expression_len && expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
					k2 = k2 * 10 + expression.charAt(i) - '0';
					i++;
				}
				i--;
				long k = 0;
				if (sign == 1)
					k = k1 * k2;
				if (sign == 2)
					k = k1 / k2;
				StringBuffer temp1 = new StringBuffer();
				while (k > 0) {
					temp1.append((char) (k % 10 + '0'));
					k /= 10;
				}
				System.out.println("len1" + temp1.length());
				if (temp1.length() == 0) {
					temp1.append('0');
				}
				for (int j = temp1.length() - 1; j >= 0; j--) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, temp1.charAt(j));
					len2++;
				}
				continue N1;
			}
			if (temp2.length() - 1 < len2)
				temp2.append("$");
			temp2.setCharAt(len2++, expression.charAt(i));
			System.out.println("3.3:" + temp2);
		}
		expression_len = len2;
		expression.delete(0, expression.length());
		for (int i = 0; i < expression_len; i++) {
			if (expression.length() - 1 < i)
				expression.append("$");
			expression.setCharAt(i, temp2.charAt(i));
		}

		System.out.println("3:" + expression);
		expression_len = temp2.length();
		expression.delete(0, expression.length());
		expression.append(temp2);
		System.out.println("33:" + expression);
		return temp2;
	}
	
	public void adddel(StringBuffer expression, StringBuffer temp2) {
		int len2 = 0;
		temp2.delete(0, temp2.length());
		N2: for (int i = 0; i < expression_len; i++) {
			if (expression.charAt(i) == '+' || expression.charAt(i) == '-') {
				System.out.println("in add");
				int sign;
				if (expression.charAt(i) == '+')
					sign = 1;
				else
					sign = 2;
				System.out.println("in add1.01" + expression.charAt(i - 1) + "..." + expression.charAt(i + 1));
				if (((expression.charAt(i - 1) >= 'a' && expression.charAt(i - 1) <= 'z')
						|| expression.charAt(i - 1) == ')')
						|| ((expression.charAt(i + 1) >= 'a' && expression.charAt(i + 1) <= 'z')
								|| expression.charAt(i + 1) == '(')) { ///之前进不来为什么
					System.out.println("in add2");
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, expression.charAt(i));
					System.out.println("+-?" + ",,," + expression.charAt(i));
					len2++;
					continue N2;
				}
				int p1 = i - 1;
				int p2 = i + 1;
				while (p1 >= 0 && expression.charAt(p1) >= '0' && expression.charAt(p1) <= '9')
					p1--;

				while (p2 < expression_len && expression.charAt(p2) >= '0' && expression.charAt(p2) <= '9')
					p2++;

				if ((p1 >= 0 && (expression.charAt(p1) == '*' || expression.charAt(p1) == '^'
						|| expression.charAt(p1) == '/'))
						|| (p2 < expression_len && (expression.charAt(p2) == '*' || expression.charAt(p2) == '^'
								|| expression.charAt(p2) == '/'))) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, expression.charAt(i));
					len2++;
					continue N2;
				}
				int p = len2 - 1;
				while (p >= 0 && temp2.charAt(p) >= '0' && temp2.charAt(p) <= '9') {
					p--;
				}
				long k1 = 0;
				long k2 = 0;
				for (int j = p + 1; j < temp2.length(); j++)
					k1 = k1 * 10 + temp2.charAt(j) - '0';

				len2 = p + 1;
				++i;
				while (i < expression_len && expression.charAt(i) >= '0' && expression.charAt(i) <= '9')
					k2 = k2 * 10 + expression.charAt(i++) - '0';

				i--;
				long k = 0;
				if (sign == 1)
					k = k1 + k2;
				if (sign == 2)
					k = k1 - k2;
				StringBuffer temp1 = new StringBuffer();
				while (k > 0) {
					temp1.append((char) (k % 10 + '0'));
					k /= 10;
				}
				if (temp1.length() == 0)
					temp1.append('0');
				for (int j = temp1.length() - 1; j >= 0; j--) {
					if (temp2.length() - 1 < len2)
						temp2.append("$");
					temp2.setCharAt(len2, temp1.charAt(j));
					len2++;
				}
				continue N2;
			}
			if (temp2.length() - 1 < len2)
				temp2.append("$");
			temp2.setCharAt(len2, expression.charAt(i));
			len2++;
		}
		expression_len = len2;
		expression.delete(0, expression.length());
		for (int i = 0; i < expression_len; i++) {
			if (expression.length() - 1 < i)
				expression.append("$");
			expression.setCharAt(i, temp2.charAt(i));  //////////
		}
		System.out.println("4:" + expression);
		
	}
}
