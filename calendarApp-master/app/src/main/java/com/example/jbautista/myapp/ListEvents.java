package com.example.jbautista.myapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryx.notes.Settings;

public class ListEvents extends ActionBarActivity implements AdapterView.OnItemClickListener{
String[] events;
ListView listView;
    public static int selectedNum;
    public static FragmentActivity mainactivity;
    public static int fragmentposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        String filename = "contentOfList"+(selectedNum+1);
        SharedPreferences contentOfListsharedPreferences=this.getSharedPreferences(filename, Context.MODE_PRIVATE);

        TextView titleTextView = (TextView)this.findViewById(R.id.titletextView);
        TextView dateTextView = (TextView)this.findViewById(R.id.datetextView);
        TextView timeTextView = (TextView)this.findViewById(R.id.timetextView);
        TextView descriptiontextView = (TextView)this.findViewById(R.id.descrirptiontextView);


        titleTextView.setText(contentOfListsharedPreferences.getString("title", ""));
        dateTextView.setText(contentOfListsharedPreferences.getString("date", ""));
        timeTextView.setText(contentOfListsharedPreferences.getString("time", ""));
        descriptiontextView.setText(contentOfListsharedPreferences.getString("description", ""));

        getActionBar().setDisplayHomeAsUpEnabled(true);
        mainactivity = this;
        //events = new String[]{"Christmas", "New Year"};
        /*listView = (ListView)findViewById(R.id.listEventsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListEvents.this, android.R.layout.simple_expandable_list_item_1, events);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(ListEvents.this); */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_events_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ListEvents.this, Settings.class);
            startActivity(intent);
        }
        if (id == R.id.calendar) {
            Intent intent = new Intent(ListEvents.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(ListEvents.this, events[position], Toast.LENGTH_SHORT).show();
        Log.i("onItemClick", events[position]);
    }
}
