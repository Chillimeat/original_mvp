package indi.ayun.original_mvp.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import indi.ayun.original_mvp.mlog.MLog;

public class UDPThread implements Runnable{
    private String inetAddress="";
    private int port=8080;
    private String udpMsg="";

    private DatagramSocket socket=null;
    private DatagramPacket packet=null;
    private BufferedReader reader=null;

    public UDPThread(String inetAddress, int port,String udpMsg) {
        this.inetAddress = inetAddress;
        this.port = port;
        this.udpMsg = udpMsg;
        reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            //初始化InetAddress 对象
            InetAddress serverAddress = InetAddress.getByName(inetAddress);
            //初始化DatagramPacket对象
            packet = new DatagramPacket(udpMsg.getBytes(),udpMsg.getBytes().length, serverAddress, port);
            //发送
            socket.send(packet);
            //获得输入流
        } catch (SocketException e) {
            MLog.d(e.toString());
            e.printStackTrace();
        } catch (UnknownHostException e) {
            MLog.d(e.toString());
            e.printStackTrace();
        } catch (IOException e) {//IO异常
            MLog.d(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            MLog.d(e.toString());
            e.printStackTrace();
        } finally {
            //如果DatagramSocket已经实例化，需要关闭
            if (socket != null) {
                socket.close();
            }
        }
    }
}
