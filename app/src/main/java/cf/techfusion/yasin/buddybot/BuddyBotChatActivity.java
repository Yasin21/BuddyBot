package cf.techfusion.yasin.buddybot;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ai.api.model.AIResponse;
import cf.techfusion.yasin.buddybot.db.ChatContract;
import cf.techfusion.yasin.buddybot.db.ChatDatabaseHelper;
import cf.techfusion.yasin.buddybot.support.AiSupport;

import static cf.techfusion.yasin.buddybot.db.ChatContract.ChatEntry;

public class BuddyBotChatActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText editText;
    private Button sendButton;
    private SQLiteDatabase mDb;
    private CustomRecyclerView chatMessageView;
    private ChatMessageAdapter chatMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_bot_chat);

        editText = (EditText) findViewById(R.id.edittext_chatbox);
        sendButton = (Button) findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(this);

        mDb = (new ChatDatabaseHelper(this)).getWritableDatabase();

        chatMessageView = (CustomRecyclerView) findViewById(R.id.reyclerview_message_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatMessageView.setLayoutManager(layoutManager);

        Cursor cursor = getAllMessage();
        chatMessageAdapter = new ChatMessageAdapter(this,cursor);
        chatMessageView.setHasFixedSize(true);
        chatMessageView.setAdapter(chatMessageAdapter);

    }


    public Cursor getAllMessage(){
        return mDb.query(
                ChatEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ChatEntry.COLUMN_CHAT_TIME
        );
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        addMessage(ChatContract.USER_MESSAGE,editText.getText().toString());
        //        Toast.makeText(this,"Message Sent "+count,Toast.LENGTH_SHORT).show();
        new AiSupport.AiRequestTask(){
            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if(aiResponse != null){
                    addMessage(ChatContract.AI_MESSAGE,aiResponse.getResult().getFulfillment().getSpeech());
                    updateView();
                }else{
                    Toast.makeText(BuddyBotChatActivity.this,"Something gose wrong",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(AiSupport.createRequest(editText.getText().toString()));
        editText.setText("");
        updateView();
    }

    public long addMessage(int type, String message){
        ContentValues insertValues = new ContentValues();
        insertValues.put(ChatEntry.COLUMN_CHAT_SENDER,type);
        insertValues.put(ChatEntry.COLUMN_CHAT_MESSAGE,message);
        return mDb.insert(ChatEntry.TABLE_NAME,null,insertValues);
    }
    public void updateView(){
        Cursor c = getAllMessage();
        chatMessageAdapter.swapCursor(c);
        chatMessageView.scrollDown();
    }



}
