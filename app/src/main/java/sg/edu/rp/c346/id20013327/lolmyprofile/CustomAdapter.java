package sg.edu.rp.c346.id20013327.lolmyprofile;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Champions> champions;

    public CustomAdapter(Context context, int resource, ArrayList<Champions> objects) {
        super(context, resource, objects);
        this.parent_context = context;
        this.layout_id = resource;
        this.champions = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        //Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvRole = rowView.findViewById(R.id.tvRole);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        RatingBar ratingBar = rowView.findViewById(R.id.ratingBar);
        TextView tvLink = rowView.findViewById(R.id.tvLink);

        //To obtain the Android version information based on the position
        Champions currentCham = champions.get(position);

        //Set the current champion to the text
        tvName.setText(currentCham.getName());
        tvRole.setText(currentCham.getRole());
        ratingBar.setRating(currentCham.getStar());
        tvLink.setText("");

        if(currentCham.getName().equalsIgnoreCase("Thresh")) {
            imageView.setImageResource(R.drawable.thresh);
            tvLink.setText("https://www.youtube.com/results?search_query=top+thresh+plays+2021");
        } else if (currentCham.getName().equalsIgnoreCase("Zyra")){
            imageView.setImageResource(R.drawable.zyra);
            tvLink.setText("https://www.youtube.com/watch?v=gZjOOHvAYiA&ab_channel=LOLSPACE");
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
        return rowView;
    }
}
