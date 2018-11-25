
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

//继承UnicastRemoteObject类实现Server接口
public class MyServerImpl extends UnicastRemoteObject implements Server
{
    private static final long serialVersionUID = 1L;
    Frame f=new Frame("Server");
    public static int labsnum=20;
    private DataSet d;
    static ArrayList<Label> labs=new ArrayList<Label>();
    protected MyServerImpl() throws RemoteException
    {
        for(int i=0;i<labsnum;i++) {
        	labs.add(new Label("",Label.CENTER));
        }
        labs.get(labsnum-1).setText("Currently No Clients!");
        d=new DataSet();
    }
    public void SendMsg(String msg) throws RemoteException{
    	System.out.println(msg);
        for(int i=0;i<labsnum-1;i++) {
        	labs.get(i).setText(labs.get(i+1).getText());
        }
        labs.get(labsnum-1).setText(msg);
    }
    private static void ShowMsg(String msg) {
    	for(int i=0;i<labsnum-1;i++) {
        	labs.get(i).setText(labs.get(i+1).getText());
        }
        labs.get(labsnum-1).setText(msg);
    }
    public void SendData(double a,double b) throws RemoteException{
    	System.out.println(a+","+b);
    	d.v.add(new Vector(a,b));
    	MyServerImpl.ShowMsg("Receive Data:("+a+","+b+")");
    }
    public String LinearRegression() throws RemoteException {

    	
    	MyServerImpl.ShowMsg("Doing Linear Regression...");
    	double sumx=0,avgx=0,ssumx=0,sz=d.get_size();
    	for(int i=0;i<d.get_size();i++) {
    		sumx+=d.v.get(i).a;
    		ssumx+=d.v.get(i).a*d.v.get(i).a;
    	}
    	avgx=sumx/sz;
    	double k1=0,k2=ssumx-sumx*sumx/sz;
    	for(int i=0;i<d.get_size();i++) {
    		k1+=d.v.get(i).b*(d.v.get(i).a-avgx);
    	}
    	double w=k1/k2,b=0;
    	for(int i=0;i<d.get_size();i++) {
    		b+=d.v.get(i).b-w*d.v.get(i).a;
    	}
    	b/=sz;
    	Vector v=new Vector(w,b);
    	MyServerImpl.ShowMsg("Doing Linear Regression:w="+v.a);
    	MyServerImpl.ShowMsg("Doing Linear Regression:b="+v.b);
    	MyServerImpl.ShowMsg("Doing Linear Regression:y="+v.a+"x+"+v.b);
    	MyServerImpl.ShowMsg("Linear Regression Finished!");
    	return String.valueOf(v.a)+"#"+String.valueOf(v.b);
    }
   
}