package RMIProject;

import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;

import javax.naming.NamingException;


public class MyServer
{
    public static void main(String[] args) throws RemoteException, NamingException, MalformedURLException, AlreadyBoundException
    {
    	//启动服务器服务
        System.out.println("Constructing server implementation");
        MyServerImpl centralWarehouse = new MyServerImpl();
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
        centralWarehouse.f.addWindowListener (new WindowAdapter(){
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
    }
}