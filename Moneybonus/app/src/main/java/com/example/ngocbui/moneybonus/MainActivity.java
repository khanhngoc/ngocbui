package com.example.ngocbui.moneybonus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private TextView us, vi, si, eu, en, ja, res;
    private EditText input;
    private Button calc;
    private Spinner sp1, sp2;
    private String baseCurrency, exchangeCurrency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneybonus);

        us = (TextView) findViewById(R.id.txtus);
        vi = (TextView) findViewById(R.id.txtvi);
        si = (TextView) findViewById(R.id.txtsi);
        eu = (TextView) findViewById(R.id.txteu);
        en = (TextView) findViewById(R.id.txten);
        ja = (TextView) findViewById(R.id.txtja);
        input = (EditText) findViewById(R.id.input);
        calc = (Button) findViewById(R.id.calc);
        res = (TextView) findViewById(R.id.res);
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = input.getText().toString();
                String rate = "0";
                switch (exchangeCurrency) {
                    case "us": rate = us.getText().toString();
                        break;
                    case "vi": rate = vi.getText().toString();
                        break;
                    case "si": rate = si.getText().toString();
                        break;
                    case "ja": rate = ja.getText().toString();
                        break;
                    case "eu": rate = eu.getText().toString();
                        break;
                    case "en": rate = en.getText().toString();
                        break;
                    default: break;

                }
                if (in.equals("")) {
                    return;
                }
                Double result = Double.parseDouble(in)*Double.parseDouble(rate);

                res.setText(result.toString());
            }
        });



        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        exchangeCurrency = "us";
                        break;
                    case 1:
                        exchangeCurrency = "vi";
                        break;
                    case 2:
                        exchangeCurrency = "si";
                        break;
                    case 3:
                        exchangeCurrency = "ja";
                        break;
                    case 4:
                        exchangeCurrency = "eu";
                        break;
                    case 5:
                        exchangeCurrency = "en";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        API api=new API();
        api.execute("http://www.apilayer.net/api/live?access_key=bfb8bb63ba50e91027ce1631a55b2d0a&currencies=USD,EUR,JPY,VND,SGD,GBP");
    }

    private void exchangeCurrency(){

        String baseRate = "1.0";
        switch (baseCurrency) {
            case "us": baseRate = us.getText().toString();
                break;
            case "vi": baseRate = vi.getText().toString();
                break;
            case "si": baseRate = si.getText().toString();
                break;
            case "ja": baseRate = ja.getText().toString();
                break;
            case "eu": baseRate = eu.getText().toString();
                break;
            case "en": baseRate = en.getText().toString();
                break;
            default:
                break;
        }

        Double usNewRate = (Double)(Double.parseDouble(us.getText().toString())/Double.parseDouble(baseRate));
        Double viNewRate = (Double)Double.parseDouble(vi.getText().toString())/Double.parseDouble(baseRate);
        Double siNewRate = (Double)Double.parseDouble(si.getText().toString())/Double.parseDouble(baseRate);
        Double jaNewRate = (Double)Double.parseDouble(ja.getText().toString())/Double.parseDouble(baseRate);
        Double euNewRate = (Double)Double.parseDouble(eu.getText().toString())/Double.parseDouble(baseRate);
        Double enNewRate = (Double)Double.parseDouble(en.getText().toString())/Double.parseDouble(baseRate);
        //Log.i("rate", usNewRate.toString());
        DecimalFormat df = new DecimalFormat("#0.000000");
        us.setText(df.format(usNewRate));
        vi.setText(df.format(viNewRate));
        si.setText(df.format(siNewRate));
        ja.setText(df.format(jaNewRate));
        eu.setText(df.format(euNewRate));
        en.setText(df.format(enNewRate));
    }
    private class API extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String jString=null;
            try{
                URL url=new URL(params[0]);
                jString=getInfo(url);
                if(jString==null){
                    throw new IOException("did not get info");
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return jString;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try{
                JSONObject jo=new JSONObject(s);
                jsonObject = jo.getJSONObject("quotes");
                us.setText(String.valueOf(jsonObject.getDouble("USDUSD")));
                vi.setText(String.valueOf(jsonObject.getDouble("USDVND")));
                si.setText(String.valueOf(jsonObject.getDouble("USDSGD")));
                eu.setText(String.valueOf(jsonObject.getDouble("USDEUR")));
                en.setText(String.valueOf(jsonObject.getDouble("USDGBP")));
                ja.setText(String.valueOf(jsonObject.getDouble("USDJPY")));
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            Log.i("info",s);
            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: baseCurrency = "us";
                            break;
                        case 1: baseCurrency = "vi";
                            break;
                        case 2: baseCurrency = "si";
                            break;
                        case 3: baseCurrency= "ja";
                            break;
                        case 4: baseCurrency = "eu";
                            break;
                        case 5: baseCurrency = "en";
                            break;
                        default: break;
                    }
                    MainActivity.this.exchangeCurrency();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    private String getInfo(URL url) throws IOException {
        InputStream input=null;
        HttpURLConnection connection=null;
        StringBuilder jString=new StringBuilder();
        try{
            connection=(HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int resCode=connection.getResponseCode();
            if(resCode!= HttpURLConnection.HTTP_OK){
                throw new IOException("abc");
            }

            input=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(input,"UTF-8"));
            String line=null;
            while ((line=reader.readLine())!=null){
                jString.append(line+"\n");
            }


        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if (input != null){
                input.close();
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        Log.i("info",jString.toString());
        return jString.toString();

    }



}

