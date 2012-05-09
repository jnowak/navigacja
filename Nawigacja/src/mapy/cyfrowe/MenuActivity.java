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
	    Button bt2 =(Button)findViewById(R.id.button3);
	    bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), GoToActivity.class);
				myIntent.putExtra("tryb", "samochod");
                startActivityForResult(myIntent, 0);

				}
	    });
	    Button bt3 =(Button)findViewById(R.id.button4);
	    bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), GoToActivity.class);
				myIntent.putExtra("tryb", "pieszo");
                startActivityForResult(myIntent, 0);

				}
	    });
	    Button bt4 =(Button)findViewById(R.id.button5);
	    bt4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), FromToActivity.class);
                startActivityForResult(myIntent, 0);

				}
	    });
	}


}
