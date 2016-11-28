package lab1;

import java.util.Vector;
import java.util.regex.Pattern;

public class Expression{
	private Vector<Object> obj=new Vector<Object>();
	public Vector<Object> getObj() {
		return obj;
	}
	public void setObj(Vector<Object> obj) {
		this.obj = obj;
	}
	public int find(String str){//я╟ур╠Да©,private
		for(int i=0;i<obj.size();i++)
			if(obj.get(i).typ==2 &&  obj.get(i).argName.equals(str))
				return i;
		return -1;
	}
	
	public void E(String ord){
			obj=new Vector<Object>();
			String temp2="+"+ord.replaceAll("\\+","\\$\\+");
			temp2=temp2.replaceAll("\\-", "\\$\\-");
			temp2=temp2.replaceAll("\\*", "\\$\\*");
			temp2=temp2.replaceAll("\\/", "\\$\\/");
			
			String[] temp=temp2.split("\\$");
			for(int i=0;i<temp.length;i++){
				Object newMem=new Object();
				if(temp[i].charAt(0)=='+')
					newMem.sign=3;
				else if(temp[i].charAt(0)=='-')
					newMem.sign=4;
				else if(temp[i].charAt(0)=='*')
					newMem.sign=5;
				else if(temp[i].charAt(0)=='/')
					newMem.sign=6;
				temp[i]=temp[i].substring(1);
				Pattern pat1=Pattern.compile("[a-z]*\\^[0-9]*"),pat2=Pattern.compile("[0-9]*\\^[0-9]*");
	       		if(pat1.matcher(temp[i]).matches()){
	       			newMem.argName=temp[i].split("\\^")[0];
	       			newMem.e=Integer.parseInt(temp[i].split("\\^")[1]);
	       			newMem.typ=2;
	       		}
	       		else if(pat2.matcher(temp[i]).matches()){
	       			newMem.num=Integer.parseInt(temp[i].split("\\^")[0]);
	       			newMem.e=Integer.parseInt(temp[i].split("\\^")[1]);
	       			newMem.typ=1;
	       		}
	       		else{
	       			newMem.e=1;
	       			if(Character.isDigit(temp[i].charAt(0))){
	       				newMem.num=Integer.parseInt(temp[i]);
	       				newMem.typ=1;
	       			}
	       			else{
	       				newMem.argName=temp[i];
	       				newMem.typ=2;	
	       			}
	       		}
	       		obj.add(newMem);
			}
	}
	
	public void D(String dir){
			int flag=0;
			Vector<Object> tempMem=new Vector<Object>();
			Vector<Object> tempMem2=new Vector<Object>();
			Object newMem;
			int l=0,r=0;
			while(l<obj.size()){
				r=l+1;
				while(r<obj.size() && obj.get(r).sign>=5){
					++r;
				}
				flag=0;
			//	System.out.println("ord.typ==3 flag:"+flag+"r:"+r);
				tempMem=new Vector<Object>();
				for(int i=l;i<r;i++){
					if(obj.get(i).typ==1){
						tempMem.add(obj.get(i));
					}
					else{
						if(obj.get(i).argName.equals(dir)){
							flag=1;
								newMem=obj.get(i);
								newMem.e--;
								tempMem.add(newMem);
								newMem=new Object();
								newMem.typ=1;
								newMem.num=obj.get(i).e+1;
								newMem.sign=5;
								newMem.e=1;
								tempMem.add(newMem);
							}
						else
							tempMem.add(obj.get(i));
					}
				}
				if(flag==1)
					for(int i=0;i<tempMem.size();i++){
						tempMem2.add(tempMem.get(i));
				}
				l=r;
		
			}
			obj=tempMem2;
	}
	
