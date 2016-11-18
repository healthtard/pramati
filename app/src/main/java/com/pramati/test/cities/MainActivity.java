package com.pramati.test.cities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Very basic Activity, the only things it does
 * are get the ListView reference from our layout.
 * Create an Adapter, set the Adapter to the ListView
 * and handle the onItemClick events for when the user
 * clicks on a row.
 */
public class MainActivity extends Activity implements  OnItemClickListener, TextWatcher {

	TxtAdapter mAdapter;
	private AutoCompleteTextView actv;
	private ListView mList;
	ArrayList<City> cities = new ArrayList<City>();
	final ArrayList<City> suggestions = new ArrayList<City>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadArrayFromFile();

		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

		//Lookup our ListView
		mList = (ListView) findViewById(R.id.mList);

		//Create Adapter. The second parameter is required by ArrayAdapter
		//which our Adapter extends. In this example though it is unused,
		//so we'll pass it a "dummy" value of -1.
		//mAdapter = new TxtAdapter(this, -1);

		mAdapter = new TxtAdapter(this, 0, cities);


		//attach our Adapter to the ListView. This will populate all of the rows.
		mList.setAdapter(mAdapter);

		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		actv.setThreshold(100);
		//actv.setAdapter(mAdapter);
		actv.addTextChangedListener(this);
		actv.setOnItemClickListener(this);

		
		/*
		 * This listener will get a callback whenever the user clicks on a row. 
		 * The pos parameter will tell us which row got clicked.
		 * 
		 * For now we'll just show a Toast with the state capital for the state that was clicked.
		 */
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Toast.makeText(v.getContext(), mAdapter.getItem(pos).getPopulation(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	/*
	 * Helper method that loads the data from the cities.csv and builds
	 * each csv row into a City object which then gets added to the Adapter.
	 */
	private void loadArrayFromFile() {
		try {
			// Get input stream and Buffered Reader for our data file.
			InputStream is = getAssets().open("cities.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;

			//Read each line
			while ((line = reader.readLine()) != null) {

				//Split to separate the name from the capital
				String[] RowData = line.split(",");

				//Create a City object for this row's data.
				City cur = new City();
				cur.setName(RowData[1]);
				try {
					if (RowData[4].isEmpty())
						cur.setPopulation("0");
					else
						cur.setPopulation(RowData[4]);
				} catch (Exception e) {

				}

				//Add the City object to the ArrayList (in this case we are the ArrayList).
				cities.add(cur);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Show Alert
		/*City selected = (City) arg0.getAdapter().getItem(arg2);
		Toast.makeText(MainActivity.this,
				"Clicked " + arg2 + " name: " + selected.getName(),
				Toast.LENGTH_SHORT).show();*/
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String input = s.toString();
		if(input.length() > 0){
			suggestions.clear();
			for (City p : cities) {
				if(p.getName().startsWith(input)){
					suggestions.add(p);

				}
			}
			mList.setAdapter(new TxtAdapter(this , R.layout.city_item , suggestions));

		}
		if(input.length() == 0){
			suggestions.clear();
			for (City p : cities) {
				if(p.getName().startsWith(input)){
					suggestions.add(p);

				}
			}
			mList.setAdapter(new TxtAdapter(this , R.layout.city_item , cities));

		}
		Log.i("test", s.toString());
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
