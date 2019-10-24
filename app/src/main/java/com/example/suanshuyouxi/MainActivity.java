package com.example.suanshuyouxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    //创建常量和变量
    Word word;
     Button button_insert,button_clere;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    ShiPeiQi shiPeiQi1,shiPeiQi2;  //在主线创建2个Adapater
    Switch aSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shiPeiQi1 = new ShiPeiQi(true);//第一个适配器，身上标签布尔 true
        shiPeiQi2 = new ShiPeiQi(false);//第一个适配器，身上标签布尔 false
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //主线程调取适配器1，身上标签是:true，即:useCardView：ture, 主适配器立即寻找对应的Loyout文件
        recyclerView.setAdapter(shiPeiQi1);
        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //根据这个参数 isChecked
                if(isChecked) {//如果已选中
                    //执行身上标签为"false"的适配品即:useCardView：false
                    recyclerView.setAdapter(shiPeiQi2);
                }else {
                    //执行身上标签为"ture"的适配品即:useCardView：ture
                    recyclerView.setAdapter(shiPeiQi1);
                }
            }
        });



        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        //通过LiveData类，当底层数据发生变化时，通过 observe呼叫onChange方法，更新UI
        wordViewModel.cangku_Neibu_Liebiao_Neirong().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                shiPeiQi1.setAllWords(words); //身上标签为"ture"的适配器,显示全部内容
                shiPeiQi2.setAllWords(words);//身上标签为"false"的适配器,显示全部内容
                //notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用shipeiqi1/2来刷新每个Item的内容。
                shiPeiQi1.notifyDataSetChanged();
                shiPeiQi2.notifyDataSetChanged();
            }
        });
        //为4个按钮赋值ID
        button_insert = findViewById(R.id.button_1);
        button_clere = findViewById(R.id.button3);


        //添加插入按钮点击事件
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycley",
                        "View",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "变量",
                        "整数类型"
                };
                for(int i = 0;i<english.length;i++){
                    wordViewModel.insterWords(new Word(english[i],chinese[i]));
                }

            }
        });
        //添加清除按钮点击事件
        button_clere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });

    }

}
    