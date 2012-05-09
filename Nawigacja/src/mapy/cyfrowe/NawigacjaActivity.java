package mapy.cyfrowe;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;




public class NawigacjaActivity extends MapActivity {
	MapView mapView;
    MapController mc;
    GeoPoint p;
	private Button addPOIButton;

	
	public Location currLocation;
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	
    	Intent intend = getIntent();

    	String from=intend.getStringExtra("from");
    	String to=intend.getStringExtra("to");
    	String s = MapWebServices.getDistanceAndTime(from, to, TransportMode.driving);
        Step[] steps = MapWebServices.getRoute(from, to, TransportMode.driving);
    	
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        final MapOverlay mapOverlay = new MapOverlay();
        
        
        LocationListener locationListener = new ConcreteLocationListener(this, mapOverlay, mapView);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        mc = mapView.getController();
       
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        
        if (steps != null)
        	listOfOverlays.add(new Route(steps));
        mapView.invalidate();

        addPOIButton = (Button)findViewById(R.id.addPOI);
        
        addPOIButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				POIList.add(currLocation);
				
			}
		});
        
        
        
        
        
        Button bt =(Button)findViewById(R.id.Button01);
        bt.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				if(mapView.isStreetView()){
					mapView.setStreetView(false);
					mapView.setSatellite(true);
				}
				else
				{
					
					mapView.setStreetView(true);
					mapView.setSatellite(false);
				
				}
				}
        });
        Button bt2 =(Button)findViewById(R.id.Button02);
        bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(mapView.isTraffic()){
						mapView.setTraffic(false);
					}
					else
					{
						mapView.setTraffic(true);
					
					}
					}
			
        });
        Button bt3 =(Button)findViewById(R.id.Button03);
        bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent();
		            setResult(RESULT_OK, intent);
		            finish();
					}
			
        });


				
			
        
        
    }
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}