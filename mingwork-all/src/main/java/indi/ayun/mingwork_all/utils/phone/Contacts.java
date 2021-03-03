package indi.ayun.mingwork_all.utils.phone;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;

import indi.ayun.mingwork_all.base.UtilBase;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

import java.util.ArrayList;

/**
 *
 * <uses-permission android:name="android.permission.READ_CONTACTS" />
 * <uses-permission android:name="android.permission.WRITE_CONTACTS" />
 *
 *
 * 通讯录是存放在/data/data/com.android.providers.contacts/databases/contacts2.db
 * minetupes表
 * id  minetupe
 * 1    vnd.android.currsor.item/email_v2
 * 2    vnd.android.currsor.item/im
 * 3    vnd.android.currsor.item/postal-address_v2
 * 4    vnd.android.currsor.item/photo
 * 5    vnd.android.currsor.item/phone_v2
 * 6    vnd.android.currsor.item/name
 * 7    vnd.android.currsor.item/organization
 * 8    vnd.android.currsor.item/nickname
 * 9    vnd.android.currsor.item/group_membership
 *
 * raw_contacts表 URI = content://com.android.contacts/raw_contacts;
 * 字段：_id  is_restricted  account_name  accunt_type  sourceid  version  dirty   deleted  contact_id  aggregation_needed  custom_ringtone  send_to_voicemail  times_contacted
 *       last_time_contacted  starred  display_name  display_name_alt  display_name_source  phonetic_name  phonetic_name_style  sort_key  sort_key_alt
 *
 *
 * data表 URI = content://com.android.contacts/data;
 * 根据email对data表查询
 * URI =content://com.android.contacts/data/emails/filter/*
 * 根据电话号码对data表查询
 * URI =content://com.android.contacts/data/phone/filter/*
 * 字段：_id  package_id  mimetype_id  raw_contact_id  is_primary  is_super_primary  data_version  data1  data2..........
 *
 * Data中的常量
 * Data._ID： "_id"
 * Data.DISPLAY_NAME：“display_name”
 * Data.DATA1：“data1”
 * Data.DATA2：“data2”
 * Data.RAW_CONTACT_ID：“raw_contact_id”
 * Data.MIMETYPE：“mimetype”
 */
public class Contacts extends UtilBase {

