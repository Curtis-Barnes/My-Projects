package com.example.hmt22.pokemongointerface;

//
// https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
//

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final Integer[] imageIDArray; //populate first 5 with level indicator stars? Add 5 to position when used
    private final String[] nameArray;
    private final String[] infoArray;
    private final Integer[] members;
    private final Integer[] levels;

    public CustomListAdapter(Activity context, String[] nameArrayParam,
                             String[] infoArrayParam, Integer[] membersParam,
                             Integer[] imageIDArrayParam,
                             Integer[] levelsArrayparam){
        super(context, R.layout.list_item, nameArrayParam);

        this.context = context;
        this.imageIDArray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;
        this.members = membersParam;
        this.levels = levelsArrayparam;
        //hello

    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item,null, true);

        rowView.setTag();

        TextView raidTitle = (TextView)rowView.findViewById(R.id.listTitle);
        TextView raidDescription = (TextView)rowView.findViewById(R.id.listDescription);
        TextView membersIn = (TextView)rowView.findViewById(R.id.membersIn);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.listImage);
        RatingBar level = (RatingBar)rowView.findViewById(R.id.RaidLevel);

        raidTitle.setText(nameArray[position]);
        raidDescription.setText(infoArray[position]);
        membersIn.setText(members[position] + " Raiders");
        level.setRating(levels[position]);
        //imageView.setImageResource(imageIDArray[position]);

        return rowView;

    }
}
