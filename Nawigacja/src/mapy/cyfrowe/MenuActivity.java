package mapy.cyfrowe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.MapActivity;
import android.app.Activity;

public class MenuActivity extends Activity{


	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
	//Your code here
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		
	    Button bt =(Button)findViewById(R.id.button1);
	    bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), NawigacjaActivity.class);
                startActivityForResult(myIntent, 0);

				}
	    });
	}


}
