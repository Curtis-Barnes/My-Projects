package com.example.hmt22.pokemongointerface;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourRaidsTabFragment extends Fragment {

    /*
    ArrayList<String> raids = new ArrayList<String>();
    ArrayList<String> raidDescriptions = new ArrayList<String>();
    ArrayList<Integer> numRaiders = new ArrayList<Integer>();
    ArrayList<Integer> images = new ArrayList<Integer>();
    ListView listView;
    */

    public YourRaidsTabFragment() {
        // Required empty public constructor
    }

    /*
    public void addToYourRaids(String name, String des, Integer raiders, Integer img){
        raids.add(name);
        raidDescriptions.add(des);
        numRaiders.add(raiders);
        images.add(img);

        return;
    }
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        String[] pr = raids.toArray(new String[raids.size()]);
        String[] pd = raidDescriptions.toArray(new String[raidDescriptions.size()]);
        Integer[] pm = numRaiders.toArray(new Integer[numRaiders.size()]);
        Integer[] pi = images.toArray(new Integer[images.size()]);


        CustomListAdapter listAdapter = new CustomListAdapter(this.getActivity(),pr, pd, pm, pi);
        listView = (ListView) getView().findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        */

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_raids_tab, container, false);


    }

}
