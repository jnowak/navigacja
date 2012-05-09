package mapy.cyfrowe;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import mapy.cyfrowe.MapOverlay;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class ConcreteLocationListener implements LocationListener {
	
	private MapOverlay mapOverlay;
	private MapView mapView;
	private NawigacjaActivity nawigacja;

	public ConcreteLocationListener(NawigacjaActivity nawigacja, MapOverlay mapOverlay, MapView mapView)
	{
		this.nawigacja = nawigacja;
		this.mapOverlay = mapOverlay;
		this.mapView = mapView;
		
	}

	public void onLocationChanged(Location location) {
		nawigacja.currLocation = location;
		mapOverlay.point  = new GeoPoint((int)(location.getLatitude()*1E6), 
				(int)(location.getLongitude()*1E6));
		mapView.getController().animateTo(mapOverlay.point);
		mapView.getController().setZoom(17);	
			
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	

}
