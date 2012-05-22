package mapy.cyfrowe;

import java.util.NavigableMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.maps.MapActivity;
import android.app.Activity;

public class GoToActivity extends Activity{


	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
	//Your code here
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotomenu);
		Intent i = getIntent();
		String tryb=i.getStringExtra("tryb");
		
	  /*  Button bt =(Button)findViewById(R.id.gobutton2);
	    bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), NawigacjaActivity.class);
                startActivityForResult(myIntent, 0);

				}
	    });*/
	    
	    
        Button bt3 =(Button)findViewById(R.id.gobutton2);
        bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent();
		            setResult(RESULT_OK, intent);
		            finish();
					}
			
        });
        
        Button bt2 =(Button)findViewById(R.id.gobutton1);
        bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), NawigacjaActivity.class);
			        EditText ed1 = (EditText)findViewById(R.id.editText1); 
			        myIntent.putExtra("t", ed1.getText().toString());
			        Step[] steps = MapWebServices.getRoute(
			        		Double.toString(NawigacjaActivity.currLocation.getLatitude()) + "," + Double.toString(NawigacjaActivity.currLocation.getLongitude()), 
			        		ed1.getText().toString(), TransportMode.driving);
			        NawigacjaActivity.route.setSteps(steps);
			        
					}
			
        });
        

	    
	    
	}


}

