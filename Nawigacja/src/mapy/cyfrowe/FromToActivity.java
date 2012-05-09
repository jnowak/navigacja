package mapy.cyfrowe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.maps.MapActivity;
import android.app.Activity;

public class FromToActivity extends Activity{


	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
	//Your code here
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fromto);
		
	
	    
	    
        Button bt3 =(Button)findViewById(R.id.fromtobutton1);
        bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), NawigacjaActivity.class);
					EditText ed1 = (EditText)findViewById(R.id.editText1);   
					EditText ed2 = (EditText)findViewById(R.id.editText2);  
					myIntent.putExtra("from", ed1.getText().toString());
					myIntent.putExtra("to", ed2.getText().toString());
	                startActivityForResult(myIntent, 0);
					}
			
        });
	    
	    
	}


}

