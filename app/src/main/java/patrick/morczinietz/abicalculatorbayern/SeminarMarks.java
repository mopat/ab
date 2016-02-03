package patrick.morczinietz.abicalculatorbayern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SeminarMarks extends Activity {
	private Button elementaryButton;
	private Spinner[] sciencePropSpinnerArray;
	private Spinner projectSeminarSpinner[];
	private TextView yourSeminarPointsTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seminar_marks_layout);
		initUI();
		initSpinnerArrays();
		setClickListener();
		loadAllSpinner();
		startMyTask(new SeminarPointsSynchronizer());
	}


	private void initUI() {
		elementaryButton = (Button) findViewById(R.id.elementary_courses_button);
		yourSeminarPointsTV = (TextView) findViewById(R.id.your_Seminar_points);
	}

	private void setClickListener() {
		elementaryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultSavior
						.setwSeminarHalfYearPoints(getWSeminarHalfYearPoints());
				ResultSavior.setSeminarPoints(getSeminarPoints());
				saveAllSpinner();
				Intent i = new Intent(SeminarMarks.this, ElementaryMarks.class);
				startActivity(i);

			}
		});
	}
	private void loadAllSpinner(){
		loadSpinnerItems("sciencePropSpinnerArray", sciencePropSpinnerArray);
		loadSpinnerItems("projectSeminarSpinner", projectSeminarSpinner);
		
	}
	private void loadSpinnerItems(String spinnerName, Spinner[] spinnerArray) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		for (int i = 0; i < spinnerArray.length; i++) {
			spinnerArray[i].setSelection(prefs.getInt(spinnerName + " "
					+ String.valueOf(i), 0));
		}
	}
	private void saveSpinnerItems(String spinnerName, Spinner[] spinnerArray) {
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor prefEditor = prefs.edit();
		for (int i = 0; i < spinnerArray.length; i++) {
			String name = spinnerName + " " + String.valueOf(i);
			int selectedPosition = spinnerArray[i].getSelectedItemPosition();
			prefEditor.putInt(name, selectedPosition);
		}
		prefEditor.commit();
	}
	private void saveAllSpinner(){
		saveSpinnerItems("sciencePropSpinnerArray", sciencePropSpinnerArray);
		saveSpinnerItems("projectSeminarSpinner", projectSeminarSpinner);
	}

	private void initSpinnerArrays() {
		sciencePropSpinnerArray = new Spinner[3];
		projectSeminarSpinner = new Spinner[1];
		sciencePropSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_science_prop);
		sciencePropSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_science_prop);
		sciencePropSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_science_prop_praeseminar);
		projectSeminarSpinner[0] = (Spinner) findViewById(R.id.points_spinner_project_seminar);
	}

	private int getSeminarPoints() {
		int seminarPoints = 0;
		seminarPoints += Integer.valueOf(sciencePropSpinnerArray[0]
				.getSelectedItem().toString());
		seminarPoints += Integer.valueOf(sciencePropSpinnerArray[1]
				.getSelectedItem().toString());
		seminarPoints += Integer.valueOf(sciencePropSpinnerArray[2]
				.getSelectedItem().toString());
		seminarPoints += Integer.valueOf(projectSeminarSpinner[0]
				.getSelectedItem().toString());
		return seminarPoints;
	}

	private int getWSeminarHalfYearPoints() {
		int WSeminarHalfYearPoints = 0;
		WSeminarHalfYearPoints += Integer.valueOf(sciencePropSpinnerArray[0]
				.getSelectedItem().toString());
		WSeminarHalfYearPoints += Integer.valueOf(sciencePropSpinnerArray[1]
				.getSelectedItem().toString());
		return WSeminarHalfYearPoints;
	}

	public void updatePoints() {
		yourSeminarPointsTV.setText("In den Seminaren erreichte Punkte\n"
				+ getSeminarPoints() + "/90");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// API 11
	private void startMyTask(AsyncTask<String, String, String> asyncTask) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			asyncTask.execute();
	}

	class SeminarPointsSynchronizer extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//System.out.println(" Seminar preeeexecute");
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			updatePoints();
			//System.out.println("Seminar publish");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//System.out.println("Seminar post");
		}

		@Override
		protected String doInBackground(String... params) {
			//Log.d("Seminar", "background");
			while (true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Config.isStopAsyncs() == true) {
					break;
				}
				publishProgress();
			}
			return null;
		}

	}

}
