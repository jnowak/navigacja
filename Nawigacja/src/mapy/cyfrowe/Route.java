package mapy.cyfrowe;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Route extends Overlay
{
	private Step[] steps;

	public Route(Step[] steps)
	{
		this.steps = steps;
	}
	
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
    {
        super.draw(canvas, mapView, shadow);
        Point start = new Point();
        Point end = new Point();
        Paint paint = new Paint();
        paint.setARGB(100, 255, 0, 0);
        for (int i = 0; i < steps.length; i++)
        {
        	mapView.getProjection().toPixels(steps[i].start, start);
        	mapView.getProjection().toPixels(steps[i].end, end);
        	
        	canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        }
        
		return true;  
    }
}
