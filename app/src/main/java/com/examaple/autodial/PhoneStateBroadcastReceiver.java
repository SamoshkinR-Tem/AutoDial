package com.examaple.autodial;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.List;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStateBroadcastRec";
    private static final String STATE = "STATE";

    Context mContext;
    String incoming_nr = "default";
    private int prev_state;

//    @Override
//    public void onReceive(Context arg0, Intent arg1) {
//
//        if(arg1.getAction().equals("android.intent.action.PHONE_STATE")){
//
//            String state = arg1.getStringExtra(TelephonyManager.EXTRA_STATE);
//
//            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
//                Log.d(TAG, "Inside Extra state off hook");
//                String number = arg1.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//                Log.e(TAG, "outgoing number : " + number);
//            }
//
//            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//                Log.e(TAG, "Inside EXTRA_STATE_RINGING");
//                String number = arg1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                Log.e(TAG, "incoming number : " + number);
//            }
//            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//                Log.d(TAG, "Inside EXTRA_STATE_IDLE");
//            }
//        }
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.v(TAG, "telephony.listen(customPhoneListener)");
//            CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); //TelephonyManager object

        int state = telephony.getCallState();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "CALL_STATE_RINGING==>");
                prefs.edit().putInt(STATE, state).apply();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "CALL_STATE_OFFHOOK==>");
                prefs.edit().putInt(STATE, state).apply();
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (prefs.getInt(STATE, TelephonyManager.CALL_STATE_IDLE) == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //Answered Call which is ended or call started by this phone
                    Log.d(TAG, "CALL_STATE_IDLE==> Answered Call ");
                    Intent _startMainActivity =
                            MainActivity.mApp
                                    .getPackageManager()
                                    .getLaunchIntentForPackage(
                                            MainActivity.mApp.getResources()
                                                    .getString(R.string.package_path));
                    _startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(_startMainActivity);
                }
                if (prefs.getInt(STATE, TelephonyManager.CALL_STATE_IDLE) == TelephonyManager.CALL_STATE_RINGING) {
                    //Rejected or Missed call
                    Log.d(TAG, "CALL_STATE_IDLE==> Rejected or Missed ");
                }
                break;
        }
