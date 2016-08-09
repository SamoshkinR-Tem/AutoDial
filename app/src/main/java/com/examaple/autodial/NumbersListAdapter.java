package com.examaple.autodial;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by artem on 30.07.16.
 */
public class NumbersListAdapter extends ArrayAdapter<String> {

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

}
