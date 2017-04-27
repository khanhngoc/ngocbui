package com.example.ngocbui.lottelotte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private String proname;
    private Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottelotte);

        sp=(Spinner) findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: proname="An Giang";
                        break;
                    case 1: proname="Binh Duong";
                        break;
                    case 2: proname="Bac Lieu";
                        break;
                    case 3: proname="Binh Phuoc";
                        break;
                    case 4: proname="Ca Mau";
                        break;
                    case 5: proname="Can Tho";
                        break;
                    case 6: proname="Ho Chi Minh";
                        break;
                    default: break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onBtnClick(View v){
        if(v.getId()==R.id.btnconf){
            Intent intent=new Intent(MainActivity.this, Lotteres.class);
            intent.putExtra("proname", proname);
            startActivity(intent);

        }
    }
}
