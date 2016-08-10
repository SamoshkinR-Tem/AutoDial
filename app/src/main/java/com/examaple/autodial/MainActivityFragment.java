package com.examaple.autodial;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";

    public String mCurrentNumber;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ArrayList<String> numbers = new ArrayList<String>();
        numbers.add("+380930001122");
        numbers.add("+380633111444");

        ListView lvPhoneNumbers = (ListView) view.findViewById(R.id.lv_phone_numbers);
        final NumbersListAdapter adapter = new NumbersListAdapter(getContext(), numbers, this);
        lvPhoneNumbers.setAdapter(adapter);

//        lvPhoneNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                mCurrentNumber = (String) parent.getItemAtPosition(position);
//                view.setBackgroundColor(Color.LTGRAY);
//                view.setId(position);
//            }
//
//        });

        Button btnCall = (Button) view.findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(view);
            }
        });

        return view;
    }

    public void call(View v) {
        Log.d(TAG, "call");

        if (mCurrentNumber == null){
            Toast toast = Toast.makeText(getContext(), "Check phone namber", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+mCurrentNumber));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }
}
