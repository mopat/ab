package patrick.morczinietz.abicalculatorbayern;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends Activity {
	private static final int MIN_POINTS = 300;
	private static final int CALC_START = 822;
	private static final int REDUCER = 18;
	private static final float NEXT_STEP = (float) 0.1;
	
	
	private int completePoints;
	private float abiMark = (float) 1.1;
	private TextView pointsResultTV, markResultTV;
	private Button backButton;
	private ImageView thumbIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		initUI();
		setPointsResult();
		setMarkResult();
		setThumbImageView();
		setFont() ;
		setClickListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_abicalculator__bayern, menu);
		return true;
	}

	private void initUI() {
		completePoints = 0;
		pointsResultTV = (TextView) findViewById(R.id.points_result_tv);
		markResultTV = (TextView) findViewById(R.id.mark_result_tv);
		backButton = (Button)findViewById(R.id.back_button);
		thumbIV = (ImageView)findViewById(R.id.thumb_iv);
	}
	private void setThumbImageView(){
		if(abiMark < 2.5){
			thumbIV.setBackgroundResource(R.drawable.daumenhoch);
		}
		else if(abiMark >= 2.5 && abiMark < 3.5){
			thumbIV.setBackgroundResource(R.drawable.daumenseite);
		}
		else{
			thumbIV.setBackgroundResource(R.drawable.daumenrunter);
		}
	}
	private void setClickListener(){
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Config.setStopAsyncs(true);
				Intent i = new Intent(Result.this, AbicalculatorBayern.class);
				startActivity(i);
			}
		});
	}
	private void setPointsResult() {
		completePoints = ResultSavior.getAbiPoints() + ResultSavior.getAbiHalfYearPoints() + ResultSavior.getSeminarPoints()
				+ ResultSavior.getElementaryPoints();
		//pointsResultTV.setText("Punkte gesamt: "
			//	+ completePoints + "/900");
		
	}

	private void setMarkResult() {
		String abiMarkString = null;

		if (completePoints > CALC_START) {
			abiMark = (float) 1.0;
			abiMarkString = String.valueOf(abiMark);
		}
		else if (completePoints < MIN_POINTS) {
			abiMarkString = "Leider nicht bestanden!";
		} else if(completePoints <= CALC_START){
			calcAbiMark();
			DecimalFormat df = new DecimalFormat("#.#");
			abiMarkString = df.format((float)abiMark);
			System.out.println(abiMarkString);
			if (abiMark % 1 == 0 || abiMark % 2.0 == 0 || abiMark % 3.0 == 0 || abiMark % 4.0 == 0) {
				abiMarkString += ".0";
			}
		}
		markResultTV.setText("Dein Abiturdurchschnitt\n"+abiMarkString+"\n\n"+("Punkte gesamt\n"
				+ completePoints + "/900"));
	}

	private void calcAbiMark() {
		int abiPoints = CALC_START;
		while (true) {
			int oldPoints = abiPoints;
			abiPoints -= REDUCER;
			if (completePoints <= oldPoints && completePoints > abiPoints) {
				break;
			}
			abiMark += NEXT_STEP;

		}
	}
	private void setFont() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/pastelcrayon.ttf");
		markResultTV.setTypeface(tf);
	}
}
