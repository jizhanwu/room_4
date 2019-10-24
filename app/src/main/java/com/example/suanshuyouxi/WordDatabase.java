package com.example.suanshuyouxi;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//singleton
//用数据库注解，并声明包括的实体和版本号
@Database(entities = {Word.class},version = 1,exportSchema = false)
//这是一个抽象类，里面全是方法头，不能有方法体
public abstract class WordDatabase extends RoomDatabase {
    //初始化一个变量，随意写即可
    private static WordDatabase INSTANCE;
    //用synchronized，强化singleton应用，使用多线程集中访问数据库时给予一个排队机制
    static synchronized WordDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
                       .build();
        }
        return INSTANCE;
    }
    //创建一个提取WordDao这个接口类的全部方法名
    public abstract WordDao getWordDao();
}
