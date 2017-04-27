package com.example.ngocbui.lottelotte;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ngocbui on 4/26/17.
 */

public class Lotteres extends Activity{
    private TextView province, pdate;
    private String pId, date, resbox;
    private JSONObject jsonObject;
    private JSONArray jsonArr;
    private TextView st, nd, rd, foth,fith, sith, seth, eith, spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotteres);
        province=(TextView) findViewById(R.id.txtprov);
        pdate=(TextView) findViewById(R.id.txtdate);
        String proname=getIntent().getStringExtra("proname");
        province.setText(proname);
        pId=getProvinceId(proname);
        pdate.setText(date);
        st=(TextView) findViewById(R.id.txtst);
        nd=(TextView) findViewById(R.id.txtnd);
        rd=(TextView) findViewById(R.id.txtrd);
        foth=(TextView) findViewById(R.id.txt4th);
        fith=(TextView) findViewById(R.id.txt5th);
        sith=(TextView) findViewById(R.id.txt6th);
        seth=(TextView) findViewById(R.id.txt7th);
        eith=(TextView) findViewById(R.id.txt8th);
        spe=(TextView) findViewById(R.id.txtdb);

        API api = new API();
        api.execute("http://thanhhungqb.tk:8080/kqxsmn");

    }

    private class API extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String jString=null;
            try{
                URL url = new URL(params[0]);

                jString=getInfo(url);
                if(jString==null) {
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
            Log.i("info",s);
            try{
                JSONObject jo=new JSONObject(s);
                jsonObject = jo.getJSONObject(pId).getJSONObject(date);
                for(int i=1; i<9;i++){
                    String index=InttoString(i);
                    String res="";
                    jsonArr=jsonObject.getJSONArray(index);
                    for(int j=0;j<jsonArr.length();j++){
                        res=res+" "+String.valueOf(jsonArr.getString(j));
                    }
                    setText(i,res);
                }
                spe.setText(String.valueOf(jsonObject.getJSONArray("DB").getString(0)));
                //st.setText(String.valueOf(jsonObject.getString(0)));
                //st.setText("20000");
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    private String getProvinceId(String proname){
        String t=null;
        switch(proname){
            case "An Giang":{
                t="AG";
                date="20-04";
            }
                break;
            case "Binh Duong": {
                t="BD";
                date="14-04";
            }
                break;
            case "Bac Lieu": {
                t="BL";
                date="18-04";
            }
                break;
            case "Binh Phuoc": {
                date="15-04";
                t="BP";
            }
                break;
            case "Ca Mau": {
                t="CM";
                date="17-04";
            }
                break;
            case "Can Tho": {
                t="CT";
                date="19-04";
            }
                break;
            case "Ho Chi Minh": {
                t="HCM";
                date="17-04";
            }
                break;
            default:break;
        }
        return t;
    }

    private String InttoString(int i){
        String index=null;
        switch(i){
            case 1: index = "1";
                break;
            case 2: index = "2";
                break;
            case 3: index = "3";
                break;
            case 4: index = "4";
                break;
            case 5: index = "5";
                break;
            case 6: index = "6";
                break;
            case 7: index = "7";
                break;
            case 8: index = "8";
                break;
            default:break;
        }
        return index;
    }

    private void setText(int i, String res){
        switch(i){
            case 1:
                st.setText(res);
                break;
            case 2:
                nd.setText(res);
                break;
            case 3:
                rd.setText(res);
                break;
            case 4:
                foth.setText(res);
                break;
            case 5:
                fith.setText(res);
                break;
            case 6:
                sith.setText(res);
                break;
            case 7:
                seth.setText(res);
                break;
            case 8:
                eith.setText(res);
                break;

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
