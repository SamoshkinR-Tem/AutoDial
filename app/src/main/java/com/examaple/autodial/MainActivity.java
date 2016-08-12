package com.examaple.autodial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static Activity mApp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mApp == null) mApp = this;

//        //Test if we've been called to show phone call list
//        Intent _outcome = getIntent();
//        String _phoneCallAction = mApp.getResources().getString(R.string.main_show_phone_call_list);
//        String _reqAction = _outcome.getAction();//Can be null when no intent involved
//
//        //Decide if we return to the Phone Call List view
//        if (_reqAction != null && _reqAction.equals(_phoneCallAction) == true) {
//            //DO something to return to look and feel
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onDestroy() {
//        PreferenceManager
//                .getDefaultSharedPreferences(this)
//                .edit()
//                .putBoolean(MainActivityFragment.MAKE_CALL, false)
//                .apply();
//        super.onDestroy();
//    }
}


//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.ActivityCompat;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import android.view.View.OnClickListener;
//
//public class MainActivity extends Activity {
//    public static Activity mApp = null;
//
//    private Button callBtn;
//    private Button dialBtn;
//    private EditText number;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_2);
//
//        number = (EditText) findViewById(R.id.phoneNumber);
//        callBtn = (Button) findViewById(R.id.call);
//        dialBtn = (Button) findViewById(R.id.dial);
//        // add PhoneStateListener for monitoring
//        MyPhoneListener phoneListener = new MyPhoneListener();
//        TelephonyManager telephonyManager =
//                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        // receive notifications of telephony state changes
//        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//
//        callBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                try {
//                    // set the data
//                    String uri = "tel:" + number.getText().toString();
//                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
//                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
//                        return;
//                    startActivity(callIntent);
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Your call has failed...",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        dialBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    String uri = "tel:" + number.getText().toString();
//                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
//                    startActivity(dialIntent);
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Your call has failed...",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private class MyPhoneListener extends PhoneStateListener {
//        private boolean onCall = false;
//
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//            switch (state) {
//                case TelephonyManager.CALL_STATE_RINGING:
//                    // phone ringing...
//                    Toast.makeText(MainActivity.this, incomingNumber + " calls you",
//                            Toast.LENGTH_LONG).show();
//                    break;
//                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    // one call exists that is dialing, active, or on hold
//                    Toast.makeText(MainActivity.this, "on call...",
//                            Toast.LENGTH_LONG).show();
//                    //because user answers the incoming call
//                    onCall = true;
//                    break;
//                case TelephonyManager.CALL_STATE_IDLE:
//                    // in initialization of the class and at the end of phone call
//                    // detect flag from CALL_STATE_OFFHOOK
//                    if (onCall == true) {
//                        Toast.makeText(MainActivity.this, "restart app after call",
//                                Toast.LENGTH_LONG).show();
//                        // restart our application
//                        Intent restart = getBaseContext().getPackageManager().
//                                getLaunchIntentForPackage(getBaseContext().getPackageName());
//                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(restart);
//                        onCall = false;
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//}
