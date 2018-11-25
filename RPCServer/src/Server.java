
import java.rmi.*;

//ºÃ≥–Remote¿‡
public interface Server extends Remote
{
    void SendMsg(String msg) throws RemoteException;
    String LinearRegression() throws RemoteException;
    void SendData(double a,double b)throws RemoteException;
}