package com.example.room_3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity //@Entity将下面这个类注解为一张数据库表，即实体
public class Word {
    @PrimaryKey(autoGenerate =true ) //用@PrimaryKey定义唯一的主键id,为唯一的，并自动增加
    private  int id;
    @ColumnInfo(name = "english_word") //用@CloumnInfo定义一个列名称，是一个变量
    private String word;
    @ColumnInfo(name = "chinese_meanin")//用@CloumnInfo定义一个列名称，是一个变量
    private String chineseMeaning;
    @ColumnInfo(name = "chinese_invisible")//用@CloumnInfo定义一个列存放布尔值名称，是一个变量
      private boolean chineseInvisible;
    //get and set  布尔列
     boolean isChineseInvisible() {
        return chineseInvisible;
    }

     void setChineseInvisible(boolean chineseInvisible) {
        this.chineseInvisible = chineseInvisible;
    }

    //get and set

     Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

     int getId() {  //提取id值，并返回一个id值
        return id;
    }

     void setId(int id) { //将新值设为id值
        this.id = id;
    }

     String getWord() {   //提取英文值，并返回一个英文值
        return word;
    }

    public void setWord(String word) {  //将新值设为Word值
        this.word = word;
    }


     String getChineseMeaning() {  //提取中文列值，并返回一个列值
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {   //将新值设为列值
        this.chineseMeaning = chineseMeaning;
    }
}
