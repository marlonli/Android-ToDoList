package com.example.jingyuan.todolist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jingyuan on 9/26/17.
 */

public class myAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Task> Items;

    public myAdapter(Activity activity, List<Task> Items) {
        this.activity = activity;
        this.Items = Items;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_items, null);

        TextView title = (TextView) convertView.findViewById(R.id.text1);
        TextView content = (TextView) convertView.findViewById(R.id.text2);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        final Task listitem = Items.get(position);

        title.setText(listitem.getTitle());
        content.setText(listitem.getContent());

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Items.remove(position);
                if(checkBox.isChecked())
                    checkBox.setChecked(false);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
