package org.elastos.meetup.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.elastos.meetup.entity.AddressBook;
import org.elastos.meetup.entity.CarrierMessage;
import org.elastos.meetup.entity.ImAddressBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by xianxian on 2018/11/24
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME ="xd_wallet_db";//数据库名字
    public static String TABLE_ACCOUNTS = "tab_accounts";// 表名
    public static String TABLE_IM_ADDRESSBOOK = "tab_im_addressbook";// Carrier联系人
    public static String TABLE_ADDRESSBOOK = "tab_addressbook";// 钱包联系人
    public static String TABLE_SYSNEWS = "tab_sysnews";// 消息
    public static String TABLE_RESENDTRANSFER = "tab_resendtransfer";// 重发转账
    public static String TABLE_TRANSFERLOG = "tab_transferlog";// 交易日志缓存
    public static String TABLE_CARRITER = "tab_carrier";// 对话内容缓存

    private static final int DB_VERSION = 1;   // 数据库版本1
    SQLiteDatabase dmademodb = null;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String  sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CARRITER + "(c_from  TEXT(100) NOT NULL,c_to  TEXT(100) NOT NULL,send_time BIGINT,c_message  TEXT,c_fridendid TEXT(100) NOT NULL);";
        String  sql1 = "CREATE TABLE IF NOT EXISTS " + TABLE_IM_ADDRESSBOOK + "(c_id  TEXT(50) NOT NULL,remark  TEXT(100) NOT NULL,c_index TEXT NOT NULL,accept INTEGER);";
        String  sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESSBOOK + "(address VARCHAR primary key , name VARCHAR not null,email VARCHAR,mobile VARCHAR,remark VARCHAR,linkType varchar);";
        String  sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_RESENDTRANSFER + "(tx_hash VARCHAR primary key, age DATETIME not null);";

        try {
            db.execSQL(sql);
            db.execSQL(sql1);
            db.execSQL(sql2);
            db.execSQL(sql3);

        } catch (SQLException e) {
            Log.e(TAG, "onCreate " + TABLE_CARRITER + "Error" + e.toString());
            return;
        }
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
    // 获得数据库操作对象
    public  void Open() {
        if (dmademodb == null) {
            try {
                dmademodb = this.getWritableDatabase();
            } catch (Exception e) {
                dmademodb = this.getReadableDatabase();
            }
        }

    }

    public void closeDB() {
        if (dmademodb != null) {
            dmademodb.close();
        }
    }
    /**
     * 查询语句
     * @param sqlStr sql语句
     * @param args   条件
     */
    public ArrayList<HashMap<String, String>> querySQL(String sqlStr, String[] args) {
//		Open();

        dmademodb= this.getWritableDatabase();
        ArrayList<HashMap<String, String>> arrayList=new ArrayList<HashMap<String,String>>();
        Cursor cursor=dmademodb.rawQuery(sqlStr, args);
        int  curCount=cursor.getColumnCount();
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            HashMap<String, String> map=new HashMap<String, String>();
            for(int i=0;i<curCount;i++){
                map.put(cursor.getColumnName(i), cursor.getString(i));
            }
            arrayList.add(map);
        }
        cursor.close();
        closeDB();
        return arrayList;
    }
    /**
     * 处理事务，增、删、改
     * @param sqliList
     * @return
     */
    public Boolean executeTransactionSQLBoolean(ArrayList<String> sqliList) {
        if (sqliList.size()<=0) {
            return null;
        }
        dmademodb=this.getWritableDatabase();
        dmademodb.beginTransaction();
        try {
            for (Iterator<String> iterator = sqliList.iterator(); iterator
                    .hasNext();) {
                String sqlstring = (String) iterator.next();
                dmademodb.execSQL(sqlstring);
            }
            dmademodb.setTransactionSuccessful();
        } finally {
            dmademodb.endTransaction();
        }
        dmademodb.close();
        return true;
    }

    /**
     * 拼凑ddressbook表sql语句
     */
    @SuppressWarnings("unchecked")
    public Boolean addressbookToSql(AddressBook obj) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        Boolean bool =false;
        if(obj.getAddress()!=null&&obj.getAddress().length()>0){
            if(!selectAddressbookByaddress(obj.getAddress())){
                sqlString="insert into "+ TABLE_ADDRESSBOOK +"(address,name,email,mobile,remark,linkType) values('"+obj.getAddress()+"','"+obj.getName()+"','"+obj.getEmail()+"','"+obj.getMobile()+"','"+obj.getRemark()+"','"+obj.getLinkType()+"')";

            }else{
                sqlString="update "+ TABLE_ADDRESSBOOK +" set name='"+obj.getName()+"',email='"+obj.getEmail()+"',mobile='"+obj.getMobile()+"',remark='"+obj.getRemark()+"',linkType='"+obj.getLinkType()+"' where address='"+obj.getAddress()+"'";

            }
            sqllist.add(sqlString);
            bool=executeTransactionSQLBoolean(sqllist);
        }

        return bool;
    }
    public Boolean selectAddressbookByaddress(String address) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(address!=null&&address.length()>0){
            sqlString="select * from  "+ TABLE_ADDRESSBOOK +" where address='"+address+"'";
            ArrayList<HashMap<String, String>> list= querySQL(sqlString,null);
            if(list!=null&&list.size()>0){
                return true;
            }
        }


        return false;
    }

    public Boolean selectImAddressbookByaddress(String address) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(address!=null&&address.length()>0){
            sqlString="select * from  "+ TABLE_IM_ADDRESSBOOK +" where c_id='"+address+"'";
            ArrayList<HashMap<String, String>> list= querySQL(sqlString,null);
            if(list!=null&&list.size()>0){
                return true;
            }
        }
        return false;
    }
    public   ArrayList<HashMap<String, String>>  selectImAddressbooks(String address) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(address!=null&&address.length()>0){
            sqlString="select * from  "+ TABLE_IM_ADDRESSBOOK +" order by remark asc";
            ArrayList<HashMap<String, String>> list= querySQL(sqlString,null);
            if(list!=null&&list.size()>0){
                return list;
            }
        }
        return null;
    }
    public Boolean insertImAddressbook(ImAddressBook obj) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(obj!=null&&obj.getC_id()!=null){
            sqlString="INSERT into"+ TABLE_IM_ADDRESSBOOK+ "(c_id,remark,accept)VALUES('"+obj.getC_id()+"','"+obj.getRemark()+"',"+obj.getAccept()+")";
            sqllist.add(sqlString);
            executeTransactionSQLBoolean(sqllist);
        }
        return false;
    }
    public Boolean insertCarrierMessage(CarrierMessage obj) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(obj!=null&&obj.getC_message()!=null){
            sqlString="INSERT into"+ TABLE_CARRITER+ "(c_from,c_to,send_time,c_message)VALUES('"+obj.getC_from()+"','"+obj.getC_to()+"',"+obj.getSend_time()+",'"+obj.getC_message()+"')";
            sqllist.add(sqlString);
            executeTransactionSQLBoolean(sqllist);
        }
        return false;
    }
    public ArrayList<HashMap<String, String>>  selectCarrierMessage(String c_to) {
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();
        if(c_to!=null&&c_to.length()>0){
            sqlString="select * from  "+ TABLE_CARRITER +" where c_from='"+c_to+"' or c_to='"+c_to+"' order by send_time desc;";
            ArrayList<HashMap<String, String>> list= querySQL(sqlString,null);
            if(list!=null&&list.size()>0){
                return list;
            }
        }
        return null;
    }
    public ArrayList<HashMap<String, String>>  selectLastCarrierMessage(int pageNO) {
        String  sqlString=null;
        String limit="";
        if(pageNO>0){
            limit=(pageNO-1*10)+","+(pageNO*10);
        }
        ArrayList<String> sqllist=new ArrayList<String>();
            sqlString="SELECT carrier.*,book.remark from "+TABLE_CARRITER+" carrier INNER JOIN "+TABLE_IM_ADDRESSBOOK+" book on carrier.c_fridendid=book.c_id GROUP BY c_fridendid ORDER BY send_time desc; "+limit+";";
            ArrayList<HashMap<String, String>> list= querySQL(sqlString,null);
            if(list!=null&&list.size()>0){
                return list;
            }

        return null;
    }

    public ArrayList<String> SQLString(String sqlStr,String[] args) {
//		Open();
        dmademodb= this.getWritableDatabase();
        ArrayList<String> arrayList=new ArrayList<String>();
        Cursor cursor=dmademodb.rawQuery(sqlStr, args);
        int  curCount=cursor.getColumnCount();
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            for(int i=0;i<curCount;i++){
                arrayList.add(cursor.getString(i));
            }
        }
        cursor.close();
        dmademodb.close();
        //closeDB();
        return arrayList;
    }
    /**
     * 交易记录重新发送
     */
    @SuppressWarnings("unchecked")
    public Boolean resendTransferSql(String txhash,String age){
        String  sqlString=null;
        ArrayList<String> sqllist=new ArrayList<String>();

        if(txhash!=null&&txhash.length()>0){
            String sql_select="select * from "+TABLE_RESENDTRANSFER+" where tx_hash='"+txhash+"'";
            ArrayList<HashMap<String, String>> selectlist= querySQL(sql_select,null);
            if(selectlist==null||selectlist.size()==0){
                sqlString="insert into "+ TABLE_RESENDTRANSFER+"(tx_hash,age) values('"+txhash+"','"+age+"')";
                sqllist.add(sqlString);
            }
        }

        Boolean bool = executeTransactionSQLBoolean(sqllist);
        return bool;
    }
}