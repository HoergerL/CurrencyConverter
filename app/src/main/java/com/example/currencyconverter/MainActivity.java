package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    static EditText currency_left;
    static TextView currency_right;
    Button color;
    int colorChange;
    int colorChange2;


    ConstraintLayout linearLayout;
    private SharedPreferences settings;

    public void settingsHandler(View view){
        Intent intent = new Intent(this, color.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color = findViewById(R.id.color);
        currency_left = (EditText) findViewById(R.id.currency_a);
        currency_right = (TextView) findViewById(R.id.currency_b);
        linearLayout = findViewById(R.id.linearLayout);


        settings = getSharedPreferences("settings", MODE_PRIVATE);

        colorChange = settings.getInt("color",colorChange);
        colorChange2 = settings.getInt("color2",colorChange2);

        linearLayout.setBackgroundColor(colorChange);
        color.setTextColor(colorChange2);
        currency_left.setTextColor(colorChange2);
        currency_right.setTextColor(colorChange2);
        currency_left.setHintTextColor(colorChange2);


        // einfügen alle Namen meiner Texte


        currency_left.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                     calculate(s.toString());

                }
                catch (Exception e){
                    currency_right.setText(String.valueOf(0.00));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            calculate(currency_left.getText().toString());

        }
        catch (Exception e){
            currency_right.setText(String.valueOf(0.00));
        }

    }

    final static int LEFT_FLAG_REQUEST = 0;
    final static int RIGHT_FLAG_REQUEST = 1;


    public void currencySelectionLeft(View view) {
        Intent intent = new Intent(this, CurrencySelection.class);
        startActivityForResult(intent, LEFT_FLAG_REQUEST);
    }

    public void currencySelectionRight(View view) {
        Intent intent = new Intent(this, CurrencySelection.class);
        startActivityForResult(intent, RIGHT_FLAG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LEFT_FLAG_REQUEST:
                    changeLeftFlag(data.getStringExtra("currency"));
                    break;
                case RIGHT_FLAG_REQUEST:
                    changeRightFlag(data.getStringExtra("currency"));
                    break;
            }
        }









    }

    public void changeLeftFlag(String currency) {
        int drawableID = getDrawableIDByCurrency(currency);
        ImageButton leftButtonFlag = (ImageButton) findViewById(R.id.imageButtonLeft);
        EditText currency_left = (EditText) findViewById(R.id.currency_a);
        leftButtonFlag.setTag(currency);
        leftButtonFlag.setImageResource(drawableID);
        currency_left.setHint(currency);

    }

    public void changeRightFlag(String currency) {
        int drawableID = getDrawableIDByCurrency(currency);
        ImageButton rightButtonFlag = (ImageButton) findViewById(R.id.imageButtonRight);
        rightButtonFlag.setImageResource(drawableID);
        rightButtonFlag.setTag(currency);
        TextView currency_right = (TextView) findViewById(R.id.currency_b);
      //  currency_right.setText(currency);
        currency_right.setHint(currency);
    }

    public int getDrawableIDByCurrency(String currency) {
        int drawableID = 0;
        switch(currency) {
            case "euro":
                drawableID =  R.drawable.europe_flag;

                break;
            case "pound":
                drawableID = R.drawable.uk_flag;
                break;
            case "singapore_dollar":
                drawableID = R.drawable.singapore_flag;
                break;
            case "indian_rupees":
                drawableID = R.drawable.india_flag;
                break;
            default:
                System.out.println("currency: "+currency+ " does not exist!");
        }
        return drawableID;


    }
    static HashMap<String, Double> dollarTable = new HashMap<String, Double>() {{
        put("euro", 1.126118);
        put("singapore_dollar", 0.738146);
        put("pound", 1.304167);
        put("indian_rupees", 0.014377);  // todo should be called "indian_rupee"
    }};

    public double convertCurrency(String from, String to, double input) {
        double factor = (1 / dollarTable.get(from) * dollarTable.get(to));
        return input * factor;
    }

    public void switch_flags(View view){
        ImageButton rightButtonFlag = (ImageButton) findViewById(R.id.imageButtonRight);
        ImageButton leftButtonFlag = (ImageButton) findViewById(R.id.imageButtonLeft);
        String right_tag = (String) rightButtonFlag.getTag();
        String left_tag = (String) leftButtonFlag.getTag();
        leftButtonFlag.setImageResource(getDrawableIDByCurrency(right_tag));
        rightButtonFlag.setImageResource(getDrawableIDByCurrency(left_tag));
        currency_left.setHint(right_tag);
        rightButtonFlag.setTag(left_tag);
        leftButtonFlag.setTag(right_tag);

        try {
            calculate(currency_left.getText().toString());

        }
        catch (Exception e){
            currency_right.setText(String.valueOf(0.00));
        }


    }

    public void calculate (String s){
        try {
            double number = Double.parseDouble(s);
            ImageButton rightButtonFlag = (ImageButton) findViewById(R.id.imageButtonRight);
            ImageButton leftButtonFlag = (ImageButton) findViewById(R.id.imageButtonLeft);

            String left = (String) rightButtonFlag.getTag();
            String right = (String) leftButtonFlag.getTag();

            double output = convertCurrency(left, right, number);
            DecimalFormat f = new DecimalFormat("#0.00");
            double result_round = Math.round(output*100.0)/100.0;
            TextView warning = (TextView) findViewById(R.id.warning);

            if((number >0) && (result_round == 0)){
                warning.setText("The number is to small");
            }

            else if(number>1000000){
                warning.setText("The number is to big");
            }
            else {
                warning.setText("");
            }
            currency_right.setText(String.valueOf(result_round));
        }
        catch (Exception e){
            currency_right.setText(String.valueOf(0.00));
        }
    }

}
