package cf.techfusion.yasin.buddybot.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static cf.techfusion.yasin.buddybot.db.ChatContract.*;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    // The database name
    private static final String DATABASE_NAME = "chat.db";

    public ChatDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CHAT_TABLE;
        SQL_CREATE_CHAT_TABLE = "CREATE TABLE "+ ChatEntry.TABLE_NAME +" ("
                +ChatEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ChatEntry.COLUMN_CHAT_SENDER+" INTEGER NOT NULL,"
                +ChatEntry.COLUMN_CHAT_MESSAGE+" TEXT NOT NULL,"
                +ChatEntry.COLUMN_CHAT_TIME+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                +"); ";
        sqLiteDatabase.execSQL(SQL_CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChatEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
