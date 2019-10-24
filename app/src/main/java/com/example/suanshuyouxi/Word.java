package com.example.suanshuyouxi;

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

    //创建一个与类名称相关的方法(对象或变量或函数)，内部实例化变量（通过鼠标右键Genreate,Constructor 或Alt+Insert
     Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }
    //创建Get ane Set,即方法体，为接口返回具体值，（通过鼠标右键Genreate,Get"and"Set 或Alt+Insert
    //实现了接口:getid,getWord,getChineseMeaning

     int getId() {  //提取id值，并返回一个id值
        return id;
    }

     void setId(int id) { //将新值设为id值
        this.id = id;
    }

     String getWord() {   //提取word值，并返回一个word值
        return word;
    }

    public void setWord(String word) {  //将新值设为Word值
        this.word = word;
    }

     String getChineseMeaning() {  //提取英文列值，并返回一个列值
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {   //将新值设为列值
        this.chineseMeaning = chineseMeaning;
    }
}
