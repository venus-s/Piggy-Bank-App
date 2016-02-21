package com.venussoontornprueksa.piggybank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class TransactionActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private int transactionAmount;
    private TextView transactionView;
    private Stack<Integer> transactionStack;

    private View.OnClickListener depositListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            final String depositAmount = transactionView.getText().toString();
            new AlertDialog.Builder(v.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Deposit Amount")
                    .setMessage("Deposit $" + depositAmount + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            performTransaction(Double.parseDouble(depositAmount));
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    };

    private View.OnClickListener withdrawListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            final String withdrawAmount = transactionView.getText().toString();
            new AlertDialog.Builder(v.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Withdraw Amount")
                    .setMessage("Withdraw $" + withdrawAmount + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            performTransaction(-Double.parseDouble(withdrawAmount));
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    };

    private View.OnClickListener pennyListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(1);
        }
    };

    private View.OnClickListener nickelListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(5);
        }
    };

    private View.OnClickListener dimeListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(10);
        }
    };

    private View.OnClickListener quarterListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(25);
        }
    };

    private View.OnClickListener oneDollarListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(100);
        }
    };

    private View.OnClickListener fiveDollarListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(500);
        }
    };

    private View.OnClickListener tenDollarListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(1000);
        }
    };

    private View.OnClickListener twentyDollarListener = new View.OnClickListener()  {
        public void onClick(View v)  {
            updateTransactionAmount(2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Context context = getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Set all money buttons
        Button penny = (Button) findViewById(R.id.pennyBtn);
        penny.setOnClickListener(pennyListener);
        Button nickel = (Button) findViewById(R.id.nickelBtn);
        nickel.setOnClickListener(nickelListener);
        Button dime = (Button) findViewById(R.id.dimeBtn);
        dime.setOnClickListener(dimeListener);
        Button quarter = (Button) findViewById(R.id.quarterBtn);
        quarter.setOnClickListener(quarterListener);
        Button oneDollar = (Button) findViewById(R.id.oneDollarBtn);
        oneDollar.setOnClickListener(oneDollarListener);
        Button fiveDollars = (Button) findViewById(R.id.fiveDollarBtn);
        fiveDollars.setOnClickListener(fiveDollarListener);
        Button tenDollars = (Button) findViewById(R.id.tenDollarBtn);
        tenDollars.setOnClickListener(tenDollarListener);
        Button twentyDollars = (Button) findViewById(R.id.twentyDollarBtn);
        twentyDollars.setOnClickListener(twentyDollarListener);

        // Withdraw, and Deposit buttons
        Button withdraw = (Button) findViewById(R.id.withdrawBtn);
        withdraw.setOnClickListener(withdrawListener);
        Button deposit = (Button) findViewById(R.id.depositBtn);
        deposit.setOnClickListener(depositListener);

        transactionStack = new Stack<>();
        transactionAmount = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        transactionView = (TextView) findViewById(R.id.transactionTotal);
        transactionView.setText(String.format("%01.02f", transactionAmount / 100.0));
    }

    private void updateTransactionAmount(int amount) {
        transactionStack.push(amount);
        transactionAmount += amount;
        transactionView.setText(String.format("%01.02f", transactionAmount / 100.0));
    }

    public void undo(View v) {
        if(!transactionStack.isEmpty()) {
            transactionAmount -= transactionStack.pop();
            transactionView.setText(String.format("%01.02f", transactionAmount / 100.0));
        }
    }

    private void performTransaction(double amount) {
        int bankTotal = myPrefs.getInt("bankTotal", 0) + (int) (amount * 100.00);
        if(bankTotal >= 0) {
            SharedPreferences.Editor peditor = myPrefs.edit();
            peditor.putInt("bankTotal", bankTotal);
            peditor.commit();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You can't withdraw more than you have!", Toast.LENGTH_LONG);
            toast.show();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
