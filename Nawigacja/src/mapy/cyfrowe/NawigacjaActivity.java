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
	
    
 
    class MapOverlay extends com.google.android.maps.Overlay
    {
		public GeoPoint point;
		
		public MapOverlay()
		{
			point = p;
		}
		@Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            if (point != null)
            {
	            mapView.getProjection().toPixels(point, screenPts);
	            
	            //---add the marker---
	            Bitmap bmp = BitmapFactory.decodeResource(
	            getResources(), R.drawable.pushpin);            
	            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);  
	           // mapView.getController().animateTo(point);
	            
	            //mapView.invalidate();
	            
            }
            return true;
        }
        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
 
                Geocoder geoCoder = new Geocoder(
                    getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(
                        p.getLatitudeE6()  / 1E6, 
                        p.getLongitudeE6() / 1E6, 1);
 
                    String add = "";
                    if (addresses.size() > 0) 
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
                    }
 
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {                
                    e.printStackTrace();
                }   
                return true;
            }
            else                
                return false;
        }        
    } 
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
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
        String coordinates[] = {"54.371673","18.612381"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(17); 
       
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        mapView.invalidate();
        
        
        
        addPOIButton = (Button)findViewById(R.id.addPOI);
        
        addPOIButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				POIList.add(currLocation);
				
			}
		});
        
        
        
       
     /*  MyMapView mv;// = new MyMapView(this, "<your Maps API key here>");

        mv = new MyMapView(this, "<your Maps API key here>");

        mv.setClickable(true);
        mv.setBuiltInZoomControls(true);

        mv.setOnZoomChangeListener(new MyMapView.OnZoomChangeListener() {


           @Override
            public void onZoomChange(MapView view, int newZoom, int oldZoom) {
                Log.d("test", "zoom changed from " + oldZoom + " to " + newZoom);
            }
        });
        mv.setOnPanChangeListener(new MyMapView.OnPanChangeListener() {
            public void onPanChange(MapView view, GeoPoint newCenter, GeoPoint oldCenter) {
                Log.d("test", "center changed from " + oldCenter.getLatitudeE6() + "," + oldCenter.getLongitudeE6() + " to " + newCenter.getLatitudeE6() + "," + newCenter.getLongitudeE6());
            }
        });*/
        
        
        
        
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