package com.ryx.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jbautista.myapp.ListEvents;
import com.example.jbautista.myapp.MainActivity;
import com.example.jbautista.myapp.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AddEvent extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    String[] titles;
    int numberOfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        listview = (ListView)findViewById(R.id.addEventListView);
        CustomAdapter customAdapter = new CustomAdapter();
        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener(AddEvent.this);
        SharedPreferences sharedPreferences=getSharedPreferences("noOfList", Context.MODE_PRIVATE);
        numberOfList=sharedPreferences.getInt("numList", 0);
    }

    private class CustomAdapter extends BaseAdapter {
        private static final int titleItem = 0;
        private static final int dateItem = 1;
        private static final int timeItem = 2;
        private static final int descriptionItem = 3;
        private static final int buttonItem = 4;

        private LayoutInflater mInflater;
        ArrayList<String> list;

        public CustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = new ArrayList<String>();
            titles = new String[]{"title", "date", "time", "description", "Buttons"};
            for(int i=0 ; i<5; i++){
                list.add(titles[i]);
            }
        }

        public int getViewTypeCount() {
            return list.size();
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            //related to database
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parents){
            ViewHolder holder=null;

            if(convertView==null){
                holder = new ViewHolder();
                if(position==titleItem){
                    convertView = mInflater.inflate(R.layout.titlerow, null);
                    TextView title = (TextView)convertView.findViewById(R.id.titletextView);
                    title.setText("Input the title");

                    //android:textAppearance="?android:attr/textAppearanceSmall"
                } else
                if(position==dateItem){
                    convertView = mInflater.inflate(R.layout.daterow, null);
                    TextView title = (TextView)convertView.findViewById(R.id.datetextView);
                    title.setText("Input the date");
                } else
                if(position==timeItem){
                    convertView = mInflater.inflate(R.layout.timerow, null);
                    TextView title = (TextView)convertView.findViewById(R.id.timetextView);
                    title.setText("Input the time");
                } else
                if(position==descriptionItem){
                    convertView = mInflater.inflate(R.layout.descriptionrow, null);
                    TextView title = (TextView)convertView.findViewById(R.id.descrirptiontextView);
                    title.setText("Input the description");
                }else
                if(position==buttonItem){
                    convertView = mInflater.inflate(R.layout.add_event, null);
                    Button addButton = (Button)convertView.findViewById(R.id.addEventButton);
                    addButton.setWidth((convertView.getWidth()/2)-6);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView title = (TextView)findViewById(R.id.titletextView);
                            TextView date = (TextView)findViewById(R.id.datetextView);
                            TextView time = (TextView)findViewById(R.id.timetextView);
                            TextView description = (TextView)findViewById(R.id.descrirptiontextView);

                            String titleString = String.valueOf(title.getText());
                            String dateString = String.valueOf(date.getText());
                            String timeString = String.valueOf(time.getText());
                            String descriptionString = String.valueOf(description.getText());

                            //Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();

                            if(!(titleString.isEmpty())){
                                SharedPreferences readsharedPreferences=getSharedPreferences("noOfList", Context.MODE_PRIVATE);
                                numberOfList=(readsharedPreferences.getInt("numList", 0))+1;

                                SharedPreferences.Editor editor = getSharedPreferences("noOfList", Context.MODE_PRIVATE).edit();
                                editor.putInt("numList", numberOfList);
                                editor.commit();

                                String contentOfListFileName = "contentOfList"+numberOfList;
                                Log.d("contentOfListFileName ", "" + contentOfListFileName);
                                SharedPreferences.Editor editorData = getSharedPreferences(contentOfListFileName, Context.MODE_PRIVATE).edit();
                                editorData.putString("title", titleString);
                                editorData.putString("date", dateString);
                                editorData.putString("time", timeString);
                                editorData.putString("description", descriptionString);
                                editorData.commit();

                                SharedPreferences sharedPreferences=getSharedPreferences("noOfList", Context.MODE_PRIVATE);
                                numberOfList=sharedPreferences.getInt("numList", 0);
                                Log.d("Current number of list: ", "" + numberOfList);
                                //ListEvents.mainactivity.finish();
                                AddEvent.this.finish();
                                Intent intent = new Intent(AddEvent.this, ListEvents.class);
                                startActivity(intent);
                                //ListEvents.fragmentposition=1;
                            }

                            else {
                                Toast.makeText(AddEvent.this, "Input the subject name ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }


            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        public class ViewHolder{
            TextView text;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(AddEvent.this, Settings.class);
            startActivity(intent);
        }
        if (id == R.id.calendar) {
            Intent intent = new Intent(AddEvent.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "You selected : " + titles[position], Toast.LENGTH_SHORT).show();
        if(position==0){
            Log.d("K", "Title");
            //Intent intent = new Intent(addNotesFragment.this, subjectAss.class);
            //startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            builder.setTitle("Title");

            // Set up the input
            final EditText input = new EditText(AddEvent.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        TextView description = (TextView)findViewById(R.id.descrirptiontextView);
        description.setText(input.getText());
        }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        }
        });

        builder.show();
        }
        /*
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    TextView description = (TextView)findViewById(R.id.titletextView);
                    description.setText(input.getText());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            }*/


        if(position==1){
            //Intent intent = new Intent(addNotesFragment.this, subjectAss.class);
            //startActivity(intent);
            Log.d("K", "Date");
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            builder.setTitle("Date");

            // Set up the input
            final DatePicker input = new DatePicker(AddEvent.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setCalendarViewShown(false);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                String month=null;
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    monthConverter();
                    TextView description = (TextView)findViewById(R.id.datetextView);
                    description.setText(month+" "+input.getDayOfMonth()+" "+input.getYear());
                }
                private void monthConverter(){
                    int m = input.getMonth();
                    switch (m){
                        case 0:
                            month=new String("January");
                            break;
                        case 1:
                            month=new String("February");
                            break;
                        case 2:
                            month=new String("March");
                            break;
                        case 3:
                            month=new String("April");
                            break;
                        case 4:
                            month=new String("May");
                            break;
                        case 5:
                            month=new String("June");
                            break;
                        case 6:
                            month=new String("July");
                            break;
                        case 7:
                            month=new String("August");
                            break;
                        case 8:
                            month=new String("September");
                            break;
                        case 9:
                            month=new String("October");
                            break;
                        case 10:
                            month=new String("November");
                            break;
                        case 11:
                            month=new String("December");
                            break;
                        default:
                            month =new String ("Invalid month");
                            break;
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else
        if(position==2){
            Log.d("ASS", "Time");
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            builder.setTitle("Time");

            // Set up the input
            final TimePicker input = new TimePicker(AddEvent.this);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("Time = ",""+input.getCurrentHour()+":"+input.getCurrentMinute()+" "+input.getBaseline());
                    TextView description=(TextView)findViewById(R.id.timetextView);
                    description.setText(input.getCurrentHour()+":"+input.getCurrentMinute());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        if(position==3){
            //Intent intent = new Intent(addNotesFragment.this, subjectAss.class);
            //startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
            builder.setTitle("Description");

            // Set up the input
            final EditText input = new EditText(AddEvent.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView description = (TextView)findViewById(R.id.descrirptiontextView);
                    description.setText(input.getText());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }
}
