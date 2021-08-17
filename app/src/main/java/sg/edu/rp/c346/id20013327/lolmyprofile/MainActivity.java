package sg.edu.rp.c346.id20013327.lolmyprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView ivThresh;
    TextView tvName, tvRole;
    EditText etName, etRole;
    RatingBar rbStar;
    Button btnAdd, btnView,btnWebsite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ivThresh = findViewById(R.id.ivThresh);
        tvName= findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);
        etName = findViewById(R.id.etName);
        etRole = findViewById(R.id.etRole);
        rbStar = findViewById(R.id.rbStar);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnWebsite = findViewById(R.id.btnWebsite);

        //To add the champions
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim(); //To get the user input for the name
                String role = etRole.getText().toString().trim(); //To get the user input for the role
                if(name.length() == 0 || role.length() == 0) { //Check if its empty
                    Toast.makeText(MainActivity.this, "Please enter both", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbh = new DBHelper(MainActivity.this); //To create the DBHelper object to pass in the activity content

                int rating = (int) rbStar.getRating();
                dbh.insertChampion(name, role, rating); //Insert the new champion into the database
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();

                etName.setText("");
                etRole.setText("");
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://www.riotgames.com/en";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String name = prefs.getString("Name", "Thresh");
        String role = prefs.getString("Role", "Support");
        int stars = prefs.getInt("Star", 5);
        etName.setText(name);
        etRole.setText(role);
        rbStar.setRating(stars);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String name = etName.getText().toString();
        String role = etRole.getText().toString();
        int stars = rbStar.getNumStars();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("Name", name); //Set the strName to what key
        prefsEdit.putString("Role", role); //Set the gpa to what key
        prefsEdit.putInt("Star", stars);
        prefsEdit.commit();
    }

}