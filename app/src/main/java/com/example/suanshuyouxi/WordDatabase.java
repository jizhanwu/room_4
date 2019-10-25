package com.example.suanshuyouxi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton
//用数据库注解，并声明包括的实体和版本号
@Database(entities = {Word.class},version = 4,exportSchema = false)
//这是一个抽象类，里面全是方法头，不能有方法体
public abstract class WordDatabase extends RoomDatabase {
    //初始化一个变量，随意写即可
    private static WordDatabase INSTANCE;
    //用synchronized，强化singleton应用，使用多线程集中访问数据库时给予一个排队机制
    static synchronized WordDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
                    //破坏式一次性清空全部数据 ，不建议
                   //.fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_3_4)
                       .build();
        }
        return INSTANCE;
    }
    //创建一个提取WordDao这个接口类的全部方法名
    public abstract WordDao getWordDao();
    //static final Migration MIGRATION_2_3 = new Migration(2,3) {
       /// @Override
       // public void migrate(@NonNull SupportSQLiteDatabase database) {
         //   database.execSQL( "ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");

      //  }
   // };

    private static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
           database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL,english_word TEXT," +
                   "chinese_meanin TEXT)");
           database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meanin ) " +
                   "SELECT id,english_word,chinese_meanin FROM word ");//从旧表制内全部列内容，插入至新表
           database.execSQL("DROP TABLE word");
           database.execSQL("ALTER TABLE word_temp RENAME to word ");

        }
    };
}
