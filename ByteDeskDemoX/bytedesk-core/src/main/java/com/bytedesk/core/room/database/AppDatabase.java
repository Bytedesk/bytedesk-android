package com.bytedesk.core.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bytedesk.core.room.converter.DateConverter;
import com.bytedesk.core.room.dao.ContactDao;
import com.bytedesk.core.room.dao.FriendDao;
import com.bytedesk.core.room.dao.GroupDao;
import com.bytedesk.core.room.dao.MessageDao;
import com.bytedesk.core.room.dao.NoticeDao;
import com.bytedesk.core.room.dao.QueueDao;
import com.bytedesk.core.room.dao.ThreadDao;
import com.bytedesk.core.room.dao.WorkGroupDao;
import com.bytedesk.core.room.entity.ContactEntity;
import com.bytedesk.core.room.entity.FriendEntity;
import com.bytedesk.core.room.entity.GroupEntity;
import com.bytedesk.core.room.entity.MessageEntity;
import com.bytedesk.core.room.entity.NoticeEntity;
import com.bytedesk.core.room.entity.PaymentEntity;
import com.bytedesk.core.room.entity.QueueEntity;
import com.bytedesk.core.room.entity.ThreadEntity;
import com.bytedesk.core.room.entity.WorkGroupEntity;
import com.bytedesk.core.util.BDCoreConstant;

/**
 * @author bytedesk.com on 2017/9/13.
 *
 * https://developer.android.com/topic/libraries/architecture/room.html
 *
 * Note: You should follow the singleton design pattern when instantiating an AppDatabase object,
 *  as each RoomDatabase instance is fairly expensive, and you rarely need access to multiple instances.
 *
 *  RxJava 2:
 *      https://medium.com/google-developers/room-rxjava-acb0cd4f3757
 *
 * @author bytedesk.com
 */

@Database(entities = {MessageEntity.class, ThreadEntity.class, QueueEntity.class,
        ContactEntity.class, WorkGroupEntity.class, GroupEntity.class, NoticeEntity.class,
        FriendEntity.class, PaymentEntity.class},
        version = 33)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();

    public abstract ThreadDao threadDao();

    public abstract QueueDao queueDao();

    public abstract ContactDao contactDao();

    public abstract WorkGroupDao workGroupDao();

    public abstract GroupDao groupDao();

    public abstract NoticeDao noticeDao();

    public abstract FriendDao friendDao();

    private static volatile AppDatabase INSTANCE;

    // Singleton 单例
    public static AppDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (AppDatabase.class) {
                if (null == INSTANCE) {

                    // https://github.com/Tencent/wcdb
                    // [WCDB] To use Room library with WCDB, pass a WCDBOpenHelper factory object
                    // to the database builder with .openHelperFactory(...). In the factory object,
                    // you can specify passphrase and cipher options to open or create encrypted
                    // database, as well as optimization options like asynchronous checkpoint.
//                    SQLiteCipherSpec cipherSpec = new SQLiteCipherSpec()
//                            .setPageSize(4096)
//                            .setKDFIteration(64000);

//                    WCDBOpenHelperFactory factory = new WCDBOpenHelperFactory()
//                            .passphrase(MarsNativeLibUtils.dbPassphraseFromJNI().getBytes())  // passphrase to the database, remove this line for plain-text
//                            .cipherSpec(cipherSpec)               // cipher to use, remove for default settings
//                            .writeAheadLoggingEnabled(true)       // enable WAL mode, remove if not needed
//                            .asyncCheckpointEnabled(true);        // enable asynchronous checkpoint, remove if not needed

//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, BDCoreConstant.DATABASE_NAME)
//
//                            // [WCDB] Specify open helper to use WCDB database implementation instead
//                            // of the Android framework.
////                            .openHelperFactory(factory)
//
//                            // Wipes and rebuilds instead of migrating if no Migration object.
//                            // Migration is not part of this codelab.
//                            .fallbackToDestructiveMigration()
//                            .addCallback(sRoomDatabaseCallback)
//                            .build();

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
     * https://github.com/Tencent/wcdb
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


