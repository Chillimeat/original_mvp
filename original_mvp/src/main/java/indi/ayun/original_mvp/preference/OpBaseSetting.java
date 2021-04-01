package indi.ayun.original_mvp.preference;

import android.content.Context;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.utils.phone.Info;
import indi.ayun.original_mvp.utils.verification.IsNothing;


public class OpBaseSetting {
    private final String NETWORK_INETADDRESS="inetAddress";//服务器ip地址
    private final String NETWORK_CLOUDIP = "cloudIP";//云，域名
    private final String NETWORK_DOMAIN="domain";//域名
    private final String NETWORK_UDP_PORT = "udp_port";
    private final String NETWORK_HTTP_PORT = "http_port";
    private final String NETWORK_SUCCESSCODE= "successCode";

    public void saveInetAddress(String inetAddress) {
        op.saveString(NETWORK_INETADDRESS, inetAddress);
    }
    public String getInetAddress() {
        return op.getString(NETWORK_INETADDRESS);
    }

    public void saveCloudIP(String cloudIP) {
        op.saveString(NETWORK_CLOUDIP, cloudIP);
    }
    public String getCloudIP() {
        return op.getString(NETWORK_CLOUDIP);
    }

    public void saveDomain(String domain) {
        op.saveString(NETWORK_DOMAIN, domain);
    }
    public String getDomain() {
        return op.getString(NETWORK_DOMAIN);
    }

    public void saveUdp_port(int udp_port) {
        op.saveInt(NETWORK_UDP_PORT, udp_port);
    }
    public int getUdp_port() {
        return op.getInt(NETWORK_UDP_PORT,0);
    }

    public void saveHttp_port(int http_port) {
        op.saveInt(NETWORK_HTTP_PORT, http_port);
    }
    public int getHttp_port() {
        return op.getInt(NETWORK_HTTP_PORT,0);
    }

    public void saveSuccessCode(int successCode) {
        op.saveInt(NETWORK_SUCCESSCODE, successCode);
    }
    public int getSuccessCode() {
        return op.getInt(NETWORK_SUCCESSCODE,0);
    }
    //单例模式
    private static OpBaseSetting instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static OpBaseSetting getInstance() {
        if (instance == null) {
            synchronized (OpBaseSetting.class){
                if (instance == null) {
                    instance = new OpBaseSetting();
                }
            }
        }
        return instance;
    }

    private Op op;

    public OpBaseSetting init(Context context) {
        op=new Op(context,OriginalMVP.getAppName()+"_baseSetting");
        return this;
    }

}
