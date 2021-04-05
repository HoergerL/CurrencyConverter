package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import yuku.ambilwarna.AmbilWarnaDialog;


public class color extends AppCompatActivity {


    public void settingsHandler(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

        Button bcolor;
        Button tcolor;
        Button back;


        ConstraintLayout ac_Layout;
        int mDefaultColor;          //Backgroundcolor
        int bDefaultColor;          //Buttoncolor

         SharedPreferences settings;     //Background&Text







        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_color);

            bcolor =findViewById(R.id.bColor);
            tcolor =findViewById(R.id.tColor);
            back = findViewById(R.id.back);

            ac_Layout = findViewById(R.id.ac_Layout);

            settings = getSharedPreferences("settings",MODE_PRIVATE);

            mDefaultColor = ContextCompat.getColor(color.this,R.color.colorPrimary);
            bDefaultColor = ContextCompat.getColor(color.this,R.color.colorPrimary);



            bcolor.setOnClickListener(new View.OnClickListener(){

                @Override
                public  void onClick(View v) {
                    openColorPicker();

                }

            });

            tcolor.setOnClickListener(new View.OnClickListener(){

                @Override
                public  void onClick(View v) {
                    openColorPicker2();

                }

            });



        }

        public void openColorPicker(){
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    mDefaultColor = color;
                    ac_Layout.setBackgroundColor(mDefaultColor);
                    settings.edit().putInt("color",mDefaultColor).apply();

                }
            });

            colorPicker.show();
        }

        public void openColorPicker2(){
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, bDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    bDefaultColor = color;
                    bcolor.setTextColor(bDefaultColor);
                    tcolor.setTextColor(bDefaultColor);
                    back.setTextColor(bDefaultColor);

                    settings.edit().putInt("color2",bDefaultColor).apply();

                }
            });

            colorPicker.show();
        }
    }

