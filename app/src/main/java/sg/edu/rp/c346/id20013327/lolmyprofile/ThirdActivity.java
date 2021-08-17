package sg.edu.rp.c346.id20013327.lolmyprofile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName,etRole;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + "Modify Champion Details");

        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etName = findViewById(R.id.etName);
        etRole = findViewById(R.id.etRole);
        ratingBar = findViewById(R.id.ratingbarStars);

        Intent i = getIntent();
        final Champions currentCham = (Champions) i.getSerializableExtra("champion");

        etID.setText(currentCham.getId() + "");
        etName.setText(currentCham.getName());
        etRole.setText(currentCham.getRole());
        ratingBar.setRating(currentCham.getStar());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentCham.setName(etName.getText().toString().trim());
                currentCham.setRole(etRole.getText().toString().trim());
//                int square = 0;
//                try {
//                    square = Integer.valueOf(etSquare.getText().toString().trim());
//                } catch (Exception e) {
//                    Toast.makeText(ThirdActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                currentSong.setSquare(square);

                //int selectedRB = rg.getCheckedRadioButtonId();
                //RadioButton rb = (RadioButton) findViewById(selectedRB);
                //currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                currentCham.setStar((int) ratingBar.getRating());
                int result = dbh.updateChampion(currentCham);
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Champions updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View viewDialog = inflater.inflate(R.layout.input, null);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
//                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the Champion? \nChampion: " + currentCham.getName() + "\nRole: " + currentCham.getRole());
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteSong(currentCham.getId());
                        if (result>0){
                            Toast.makeText(ThirdActivity.this, "Champion deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ThirdActivity.this, MainActivity.class);
                        Toast.makeText(ThirdActivity.this, "Cancel Delete", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View viewDialog = inflater.inflate(R.layout.input,null);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
//                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Do not Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        etIsland.setText("");
                    }
                });

                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}