package sg.edu.rp.c346.id20013327.lolmyprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btnView;
    Spinner spnRole;
    ListView lv;
    ArrayList<String> role;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<Champions> championList;
    CustomAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

//        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//        String name = prefs.getString("Name", );
//        String role = prefs.getRole("Role", );
//        int stars = prefs.getStar("Star", );

        DBHelper dbh = new DBHelper(this);
        championList.clear();
        championList.addAll(dbh.getAllChampions());
        adapter.notifyDataSetChanged();

        role.clear();
        role.addAll(dbh.getRole());
        spinnerAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = this.findViewById(R.id.lv);
        btnView = this.findViewById(R.id.btnView);
        spnRole = this.findViewById(R.id.spnnerRole);

        //To retrieve the data in the database
        DBHelper dbh = new DBHelper(this);
        championList = dbh.getAllChampions();
        role = dbh.getRole();
        dbh.close();

        //Create a adapter for listView
        adapter = new CustomAdapter(this, R.layout.row, championList);
        lv.setAdapter(adapter);

        //When user clicks on the listView, it will bring them to the third activity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class); //Create an intent to bring the user to the destinated class
                i.putExtra("champion", championList.get(position));
                startActivity(i);
            }
        });

        //When the user clicks on the button to view all champions with 5 star
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this); //
                championList.clear();
                championList.addAll(dbh.getAllChampionsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, role);
        spnRole.setAdapter(spinnerAdapter);

        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                championList.clear();
                championList.addAll(dbh.getAllChampionsByRole(String.valueOf(role.get(position))));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        tvLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String link = tvLink.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                startActivity(intent);
//            }
//        });



    }
}