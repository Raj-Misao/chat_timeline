package miso.demochatapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    ArrayList<Contact> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.mylist);
        SharedPreferences sharedPreferences = getSharedPreferences("Mypref",MODE_PRIVATE);
        String senderid = sharedPreferences.getString("id",null);
        new BackgroundTask().execute(senderid);
    }

    class BackgroundTask extends AsyncTask<String,Void,ArrayList<Contact>>{

        String json_url;
        String JSON_STRING;
        @Override
        protected void onPreExecute() {
//            json_url = "http://10.0.1.59/Chatapplication/get_json_data.php";
            json_url = "http://192.168.1.147/Chatapplication/get_json_data.php";
        }

        @Override
        protected ArrayList<Contact> doInBackground(String... params) {
            String loginuser = params[0];
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(loginuser,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();



                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    sb.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                json_string = sb.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("data");

                int count = 0;
                String id,name;
                while (count<jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    id = JO.getString("uid");
                    name = JO.getString("username");
                    Contact contact = new Contact(id,name);
                    list.add(contact);
                    count++;
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> result) {
            super.onPostExecute(result);
            contactAdapter  = new ContactAdapter(HomeActivity.this,R.layout.single_list_item,list);
            listView.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Contact detail = (Contact)listView.getItemAtPosition(position);

                    Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
                    intent.putExtra("receiverid",detail.getId());
                    startActivity(intent);
                }


            });

        }
    }
}
