package patrick.morczinietz.abicalculatorbayern;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SchoolAdapter extends ArrayAdapter<School> {

	private List<School> schoolList;
	private Context context;

	public SchoolAdapter(Context context, List<School> objects) {
		super(context, R.layout.cell, objects);
		this.context = context;
		schoolList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cell, null);
		}
			
		School s = schoolList.get(position);
		
		if (s != null) {

			TextView stadt = (TextView) v.findViewById(R.id.stadt);
			TextView name = (TextView) v.findViewById(R.id.name);
			

			stadt.setText(s.getStadt());
			name.setText(s.getName());
			
		}

		return v;
	}
}
