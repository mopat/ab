package patrick.morczinietz.abicalculatorbayern;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ElementaryMarks extends Activity {

	private static final int MAX_CON = 14;
	private boolean tooMuchCons = false;
	private boolean tooLittleCons = true;
	private boolean enoughCons = false;

	private Button resultButton;
	private Spinner[] firstCourseSpinnerArray;
	private Spinner[] secondCourseSpinnerArray;
	private Spinner[] thirdCourseSpinnerArray;
	private Spinner[] fourthCourseSpinnerArray;
	private Spinner[] fifthCourseSpinnerArray;
	private Spinner[] sixthCourseSpinnerArray;
	private Spinner[] seventhCourseSpinnerArray;
	private Spinner[] coursesSpinnerArray;
	private TextView yourElementaryPoints, yourContributionsTV;
	private ArrayList<String> selectedCoursesArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elementary_marks_layout);
		//setupAlertDialogStart();
		initUI();
		initSpinnerArrays();
		initSpinner();
		initCoursesSpinnerArray();
		setSelectedCoursesArrayList();
		setClickListener();
		loadAllSpinner();
		startMyTask(new ElementaryPointsSynchronizer());
	}


	private void initUI() {
		selectedCoursesArrayList = new ArrayList<String>();
		resultButton = (Button) findViewById(R.id.result_button);
		yourElementaryPoints = (TextView) findViewById(R.id.your_elementary_points);
		yourContributionsTV = (TextView) findViewById(R.id.cons_TV);
	}
	private void loadAllSpinner(){
		loadSpinnerItems("firstCourseSpinnerArrayElem", firstCourseSpinnerArray);
		loadSpinnerItems("secondCourseSpinnerArrayElem", secondCourseSpinnerArray);
		loadSpinnerItems("thirdCourseSpinnerArrayElem", thirdCourseSpinnerArray);
		loadSpinnerItems("fourthCourseSpinnerArrayElem", fourthCourseSpinnerArray);
		loadSpinnerItems("fifthCourseSpinnerArrayElem", fifthCourseSpinnerArray);
		loadSpinnerItems("sixthCourseSpinnerArrayElem", sixthCourseSpinnerArray);
		loadSpinnerItems("seventhCourseSpinnerArrayElem", seventhCourseSpinnerArray);
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
		saveSpinnerItems("firstCourseSpinnerArrayElem", firstCourseSpinnerArray);
		saveSpinnerItems("secondCourseSpinnerArrayElem", secondCourseSpinnerArray);
		saveSpinnerItems("thirdCourseSpinnerArrayElem", thirdCourseSpinnerArray);
		saveSpinnerItems("fourthCourseSpinnerArrayElem", fourthCourseSpinnerArray);
		saveSpinnerItems("fifthCourseSpinnerArrayElem", fifthCourseSpinnerArray);
		saveSpinnerItems("sixthCourseSpinnerArrayElem", sixthCourseSpinnerArray);
		saveSpinnerItems("seventhCourseSpinnerArrayElem", seventhCourseSpinnerArray);
	}
	@Override
	public void onBackPressed() {
		saveAllSpinner();
		super.onBackPressed();
	}

	private void initSpinnerArrays() {
		firstCourseSpinnerArray = new Spinner[6];
		secondCourseSpinnerArray = new Spinner[6];
		thirdCourseSpinnerArray = new Spinner[6];
		fourthCourseSpinnerArray = new Spinner[6];
		fifthCourseSpinnerArray = new Spinner[6];
		sixthCourseSpinnerArray = new Spinner[6];
		seventhCourseSpinnerArray = new Spinner[6];
		coursesSpinnerArray = new Spinner[7];

	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
	private void startMyTask(AsyncTask<String, String, String> asyncTask) {
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	    else
	        asyncTask.execute();
	}
	private void setupAlertDialogStart(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		TextView title = new TextView(this);
		title.setText(getResources().getString(R.string.alert_title_elem));
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setPadding(1, 3, 1, 3);
		title.setTextSize(25);
		title.setTextColor(getResources().getColor(R.color.abi_color));
		
		builder.setCustomTitle(title);
		TextView message = new TextView(this);
		message.setText(getResources().getString(R.string.alert_message_elem));
		message.setGravity(Gravity.CENTER_HORIZONTAL);
		message.setPadding(1, 3, 1, 3);
		message.setTextSize(18);
		builder.setView(message);
		
		builder.setCancelable(true);
		builder.setPositiveButton("Schliessen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
			
	
	}
	private void setupAlertDialogCalc(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		TextView title = new TextView(this);
		title.setText(getResources().getString(R.string.alert_title_elem));
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setPadding(1, 3, 1, 3);
		title.setTextSize(25);
		title.setTextColor(getResources().getColor(R.color.abi_color));	
		builder.setCustomTitle(title);
		
		TextView message = new TextView(this);
		message.setText(getResources().getString(R.string.alert_message_elem_calc));
		message.setGravity(Gravity.CENTER_HORIZONTAL);
		message.setPadding(1, 3, 1, 3);
		message.setTextSize(18);
		builder.setView(message);
		builder.setPositiveButton("Schliessen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	private void updateSelectedCoursesArrayList() {
		for (int i = 0; i < selectedCoursesArrayList.size(); i++) {
			String course = coursesSpinnerArray[i].getSelectedItem().toString();
			selectedCoursesArrayList.set(i, course);
		}
	}

	private int getEffortsCount() {
		int effortsCount = 0;
		effortsCount += Integer.valueOf(firstCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(secondCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(thirdCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(fourthCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(fifthCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(sixthCourseSpinnerArray[5]
				.getSelectedItem().toString());
		effortsCount += Integer.valueOf(seventhCourseSpinnerArray[5]
				.getSelectedItem().toString());
		return effortsCount;
	}

	
	private String getSelectedCourseString(Spinner spinnerArray[]) {
		String selectedCourseString = spinnerArray[0].getSelectedItem()
				.toString();
		return selectedCourseString;
	}

	

	private void updatePoints() {
		int elementaryPoints = getElementaryCoursesPoints();
		//Log.d("updatePointselem", "updatePointselem");
		setBooleanCons();
		yourElementaryPoints.setText("Erreichte Punkte in Grundkursen\n"
				+ elementaryPoints + "/210");
		if (tooLittleCons) {
			yourContributionsTV
					.setText("Eingebrachte Grundkursleistungen: "
							+ getEffortsCount() + "/14 \nDu Musst noch "
							+ (MAX_CON - getEffortsCount())
							+ " Leistungen einbringen.");
		}
		if (tooMuchCons) {
			yourContributionsTV.setText("Eingebrachte Grundkursleistungen: "
					+ getEffortsCount() + "/14 \n Du Musst "
					+ (getEffortsCount() - MAX_CON) + " Leistungen streichen.");
		}
		if (enoughCons) {
			yourContributionsTV
					.setText("Du hast genau 14 Grundkursleistungen eingebracht!\n Du kannst die Berechnung jetzt starten.");
		}
	}

	private void setBooleanCons() {
		if (getEffortsCount() < MAX_CON) {
			tooLittleCons = true;
			tooMuchCons = false;
			enoughCons = false;
		} else if (getEffortsCount() > MAX_CON) {
			tooMuchCons = true;
			tooLittleCons = false;
			enoughCons = false;
		} else if (getEffortsCount() == MAX_CON) {
			enoughCons = true;
			tooLittleCons = false;
			tooMuchCons = false;
		}
	}

	private int getCoursePoints(Spinner[] spinnerArray) {
		int coursePoints = 0;
		int numCons = 0;
		if (spinnerArray[0].getSelectedItem().toString().equals("--") == false) {
			numCons = Integer.valueOf(spinnerArray[5].getSelectedItem()
					.toString());
		
			switch (numCons) {
			case 0:
				return 0;
			case 1:
				coursePoints = getSingleBestCoursePoints(spinnerArray);
				return coursePoints;
			case 2:
				coursePoints = getTwoBestCoursePoints(spinnerArray);
				return coursePoints;
			case 3:
				coursePoints = getThreeBestCoursePoints(spinnerArray);
				return coursePoints;
			case 4:
				coursePoints = getAllCoursePoints(spinnerArray);
				return coursePoints;
			}
		} else {
			return 0;
		}
		return coursePoints;
	}

	private int getElementaryCoursesPoints() {
		int elementaryPoints = 0;

		elementaryPoints += getCoursePoints(firstCourseSpinnerArray);
		elementaryPoints += getCoursePoints(secondCourseSpinnerArray);
		elementaryPoints += getCoursePoints(thirdCourseSpinnerArray);
		elementaryPoints += getCoursePoints(fourthCourseSpinnerArray);
		elementaryPoints += getCoursePoints(fifthCourseSpinnerArray);
		elementaryPoints += getCoursePoints(sixthCourseSpinnerArray);
		elementaryPoints += getCoursePoints(seventhCourseSpinnerArray);
		return elementaryPoints;
	}


	class ElementaryPointsSynchronizer extends
			AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//System.out.println( "Elem preeeexecute");
			Config.setSeminarMarksButtonPressed(false);
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			updateSelectedCoursesArrayList();
			updatePoints();
			//System.out.println( "Elem progress");
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//System.out.println(" ELEm post");
		}

		@Override
		protected String doInBackground(String... params) {
			while (true) {
				//System.out.println( "Elem loop");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Config.isStopAsyncs() == true){
					break;
				}
				publishProgress();
				
			}
			return null;
		}

	}

	// Single Best
	// -----------------------------------------------------------------------------------------------------------------------
	private int getSingleBestCoursePoints(Spinner spinnerArray[]) {
		int[] spinnerValuesArray = getSortedArray(spinnerArray);
		int singleBestCoursePoints = spinnerValuesArray[spinnerValuesArray.length - 1];
		return singleBestCoursePoints;

	}

	// Two Best
	// ------------------------------------------------------------------------------------------------------------------------
	private int getTwoBestCoursePoints(Spinner spinnerArray[]) {
		int[] spinnerValuesArray = getSortedArray(spinnerArray);
		int maxOne = spinnerValuesArray[spinnerValuesArray.length - 1];
		int maxTwo = spinnerValuesArray[spinnerValuesArray.length - 2];
		int twoBestCouresPoint = maxOne + maxTwo;
		return twoBestCouresPoint;

	}

	// Three Best
	// --------------------------------------------------------------------------------------------------------------------------------
	private int getThreeBestCoursePoints(Spinner spinnerArray[]) {
		int[] spinnerValuesArray = getSortedArray(spinnerArray);
		int maxOne = spinnerValuesArray[spinnerValuesArray.length - 1];
		int maxTwo = spinnerValuesArray[spinnerValuesArray.length - 2];
		int maxThree = spinnerValuesArray[spinnerValuesArray.length - 3];
		int threeBestCoursePoints = maxOne + maxTwo + maxThree;
		return threeBestCoursePoints;
	}

	// All
	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	private int getAllCoursePoints(Spinner spinnerArray[]) {
		int coursePoints = 0;
		for (int i = 1; i < 5; i++) {
			if (spinnerArray[i].getSelectedItem().toString().equals("--") == false)
				coursePoints += Integer.valueOf(spinnerArray[i]
						.getSelectedItem().toString());

		}
		return coursePoints;
	}

	// --------------------------------------------------------------------------------------

	private int[] getSortedArray(Spinner spinnerArray[]) {
		int[] spinnerValuesArray = new int[spinnerArray.length - 2];
		for (int i = 1; i <= spinnerValuesArray.length; i++) {
			if (spinnerArray[i].getSelectedItem().toString().equals("--")) {
				spinnerValuesArray[i - 1] = 0;
			} else {
				spinnerValuesArray[i - 1] = Integer.valueOf(spinnerArray[i]
						.getSelectedItem().toString());
			}
		}
		java.util.Arrays.sort(spinnerValuesArray);
		return spinnerValuesArray;
	}

	private void setClickListener() {
		resultButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveAllSpinner();
				if(tooLittleCons || tooMuchCons){
					setupAlertDialogCalc();
				}
				if (enoughCons) {
					ResultSavior
							.setElementaryPoints(getElementaryCoursesPoints());
					Intent i = new Intent(ElementaryMarks.this, Result.class);
					//updateSelectedCoursesArrayList();
					startActivity(i);
				}
			}
		});
	}

	private void initSpinner() {
		initSpinnerOne();
		initSpinnerTwo();
		initSpinnerThree();
		initSpinnerFour();
		initSpinnerFive();
		initSpinnerSix();
		initSpinnerSeven();
	}

	private void initCoursesSpinnerArray() {
		coursesSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_first_element);
		coursesSpinnerArray[1] = (Spinner) findViewById(R.id.c_spinner_second_element);
		coursesSpinnerArray[2] = (Spinner) findViewById(R.id.c_spinner_third_element);
		coursesSpinnerArray[3] = (Spinner) findViewById(R.id.c_spinner_fourth_element);
		coursesSpinnerArray[4] = (Spinner) findViewById(R.id.c_spinner_fifth_element);
		coursesSpinnerArray[5] = (Spinner) findViewById(R.id.c_spinner_sixth_element);
		coursesSpinnerArray[6] = (Spinner) findViewById(R.id.c_spinner_seventh_element);

	}

	private void initSpinnerOne() {
		firstCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_first_element);
		firstCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_first_element);
		firstCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_first_element);
		firstCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_first_element);
		firstCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_first_element);
		firstCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner1);
	}

	private void initSpinnerTwo() {
		secondCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_second_element);
		secondCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_second_element);
		secondCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_second_element);
		secondCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_second_element);
		secondCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_second_element);
		secondCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner2);
	}

	private void initSpinnerThree() {
		thirdCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_third_element);
		thirdCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_third_element);
		thirdCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_third_element);
		thirdCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_third_element);
		thirdCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_third_element);
		thirdCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner3);
	}

	private void initSpinnerFour() {
		fourthCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_fourth_element);
		fourthCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_fourth_element);
		fourthCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_fourth_element);
		fourthCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_fourth_element);
		fourthCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_fourth_element);
		fourthCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner4);
	}

	private void initSpinnerFive() {
		fifthCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_fifth_element);
		fifthCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_fifth_element);
		fifthCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_fifth_element);
		fifthCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_fifth_element);
		fifthCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_fifth_element);
		fifthCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner5);
	}

	private void initSpinnerSix() {
		sixthCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_sixth_element);
		sixthCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_sixth_element);
		sixthCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_sixth_element);
		sixthCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_sixth_element);
		sixthCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_sixth_element);
		sixthCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner6);
	}

	private void initSpinnerSeven() {
		seventhCourseSpinnerArray[0] = (Spinner) findViewById(R.id.c_spinner_seventh_element);
		seventhCourseSpinnerArray[1] = (Spinner) findViewById(R.id.p_spinner_one_seventh_element);
		seventhCourseSpinnerArray[2] = (Spinner) findViewById(R.id.p_spinner_two_seventh_element);
		seventhCourseSpinnerArray[3] = (Spinner) findViewById(R.id.p_spinner_three_seventh_element);
		seventhCourseSpinnerArray[4] = (Spinner) findViewById(R.id.p_spinner_four_seventh_element);
		seventhCourseSpinnerArray[5] = (Spinner) findViewById(R.id.spinner7);
	}

	private int getSmallestCourseArrayIndex(Spinner spinnerArray[]) {
		int min = Integer.valueOf(spinnerArray[1].getSelectedItem().toString());
		int smallestValueIndex = 1;
		for (int i = 1; i < spinnerArray.length; i++) {
			int currentArrayValue = Integer.valueOf(spinnerArray[i]
					.getSelectedItem().toString());
			if (currentArrayValue < min) {
				min = currentArrayValue;
				smallestValueIndex = i;
			}
		}
		return smallestValueIndex;
	}

	private int[] getTwoBiggestCourseArray2(Spinner spinnerArray[]) {
		int[] biggestValues = new int[2];
		int[] spinnerValuesArray = getSortedArray(spinnerArray);
		biggestValues[0] = spinnerValuesArray[spinnerValuesArray.length - 2];
		biggestValues[1] = spinnerValuesArray[spinnerValuesArray.length - 1];
		return biggestValues;
	}

	
	
	private boolean isCourseSelected(String selectedCourse) {
		for (String c : selectedCoursesArrayList) {
			if (c.equals(selectedCourse)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNaturalScienceAbi(String selectedCourse) {
		for (String c : selectedCoursesArrayList) {
			if (c.equals(Courses.PHYSIC) || c.equals(Courses.CHEMICS)
					|| c.equals(Courses.BIOLOGY)) {
				return true;
			}
		}
		return false;
	}
	private boolean isHistorySocial(String selectedCourse) {
		if (selectedCourse.equals(Courses.HISTORY_SOCIAL)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isThreeSemDuty(String selectedCourse) {
		if (selectedCourse.equals(Courses.RELI_ETHIK)
				|| selectedCourse.equals(Courses.GEO)
				|| selectedCourse.equals(Courses.ECONOMICS)
				|| selectedCourse.equals(Courses.ART)
				|| selectedCourse.equals(Courses.MUSIC)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isNaturalScience(String selectedCourse) {
		if (selectedCourse.equals(Courses.PHYSIC)
				|| selectedCourse.equals(Courses.CHEMICS)
				|| selectedCourse.equals(Courses.BIOLOGY)) {
			return true;
		} else {
			return false;
		}
	}

	private void setSelectedCoursesArrayList() {
		selectedCoursesArrayList
				.add(getSelectedCourseString(firstCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(secondCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(thirdCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(fourthCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(fifthCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(sixthCourseSpinnerArray));
		selectedCoursesArrayList
				.add(getSelectedCourseString(seventhCourseSpinnerArray));
	}

	private void updateSelectedCoursesSaviorArrayList() {
		SelectedCoursesSavior
				.addCourse((getSelectedCourseString(firstCourseSpinnerArray)));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(secondCourseSpinnerArray));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(thirdCourseSpinnerArray));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(fourthCourseSpinnerArray));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(fifthCourseSpinnerArray));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(sixthCourseSpinnerArray));
		SelectedCoursesSavior
				.addCourse(getSelectedCourseString(seventhCourseSpinnerArray));
	}
	private int getNaturalScienceAbiCount() {
		int naturalSciencesAbiCount = 0;
		for (int i = 0; i < SelectedCoursesSavior.getAllSelectedCoursesList()
				.size(); i++) {
			String currentCourseSavior = SelectedCoursesSavior
					.getAllSelectedCoursesList().get(i);
			if (currentCourseSavior.equals(Courses.BIOLOGY)
					|| currentCourseSavior.equals(Courses.PHYSIC)
					|| currentCourseSavior.equals(Courses.CHEMICS)) {
				naturalSciencesAbiCount++;
			}

		}
		return naturalSciencesAbiCount;
	}
	private int getNaturalSciencesCount() {
		int naturalSciencesCount = 0;
		for (int j = 0; j < selectedCoursesArrayList.size(); j++) {
			String currentCourseElement = selectedCoursesArrayList.get(j);
			if (currentCourseElement.equals(Courses.BIOLOGY)
					|| currentCourseElement.equals(Courses.PHYSIC)
					|| currentCourseElement.equals(Courses.CHEMICS)) {
				naturalSciencesCount++;
			}
		}
		return naturalSciencesCount;
	}
}
