package cf.techfusion.yasin.buddybot.db;

import android.provider.BaseColumns;

public class ChatContract{
    public static final int USER_MESSAGE = 0;
    public static final int AI_MESSAGE = 1;

    public static final class ChatEntry implements BaseColumns{
        public static final String TABLE_NAME = "chat";
        public static final String COLUMN_CHAT_SENDER = "chatSender";
        public static final String COLUMN_CHAT_MESSAGE = "chatMessage";
        public static final String COLUMN_CHAT_TIME = "chatTime";
    }

}
