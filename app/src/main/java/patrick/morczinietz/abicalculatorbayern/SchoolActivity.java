package patrick.morczinietz.abicalculatorbayern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class SchoolActivity extends Activity {
	private Button uniButton, hschoolButton, artSchoolButton;
	private AdView adView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schools_layout);
		initUI();
		setAd();
		setClickListener();
	}
	private void setAd(){
		adView = (AdView)findViewById(R.id.adMob_school); 
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

	private void setClickListener() {
		uniButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SchoolActivity.this, UniActivity.class));
			}
		});
		hschoolButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SchoolActivity.this,
						HSchoolActivity.class));
			}
		});

		artSchoolButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SchoolActivity.this,
						ArtSchoolActivity.class));
			}
		});

	}

	private void initUI() {
		// TODO Auto-generated method stub
		uniButton = (Button) findViewById(R.id.uni_button);
		hschoolButton = (Button) findViewById(R.id.hschool_button);
		artSchoolButton = (Button) findViewById(R.id.artschool_button);
	}

}
