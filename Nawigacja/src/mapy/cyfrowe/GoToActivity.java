package mapy.cyfrowe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.MapActivity;
import android.app.Activity;

public class GoToActivity extends Activity{


	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
	//Your code here
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotomenu);
		
		
	  /*  Button bt =(Button)findViewById(R.id.gobutton2);
	    bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), MenuActivity.class);
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
	    
	    
	}


}