	public void S(String[] arg_name,Vector<Integer> value){
			Vector<Object> tempMem,tempMem2=new Vector<Object>();
			Object newMem;
			
			for(int i=0;i<obj.size();i++){
				int ind=-1;
				if(obj.get(i).typ==2)
					for(int j=0;j<arg_name.length;j++)
						if(obj.get(i).argName.equals(arg_name[j]))
							ind=j;
				if(ind!=-1){///
					newMem=obj.get(i);
					newMem.typ=1;
					newMem.num=value.elementAt(ind);
					obj.set(i, newMem);
				}
				if(obj.get(i).e==0){
					newMem=new Object();
					newMem.e=1;
					newMem.typ=1;
					newMem.num=1;
					newMem.sign=obj.get(i).sign;
					obj.set(i, newMem);
				}
				if(obj.get(i).typ==1){
					int k=obj.get(i).num;
					newMem=obj.get(i);
       				for(int j=1;j<newMem.e;j++)
       					newMem.num*=k;
       				newMem.e=1;
       				obj.set(i,newMem);
				}
			}
			newMem=new Object();
			newMem.typ=1;
			newMem.num=0;
			newMem.e=1;
			newMem.sign=3;
			tempMem2.add(newMem);
			int l=0,r=0;
			while(l<obj.size()){
				r=l+1;
				while(r<obj.size() && obj.get(r).sign>=5){
					++r;
				}
				if(r==l+1){
					if(obj.get(l).typ==1){
						if(obj.get(l).sign==3){
							newMem=tempMem2.get(0);
							newMem.num+=obj.get(l).num;
						}
						else if(obj.get(l).sign==4){
							newMem=tempMem2.get(0);
							newMem.num-=obj.get(l).num;
						}
					}
					else
						tempMem2.add(obj.get(l));
					l=r;
					continue;
				}
				tempMem=new Vector<Object>();
				newMem= new Object();
				newMem.typ=1;
				newMem.e=1;
				newMem.sign=obj.get(l).sign;
				if(obj.get(l).typ==1)
					newMem.num=obj.get(l).num;
				else
					newMem.num=1;
				tempMem.add(newMem);
				newMem= new Object();
				newMem.typ=1;
				newMem.e=1;
				newMem.sign=6;
				newMem.num=1;
				tempMem.add(newMem);
				if(obj.get(l).typ==2)
				{
					newMem=new Object();
					newMem.typ=2;
					newMem.e=obj.get(l).e;
					newMem.sign=5;
					newMem.argName=obj.get(l).argName;
					tempMem.add(newMem);
				}
				for(int i=l+1;i<r;i++){
					if(obj.get(i).typ==1){
						if(obj.get(i).sign==5){
							newMem=tempMem.get(0);
							newMem.num*=obj.get(i).num;
						}
						else if(obj.get(i).sign==6){
							newMem=tempMem.get(1);
							newMem.num*=obj.get(i).num;
						}
					}
					else
						tempMem.add(obj.get(i));
				}
				l=r;
				if(tempMem.get(0).num==0)
					continue;
				for(int i=0;i<tempMem.size();i++){
					if(i==1 && tempMem.get(i).num==1)
						continue;
					tempMem2.add(tempMem.get(i));
				}
			}
			obj=tempMem2;
	}
	
	public String toString(){
		
		int flag=0;
		String result="";
		for(int i=0;i<obj.size();i++){
			if(i==0 && obj.get(i).typ==1 && obj.get(i).num==0)
				continue;
			if(flag==1 && obj.get(i).sign==3){
				result = result + "+";
			}
			else if(obj.get(i).sign==4){
				result = result + "-";
			}
			else if(obj.get(i).sign==5){
			    result = result + "*";
			}
			else if(obj.get(i).sign==6){
				result = result + "/";
			}
			if(obj.get(i).typ==1){
				result = result + obj.get(i).num;
			}
			else{
				result = result + obj.get(i).argName;
			}
			if(obj.get(i).e!=1){
				result = result + "^" + obj.get(i).e;
			}
			flag=1;
		}
		if(obj.size()==0)
			result = "0";
		//System.out.println(obj.size());
		//System.out.println("result is:"+result);
		return result;
	}

}
