package RMITest2;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;

import javax.naming.NamingException;


public class WarehouseServer
{
    public static void main(String[] args) throws RemoteException, NamingException, MalformedURLException, AlreadyBoundException
    {
        System.out.println("Constructing server implementation");
        WarehouseImpl centralWarehouse = new WarehouseImpl();
        
        System.out.println("Binding server implementation to registry");
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://localhost:1099/central_warehoues",centralWarehouse);
        
        System.out.println("Waiting for invocations from clients ...");
        centralWarehouse.f.setLayout(new GridLayout(centralWarehouse.labsnum,1));
        centralWarehouse.f.setSize(500,500);
        centralWarehouse.f.setBackground(Color.gray);
        for(int i=0;i<centralWarehouse.labsnum;i++) {
        	centralWarehouse.f.add(centralWarehouse.labs.get(i));
        }
        centralWarehouse.f.setAlwaysOnTop(!centralWarehouse.f.isAlwaysOnTop());
        centralWarehouse.f.setVisible(true);
        centralWarehouse.f.addWindowListener (new WindowAdapter(){ // 为了关闭窗口
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
    }
}