package indi.ayun.mingwork_all.utils.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class SMSAndPhone {
    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
    /**
     * 跳转至拨号界面
     *
     * @param mContext
     *            上下文Context
     * @param phoneNumber
     *            需要呼叫的手机号码
     */
    public static void toCallPhoneActivity(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL, uri);
        mContext.startActivity(call);
    }
    /**
     * 直接呼叫指定的号码(需要<uses-permission
     * android:name="android.permission.CALL_PHONE"/>权限)
     *
     * @param mContext
     *            上下文Context
     * @param phoneNumber
     *            需要呼叫的手机号码
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_CALL, uri);
        mContext.startActivity(call);
    }
    public interface SMSListener{
        void SMSStart(int start,String note);
    }
    /**
     * 直接调用短信API发送信息(设置监听发送和接收状态)
     *
     * @param strPhone
     *            手机号码
     * @param strMsgContext
     *            短信内容
     */
    public static void sendMessage(final Context mContext, final String strPhone, final String strMsgContext, final SMSListener smsListener) {

        // 处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendIntent = PendingIntent.getBroadcast(mContext, 0,
                sentIntent, 0);
        // register the Broadcast Receivers
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        smsListener.SMSStart(0,"短信发送成功");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        smsListener.SMSStart(1,"短信发送失败");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        smsListener.SMSStart(2,"无线电关闭");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        smsListener.SMSStart(3,"pdu错误");
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        // 处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent backIntent = PendingIntent.getBroadcast(mContext, 0,
                deliverIntent, 0);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                smsListener.SMSStart(4,"对方已收到");
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        // 拆分短信内容（手机短信长度限制）
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> msgList = smsManager.divideMessage(strMsgContext);
        for (String text : msgList) {
            smsManager.sendTextMessage(strPhone, null, text, sendIntent,
                    backIntent);
        }
    }
    /**
     * 获取手机短信并保存到xml中
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
     *
     * @param context 上下文
     */
    public static void getAllSMS(Context context) {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转至发送短信界面(自动设置接收方的号码)
     *
     * @param mContext
     *            Activity
     * @param strPhone
     *            手机号码
     * @param strMsgContext
     *            短信内容
     */
    public static void toSendMessageActivity(Context mContext, String strPhone, String strMsgContext) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(strPhone)) {
            Uri uri = Uri.parse("smsto:" + strPhone);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            sendIntent.putExtra("sms_body", strMsgContext);
            mContext.startActivity(sendIntent);
        }
    }
}
