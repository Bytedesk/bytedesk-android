package com.bytedesk.im.core.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bytedesk.im.core.room.converter.DateConverter;
import com.bytedesk.im.core.room.dao.MessageDao;
import com.bytedesk.im.core.room.dao.ThreadDao;
import com.bytedesk.im.core.room.entity.MessageEntity;
import com.bytedesk.im.core.room.entity.ThreadEntity;
import com.bytedesk.im.core.util.BDCoreConstant;
import com.orhanobut.logger.Logger;

/**
 * @author bytedesk.com on 2017/9/13.
 * <a href="https://developer.android.com/topic/libraries/architecture/room.html">...</a>
 * Note: You should follow the singleton design pattern when instantiating an AppDatabase object,
 *  as each RoomDatabase instance is fairly expensive, and you rarely need access to multiple instances.
 *  RxJava 2:
 *      <a href="https://medium.com/google-developers/room-rxjava-acb0cd4f3757">...</a>
 * @author bytedesk.com
 */
@Database(entities = {MessageEntity.class, ThreadEntity.class, },
        version = 34)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();

    public abstract ThreadDao threadDao();

    private static volatile AppDatabase INSTANCE;

    // Singleton 单例
    public static AppDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (AppDatabase.class) {
                if (null == INSTANCE) {
                    // 未加密版本
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, BDCoreConstant.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * <a href="https://github.com/Tencent/wcdb">...</a>
     *
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static Callback sRoomDatabaseCallback = new Callback() {
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            // If you want to keep the data through app restarts,
//            // comment out the following line.
//            new PopulateDbAsync(INSTANCE).execute();
//        }
    };

}


