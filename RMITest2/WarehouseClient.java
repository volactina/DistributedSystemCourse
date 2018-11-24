package RMITest2;
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

public class WarehouseClient
{
    public static void main(String[] args) throws Exception
    {		
    	WarehouseClient thisclient=new WarehouseClient();
        System.out.println("RMI registry binding:");
        String url = "rmi://localhost:1099/central_warehoues";
        Warehouse centralWarehouse = (Warehouse) Naming.lookup(url);
        String descr = "mate7";
        double price = centralWarehouse.getPrice(descr);
        System.out.println(descr + ":" + price);
        System.out.println("mate8"+":"+centralWarehouse.getPrice("mate8"));
        
        centralWarehouse.SendMsg("Client communicating to Server!");
        DataSet d=new DataSet();
        d.LoadSample();
        thisclient.ShowResult(d,new Vector(),false);
        for(int i=0;i<d.get_size();i++) {
        	System.out.println(d.v.get(i).a+","+d.v.get(i).b);
        	centralWarehouse.SendData(d.v.get(i).a,d.v.get(i).b);
        }
        String sv=centralWarehouse.LinearRegression();
        Vector v=new Vector(Double.valueOf(sv.substring(0,sv.indexOf("#"))),Double.valueOf(sv.substring(sv.indexOf("#")+1)));
        System.out.println(v.a+","+v.b);
        thisclient.ShowResult(d,v,true);
        Scanner in=new Scanner(System.in);
        while(in.hasNextLine()) {
        	centralWarehouse.SendMsg(in.nextLine());
        }
        in.close();
    }
    public void showMsg(String msg){
    	System.out.println(msg);
    }
    public void ShowResult(DataSet d,Vector v,boolean flag) {
    	// �������
        JFrame jFrame = new JFrame("Client");
        // ��������
        JPanel jpanel = new JPanel() {
            //���кţ���ʡ�ԣ�
            private static final long serialVersionUID = 1L;

            // ��дpaint����
            @Override
            public void paint(Graphics graphics) {
                // �����ȵ��ø����paint����
                super.paint(graphics);
                //graphics.drawOval(100, 70, 30, 30);//��Բ��
                //graphics.drawRect(105, 100, 20, 30);//������
                
                //graphics.drawLine(200, 0, 0, 120);// ��ֱ��
                //graphics.drawLine(0, 0, 150, 0);// ��ֱ��
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
        //������Ƕ�뵽�����
        jFrame.add(jpanel);
        // ���û����С����ȣ��߶ȣ���Ĭ�϶�Ϊ0
        jFrame.setSize(350, 350);
        // ������չʾ������true���ÿɼ���Ĭ��Ϊfalse����
        jFrame.setVisible(true);
    }
}