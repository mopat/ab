package patrick.morczinietz.abicalculatorbayern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class ToDo extends Activity {
	private Button addButton;
	private EditText noteInput;
	private ListView listView;
	private ToDoDBAdapter todoDBAdapter;
	private SimpleCursorAdapter simpleCursorAdapter;
	private Cursor cursor;
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_layout);
		setupUI();
		initAdapter();
		addItem();
		deleteItemLongClick();
		showItemDetails();
		setAd();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_abicalculator__bayern, menu);
		return true;
	}

	private void setupUI() {

		addButton = (Button) findViewById(R.id.addButton);
		noteInput = (EditText) findViewById(R.id.editText);
		listView = (ListView) findViewById(R.id.listView);

	}

	private void initAdapter() {
		todoDBAdapter = new ToDoDBAdapter(this);
		todoDBAdapter.open();
		populateToDoList();
	}
	private void setAd(){
		adView = (AdView)findViewById(R.id.adMob_todo); 
		// Lookup your LinearLayout assuming it�s been given
	    // the attribute android:id="@+id/mainLayout"
		
	    
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());


	}
	
	  @Override
	public void onDestroy() {
		  adView.destroy();
		todoDBAdapter.close();
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private void populateToDoList() {
		cursor = todoDBAdapter.getAllToDoItemCursor();
		startManagingCursor(cursor);

		String[] from = new String[] { ToDoDBAdapter.KEY_TASK };
		int[] to = new int[] { android.R.id.text1 };

		simpleCursorAdapter = new SimpleCursorAdapter(ToDo.this,
				android.R.layout.simple_list_item_1, cursor, from, to);

		listView.setAdapter(simpleCursorAdapter);
		updateList();
	}

	@SuppressWarnings("deprecation")
	private void updateList() {
		cursor.requery();
		simpleCursorAdapter.notifyDataSetChanged();

	}

	@SuppressLint("NewApi")
	private void addItem() {
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String newItem = noteInput.getText().toString();
				if (newItem.isEmpty() == false) {
					todoDBAdapter.insertToDoItem(newItem);
					showAddedToast();
					noteInput.setText("");
					InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

					inputManager.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					updateList();
				}
			}
		});
	}

	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode,
			Intent returnedIntent) {

		String changedItem = returnedIntent.getStringExtra("changedItem");
		int itemPos = returnedIntent.getIntExtra("pos", 0);

		if (resultCode == Config.DELETE_ITEM) {
			todoDBAdapter.removeToDoItem(itemPos);
			showDeleteToast();
		} else if (resultCode == Config.RESULT_OK) {
			if (changedItem.isEmpty()) {
				todoDBAdapter.removeToDoItem(itemPos);
			} else {
				cursor.moveToPosition(itemPos);
				todoDBAdapter.removeToDoItem(itemPos);
				todoDBAdapter.insertToDoItem(changedItem);
			}
		}
		updateList();
	}

	private void deleteItemLongClick() {

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> listView, View list,
					int pos, long arg3) {
				cursor.moveToPosition(pos);
				int itemPos = cursor.getInt(ToDoDBAdapter.COLUMN_KEY);
				todoDBAdapter.removeToDoItem(itemPos);
				showDeleteToast();
				updateList();

				return false;
			}
		});
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showItemDetails() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View list, int pos,
					long arg3) {
				Intent i = new Intent(ToDo.this, Details.class);

				int itemPos = cursor.getInt(ToDoDBAdapter.COLUMN_KEY);
				String chosenNote = cursor.getString(cursor
						.getColumnIndex(ToDoDBAdapter.KEY_TASK));
				i.putExtra("note", chosenNote);
				i.putExtra("pos", itemPos);
				startActivityForResult(i, Config.REQUEST_CODE);
			}
		});
	}

	private void showDeleteToast() {
		String text = "Notiz gelöscht";
		int duration = Toast.LENGTH_SHORT;
		Toast deleteToast = Toast.makeText(ToDo.this, text, duration);
		deleteToast.show();
	}

	private void showAddedToast() {
		String text = "Notiz hinzugefügt";
		int duration = Toast.LENGTH_SHORT;
		Toast addToast = Toast.makeText(ToDo.this, text, duration);
		addToast.show();
	}
	@Override
	public void onBackPressed(){
		Config.setStopAsyncs(true);
		super.onBackPressed();
	}
}
