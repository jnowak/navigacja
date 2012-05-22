package mapy.cyfrowe;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

class MapOverlay extends com.google.android.maps.Overlay
{
	public GeoPoint point;
	
	
	@Override
    public boolean draw(Canvas canvas, MapView mapView, 
    boolean shadow, long when) 
    {
        super.draw(canvas, mapView, shadow);                   
        Paint paint = new Paint();
        paint.setARGB(100, 0, 0, 255);
        paint.setTextSize(20);

        Point screenPts = new Point();
        if (point != null)
        {
        	
            mapView.getProjection().toPixels(point, screenPts);
            
            canvas.drawCircle(screenPts.x, screenPts.y, 10, paint);
            canvas.drawText(point.toString(), 0, 100, paint);
            
        }
        return true;
    }

} 
