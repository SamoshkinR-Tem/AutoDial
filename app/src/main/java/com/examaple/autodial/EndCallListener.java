package com.examaple.autodial;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by artem on 30.07.16.
 */
public class EndCallListener extends PhoneStateListener {

    private String TAG ="EndCallListener";
    private int     LAUNCHED = -1;

    SharedPreferences mPrefs = PreferenceManager
            .getDefaultSharedPreferences(
                    MainActivity.mApp.getBaseContext());

    SharedPreferences.Editor mPrefsEditor = mPrefs.edit();

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        String _prefKey = MainActivity.mApp
                .getResources().getString(R.string.last_phone_call_state_key),
                _bPartyNumber = MainActivity.mApp
                        .getResources().getString(R.string.last_phone_call_bparty_key);

        int mLastCallState = mPrefs.getInt(_prefKey, LAUNCHED);

        //Save current call sate for next call
        mPrefsEditor.putInt(_prefKey,state);
        mPrefsEditor.commit();

        if(TelephonyManager.CALL_STATE_RINGING == state) {
            Log.i(TAG, " >> RINGING, number: " + incomingNumber);
        }
        if(TelephonyManager.CALL_STATE_IDLE == state && mLastCallState != LAUNCHED ) {
            //when this state occurs, and your flag is set, restart your app

//            if (incomingNumber.equals(_bPartyNumber) == true) {
//                //Call relates to last app initiated call
//                Intent _startMainActivity =
//                        MainActivity.mApp
//                                .getPackageManager()
//                                .getLaunchIntentForPackage(
//                                        MainActivity.mApp.getResources()
//                                                .getString(R.string.figjam_package_path));
//
//                _startMainActivity.setAction(
//                        MainActivity.mApp.getResources()
//                                .getString(R.string.main_show_phone_call_list));
//
//                MainActivity.mApp
//                        .startActivity(_startMainActivity);
//                Log.i(TAG, "IDLE >> Starting MainActivity with intent");
//            }
//            else
//                Log.i(TAG, "IDLE after calling "+incomingNumber);

        }

    }
}