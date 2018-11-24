package RMITest2;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class WarehouseImpl extends UnicastRemoteObject implements Warehouse
{
    private static final long serialVersionUID = 1L;
    private Map<String,Double> prices;
    private ArrayList<WarehouseClient> clients=new ArrayList<WarehouseClient>();
    Frame f=new Frame("Server");
    public static int labsnum=20;
    private DataSet d;
    static ArrayList<Label> labs=new ArrayList<Label>();
    //Label lab=new Label("No clients now",Label.CENTER);
    protected WarehouseImpl() throws RemoteException
    {
        prices = new HashMap<String,Double>();
        prices.put("mate7",3700.00);
        for(int i=0;i<labsnum;i++) {
        	labs.add(new Label("",Label.CENTER));
        }
        labs.get(labsnum-1).setText("Currently No Clients!");
        d=new DataSet();
    }
    public double getPrice(String description) throws RemoteException
    {
        Double price = prices.get(description);
        return price == null? 0 : price;
    }
    public void SendMsg(String msg) throws RemoteException{
    	System.out.println(msg);
        for(int i=0;i<labsnum-1;i++) {
        	labs.get(i).setText(labs.get(i+1).getText());
        }
        labs.get(labsnum-1).setText(msg);
    	//lab.setText(msg);
//    	for(WarehouseClient c:clients) {
//    		c.showMsg(msg);
//    	}
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
    	WarehouseImpl.ShowMsg("Receive Data:("+a+","+b+")");
    }
    
    public String LinearRegression() throws RemoteException {

    	
    	WarehouseImpl.ShowMsg("Doing Linear Regression...");
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
    	WarehouseImpl.ShowMsg("Doing Linear Regression:w="+v.a);
    	WarehouseImpl.ShowMsg("Doing Linear Regression:b="+v.b);
    	WarehouseImpl.ShowMsg("Doing Linear Regression:y="+v.a+"x+"+v.b);
    	WarehouseImpl.ShowMsg("Linear Regression Finished!");
    	return String.valueOf(v.a)+"#"+String.valueOf(v.b);
    }
   
}