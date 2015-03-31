package com.idroidwarz.application.travelalarm;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MainActivity extends Activity {
    Button addressButton;
    TextView addressTV;
    EditText latLongTV;
    EditText long1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addressTV = (TextView) findViewById(R.id.addressTV);
        latLongTV = (EditText) findViewById(R.id.latLongTV);
        long1 = (EditText)findViewById(R.id.longgtv);


        addressButton = (Button) findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText editText = (EditText) findViewById(R.id.addressET);
                String address = editText.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new GeocoderHandler());


            }
        });


        nextpage();


    }



    public void nextpage()
    {
        final Context context = this;
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0) {

                String latdata = latLongTV.getText().toString();
                String londata= long1.getText().toString();
                if(latdata != null && !(latdata.equals("")) )
                {
                    Intent i =  new Intent(getApplicationContext(),GPSMain.class);
                    i.putExtra("DATA",latdata);
                    i.putExtra("DATA2",londata);
                    startActivity(i);

                }

                else {

                            Toast.makeText(context,"Empty String", Toast.LENGTH_LONG).show();

                }


                Intent intent = new Intent(context, GPSMain.class);
                startActivity(intent);

            }
        });

    }

    private  class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String longitudeAddress = null;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    longitudeAddress = bundle.getString("londata");

                    break;
                default:
                    locationAddress = null;
            }
            latLongTV.setText(locationAddress);
            long1.setText(longitudeAddress);

        }
    }


}
