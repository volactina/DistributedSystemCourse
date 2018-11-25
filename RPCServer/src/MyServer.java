
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
        MyServerImpl centralServer = new MyServerImpl();
        System.out.println("Binding server implementation to registry");
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://localhost:1099/central_warehoues",centralServer);
        System.out.println("Waiting for invocations from clients ...");
        
        centralServer.f.setLayout(new GridLayout(centralServer.labsnum,1));
        centralServer.f.setSize(500,500);
        centralServer.f.setBackground(Color.gray);
        for(int i=0;i<centralServer.labsnum;i++) {
        	centralServer.f.add(centralServer.labs.get(i));
        }
        centralServer.f.setAlwaysOnTop(!centralServer.f.isAlwaysOnTop());
        centralServer.f.setVisible(true);
        centralServer.f.addWindowListener (new WindowAdapter(){
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
    }
}