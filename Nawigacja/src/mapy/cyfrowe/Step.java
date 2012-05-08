package mapy.cyfrowe;

import com.google.android.maps.GeoPoint;

public class Step
{	
	GeoPoint start;
	GeoPoint end;
	String duration;
	String instructions;
	String distance;
	
	public Step(GeoPoint start, GeoPoint end, String duration,
			 String distance, String instructions)
	{
		super();
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.instructions = instructions;
		this.distance = distance;
	}
	

}