    /**
     * 跳转至联系人选择界面
     *
     * @param mContext 上下文
     * @param requestCode 请求返回区分代码
     */
    public static void toChooseContactsList(Activity mContext, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mContext.startActivityForResult(intent, requestCode);
    }
    /**
     * 获取选择的联系人的手机号码
     * @param mContext 上下文
     * @param resultCode 请求返回Result状态区分代码
     * @param data onActivityResult返回的Intent
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getChoosedPhoneNumber(Activity mContext, int resultCode, Intent data) {
        // 返回结果
        String phoneResult = "";
        if (Activity.RESULT_OK == resultCode) {
            Uri uri = data.getData();
            Cursor mCursor = mContext.managedQuery(uri, null, null, null, null);
            mCursor.moveToFirst();

            int phoneColumn = mCursor
                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = mCursor.getInt(phoneColumn);
            if (phoneNum > 0) {
                // 获得联系人的ID号
                int idColumn = mCursor
                        .getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = mCursor.getString(idColumn);
                // 获得联系人的电话号码的cursor;
                Cursor phones = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                if (phones.moveToFirst()) {
                    // 遍历所有的电话号码
                    for (; !phones.isAfterLast(); phones.moveToNext()) {
                        int index = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phone_type = phones.getInt(typeindex);
                        String phoneNumber = phones.getString(index);
                        if (phone_type == 2) {
                            phoneResult = phoneNumber;
                        }
                    }
                    if (!phones.isClosed()) {
                        phones.close();
                    }
                }
            }
            // 关闭游标
            mCursor.close();
        }

        return phoneResult;
    }

    /**
     * 读取通讯录的全部的联系人
     */
    public String getAllContacts(){
        String str ="";
        //uri = content://com.android.contacts/contacts
        Uri uri = Uri.parse("content://com.android.contacts/contacts"); //访问raw_contacts表
        ContentResolver resolver = getContext().getContentResolver();
        //获得_id属性
        Cursor cursor = resolver.query(uri, new String[]{Data._ID}, null, null, null);
        while(cursor.moveToNext()){
            StringBuilder buf = new StringBuilder();
            //获得id并且在data中寻找数据
            int id = cursor.getInt(0);
            buf.append("id="+id);
            uri = Uri.parse("content://com.android.contacts/contacts/"+id+"/data");
            //data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(uri, new String[]{Data.DATA1,Data.MIMETYPE}, null,null, null);
            while(cursor2.moveToNext()){
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")){       //如果是名字
                    buf.append(",name="+data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")){  //如果是电话
                    buf.append(",phone="+data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")){  //如果是email
                    buf.append(",email="+data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")){ //如果是地址
                    buf.append(",address="+data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")){  //如果是组织
                    buf.append(",organization="+data);
                }
            }
            str= buf.toString();
        }
        return  str;
    }
    public interface CrudContactListen{
        void CrudContact(int start,String note);
    }

    /**
     * 一个添加联系人信息--待测试有问题
     * @param name
     * @param phoneNumber
     */
    public void addContact(Context context,String name, String phoneNumber,String email,String organization,byte[] photo,CrudContactListen crudContactListen) {
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
        // 联系人的地址
        values.put(Organization.DATA, organization);
        // 地址类型
        values.put(Organization.TYPE, Organization.TYPE_WORK);
        // 向联系人地址URI添加地址
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE,Photo.CONTENT_ITEM_TYPE);
        // 联系人的图片
        values.put(Photo.DATA14, photo);
        // 向联系人地址URI添加地址
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
        // 联系人的地址
        values.put(Organization.DATA, organization);
        // 地址类型
        values.put(Organization.TYPE, Organization.TYPE_WORK);
        // 向联系人地址URI添加地址
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
        // 联系人的Email地址
        values.put(Email.DATA, email);
        // 电子邮件的类型
        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人Email URI添加Email数据
        context.getContentResolver().insert(Data.CONTENT_URI, values);

        if (queryContactPhone(phoneNumber)){
            crudContactListen.CrudContact(0,"联系人数据添加成功");
        }else {
            crudContactListen.CrudContact(1,"联系人数据添加失败");
        }
    }

    /**
     * 删除一个联系人
     * (1)先在raw_contacts表根据姓名(此处的姓名为name记录的data2的数据而不是data1的数据)查出id;
     * (2)在data表中只要raw_contact_id匹配的都删除；
     * @throws Exception
     */
    public void deleteContactName(String name,CrudContactListen crudContactListen)throws Exception{
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data._ID},"display_name=?", new String[]{name}, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
        }
        if (queryContactName(name)){
            crudContactListen.CrudContact(0,"删除失败");
        }else {
            crudContactListen.CrudContact(1,"删除成功");
        }
    }
    /**
     * 删除一个联系人
     * (1)先在raw_contacts表根据姓名(此处的姓名为name记录的data2的数据而不是data1的数据)查出id;
     * (2)在data表中只要raw_contact_id匹配的都删除；
     * @throws Exception
     */
    public void deleteContactPhone(String phone,CrudContactListen crudContactListen)throws Exception{
        //删了data表的数据
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phone);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data.DISPLAY_NAME}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            deleteContactName(cursor.getString(0),null);
        }
        if (queryContactPhone(phone)){
            crudContactListen.CrudContact(0,"删除失败");
        }else {
            crudContactListen.CrudContact(1,"删除成功");
        }
    }
    /**
     * 根据电话号码查询
     * @param phone
     * @return
     */
    public boolean queryContactPhone(String phone){
        boolean tab;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phone);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data.DISPLAY_NAME}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            MLog.i("Contacts", "name="+cursor.getString(0));
            return true;
        }
        return false;
    }
    /**
     * 根据电话号码查询
     * @param name
     * @return
     */
    public boolean queryContactName(String name){
        boolean tab;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/name/filter/"+name);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data.DATA2}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            MLog.i("Contacts", "data2="+cursor.getString(0));
            return true;
        }
        return false;
    }
    /**
     * 根据电话号码查询，返回id
     * @param phone
     * @return
     */
    public int queryContactPhoneId(String phone){
        boolean tab;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phone);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data._ID}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            MLog.i("Contacts", "name="+cursor.getString(0));
            return cursor.getInt(0);
        }
        return -1;
    }
    /**
     * 根据电话号码查询，返回id
     * @param name
     * @return
     */
    public int queryContactNameId(String name){
        boolean tab;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/name/filter/"+name);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data._ID}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            MLog.i("Contacts", "data2="+cursor.getString(0));
            return cursor.getInt(0);
        }
        return -1;
    }
    /**
     * 来电显示---用电话号码查询姓名
     * @param phone
     * @return
     */
    public String callerIDDisplay(String phone){
        boolean tab;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phone);
        ContentResolver resolver =getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data.DISPLAY_NAME}, null, null, null); //从raw_contact表中返回display_name
        if(cursor.moveToFirst()){
            MLog.i("Contacts", "name="+cursor.getString(0));
            return cursor.getString(0);
        }else {
            return phone;
        }
}

    /**
     * 更新联系人信息--待测试有问题
     * (1)不需要更新raw_contacts，只需要更新data表；
     * (2)uri=content://com.android.contacts/data 表示对data表进行操作；
     * @param phone
     * @throws Exception
     */
    public void testUpdate(int id,String phone,String name, String email,String organization,byte[] photo,CrudContactListen crudContactListen)throws Exception{
        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
        ContentResolver resolver =getContext().getContentResolver();
        ContentValues values = new ContentValues();
        if (IsNothing.onAnything(phone)) {
            values.put("data2", phone);
            resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2", id + ""});
            values.clear();
        }
        if (IsNothing.onAnything(name)) {
            values.put("data2", name);
            resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/name", id + ""});
            values.clear();
        }
        if (IsNothing.onAnything(email)) {
            values.put("data2", email);
            resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/email_v2", id + ""});
            values.clear();
        }
        if (IsNothing.onAnything(organization)) {
            values.put("data2", organization);
            resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/organization", id + ""});
            values.clear();
        }
        if (IsNothing.onAnything(photo)) {
            values.put("data14", photo);
            resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/photo", id + ""});
            values.clear();
        }
        if (queryContactPhone(phone)){
            crudContactListen.CrudContact(0,"联系人数据添加成功");
        }else {
            crudContactListen.CrudContact(1,"联系人数据添加失败");
        }
    }

    /**
     * 用表中建立数据的方式插入一条数据
     * @param phone
     * @param name
     * @param email
     * @param organization
     * @param photo
     * @param postalAddress
     * @throws Exception
     */
    public void addContactsInTransaction(String phone,String name, String email,String organization,byte[] photo,String postalAddress,CrudContactListen crudContactListen) throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getContext().getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        // 向raw_contact表添加一条记录
        //此处.withValue("account_name", null)一定要加，不然会抛NullPointerException
        ContentProviderOperation operation1 = ContentProviderOperation
                .newInsert(uri).withValue("account_name", null).build();
        operations.add(operation1);
        // 向data添加数据
        uri = Uri.parse("content://com.android.contacts/data");
        //添加姓名
        ContentProviderOperation operation2 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                //withValueBackReference的第二个参数表示引用operations[0]的操作的返回id作为此值
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", name).build();
        operations.add(operation2);
        //添加手机数据
        ContentProviderOperation operation3 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data2", phone).withValue("data1", "0000000").build();
        operations.add(operation3);
        //添加email
        ContentProviderOperation operation4 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                .withValue("data2", email).withValue("data1", "0000000").build();
        operations.add(operation4);
        //添加organization
        ContentProviderOperation operation5 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/organization")
                .withValue("data2", organization).withValue("data1", "0000000").build();
        operations.add(operation5);
        //添加photo
        ContentProviderOperation operation6 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/photo")
                .withValue("data2", photo).withValue("data1", "0000000").build();
        operations.add(operation6);
        //添加postalAddress
        ContentProviderOperation operation7 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/postal-address_v2")
                .withValue("data2", postalAddress).withValue("data1", "0000000").build();
        operations.add(operation7);

        resolver.applyBatch("com.android.contacts", operations);
        if (queryContactPhone(phone)){
            crudContactListen.CrudContact(0,"联系人数据添加成功");
        }else {
            crudContactListen.CrudContact(1,"联系人数据添加失败");
        }
    }
}
