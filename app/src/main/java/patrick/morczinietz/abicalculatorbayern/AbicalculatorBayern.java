package patrick.morczinietz.abicalculatorbayern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;


public class AbicalculatorBayern extends Activity {
	
	private ImageView  dateButton, toDoButton, schoolButton, calcImageView;

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abicalculator__bayern);
		initUI();
		setClickListener();
		setAd();
	}


	private void initUI(){
		calcImageView = (ImageView)findViewById(R.id.start_button);
		dateButton = (ImageView)findViewById(R.id.dates_button);
		toDoButton = (ImageView)findViewById(R.id.toDo_button);
		schoolButton =(ImageView)findViewById(R.id.school_button);

	}

	private void setAd(){
		adView = (AdView)findViewById(R.id.adMob); 
		// Lookup your LinearLayout assuming it�s been given
	    // the attribute android:id="@+id/mainLayout"
		
	    
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());

	}
	@Override
	  public void onDestroy() {
	    adView.destroy();
	    super.onDestroy();
	  }

	private void setClickListener(){
		calcImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Config.setStopAsyncs(false);
				Intent i =  new Intent(AbicalculatorBayern.this, AbiMarks.class);
				startActivity(i);	
			}
			
		});
		
		dateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent j =  new Intent(AbicalculatorBayern.this, Dates.class);
				startActivity(j);
			}
		});
		toDoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent k = new Intent(AbicalculatorBayern.this, ToDo.class);
				startActivity(k);				
			}
		});
		schoolButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent h = new Intent(AbicalculatorBayern.this, SchoolActivity.class);
				startActivity(h);	
				
			}
		});
	}
	@Override
	public void onBackPressed(){
		this.finish();
		
		super.onBackPressed();
	}

}
