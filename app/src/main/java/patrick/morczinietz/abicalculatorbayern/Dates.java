package patrick.morczinietz.abicalculatorbayern;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class Dates extends Activity {
	private TextView diffGermanTV, diffMathTV, diffThirdTV, diffOralTV;
	private String dayDiffGerman, dayDiffThird, dayDiffMath, dayDiffOral;
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dates_layout);	
		initUI();
		updateDays();
		setTextViews();
		setAd();
	}



	private void initUI() {
		diffGermanTV = (TextView) findViewById(R.id.german_date);
		diffThirdTV = (TextView) findViewById(R.id.third_date);
		diffMathTV = (TextView) findViewById(R.id.math_date);
		diffOralTV = (TextView) findViewById(R.id.oral_date);
	}
	private void setAd(){
		adView = (AdView)findViewById(R.id.adMob_dates); 
		// Lookup your LinearLayout assuming it�s been given
	    // the attribute android:id="@+id/mainLayout"
		
	    
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());

	}

	private void updateDays() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		Calendar calGerman = Calendar.getInstance();
		calGerman.set(2016, 5, 3);
		Calendar calThird = Calendar.getInstance();
		calThird.set(2016, 5, 6);
		Calendar calMath = Calendar.getInstance();
		calMath.set(2016, 4, 29);
		Calendar calOral = Calendar.getInstance();
		calOral.set(2013, 5, 30);
		dayDiffGerman = getDayDifference(calGerman);
		dayDiffThird = getDayDifference(calThird);
		dayDiffMath = getDayDifference(calMath);
		dayDiffOral = getDayDifference(calOral);
	}

	private void setTextViews() {
		diffGermanTV.setText(getResources().getString(R.string.german_date_string) + ", noch " +dayDiffGerman +" Tage" );
		diffThirdTV.setText(getResources().getString(R.string.third_date_string) + ", noch " +dayDiffThird +" Tage" );
		diffMathTV.setText(getResources().getString(R.string.math_date_string) + ", noch " +dayDiffMath +" Tage" );
		diffOralTV.setText(getResources().getString(R.string.oral_date_string) + ", noch " +dayDiffOral +" Tage" );
		setTVbgrColor(diffGermanTV, Integer.valueOf(dayDiffGerman), getResources().getString(R.string.german_date_string));
		setTVbgrColor(diffThirdTV, Integer.valueOf(dayDiffThird), getResources().getString(R.string.third_date_string));
		setTVbgrColor(diffMathTV, Integer.valueOf(dayDiffMath), getResources().getString(R.string.math_date_string));
		setTVbgrColor(diffOralTV, Integer.valueOf(dayDiffOral), getResources().getString(R.string.oral_date_string));
	}
	private void setTVbgrColor(TextView date, int dayDiff, String type){
		if(dayDiff <= 10 && dayDiff > 0){
			date.setBackgroundColor(Color.GREEN);
		}
		if(dayDiff <= 5 && dayDiff > 0){
			date.setBackgroundColor(Color.YELLOW);
		}
		if(dayDiff <= 3  && dayDiff > 0){
			date.setBackgroundColor(Color.RED);
			date.setTextColor(Color.WHITE);
		}
		if(dayDiff == 0){
			date.setBackgroundColor(Color.BLUE);
			date.setTextColor(Color.WHITE);
			date.setText(type + ", HEUTE! ♧");
		}
		if(dayDiff < 0){
			date.setText(type + " ✔");
			date.setBackgroundColor(Color.GRAY);
			date.setTextColor(Color.WHITE);
		}
	}

	private String getDayDifference(Calendar cal) {
		Calendar calOne = Calendar.getInstance();
		int dayOfYear = calOne.get(Calendar.DAY_OF_YEAR);
		int testDay = cal.get(Calendar.DAY_OF_YEAR);
		int dayDifference = testDay - dayOfYear;
		String strDayDifference = String.valueOf(dayDifference);
		return strDayDifference;
	}
}
