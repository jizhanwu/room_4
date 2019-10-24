package com.example.suanshuyouxi;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


                                             //<>内容为写完小的ViewHoldier小方格后，随后添加的
//内容添加完成后，点击小红灯泡，implement methodg(导入方法），出现对话框，3个选中，点“OK”，重写;:
/*
1.getView(int position(位置), View convertView(视图转换), ViewGroup parent(父类) 模糊看不清
2.onBingViewHolder(Holer)
3.onCreatViewHolder(
 */
//创建一个类，实现适配器的功能（创建—>数据绑定->返回）
public class ShiPeiQi extends RecyclerView.Adapter<ShiPeiQi.MyViewHolder> {
    private List<Word> allWords = new ArrayList<>();//为了防空指针（为allWords在内存创建一个新的空间）
    private boolean useCardView;//新建一个布尔变量，判断ture还是fose？

    ShiPeiQi(boolean useCardView) {//这个适配器通过useCardView来承载是“错”还是“对”？
        this.useCardView = useCardView;
    }

    void setAllWords(List<Word> allWords) { //数组列表内容即View
        this.allWords = allWords;//new ArrayList<>()，数组字符串列表 内容赋值给allWords
    }

    //点击3个，系统自动重写了以下带@Override 3个方法:
    @NonNull
    @Override//系统自动重写的第一个方法,（创建重写一个ViewHOlder)
    // LayoutInflater 直译为 布局填充器，常用 inflate() 将一个 xml 布局文件转换成一个 View
    //参数一：ViewGroup parent，是指RecycleView的布局 ,参数二：int viewType，是指Item的属性
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());//布局填充器

        View itemView;  //适配器ViewHolder（即ItemView)根据useCardView的值"ture or false"还确定用哪个布局文件
        if (useCardView) {
            //第一个参数为想加载的布局资源，第二个将视图放在根视图，第三个视图载入完成 后是否放入根视图中
            itemView = layoutInflater.inflate(R.layout.cell_kapian,  parent, false);

        } else {
            itemView = layoutInflater.inflate(R.layout.mei_yi_hang, parent, false);
        }

        return new MyViewHolder(itemView);//呼叫MyViewHolder的ConstraintLayout,itemView作为参数传递进去


    }

    @Override//系统自动重写的第二个方法，（对itemView中的数据变量进行指定，也叫数据绑定）
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //绑定逻辑，获取一个word,利用一个position(位置),把三个变量进行关联
        Word word = allWords.get(position);
        //getid返回的是一个整数，/用String包裹一下，否则会报错
        //position指当前列表位置+1，默认从0开始的
        holder.textView_xuhao.setText(String.valueOf(position + 1));
        //不用包裹，因为返回值是一个String，调取数据库Entity(word)列的名称
        holder.textView_Eniglishi.setText(word.getWord());
        holder.textView_chinese.setText(word.getChineseMeaning());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//设置点击跳转第三方网页链接
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=google" + holder.textView_Eniglishi.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override//系统自动重写的第三个方法,(返回列表数量数值)
    public int getItemCount() {
        return allWords.size();
    }




     //为节省运行资源效率，一般自定义类 ViewHolder 来减少无限的findViewById() 的使用
    //创建一个类(小红灯泡..创建并建设正在匹配超好的）viewHolder(可当作缓存，按需加载,重复使用,提高性能)，
    // 定义一个ViewHolder,也会称为 :系统分配的名叫itemView(一条视图）
    //内部类前加一个"static"防止内存泄漏（即:执行时找不到内容，导致运行怠速内存爆满溢出）
         static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_xuhao, textView_Eniglishi, textView_chinese; //为3个内容进行变量创建

        MyViewHolder(@NonNull View itemView) {//方法创建完后，点击小红灯泡，系统自动生成这些代码
            super(itemView);//此行为系统自动生成，参数类型：View,参数名：itemView(细分小格子容器）
            textView_xuhao = itemView.findViewById(R.id.textView_xuhao);
            textView_Eniglishi = itemView.findViewById(R.id.textView_Eniglishi);
            textView_chinese = itemView.findViewById(R.id.textView_chinese);

        }
    }


      }
/*
当在开发中无论是Activity中还是Fragment中基本上都会使用到这个功能：使用这个功能要注意以下几点：

1：当项目中需要很多个不同的RecyclerView来实现的时候就先定义一个中的RecyclerView来盛放子RecyclerView

2：定义一个总的Adapter来加载不同的RecyclerView

3：注意子RecyclerView的viewType类型一定不能相同要不然就只加载一个

4：一定要使用LayoutManager来管理RecyclerView，这是RecyclerView的优于ListView的地方，可扩展性强

5：等理解深入的时候要学会解决滑动冲突，因为我们很有可能需要上下滑动和水平滑动。
————————————————
版权声明：本文为CSDN博主「AndyYuan317」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_42618969/article/details/81290143
 */