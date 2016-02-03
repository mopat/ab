package patrick.morczinietz.abicalculatorbayern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBAdapter {
	private static final String DATABASE_NAME = "DBtoDoList";
	private static final String DATABASE_TABLE = "itemsTable";
	private static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase db ;

	//Spalten namen
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK ="task";
	
	//Spalten ids
	public static final int COLUMN_TASK = 1;
	public static final int COLUMN_KEY = 0;
	
	private final String DROP = "DROP TABLE IF EXISTS ";
	private final String TABLE_CREATE = "CREATE TABLE ";
	
	private ToDoDBOpenHelper dbHelper;
	
	public ToDoDBAdapter(Context context){
		dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void close(){
		db.close();
	}
	public void open(){
		try{
			db =  dbHelper.getWritableDatabase();
		}
		catch(SQLiteException e){
			db = dbHelper.getReadableDatabase();
		}
		
	}
	public long insertToDoItem(String item){
		ContentValues newToDoValues = new ContentValues();
		newToDoValues.put(KEY_TASK, item);
		return db.insert(DATABASE_TABLE, null, newToDoValues);
	}
	public long setToDoItem(String item){
		ContentValues newToDoValues = new ContentValues();
		newToDoValues.put(KEY_TASK, item);
		return db.insert(DATABASE_TABLE, null, newToDoValues);
	}
	public void removeToDoItem(int itemPos){
		db.delete(DATABASE_TABLE, KEY_ID + "="+ itemPos, null);
	}
	public Cursor getAllToDoItemCursor(){
		Cursor allItems = 	db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TASK },
				null, null, null, null, null);
		return allItems;
	}
	
	class ToDoDBOpenHelper extends SQLiteOpenHelper{
		
		public ToDoDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(TABLE_CREATE  + DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TASK + " text not null);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
}
