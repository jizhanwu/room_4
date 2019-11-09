package com.example.room_4;


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
    void insertWords(Word...words);//插入数据

    @Update
    void updateWords(Word... words);//修改数据

    //@Delete
    //void deleteWords(Word... words);//删除数据
    @Delete
    void deleteWords(Word...words);//删除数据


    @Query("DELETE FROM Word ")//@Query用来启动Sql语言查询select功能，查询并删除全部数据 接口
   void deleteAllWords(); //删除全部数据接口

    @Query("SELECT * FROM Word ORDER BY ID DESC") //查询并排序显示结果,返回一个livedata
    LiveData<List<Word>> xuanze_All_liebiao_paixu_neirong();

    @Query("SELECT*FROM WORD WHERE english_word LIKE:patten ORDER BY ID DESC")
    LiveData<List<Word>>findWordsWithPatten(String patten);

}
