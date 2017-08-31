package miso.demochatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ChatActivity extends AppCompatActivity{
    private EditText msgbox;
    private RecyclerView chatRecyclerView;
    private ChatAdapter mChatAdapter;
    private List<Chat> chats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chats = new ArrayList<>();
        chatRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        msgbox = (EditText) findViewById(R.id.message_edit_box);

        String receiverid = getIntent().getExtras().getString("receiverid");
        SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
        String senderid = sp.getString("id",null);
        Chat.currentuser = senderid;
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(senderid,receiverid);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        mChatAdapter = new ChatAdapter(chats);
        chatRecyclerView.setAdapter(mChatAdapter);

        msgbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    sendMessage(msgbox.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }



    public class BackgroundTask extends AsyncTask<String,Void,Void>{
        String Json_string;
        @Override
        protected Void doInBackground(String... params) {
            String sid = params[0];
            String rid = params[1];

            try {

                URL url = new URL("http://192.168.1.147/Chatapplication/get_message.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("sender","UTF-8")+"="+URLEncoder.encode(sid,"UTF-8")+"&"+
                        URLEncoder.encode("receiver","UTF-8")+"="+URLEncoder.encode(rid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }

                Json_string = stringBuilder.toString().trim();

                try {
                    JSONObject jsonObject = new JSONObject(Json_string);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    int count = 0;
                    while (count<jsonArray.length()){
                        JSONObject JO = jsonArray.getJSONObject(count);
                        Chat chat = new Chat();
                        chat.setSednerid(JO.getString("senderid"));
                        chat.setReceiverid(JO.getString("receiverid"));
                        chat.setMessage(JO.getString("message"));
                        chat.setLayoutid(1);
                        chats.add(chat);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//

            return null;
        }

    }

//
//    private void sendMessage() {
//        String message = msgbox.getText().toString();
//        String receiverid = getIntent().getExtras().getString("receiverid");
//        SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
//        String senderid = sp.getString("id",null);
//        String currentuser = sp.getString("id",null);
//        Chat.currentuser = currentuser;
//        Chat chat = new Chat(senderid,receiverid,message, System.currentTimeMillis(),1);
////        DatabaseHelper db = new DatabaseHelper(this);
////        String method = "message_send";
////        db.execute(method,senderid,receiverid,message);
//        chats.add(chat);
//        mChatAdapter.notifyDataSetChanged();
//        chatRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount()-1);
//    }

    public void sendMessage(String message){
        String receiverid = getIntent().getExtras().getString("receiverid");
        SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
        String senderid = sp.getString("id",null);
        String currentuser = senderid;
        Chat chat = new Chat();
        Chat.currentuser = currentuser;
        chat.setSednerid(senderid);
        chat.setLayoutid(1);
        chat.setReceiverid(receiverid);
        chat.setMessage(message);
        chats.add(chat);
        mChatAdapter.notifyDataSetChanged();
        chatRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount()-1);
        DatabaseHelper databaseHelper = new DatabaseHelper(ChatActivity.this);
        String method = "message_send";
        databaseHelper.execute(method,senderid,receiverid,message);

    }
}
