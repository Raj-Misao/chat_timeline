package miso.demochatapplication;

/**
 * Created by Sonu on 29-Jul-17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sonu on 19-Jul-17.
 */

public class DatabaseHelper extends AsyncTask<String,Void,String> {

    Context context;
    URL url;
    HttpURLConnection httpURLConnection;
    InputStream is;
    OutputStream os;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    String _url;
    DatabaseHelper(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
//        _url = "http://10.0.1.59/Chatapplication/register.php";
        _url = "http://192.168.1.147/Chatapplication/register.php";
        String method = params[0];
        if(method.equals("Register"))
        {
            String name = params[1];
            String email = params[2];
            String password = params[3];
            String birthday = params[4];
            String gender = params[5];
            try {

                url = new URL(_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("birthday","UTF-8")+"="+URLEncoder.encode(birthday,"UTF-8")+"&"+
                        URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                is = httpURLConnection.getInputStream();
                is.close();
                return "Registration success";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("Login"))
        {
            String email = params[1];
            String password = params[2];
//            _url = "http://10.0.1.59/Chatapplication/login.php";
            _url = "http://192.168.1.147/Chatapplication/login.php";

            try {
                url = new URL(_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();



                is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                String result = sb.toString();
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(method.equals("message_send")){
            String senderid = params[1];
            String receiverid = params[2];
            String message = params[3];
            _url = "http://192.168.1.147/Chatapplication/message_conversation.php";
//            _url = "http://10.0.1.59/Chatapplication/message_conversation.php";
            try {

                url = new URL(_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("sid","UTF-8")+"="+URLEncoder.encode(senderid,"UTF-8")+"&"+
                        URLEncoder.encode("rid","UTF-8")+"="+URLEncoder.encode(receiverid,"UTF-8")+"&"+
                        URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(message,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                is = httpURLConnection.getInputStream();
                is.close();
                return "Message sent";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration success"))
        {
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Message sent"))
        {
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        }
        else
        {
            String s = result.trim();
            String id, name;
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                JSONObject userinfo = jsonArray.getJSONObject(0);
                id = userinfo.getString("uid");
                name = userinfo.getString("username");
                SharedPreferences pref = context.getSharedPreferences("Mypref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id",id);
                editor.putString("name",name);
                editor.commit();
                Intent i = new Intent(context,HomeActivity.class);
                context.startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