//            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager
        Log.v(TAG, "telephony CallState:" + String.valueOf(telephony.getCallState()));

        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            Log.v(TAG, "phoneNr: " + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
        } else if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            Log.v(TAG, "phoneNr: " + this.getResultData());
        }
    }

    /* Custom PhoneStateListener */
    public class CustomPhoneStateListener extends PhoneStateListener {
        private boolean onCall = false;


        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            super.onCellInfoChanged(cellInfo);
//            Log.i(TAG, "onCellInfoChanged: " + cellInfo);
        }

        @Override
        public void onDataActivity(int direction) {
            super.onDataActivity(direction);
            switch (direction) {
                case TelephonyManager.DATA_ACTIVITY_NONE:
//                    Log.i(TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                    break;
                case TelephonyManager.DATA_ACTIVITY_IN:
//                    Log.i(TAG, "onDataActivity: DATA_ACTIVITY_IN");
                    break;
                case TelephonyManager.DATA_ACTIVITY_OUT:
//                    Log.i(TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                    break;
                case TelephonyManager.DATA_ACTIVITY_INOUT:
//                    Log.i(TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                    break;
                case TelephonyManager.DATA_ACTIVITY_DORMANT:
//                    Log.i(TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                    break;
                default:
//                    Log.w(TAG, "onDataActivity: UNKNOWN " + direction);
                    break;
            }
        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
//            Log.i(TAG, "onServiceStateChanged: " + serviceState.toString());
//            Log.i(TAG, "onServiceStateChanged: getOperatorAlphaLong "
//                    + serviceState.getOperatorAlphaLong());
//            Log.i(TAG, "onServiceStateChanged: getOperatorAlphaShort "
//                    + serviceState.getOperatorAlphaShort());
//            Log.i(TAG, "onServiceStateChanged: getOperatorNumeric "
//                    + serviceState.getOperatorNumeric());
//            Log.i(TAG, "onServiceStateChanged: getIsManualSelection "
//                    + serviceState.getIsManualSelection());
//            Log.i(TAG,
//                    "onServiceStateChanged: getRoaming "
//                            + serviceState.getRoaming());

            switch (serviceState.getState()) {
                case ServiceState.STATE_IN_SERVICE:
//                    Log.i(TAG, "onServiceStateChanged: STATE_IN_SERVICE");
                    break;
                case ServiceState.STATE_OUT_OF_SERVICE:
//                    Log.i(TAG, "onServiceStateChanged: STATE_OUT_OF_SERVICE");
                    break;
                case ServiceState.STATE_EMERGENCY_ONLY:
//                    Log.i(TAG, "onServiceStateChanged: STATE_EMERGENCY_ONLY");
                    break;
                case ServiceState.STATE_POWER_OFF:
//                    Log.i(TAG, "onServiceStateChanged: STATE_POWER_OFF");
                    break;
            }
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
//            String _prefKey = MainActivity.mApp
//                    .getResources().getString(R.string.last_phone_call_state_key),
//                    _lastNumber = MainActivity.mApp
//                            .getResources().getString(R.string.last_phone_call_bparty_key);
//
//            if (incomingNumber != null && incomingNumber.length() > 0) {
//                incoming_nr = incomingNumber;
//                //Save current call sate for next call
//                mPrefsEditor.putString(_lastNumber, incomingNumber);
//                mPrefsEditor.commit();
//            }

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(TAG, "CALL_STATE_RINGING==>" + incoming_nr);
                    prev_state = state;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d(TAG, "CALL_STATE_OFFHOOK==>" + incoming_nr);
                    prev_state = state;
                    onCall = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)) {
                        prev_state = state;
                        //Answered Call which is ended
                        Log.d(TAG, "CALL_STATE_IDLE==> Answered Call " + incoming_nr);
                    }
                    if ((prev_state == TelephonyManager.CALL_STATE_RINGING)) {
                        prev_state = state;
                        //Rejected or Missed call
                        Log.d(TAG, "CALL_STATE_IDLE==> Rejected or Missed " + incoming_nr);
                    }

//                    Intent _startMainActivity =
//                            MainActivity.mApp
//                                    .getPackageManager()
//                                    .getLaunchIntentForPackage(
//                                            MainActivity.mApp.getResources()
//                                                    .getString(R.string.package_path));
//                    _startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(_startMainActivity);

                    onCall = false;

                    break;

            }
        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
            if (location instanceof GsmCellLocation) {
                GsmCellLocation gcLoc = (GsmCellLocation) location;
//                Log.i(TAG,
//                        "onCellLocationChanged: GsmCellLocation "
//                                + gcLoc.toString());
//                Log.i(TAG, "onCellLocationChanged: GsmCellLocation getCid "
//                        + gcLoc.getCid());
//                Log.i(TAG, "onCellLocationChanged: GsmCellLocation getLac "
//                        + gcLoc.getLac());
//                Log.i(TAG, "onCellLocationChanged: GsmCellLocation getPsc"
//                        + gcLoc.getPsc()); // Requires min API 9
            } else if (location instanceof CdmaCellLocation) {
                CdmaCellLocation ccLoc = (CdmaCellLocation) location;
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation "
//                                + ccLoc.toString());
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation getBaseStationId "
//                                + ccLoc.getBaseStationId());
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
//                                + ccLoc.getBaseStationLatitude());
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
//                                + ccLoc.getBaseStationLongitude());
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation getNetworkId "
//                                + ccLoc.getNetworkId());
//                Log.i(TAG,
//                        "onCellLocationChanged: CdmaCellLocation getSystemId "
//                                + ccLoc.getSystemId());
            } else {
//                Log.i(TAG, "onCellLocationChanged: " + location.toString());
            }
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean cfi) {
            super.onCallForwardingIndicatorChanged(cfi);
//            Log.i(TAG, "onCallForwardingIndicatorChanged: " + cfi);
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean mwi) {
            super.onMessageWaitingIndicatorChanged(mwi);
//            Log.i(TAG, "onMessageWaitingIndicatorChanged: " + mwi);
        }
    }
}