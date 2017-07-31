package com.sdkkit.gameplatform.statistic.sqlite;

import com.sdkkit.gameplatform.statistic.util.HLog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	private StringBuilder sql;
//	public static String DBNAME = "statistic.db"; 
	public static String DBNAME = "dbLevel.db"; 
	public static String TABLE = "Level"; 
	public static final int VERSION = 5;
	public DBHelper(Context context, String name, int version) {
		super(context,DBNAME, null, VERSION);  // 创建一个数据库
	}
	private static DBHelper mInstance; 
	public synchronized static DBHelper getInstance(Context context,String dbname, int version){
		if(mInstance==null
				){
			mInstance=new DBHelper(context,dbname,version);
		}
		return mInstance;
	}
	
	
	// 只要是android系统提供的方法 如果以on开头 那么该方法就会自动调用 
	 
	// 在数据库第一次被创建时被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		sql=new StringBuilder();
		if(TABLE.equals("Level")){
			sql = sql.append("CREATE TABLE "+TABLE+" (id INTEGER PRIMARY KEY AUTOINCREMENT,");
				  sql.append("usermark VARCHAR(32),serverno VARCHAR(32),rolemark VARCHAR(32),grade VARCHAR(32),");
				  sql.append("starttime VARCHAR(32), endtime VARCHAR(32),seqno VARCHAR(32),");
				  sql.append("levelid VARCHAR(32), difficulty VARCHAR(32),status VARCHAR(32),reason VARCHAR(32))");
		}
	    db.execSQL(sql.toString());
	}
	// 更新数据库版本的时候 该方法就会被调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE);
		onCreate(db);
		HLog.i("onUpgrade","数据库被更新了");
	}


	
	

}
