package mapy.cyfrowe;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.location.Location;


public class POIList {
	
	private static List<Location> poi;
	private static SQLiteDatabase database;
	
	
	public static void add(Location location)
	{
		if (poi == null)
			createInstance();

		poi.add(location);
		ContentValues values = new ContentValues();
		values.put("latitude", location.getLatitude());
		values.put("longitude", location.getLongitude());
		database.insert("POI_List", null, values);
	}
	
	public static List<Location> getAll()
	{
		if (poi == null)
			createInstance();
		return poi;
	}
	
	private static List<Location> getLocationFromDatabase()
	{
		List<Location> list = new ArrayList<Location>();
		
		Cursor c = database.rawQuery("select * from POI_list", null);
		
		while (c.moveToNext())
		{
			Location location = new Location("");
			location.setLatitude(c.getDouble(0));
			location.setLongitude(c.getDouble(1));
			
			list.add(location);
			
		}
		
		return list;
		
		
	}
	
	private static void createInstance()
	{


		database = SQLiteDatabase.openOrCreateDatabase("poi.sql", null);
		database.execSQL("create table if not exists POI_List" +
								"(" +
								"	id int primary key,"+
								"	latitude double," +
								"	longitude double" +
								");");
		poi = getLocationFromDatabase();

	}
	
	

}
