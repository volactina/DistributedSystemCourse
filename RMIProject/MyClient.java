package RMIProject;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.naming.NamingException;

public class MyClient
{
    public static void main(String[] args) throws Exception
    {		
    	//访问服务器
    	MyClient thisclient=new MyClient();
        System.out.println("RMI registry binding:");
        String url = "rmi://localhost:1099/central_warehoues";
        Server centralServer = (Server) Naming.lookup(url);
        centralServer.SendMsg("Client communicating to Server!");
        //导入数据
        DataSet d=new DataSet();
        d.LoadSample();
        thisclient.ShowResult(d,new Vector(),false);//在客户端展示数据集
        //将数据发送到服务器
        for(int i=0;i<d.get_size();i++) {
        	System.out.println(d.v.get(i).a+","+d.v.get(i).b);
        	centralServer.SendData(d.v.get(i).a,d.v.get(i).b);
        }
        String sv=centralServer.LinearRegression();//远程调用服务器线性回归方法
        //获得线性回归结果在本地展示
        Vector v=new Vector(Double.valueOf(sv.substring(0,sv.indexOf("#"))),Double.valueOf(sv.substring(sv.indexOf("#")+1)));
        System.out.println(v.a+","+v.b);
        thisclient.ShowResult(d,v,true);
        Scanner in=new Scanner(System.in);
        while(in.hasNextLine()) {
        	centralServer.SendMsg(in.nextLine());
        }
        in.close();
    }
    public void showMsg(String msg){
    	System.out.println(msg);
    }
    public void ShowResult(DataSet d,Vector v,boolean flag) {
        JFrame jFrame = new JFrame("Client");
        JPanel jpanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            public void paint(Graphics graphics) {
                super.paint(graphics);
                graphics.drawLine(5,5,250,5);
                graphics.drawString("x",260,10);
                graphics.drawLine(5,5,5,250);
                graphics.drawString("y",10,260);
                graphics.setColor(Color.RED);
                for(int i=0;i<d.get_size();i++) {
                	graphics.fillOval((int)d.v.get(i).a+5, (int)d.v.get(i).b+5, 3, 3);
                }
                if(flag) {
                	graphics.setColor(Color.black);
                	double x1=0,y1=v.a*x1+v.b,x2=200,y2=v.a*x2+v.b;
                	System.out.println(v.a+","+v.b);
                	System.out.println(x1+","+y1+","+x2+","+y2);
                	graphics.setColor(Color.blue);
                	graphics.drawLine((int)x1+5, (int)y1+5, (int)x2+5, (int)y2+5);
                	graphics.drawString("y="+v.a+"x+"+v.b,40,40);
                }
                graphics.setColor(Color.black);
            }
        };
        jFrame.add(jpanel);
        jFrame.setSize(350, 350);
        jFrame.setVisible(true);
    }
}
