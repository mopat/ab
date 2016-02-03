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

public class AbiMarks extends Activity {
	private Button seminarButton;
	private Spinner[] firstCourseSpinnerArray;
	private Spinner[] secondCourseSpinnerArray;
	private Spinner[] thirdCourseSpinnerArray;
	private Spinner[] fourthCourseSpinnerArray;
	private Spinner[] fifthCourseSpinnerArray;
	private Spinner[] coursesSpinnerArray;
	private TextView yourAbiPointsTV, yourElementaryAbiCoursePointsTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abi_marks_layout);
		initUI();
		initSpinnerArrays();
		initCourseSpinnerArray();
		initSpinnerGerman();
		initSpinnerMath();
		initSpinnerThird();
		initSpinnerFourth();
		initSpinnerFifth();
		loadAllSpinner();
		setClickListener();
		startMyTask(new AbiPointsSynchronizer());

	}

	private void initUI() {
		seminarButton = (Button) findViewById(R.id.seminar_button);
		yourAbiPointsTV = (TextView) findViewById(R.id.your_abi_points);
		yourElementaryAbiCoursePointsTV = (TextView) findViewById(R.id.your_elementary_abi_points);
	}

	private void setClickListener() {
		seminarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultSavior.setAbiPoints(getCompleteAbiPoints());
				ResultSavior.setAbiHalfYearPoints(getHalfYearPoints());
				Intent i = new Intent(AbiMarks.this, SeminarMarks.class);
				//addCoursesToArrayList();
				saveAllSpinner();
				startActivity(i);
			}
		});
	}
	private void loadAllSpinner(){
		loadSpinnerItems("firstCourseSpinnerArray", firstCourseSpinnerArray);
		loadSpinnerItems("firstCourseSpinnerArray", firstCourseSpinnerArray);
		loadSpinnerItems("secondCourseSpinnerArray", secondCourseSpinnerArray);
		loadSpinnerItems("thirdCourseSpinnerArray", thirdCourseSpinnerArray);
		loadSpinnerItems("fourthCourseSpinnerArray", fourthCourseSpinnerArray);
		loadSpinnerItems("fifthCourseSpinnerArray", fifthCourseSpinnerArray);
		loadSpinnerItems("coursesSpinnerArray", coursesSpinnerArray);
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
		saveSpinnerItems("firstCourseSpinnerArray", firstCourseSpinnerArray);
		saveSpinnerItems("secondCourseSpinnerArray", secondCourseSpinnerArray);
		saveSpinnerItems("thirdCourseSpinnerArray", thirdCourseSpinnerArray);
		saveSpinnerItems("fourthCourseSpinnerArray", fourthCourseSpinnerArray);
		saveSpinnerItems("fifthCourseSpinnerArray", fifthCourseSpinnerArray);
		saveSpinnerItems("coursesSpinnerArray", coursesSpinnerArray);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// API 11
	private void startMyTask(AsyncTask<String, String, String> asyncTask) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			asyncTask.execute();
	}

	public void updatePoints() {
		yourAbiPointsTV.setText("Im Abitur erreichte Punkte\n"
				+ getCompleteAbiPoints() + "/300");
		yourElementaryAbiCoursePointsTV
				.setText("In den Halbjahren erreichte Punkte\n"
						+ getHalfYearPoints() + "/300");
	}

	@Override
	public void onBackPressed() {
		Config.setStopAsyncs(true);
		saveAllSpinner();
		super.onBackPressed();
	}

	private int getHalfYearPoints() {
		int halfYearPoints = 0;
		for (int i = 0; i < 4; i++) {
			halfYearPoints += Integer.valueOf(firstCourseSpinnerArray[i]
					.getSelectedItem().toString());
			halfYearPoints += Integer.valueOf(secondCourseSpinnerArray[i]
					.getSelectedItem().toString());
			halfYearPoints += Integer.valueOf(thirdCourseSpinnerArray[i]
					.getSelectedItem().toString());
			halfYearPoints += Integer.valueOf(fourthCourseSpinnerArray[i]
					.getSelectedItem().toString());
			halfYearPoints += Integer.valueOf(fifthCourseSpinnerArray[i]
					.getSelectedItem().toString());
		}
		return halfYearPoints;
	}

	private int getCompleteAbiPoints() {
		int completeAbiPoints = 0;
		completeAbiPoints += getCourseAbiPoints(firstCourseSpinnerArray);
		completeAbiPoints += getCourseAbiPoints(secondCourseSpinnerArray);
		completeAbiPoints += getCourseAbiPoints(thirdCourseSpinnerArray);
		completeAbiPoints += getCourseAbiPoints(fourthCourseSpinnerArray);
		completeAbiPoints += getCourseAbiPoints(fifthCourseSpinnerArray);
		return completeAbiPoints;

	}

	private int getCourseAbiPoints(Spinner[] spinnerArray) {
		int abiPoints = 0;
		if (spinnerArray.length > 5) {
			if (spinnerArray[5].getSelectedItem().toString().equals("keine")) {
				abiPoints += calcAbiPonts(Integer.valueOf(spinnerArray[4]
						.getSelectedItem().toString()));

			} else {

				abiPoints += calcAbiPontsWithRepeat(
						Integer.valueOf(spinnerArray[4].getSelectedItem()
								.toString()), Integer.valueOf(spinnerArray[5]
								.getSelectedItem().toString()));
			}

		} else {
			abiPoints += calcOralAbiPoints(Integer.valueOf(spinnerArray[4]
					.getSelectedItem().toString()));
		}
		return abiPoints;
	}

	private int calcAbiPontsWithRepeat(int abiTest, int repeatTest) {
		int abiPointsWithRepeat = ((abiTest * 2 + repeatTest) / 3) * 4;
		return abiPointsWithRepeat;
	}

	private int calcAbiPonts(int abiTest) {
		int abiPoints = abiTest * 4;
		return abiPoints;
	}

	private int calcOralAbiPoints(int oralAbiTest) {
		int abiPointsOral = oralAbiTest;
		return abiPointsOral;
	}

	class AbiPointsSynchronizer extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//System.out.println(" Abimarks preeeexecute");
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			updatePoints();
			//System.out.println("Abimarks publish");

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//System.out.println("Abimarks post");
		}

		@Override
		protected String doInBackground(String... params) {
			while (true) {
			//	System.out.println("Abimarks loop");
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

	private void addCoursesToArrayList() {
		for (int i = 0; i < coursesSpinnerArray.length; i++) {
			SelectedCoursesSavior
					.addCourse(getSelectedCourse(coursesSpinnerArray[i]));
		}
	}

	private String getSelectedCourse(Spinner courseSpinner) {
		String selectedCourse = courseSpinner.getSelectedItem().toString();
		return selectedCourse;
	}

	private void initCourseSpinnerArray() {
		coursesSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_one_german);
		coursesSpinnerArray[1] = (Spinner) findViewById(R.id.c_spinner_one_math);
		coursesSpinnerArray[2] = (Spinner) findViewById(R.id.c_spinner_three);
		coursesSpinnerArray[3] = (Spinner) findViewById(R.id.c_spinner_four);
		coursesSpinnerArray[4] = (Spinner) findViewById(R.id.c_spinner_five);
	}

	private void initSpinnerArrays() {
		coursesSpinnerArray = new Spinner[5];
		firstCourseSpinnerArray = new Spinner[6];
		secondCourseSpinnerArray = new Spinner[6];
		thirdCourseSpinnerArray = new Spinner[6];
		fourthCourseSpinnerArray = new Spinner[5];
		fifthCourseSpinnerArray = new Spinner[5];

	}

	private void initSpinnerGerman() {
		firstCourseSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_german);
		firstCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_german);
		firstCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_german);
		firstCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_four_german);
		firstCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_test_german);
		firstCourseSpinnerArray[5] = (Spinner) findViewById(R.id.p_spinner_test_repeat_german);
	}

	private void initSpinnerMath() {
		secondCourseSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_math);
		secondCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_math);
		secondCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_math);
		secondCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_four_math);
		secondCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_test_math);
		secondCourseSpinnerArray[5] = (Spinner) findViewById(R.id.p_spinner_test_repeat_math);
	}

	private void initSpinnerThird() {
		thirdCourseSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_third);
		thirdCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_third);
		thirdCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_third);
		thirdCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_four_third);
		thirdCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_test_third);
		thirdCourseSpinnerArray[5] = (Spinner) findViewById(R.id.p_spinner_test_repeat_third);
	}

	private void initSpinnerFourth() {
		fourthCourseSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_fourth_oral);
		fourthCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_fourth_oral);
		fourthCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_fourth_oral);
		fourthCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_four_fourth_oral);
		fourthCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_test_fourth_oral);
	}

	private void initSpinnerFifth() {
		fifthCourseSpinnerArray[0] = (Spinner) findViewById(R.id.p_spinner_one_fifth_oral);
		fifthCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_two_fifth_oral);
		fifthCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_three_fifth_oral);
		fifthCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_four_fifth_oral);
		fifthCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_test_fifth_oral);
	}

}
