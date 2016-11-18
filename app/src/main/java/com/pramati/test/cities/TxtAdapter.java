package com.pramati.test.cities;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/*
 * Very basic Custom Adapter that takes state name,capital pairs out of a csv
 * file from the assets and uses those values to build a List of City objects.
 * Overrides the default getView() method to return a TextView with the state name.
 * 
 * ArrayAdapter - a type of Adapter that works a lot like ArrayList.
 */
public class TxtAdapter extends ArrayAdapter<City>{
	Context ctx;
	ArrayList<City> cities;
	private static LayoutInflater inflater = null;
	private ListView mList;
	
	//We must accept the textViewResourceId parameter, but it will be unused
	//for the purposes of this example.
	public TxtAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		//Store a reference to the Context so we can use it to load a file from Assets.
		this.ctx = context;
		
		//Load the data.
		//loadArrayFromFile();
	}

	public TxtAdapter(MainActivity mainActivity, int textViewResourceId, ArrayList<City> cities) {
		super(mainActivity, android.R.layout.simple_list_item_1, cities);
		try {
			this.ctx = mainActivity;
			this.cities = cities;
			inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch(Exception e) {

		}

	}


	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		final ViewHolder holder;
		try {
			if (convertView == null) {
				vi = inflater.inflate(R.layout.city_item, null);
				holder = new ViewHolder();

				holder.city_name = (TextView) vi.findViewById(R.id.city_name);
				holder.city_population = (TextView) vi.findViewById(R.id.city_population);


				vi.setTag(holder);
			} else {
				holder = (ViewHolder) vi.getTag();
			}



			holder.city_name.setText(cities.get(position).getName());
			holder.city_population.setText(cities.get(position).getPopulation());


		} catch (Exception e) {


		}
		return vi;
	}

	public int getCount() {
		return cities.size();
	}


	public long getItemId(int position) {
		return position;
	}


	public static class ViewHolder {
		public TextView city_name;
		public TextView city_population;

	}


}
