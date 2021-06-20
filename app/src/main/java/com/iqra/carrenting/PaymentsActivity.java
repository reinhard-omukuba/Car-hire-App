package com.iqra.carrenting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentsActivity extends AppCompatActivity {

    private String car_price = null;
    private String car_id = null;
    private String carName = null;
    private String engineSize = null;
    private String fullDuration = null;

    private TextView theCarPrice;

    private Spinner mSpinner;
    private TextView tvTotalAmount;
    private Button btnPay;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String theprice;

    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration paypalconfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnPay = findViewById(R.id.btnPay);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bookings");

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
        startService(intent);

        theCarPrice = findViewById(R.id.theCarPrice);
        car_price = getIntent().getExtras().getString("car_price");
        car_id = getIntent().getExtras().getString("car_id");

        carName = getIntent().getExtras().getString("carName");
        engineSize = getIntent().getExtras().getString("engineSize");
        theCarPrice.setText(car_price);


        mSpinner  = (Spinner) findViewById(R.id.spinnerdays);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text + " Days", Toast.LENGTH_SHORT).show();


                int unitPrice = Integer.parseInt(car_price);
                int unitDays = Integer.parseInt(text);

                fullDuration = unitDays +"";


                tvTotalAmount.setText(String.valueOf(unitDays * unitPrice));

                int totalPayableAmount = unitPrice * unitDays;

                theprice = totalPayableAmount + "";

                //payment Activity




                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalPayableAmount/100), "USD",
                                "Car hire app", PayPalPayment.PAYMENT_INTENT_SALE);

                        Intent intent = new Intent(PaymentsActivity.this,  PaymentActivity.class);
                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
                        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

                        startActivityForResult(intent, PAYPAL_REQ_CODE);
                    }

                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAYPAL_REQ_CODE){

            if(resultCode == Activity.RESULT_OK){

                mAuth =FirebaseAuth.getInstance();
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                String userid=user.getUid();
               // Map<String, String> bookingDate = ServerValue.TIMESTAMP;

                //Date date = new Date();
                // Map<String, String> timestamp = ServerValue.TIMESTAMP;


                DatabaseReference current_user_db = mDatabase.push();



                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {



                        current_user_db.child("userid").setValue(userid);
                        current_user_db.child("booking_price").setValue(theprice);
                        current_user_db.child("car_id").setValue(car_id);

                        current_user_db.child("car_name").setValue(carName);
                        current_user_db.child("booking_durations").setValue(fullDuration);
                        current_user_db.child("engine_size").setValue(engineSize);
                        //current_user_db.child("booking_date").setValue(timestamp);



                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });


                finish();

                Intent intent = new Intent(PaymentsActivity.this, HomeActivity.class);
                startActivity(intent);
                Toast.makeText(PaymentsActivity.this, "Booking Successful!", Toast.LENGTH_LONG).show();


            }else{
                Toast.makeText(this, "Payment is Unsuccessful", Toast.LENGTH_LONG).show();
            }
        }

    }


}