package RMIProject;

import java.util.ArrayList;

public class DataSet {
	ArrayList<Vector> v;
	private int size=100;
	DataSet(){
		v=new ArrayList<Vector>();
	}
	public void LoadSample() {
		double a=3,b=25,maxranx=(300-b)/a;
		for(int i=0;i<size;i++) {
			double x=Math.random()*maxranx;
			double y=a*x+b+(Math.random()-0.5)*50;
			v.add(new Vector(x,y));
		}
	}
	public int get_size() {
		return size;
	}
}
