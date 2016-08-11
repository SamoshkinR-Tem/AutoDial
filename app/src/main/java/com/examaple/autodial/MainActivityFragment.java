package com.examaple.autodial;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";
    private static final String NUMBERS = "NUMBERS";

    public String mCurrentNumber;
    List<Number> mNumbersList;
    Set<String> mNumbersSet;
    NumbersListAdapter mAdapter;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mPrefsEditor;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        mNumbersList = new ArrayList<>();
        mNumbersSet = mPrefs.getStringSet(NUMBERS, new HashSet<String>());

        for (String s : mNumbersSet) {
            mNumbersList.add(new Number(s));
        }

        ListView lvPhoneNumbers = (ListView) view.findViewById(R.id.lv_phone_numbers);
        mAdapter = new NumbersListAdapter(getContext(), mNumbersList, this);
        lvPhoneNumbers.setAdapter(mAdapter);

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

    public void showEditNumberDialog(final int position) {
        // custom dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_number);

        // set the custom dialog components - text, image and button
        final EditText etNewNumber = (EditText) dialog.findViewById(R.id.et_new_number);
        if (position != -1)
            etNewNumber.setText(mNumbersList.get(position).getNumber());

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != -1) {
                    mNumbersSet.remove(mNumbersList.get(position).getNumber());
                    mNumbersList.get(position).setNumber(etNewNumber.getText().toString());
                } else {
                    Number tmpNum = new Number(etNewNumber.getText().toString());
                    if (!mNumbersList.contains(tmpNum))
                        mNumbersList.add(tmpNum);
                    mAdapter.notifyDataSetChanged();
                }
                mNumbersSet.add(etNewNumber.getText().toString());
                mPrefs.edit().putStringSet(NUMBERS, mNumbersSet).apply();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // 0939152543 Vitaliy Holovko

    public void call(View v) {
        Log.d(TAG, "call");

        if (mCurrentNumber == null) {
            Toast toast = Toast.makeText(getContext(), "Check phone number", Toast.LENGTH_LONG);
            toast.show();
        } else {

            Intent _startMainActivity =
                    MainActivity.mApp
                            .getPackageManager()
                            .getLaunchIntentForPackage(
                                    MainActivity.mApp.getResources()
                                            .getString(R.string.package_path));

            _startMainActivity.setAction(Intent.ACTION_CALL);
            _startMainActivity.setData(Uri.parse("tel:" + mCurrentNumber));

            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mCurrentNumber)));
        }
    }

//    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//            .setTicker(tickerText)
//            .setColor(Color.BLACK)
//            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_directions_bus_white_48dp))
//            .setSmallIcon(R.mipmap.ic_directions_bus_white_24dp)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .addAction(new NotificationCompat.Action(R.mipmap.ic_directions_bus_white_24dp,"Call to " + number,pendingIntentForCall));

    class Number {
        private String number;

        public Number(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Number)) return false;

            Number number1 = (Number) o;

            return getNumber() != null ? getNumber().equals(number1.getNumber()) : number1.getNumber() == null;

        }

        @Override
        public int hashCode() {
            return getNumber() != null ? getNumber().hashCode() : 0;
        }
    }
}
