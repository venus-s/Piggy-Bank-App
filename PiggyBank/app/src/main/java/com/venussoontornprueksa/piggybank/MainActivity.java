package com.venussoontornprueksa.piggybank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private int bankTotal;
    private TextView total;

    private View.OnClickListener plusListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            Intent intent = new Intent(v.getContext(), TransactionActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

//        SharedPreferences.Editor peditor = myPrefs.edit();
//        peditor.putInt("bankTotal", 0);
//        peditor.commit();

        Button transactionBtn = (Button) findViewById(R.id.button);
        transactionBtn.setOnClickListener(plusListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bankTotal = myPrefs.getInt("bankTotal", 0);
        total = (TextView) findViewById(R.id.total);
        total.setText(String.format("%01.02f", bankTotal / 100.0));
    }

//    I don't think we need this since this activity never changes the total?
//    @Override
//    protected void onPause() {
//
//        SharedPreferences.Editor peditor = myPrefs.edit();
//        peditor.putInt("bankTotal", bankTotal);
//        peditor.commit();
//
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//
//        SharedPreferences.Editor peditor = myPrefs.edit();
//        peditor.putInt("bankTotal", bankTotal);
//        peditor.commit();
//
//        super.onStop();
//    }
}
