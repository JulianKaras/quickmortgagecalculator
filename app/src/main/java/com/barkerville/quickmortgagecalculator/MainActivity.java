package com.barkerville.quickmortgagecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final String PRINCIPAL = "PRINCIPAL";
    private static final String INTEREST_RATE = "INTEREST_RATE";
    private static final String NUMBER_OF_YEARS = "NUMBER_OF_YEARS";
    private static final String MONTHLY_PAYMENT = "MONTHLY_PAYMENT";
    private static final String YEARLY_PAYMENTS = "YEARLY_PAYMENTS";


    private int principal;
    private double interestRate;
    private double effectiveAnnualRate;
    private double monthlyPayment;
    private int numberOfYears;
    private int yearlyPayments = 12;

    EditText principalET;
    EditText numberOfYearsET;
    EditText interestRateET;
    EditText monthlyPaymentET;

    Button calculateButton;

    NumberFormat currency = NumberFormat.getCurrencyInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            principal = 0;
            interestRate = 0.0;
            monthlyPayment = 0.0;
            numberOfYears = 0;
            yearlyPayments = 12;
        } else {

            principal = savedInstanceState.getInt(PRINCIPAL);
            interestRate = savedInstanceState.getDouble(INTEREST_RATE);
            monthlyPayment = savedInstanceState.getDouble(MONTHLY_PAYMENT);
            numberOfYears = savedInstanceState.getInt(NUMBER_OF_YEARS);
            yearlyPayments = savedInstanceState.getInt(YEARLY_PAYMENTS);

        }

        principalET = (EditText) findViewById(R.id.principalEditText);
        interestRateET = (EditText) findViewById(R.id.interestEditText);
        numberOfYearsET = (EditText) findViewById(R.id.yearsEditText);
        monthlyPaymentET = (EditText) findViewById(R.id.monthlyPaymentET);

        calculateButton = (Button)findViewById(R.id.button);

        principalET.addTextChangedListener(principalListener);
        interestRateET.addTextChangedListener(interestRateListener);
        numberOfYearsET.addTextChangedListener(numberOfYearsListener);

        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
        public void onClick(View view){
                updateMonthlyPayment();
            }
        });


    }
        private TextWatcher principalListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    principal = Integer.parseInt(s.toString());
                } catch (NumberFormatException e){
                    principal = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

     private TextWatcher interestRateListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try{
                interestRate = Double.parseDouble(s.toString());
            } catch (NumberFormatException e){
                interestRate = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

        private TextWatcher numberOfYearsListener = new TextWatcher () {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    numberOfYears = Integer.parseInt(s.toString());
                } catch (NumberFormatException e){
                    numberOfYears = 0;
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    private void updateMonthlyPayment() {

        double rate = interestRate/100;
        int n = yearlyPayments*numberOfYears;
        effectiveAnnualRate = rate/yearlyPayments;

        monthlyPayment = (principal*1000)*(effectiveAnnualRate*(Math.pow(1 + effectiveAnnualRate, n)))/(Math.pow(1 + effectiveAnnualRate, n) - 1);
        monthlyPaymentET.setText(currency.format(monthlyPayment));

    }









        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

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

}