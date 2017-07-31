package com.sdkkit.gameplatform.statistic.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.sdkkit.gameplatform.statistic.CacheDataCore;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.util.HLog;
import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBLevelManager {
	private static DBHelper helper;
	private static SQLiteDatabase db;
	
	public static String TABLE = "Level"; 
	private static DBLevelManager dbManager;
	private DBLevelManager(){};
	
	public static DBLevelManager getInstance(){
		synchronized (DBLevelManager.class) {
			if (dbManager == null) {
				dbManager = new DBLevelManager(); 
			}
		}
	
		return dbManager ;
	}
	
	
	
	
	public void initDB(Context context){
		try {
			helper = DBHelper.getInstance(context,DBHelper.DBNAME, DBHelper.VERSION);
			db = helper.getWritableDatabase();
			} catch (Exception e) {
				HLog.i("DBLevelManager constructor",e.getMessage());
//				e.printStackTrace();
			}
	}
	/**
	 * 插入开始关卡的值，结束关卡更新开始关卡的值(有了行id之后）
	 * @param map
	 */
	public void insert(DataMap map){
			
				String status=map.get(ProtocolKeys.KEY_STATUS).toString();
				StringBuilder sql = new StringBuilder();
				if(status.equals("1")){//开始关卡插入数据
					synchronized (helper) {
						if(!db.isOpen()){
							db=helper.getWritableDatabase();
						}
						db.beginTransaction();
						
						try {
							sql = new StringBuilder("insert into "+TABLE+"("
									+ "usermark,serverno,rolemark,grade,starttime,endtime,"
									+ "seqno,levelid,status,difficulty");
							sql.append(" ) values (");
							sql.append("'"+map.get(ProtocolKeys.KEY_USERMARK)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_SERVERNO)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_ROLEMARK)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_GRADE)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_STARTTIME)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_ENDTIME)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_SEQNO)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_LEVELID)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_STATUS)+"',");
							sql.append("'"+map.get(ProtocolKeys.KEY_DIFFICULTY)+"'");
							sql.append(")");
							HLog.i("insert--->", sql.toString());
							db.execSQL(sql.toString());
							HLog.i("insert--->", "插入开始关卡数据成功！");
							db.setTransactionSuccessful();//设置事物成功完成
						} catch (Exception e) {
//							e.printStackTrace();
							HLog.i(e.getMessage());
							db.endTransaction();
							db.close();
							
						} finally{
							db.endTransaction();
							db.close();
						}
					}
				
				}else{
					int id=map.getInt(ProtocolKeys.KEY_ID);
					if(id>0){
						synchronized (helper) {
							if(!db.isOpen()){
								db=helper.getWritableDatabase();
							}
							db.beginTransaction();
							try {
								sql = new StringBuilder("update "+TABLE+" set ");
								sql.append("endtime   = '" +map.get(ProtocolKeys.KEY_ENDTIME)+"',");
								sql.append("status = '" +map.get(ProtocolKeys.KEY_STATUS)+"',");
								sql.append("reason  = '" +map.get(ProtocolKeys.KEY_REASON)+"' ");
								sql.append("where id 	= " + id);
								db.execSQL(sql.toString());
								HLog.i("insert--->", "更新插入结束关卡数据成功！");
								db.setTransactionSuccessful();//设置事物成功完成
							} catch (Exception e) {
								e.printStackTrace();
								db.endTransaction();
								db.close();
							} finally{
								db.endTransaction();
								db.close();
							}
						}
					}else{
						HLog.i("insert--->", "更新插入结束关卡数据失败！");
					}
				}
	}
	
	
	/**
	 * 通过结束关卡，获取开始关卡的行id
	 * 查询没有对应结束关卡的数据id（有的话就更新插入结束关卡的数据，没有就不进行插入数据的操作）
	 * @param map
	 * @return
	 */
	public  int getStartlevelId(DataMap map){
		int id=0;
		synchronized (helper) {
			if(!db.isOpen()){
				db=helper.getWritableDatabase();
			}
			try {
				StringBuilder sql = new StringBuilder();
				sql.append(" usermark= ? " );
				sql.append("and serverno=? " );
				sql.append("and rolemark=? " );
				sql.append("and seqno=? " );
				sql.append("and levelid=?  " );
				sql.append("and endtime=''and status=1 ");
				Cursor cursor = db.rawQuery("select max(id) from "+TABLE+" where"+sql.toString(),
						new String[] {  map.getString(ProtocolKeys.KEY_USERMARK),
										map.getString(ProtocolKeys.KEY_SERVERNO),
										map.getString(ProtocolKeys.KEY_ROLEMARK),
										map.getString(ProtocolKeys.KEY_SEQNO),
										map.getString(ProtocolKeys.KEY_LEVELID)});
				cursor.moveToFirst();
				id=cursor.getInt(0);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				db.close();
			}
		}
		return id;
	}
	/**
	 * 获取结束关卡之后要发送的完整map值
	 * @param map
	 * @return
	 */
	public  DataMap getSuccessLevelMapById(DataMap map){
		
		synchronized (helper) {
			if(!db.isOpen()){
				db=helper.getWritableDatabase();
			}
			try{
				Cursor cursor = db.rawQuery("select grade,starttime,"
						/*+ "seqno,levelid,"*/
						+ "difficulty  from "+TABLE +"  where id=?",
						new String[] { map.getString(ProtocolKeys.KEY_ID)});
				while (cursor.moveToNext()) {
					map.put(ProtocolKeys.KEY_GRADE, cursor.getString(cursor.getColumnIndex("grade")));
					map.put(ProtocolKeys.KEY_STARTTIME, cursor.getString(cursor.getColumnIndex("starttime")));
					/*map.put(ProtocolKeys.KEY_SEQNO, cursor.getString(cursor.getColumnIndex("seqno")));
					map.put(ProtocolKeys.KEY_LEVELID, cursor.getString(cursor.getColumnIndex("levelid")));*/
					map.put(ProtocolKeys.KEY_DIFFICULTY, cursor.getString(cursor.getColumnIndex("difficulty")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				db.close();
			}
		}
		return map;
	
	}
	
	
	/**
	 * 用本地缓存：对于关卡失败的请求，发送了就删除数据库的值
	 * 单独操作数据库：就在成功之后删除数据库相关id的值
	 * @param id
	 */
	public void delete(int id){
		synchronized (helper) {
			if(!db.isOpen()){
				db=helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("delete from "+TABLE+" where id = " + id);
				db.execSQL(sql.toString());
					
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				db.endTransaction();
			}
			db.close();
		}
	}
	
	public List<DataMap> queryExceptionalLevel(){
		List<DataMap> list = null;
		synchronized (helper) {
			if(!db.isOpen()){
				db=helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("update "+TABLE+" set status='4' where status='1'");
				db.execSQL(sql.toString());
				sql=new StringBuilder("select id,usermark,serverno,rolemark,"
											+ "grade,starttime,endtime,seqno,"
											+ "levelid,status,difficulty from "
											+TABLE+" where status='4'");
				Cursor cursor=db.rawQuery(sql.toString(),null);
				list=new ArrayList<DataMap>();
				DataMap map=null;
				while(cursor.moveToNext()){
					map=new DataMap();
					map.put(ProtocolKeys.KEY_ID,cursor.getString(cursor.getColumnIndex("id")));
					map.put(ProtocolKeys.KEY_USERMARK,cursor.getString(cursor.getColumnIndex("usermark")));
					map.put(ProtocolKeys.KEY_SERVERNO,cursor.getString(cursor.getColumnIndex("serverno")));
					map.put(ProtocolKeys.KEY_ROLEMARK,cursor.getString(cursor.getColumnIndex("rolemark")));
					map.put(ProtocolKeys.KEY_GRADE,cursor.getString(cursor.getColumnIndex("grade")));
					map.put(ProtocolKeys.KEY_STARTTIME,cursor.getString(cursor.getColumnIndex("starttime")));
					map.put(ProtocolKeys.KEY_ENDTIME,cursor.getString(cursor.getColumnIndex("endtime")));
					map.put(ProtocolKeys.KEY_SEQNO,cursor.getString(cursor.getColumnIndex("seqno")));
					map.put(ProtocolKeys.KEY_LEVELID,cursor.getString(cursor.getColumnIndex("levelid")));
					map.put(ProtocolKeys.KEY_STATUS,cursor.getString(cursor.getColumnIndex("status")));
					map.put(ProtocolKeys.KEY_DIFFICULTY,cursor.getString(cursor.getColumnIndex("difficulty")));
					list.add(map);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				db.endTransaction();
				db.close();
			}
			return list;
		}
		
	}
	
	public void deleteExceptionalLevel(int id){
		synchronized (helper) {
			if(!db.isOpen()){
				db=helper.getWritableDatabase();
			}
			db.beginTransaction();
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("delete from "+TABLE+" where  status='4' and id="+id);
				db.execSQL(sql.toString());
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				db.endTransaction();
				db.close();
			}
			
		}
		
	}
	
}
	
	

	
	
	

