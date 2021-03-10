package indi.ayun.original_mvp.udp;


import indi.ayun.original_mvp.OriginalMVP;

public class UDPRequestCall {
    private String inetAddress="";
    private int port=8080;
    private UDPThread udpThread;

    private int corePoolSize=5;
    private int maximumPoolSize=10;
    private long keepAliveTime=200L;

    public UDPRequestCall(String inetAddress, int port){
        this.inetAddress=inetAddress;
        this.port=port;
    }



    public void send(String udpMsg) {
        udpThread=new UDPThread(inetAddress,port,udpMsg);
        OriginalMVP.getThreadExecutor().addCommand(udpThread);
    }


}
