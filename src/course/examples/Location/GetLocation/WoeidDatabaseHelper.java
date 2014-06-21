package course.examples.Location.GetLocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class WoeidDatabaseHelper {
	private static final String LOG_TAG = WoeidDatabaseHelper.class.getCanonicalName();
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "woeid.db";
	private static final String TABLE_WOEID_GEONAMEID = "woeid_geonameid_db";

	final private Context mContext; // used to mContext.deleteDatabase(DATABASE_NAME);
	final private SQLiteDatabase database;
	final private WoeidOpenHelper mWoeidOpenHelper;

	public WoeidDatabaseHelper(Context context) {
		mContext = context;
		mWoeidOpenHelper = new WoeidOpenHelper(context);
		database = mWoeidOpenHelper.getWritableDatabase();
	}
	
	public void saveWoeidRecord(WoeidRecord wr) {
		ContentValues c = new ContentValues();
		c.put(Schema.WoeidTable.Cols.WOEID, wr.getWoeid());
		c.put(Schema.WoeidTable.Cols.GEONAMEID, wr.getGoenameid());
		database.insert(TABLE_WOEID_GEONAMEID, null, c);
	}
	
	public static WoeidRecord getWoeidRecordFromCursor(Cursor c) {
		long rowId = c.getInt(c.getColumnIndex(Schema.WoeidTable.Cols.ID));
		int woeid = c.getInt(c.getColumnIndex(Schema.WoeidTable.Cols.WOEID));
		int geonameid = c.getInt(c.getColumnIndex(Schema.WoeidTable.Cols.GEONAMEID));
		WoeidRecord wr = new WoeidRecord(woeid, geonameid);
		wr.setId(rowId);
		return wr;
	}
	
	private Cursor queryWoeidByGeonameid(int geonameid) {
		/*
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_WOEID_GEONAMEID);
		qb.appendWhere(Schema.WoeidTable.Cols.GEONAMEID + " IN (?)");
		String[] whereArgs = {String.valueOf(geonameid)};
		String orderBy = Schema.Item.Cols.ID + " DESC";
		Cursor c = qb.query(database, null, null, whereArgs, null, null,
				orderBy);
		return c;
		*/
		return database.rawQuery(
				"SELECT * FROM " + TABLE_WOEID_GEONAMEID, 
				null);
	}
	
	public WoeidRecord getWoeidByGeonameid(int geonameid) {
		Cursor c = queryWoeidByGeonameid(geonameid);
		if (c != null) {
			Log.i(LOG_TAG, "Number of entries found: " + c.getCount());
			if (c.moveToFirst()) {
				return getWoeidRecordFromCursor(c);
			}
		}
		return null;
	}
	
	public void deleteDatabase() {
		database.delete(TABLE_WOEID_GEONAMEID, null, null);
	}

	private class WoeidOpenHelper extends SQLiteOpenHelper {

		public WoeidOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(LOG_TAG, "CREATE TABLE" + TABLE_WOEID_GEONAMEID);
			db.execSQL("CREATE TABLE " + TABLE_WOEID_GEONAMEID + "("
					+ Schema.WoeidTable.Cols.ID + " INTEGER PRIMARY KEY, "
					+ Schema.WoeidTable.Cols.WOEID + " INTEGER, "
					+ Schema.WoeidTable.Cols.GEONAMEID + " INTEGER)");
			Log.i(LOG_TAG, "DONE CREATE TABLE");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_WOEID_GEONAMEID + "");
			onCreate(database);			
		}
		
	}
}
