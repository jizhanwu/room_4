package com.example.room_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;


public class MainActivity extends AppCompatActivity {

private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NavController赋值
        navController = Navigation.findNavController(findViewById(R.id.fragment));
        //为左上角加一个“小箭头”
        NavigationUI.setupActionBarWithNavController(this, navController);

    }



    @Override
    //为左上解“小箭头”添加功能
    public boolean onSupportNavigateUp() {
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //此条是系统建议必须加上的，不然"hideSoftInputFromWindo"可能会出现空指针
        assert imm != null;
        imm.hideSoftInputFromWindow(findViewById(R.id.fragment).getWindowToken(),0);
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
    //
    // 隐藏软键盘
    @Override
    protected void onResume() {
        super.onResume();
    }
}
    