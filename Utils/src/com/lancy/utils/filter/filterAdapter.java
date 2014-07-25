package com.lancy.utils.filter;

import java.util.List;

import com.lancy.utils.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class filterAdapter extends ArrayAdapter<User> {
	private LayoutInflater inflater;
	private Context context;

	public filterAdapter(Context context, int resource,
			List<User> objects) {
		super(context, resource,  objects);
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listviewitem, null, false);
		}
		
		
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if(holder == null){
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.itemtv);
			holder.age = (TextView) convertView.findViewById(R.id.agetv);
			holder.sex = (TextView) convertView.findViewById(R.id.sextv);
			convertView.setTag(holder);
		}
		User user = getItem(position);
		
		holder.name.setText(user.getName());
		holder.age.setText(user.getAge()+"");
		holder.sex.setText(user.getSex());
		
		return convertView;
	}
	 

	

	private static class ViewHolder {
		TextView name;
		TextView age;
		TextView sex;
	}


	
}
