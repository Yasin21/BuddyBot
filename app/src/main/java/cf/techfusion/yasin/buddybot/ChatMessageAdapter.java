package cf.techfusion.yasin.buddybot;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cf.techfusion.yasin.buddybot.db.ChatContract;


public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ChatMessageAdapter(Context context,Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == ChatContract.USER_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recived,parent,false);
        }
        return new ChatViewHolder(view);
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
            
        }
    }


    @Override
    public int getItemViewType(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getInt(mCursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_CHAT_SENDER));
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        String message = mCursor.getString(mCursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_CHAT_MESSAGE));
        String datetime = mCursor.getString(mCursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_CHAT_TIME));
        String timeSplit[] = datetime.split(" ")[1].split(":");
        String time = timeSplit[0]+":"+timeSplit[1];
        holder.bind(message,time);
    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder{
        public TextView mText;
        public TextView mTime;
        public ChatViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text_message_body);
            mTime = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        public void bind(String message,String time){
            mText.setText(message);
            mTime.setText(time);
        }
    }
}
