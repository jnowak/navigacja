package mapy.cyfrowe;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class MyMapView extends MapView {
public interface OnZoomChangeListener {
    public void onZoomChange(MapView view, int newZoom, int oldZoom);
}

public interface OnPanChangeListener {
    public void onPanChange(MapView view, GeoPoint newCenter, GeoPoint oldCenter);
}

private MyMapView _this;

    // Set this variable to your preferred timeout
private long events_timeout = 500L;
private boolean is_touched = false;
private GeoPoint last_center_pos;
private int last_zoom;
private Timer zoom_event_delay_timer = new Timer();
private Timer pan_event_delay_timer = new Timer();

private MyMapView.OnZoomChangeListener zoom_change_listener;
private MyMapView.OnPanChangeListener pan_change_listener;


public MyMapView(Context context, String apiKey) {
    super(context, apiKey);
    _this = this;
    last_center_pos = this.getMapCenter();
    last_zoom = this.getZoomLevel();
}

public MyMapView(Context context, AttributeSet attrs) {
    super(context, attrs);
}

public MyMapView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

public void setOnZoomChangeListener(MyMapView.OnZoomChangeListener l) {
    zoom_change_listener = l;
}

public void setOnPanChangeListener(MyMapView.OnPanChangeListener l) {
    pan_change_listener = l;
}

@Override
public boolean onTouchEvent(MotionEvent ev) {
    if (ev.getAction() == 1) {
        is_touched = false;
    } else {
        is_touched = true;
    }

    return super.onTouchEvent(ev);
}

@Override
public void computeScroll() {
    super.computeScroll();

    if (getZoomLevel() != last_zoom) {
                    // if computeScroll called before timer counts down we should drop it and start it over again
        zoom_event_delay_timer.cancel();
        zoom_event_delay_timer = new Timer();
        zoom_event_delay_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                zoom_change_listener.onZoomChange(_this, getZoomLevel(), last_zoom);
                last_zoom = getZoomLevel();
            }
        }, events_timeout);
    }

    // Send event only when map's center has changed and user stopped touching the screen
    if (!last_center_pos.equals(getMapCenter()) || !is_touched) {
        pan_event_delay_timer.cancel();
        pan_event_delay_timer = new Timer();
        pan_event_delay_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pan_change_listener.onPanChange(_this, getMapCenter(), last_center_pos);
                last_center_pos = getMapCenter();
            }
        }, events_timeout);
    }
}

}