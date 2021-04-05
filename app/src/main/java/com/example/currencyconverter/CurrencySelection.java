package com.example.currencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CurrencySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);
    }

    public void onClickFlag(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        // IMPORTANT: putExtra() uses a key/value pair scheme to store data in an intent:
        // putExtra("key-as-String", <the value to be stored>) !!

        // getTag(): I've added the attribute "tag" to every button/Imagebutton in the currency selection
        // activity. For example the European Flag saves under "tag" the string: "euro"
        // this is the data that is actually passed to back to the main activity!
        // Back in the main activity "euro" is used with a switch statement, to find the drawable flag id
        // (see: getDrawableIDByCurrency) and later we will use it to extract the currency itself to do
        // the calculationts!
        intent.putExtra("currency", (String) view.getTag());
        setResult(RESULT_OK, intent); // <- very important for onActivityForResult to work!
        finish(); // <- here we cleanly return from the activity
    }
}
