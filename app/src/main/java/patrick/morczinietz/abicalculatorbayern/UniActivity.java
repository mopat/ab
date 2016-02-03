package patrick.morczinietz.abicalculatorbayern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class UniActivity extends Activity {

	private ArrayList<School> uniList;
	private ListView uniListView;
	private SchoolAdapter uniAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uni_layout);
		readUniFile();
		prepareListView();
		setClickListener();
	}

	private void setClickListener() {
		uniListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				School school = (School) uniListView.getItemAtPosition(pos);
				String schoolName = school.getName();
				setupAlertDialog(schoolName);
			}
		});

	}

	public void openWebURL(String schoolName) {
		Uri uri = Uri.parse("http://www.google.com/#q=" + schoolName);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	private void setupAlertDialog(final String schoolName) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		TextView title = new TextView(this);
		title.setText("Suchen");
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setPadding(1, 3, 1, 3);
		title.setTextSize(25);
		title.setTextColor(getResources().getColor(R.color.abi_color));

		builder.setCustomTitle(title);
		TextView message = new TextView(this);
		message.setText("Möchten Sie nach "+schoolName +" suchen?");
		message.setGravity(Gravity.CENTER_HORIZONTAL);
		message.setPadding(1, 3, 1, 3);
		message.setTextSize(18);
		builder.setView(message);

		builder.setCancelable(true);
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				openWebURL(schoolName);

			}
		});
		builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	private void prepareListView() {
		uniAdapter = new SchoolAdapter(UniActivity.this, uniList);
		uniListView = (ListView) findViewById(R.id.uniListView);
		uniListView.setAdapter(uniAdapter);
	}

	private void readUniFile() {
		uniList = new ArrayList<School>();
		Resources res = this.getResources();
		InputStream inputStream = res.openRawResource(R.raw.unistxt);

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				
				String array[] = line.split(";"); // Die Zeile an ;-Zeichen in
													// einzelne Strings
													// aufspalten

				uniList.add(new School(array[0], array[1], array[2]));
			}

			bufferedReader.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
