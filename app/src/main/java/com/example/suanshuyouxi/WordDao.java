package com.example.suanshuyouxi;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao  //数据访问对象，数据库操作接口,里面全是方法名，对应四个按钮功能
public interface WordDao {
    @Insert
    void insertword(Word...words);//插入数据接口
    @Update
    void updateWords(Word... words);//更新数据接口
    @Delete
    void deleteWords(Word... words);//清除数据接口
    @Query("DELETE FROM Word")//@Query用来启动Sql语言查询select功能，查询并删除接口
    void deleteAllWords(); //查询并删除数据接口
    @Query("SELECT * FROM Word ORDER BY id") //查询并排序显示结果,返回一个livedata
    //LiveData<List<Word>>getAllwordsLive();
    LiveData<List<Word>> xuanze_All_liebiao_paixu_neirong();


}
