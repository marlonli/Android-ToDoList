package com.example.jingyuan.todolist;

import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText etitle;
    private EditText eSub;
    private List<Task> list = new ArrayList<>();
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("Activity State", "onCreate!");
        listView = (ListView)findViewById(R.id.listView);
        etitle = (EditText)findViewById(R.id.editTitle);
        eSub = (EditText) findViewById(R.id.editSub);
        final Button button = (Button) findViewById(R.id.button);

        // Initialize adapter
        adapter = new myAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);

        // Button onClick Listener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                addItemToList();
                try {
                    addItemToFile(list);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("MainActivity", " button clicked: ADD");
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                list.remove(pos);
                adapter.notifyDataSetChanged();

                Log.v("MainActivity"," long clicked, pos: " + pos + " " + id);

                return true;
            }
        });

//        createProcessBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Activity State", "Saving...");
        // Implemented saving instance state, can save and restore list items

//        for (int i = 0; i < list.size(); i++) {
//            outState.putSerializable("list" + i, list.get(i));
//        }
//
//        outState.putInt("size", list.size());

    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        Log.v("Activity State", "Restoring");

        // Implemented saving instance state, can save and restore list items
//        int size = inState.getInt("size");
//        for (int i = 0; i < size; i++) {
//            list.add((Task) inState.getSerializable("list" + i));
//        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Activity State", "onDestroy!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        readFileContent();
        adapter.notifyDataSetChanged();
        Log.v("Activity State", "onResume!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            addItemToFile(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Activity State", "onStop!");
    }

    // Unused
    private void createProcessBar() {
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
    }


    private void addItemToList() {
        String input1 = etitle.getText().toString();
        String input2 = eSub.getText().toString();
        if(input1.length() > 0)
        {
            Task item = new Task(input1, input2);
            list.add(item);
            adapter.notifyDataSetChanged();
        }
    }

    private void addItemToFile(List<Task> list) throws IOException {

        File dir = getFilesDir ();
        String dirname = dir.toString();
        FileOutputStream fos = new FileOutputStream(dirname + "/task.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (Task t : list) {
            oos.writeObject(t);
            Log.v("MainActivity", "Writing to file: " + dirname + "/task.txt");
        }

        oos.flush();
        oos.close();

    }

    private void readFileContent() {
        try {
            File dir = getFilesDir ();
            String dirname = dir.toString();
            Log.v("Loading File DIR name", dirname);
            FileInputStream fis=new FileInputStream(dirname + "/task.txt");
            ObjectInputStream ois=new ObjectInputStream(fis);
//            Task item;
            Log.v("MainActivity", "Loading file...");
            while (true) {
                Task item = (Task) ois.readObject();
                if (item == null) break;
                list.add(item);
            }
            adapter.notifyDataSetChanged();
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("Exception", "FileNotFound when resumed");
        } catch (IOException e) {
            e.printStackTrace();

            Log.v("IOException", "Read Exception");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // To delete the text file (unused)
    public static void deletefile(String fileName) {
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
