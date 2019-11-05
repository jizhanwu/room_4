package com.example.room_3;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Fragment extends Fragment {
    private Button button;
    private EditText editTextEnglish,editTextChinese;
    private WordViewModel wordViewModel;



    public Add_Fragment() {
        // Required empty public constructor
    }

    //每次创建、绘制该Fragment的View组件时回调该方法，Fragment将会显示该方法返回的View组件。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_, container, false);
    }
    //空白处输入“onAc....”系统会自动 重建onActivityCreated“
    //当Fragment所在的Activity被启动完成后回调该方法。
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //变量赋值
        final FragmentActivity activity = requireActivity();
        wordViewModel = ViewModelProviders.of(activity).get(WordViewModel.class);
        button = activity.findViewById(R.id.button_tijiao);
        editTextChinese = activity.findViewById(R.id.editText_Chinese);
        editTextEnglish = activity.findViewById(R.id.editText_Englishi);
        //默认提交按钮颜色是灰色不可点击的
        //setEnabled为false，该控件将不再响应点击、触摸以及键盘事件等，处于完全被禁用的状态，
        // 并且该控件会被重绘。对于Button来说，设置为false，控件会变灰不可点击。对于EditText来说，
        // 控件也会变灰，且不可输入文字。
        button.setEnabled(false);
        //英文输入框标签请求为屏幕内的焦点View
        editTextEnglish.requestFocus();
        //输入办法管理器

         InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //显示软键盘，默认为英文输入
        imm.showSoftInput(editTextEnglish,0);
        //TextWatcher，监听文本变化，实例化
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取文本，变字符串，去掉前后空格
                String english = editTextEnglish.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                //英文和中文不等于“空”，提交按钮变为正常“黑色”
                button.setEnabled(!english.isEmpty()&&!chinese.isEmpty());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        //为两个输入框添加“文本改变监听事件”
        editTextEnglish.addTextChangedListener(textWatcher);
        editTextChinese.addTextChangedListener(textWatcher);
        //提交按钮“点击监听”
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入内容
                String english = editTextEnglish.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                //实例化一个数据库，传递参数new Word(Word...words)
                Word word = new Word(english,chinese);
                //通过ViewModel插入数据库
                wordViewModel.insterWords(word);
                //导航控制器
                NavController navController = Navigation.findNavController(v);
                //提交按钮点击后，进入上层页面，即“主页”
                navController.navigateUp();
                //输入方法管理，
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                //此条是系统建议必须加上的，不然"hideSoftInputFromWindo"可能会出现空指针
                assert imm != null;
                //隐藏输入窗口
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
        });





    }
}
