package patrick.morczinietz.abicalculatorbayern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class Details extends Activity {
	private static final String EMPTY_STRING = "";
	private Button deleteButton;
	private Button okButton;
	private EditText editDetail;
	private int itemPos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        setupUI();
        showNote();     
        deleteItem();
        setItem();
    }

   
    private void setupUI(){

		Intent i = getIntent();
    
    	itemPos = i.getIntExtra("pos", 1);
    	editDetail = (EditText)findViewById(R.id.editNote);
    	deleteButton = (Button)findViewById(R.id.deleteButton);
    	okButton = (Button)findViewById(R.id.okButton);

    }
    private void showNote(){
    	Intent i = getIntent();
    	String note = i.getStringExtra("note");
    	editDetail.setText(note);
    }
    private void editItem(){
    	
    	String changedItem =  editDetail.getText().toString();
    	Intent returnIntent = new Intent(Details.this, ToDo.class);
    
    	if(changedItem.isEmpty() == false){
	    	returnIntent.putExtra("pos", itemPos);
	      	returnIntent.putExtra("changedItem", changedItem );
	    	setResult(Config.RESULT_OK, returnIntent);
    	
    	}
    }
    @Override
    public void onBackPressed(){
    	String changedItem =  editDetail.getText().toString();
    
    	Intent returnIntent = new Intent(Details.this, ToDo.class);
    	if(changedItem.isEmpty() == false){
	    	returnIntent.putExtra("pos", itemPos);
	      	returnIntent.putExtra("changedItem", changedItem );
	    	setResult(Config.RESULT_OK, returnIntent);
    	}
    	else{
    		setResult(Config.DELETE_ITEM, returnIntent);
    	}
    	
    	super.onBackPressed();
    	
    }
    private void deleteItem(){
    	deleteButton.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				editDetail.setText(EMPTY_STRING);
				Intent returnIntent = new Intent(Details.this, ToDo.class);
				returnIntent.putExtra("pos", itemPos);
				setResult(Config.DELETE_ITEM, returnIntent);
				finish();		
			}
		});   	
    }
    private void setItem(){
    	okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				editItem();
				finish();
				
			}
		});
    }  
    
}
