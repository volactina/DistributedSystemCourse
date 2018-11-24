package RMITest2;
import java.rmi.*;
import java.awt.*;
import java.awt.event.*;

public interface Warehouse extends Remote
{
    double getPrice(String description) throws RemoteException;
    void SendMsg(String msg) throws RemoteException;
    String LinearRegression() throws RemoteException;
    void SendData(double a,double b)throws RemoteException;
}