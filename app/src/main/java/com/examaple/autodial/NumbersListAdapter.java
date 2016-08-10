package com.examaple.autodial;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by artem on 30.07.16.
 */
public class NumbersListAdapter extends BaseAdapter {
    Context context;
    List<String> numbers;
    HashMap<String, Integer> mIdMap = new HashMap<>();
    MainActivityFragment frag;
    int currPos = 0;
    private static LayoutInflater inflater = null;

    public NumbersListAdapter(Context context, List<String> numbers, MainActivityFragment frag) {
        this.numbers = numbers;
        this.context = context;
        this.frag = frag;

        int counter = 0;
        for (String number : numbers) {
            mIdMap.put(number, counter);
            counter++;
        }

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        String item = (String) getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.list_item_number, null);
        TextView tv = (TextView) rowView.findViewById(R.id.tv_number);
        ImageButton btnEdit = (ImageButton) rowView.findViewById(R.id.btn_edit);
        tv.setText(numbers.get(position));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked btnEdit", Toast.LENGTH_LONG).show();
            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.mCurrentNumber = (String) getItem(position);
                view.setBackgroundColor(Color.LTGRAY);
                view.setId(position);

                if (currPos != position) {
                    View lastView = parent.findViewById(currPos);
                    if (lastView != null){
                        lastView.setBackgroundColor(Color.WHITE);
                    }
                }

                currPos = position;

            }
        });
        return rowView;
    }

}


/*extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public NumbersListAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}*/
