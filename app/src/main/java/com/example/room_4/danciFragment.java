package com.example.room_4;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class danciFragment extends Fragment {
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private ShiPeiQi shiPeiQi1,shiPeiQi2;
    private FloatingActionButton floatingActionButton ,floatingActionButton2;
    private LiveData<List<Word>> filteredWords;
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_VIEW = "is_using_card_view";
    private List<Word> muqian_words;


    public danciFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    //实现顶部菜单，输入"onOptionsItemSelected"选项条 选择”栏
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //如果有多个选项，要分别单个处理
        switch(item.getItemId()){
            case R.id.clearData:
            //添加一个“是否确认清除数据”对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("清空数据");

                    //设置确定按钮“点击监听”事件
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    wordViewModel.deleteAllWords();

                }

            });

                //设置取消按钮“点击监听”事件
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create();
                    builder.show();
                    break;


                //实现“点击切换视图”功能
            case R.id.switchView:
                //设置“共享偏好”以保存当时的状态，先“赋值”
                SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                //"共享偏好"修改
                SharedPreferences.Editor editor = shp.edit();
                boolean viewType = shp.getBoolean(IS_USING_CARD_VIEW,false);
                if(viewType){
                  recyclerView.setAdapter(shiPeiQi1);
                  //修改“共享偏好”的布尔值为“另一个”
                  editor.putBoolean(IS_USING_CARD_VIEW,false);

                }else{
                    recyclerView.setAdapter(shiPeiQi2);
                    editor.putBoolean(IS_USING_CARD_VIEW,true);

                }
                //不要忘记，将此时数据存储
                editor.apply();



        }
        return super.onOptionsItemSelected(item);
    }






    // 添加顶部搜索栏      方法：输入"onCreateOptions"自动 创建“选项菜单”栏  搜索栏
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //xml转换Contration
        inflater.inflate(R.menu.main_menu,menu);
        //设定一个"searchView""  放大镜 ，实例变量并赋值
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //设定“SearchView”即搜索区域宽度，1000dp
        searchView.setMaxWidth(700);
        //设置"SearchView监听器" 询问 文本 监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //当输入内容实时改变，呼叫下面方法体
               //这句用来测试 Log.d("myLog", "onQueryTextChange: "+ newText);
                //赋值时顺便把两边空白不需要的东西切掉，trim()
                String patten = newText.trim();
                //****搜索的观察容易和原始页面观察发生碰撞，所以在搜索之前要“移除”之前的所有观察
                filteredWords.removeObservers(requireActivity());
                //从ViewModel中调取接口
                filteredWords = wordViewModel.findWordsWithPatten(patten);
                //添加实时观察更新
                filteredWords.observe( requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = shiPeiQi1.getItemCount();
                        muqian_words = words;
                        //提交的数据列表会在后台差异化比较，根据结果，来刷新界面
                        if (temp!=words.size() ){
                            shiPeiQi1.submitList(words); //递交列表
                            shiPeiQi2.submitList(words);//递交列表

                    }}
                });

                return true;//如果逻辑事件认为“完成”，不再向下面传递，就设“ture",否则：false
            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danci_, container, false);
    }
    //主ActivityCreate页面创建，
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置变量赋值
        wordViewModel = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        shiPeiQi1 = new ShiPeiQi(true,wordViewModel);
        shiPeiQi2 = new ShiPeiQi(false,wordViewModel);
        //主页显示动画，设置一个回调函数，刷新序列号
        recyclerView.setItemAnimator(new DefaultItemAnimator(){
           //动画结束后，刷新序列号
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //防空指针
                if(linearLayoutManager!=null){
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i = firstPosition; i <= lastPosition; i++){
                        ShiPeiQi.MyViewHolder holder = (ShiPeiQi.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        //防止holder出现空指针
                        if(holder!=null){
                            holder.textView_xuhao.setText(String.valueOf(i+1));
                        }

                    }


                }

            }
        });


        //设置“共享偏好”以保存当时的状态，先“赋值” ,备注：此功能在后台有缓存，不会增加系统负担
        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        //再次读取“共享偏好”的“布尔值：ture 还是 false”
        boolean viewType = shp.getBoolean(IS_USING_CARD_VIEW,false);
        if (viewType){
            recyclerView.setAdapter(shiPeiQi2);
        }else{
            recyclerView.setAdapter(shiPeiQi1);
        }

        //recyclerView.setAdapter(shiPeiQi1);
        //不过滤，获取所有列表内容，整体下移，为了让新添加 内容始终显示在页面顶部
        filteredWords = wordViewModel.cangku_Neibu_Liebiao_Neirong();
        //添加观察
       filteredWords.observe( requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = shiPeiQi1.getItemCount();
                muqian_words = words;
                //提交的数据列表会在后台差异化比较，根据结果，来刷新界面
                if (temp!=words.size() ){
                    //视图整体向下滚动200dp
                    recyclerView.smoothScrollBy(0,-250);
                    shiPeiQi1.submitList(words); //提交列表
                    shiPeiQi2.submitList(words);
                }

            }
        });


       //添加“滑动删除”功能
        //                                                       方向：上下，左或右
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override//滑动一行，进行删除
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Word wordToDelete = muqian_words.get(viewHolder.getAdapterPosition());
                wordViewModel.deleteWords(wordToDelete);

                //安掉系统拖动控件(第一个参数：哪个视图，第二个：字符串，第三个：时间)
                Snackbar.make(requireActivity().findViewById(R.id.danci_fragment),"删除了一个词汇",Snackbar.LENGTH_SHORT)
                        //设置一个动作
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wordViewModel.insterWords(wordToDelete);

                            }
                        })
                        .show();



            }
        }).attachToRecyclerView(recyclerView);

        //添加底部+号按钮点击监听事件
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton2 =requireActivity().findViewById(R.id.floatingActionButton2);
        //主悬浮按钮“点击监听”事件
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义导航管理器变量，并实例赋值
                 NavController navController = Navigation.findNavController(v);
                 //点击跳转到添加单词页面
                navController.navigate(R.id.action_danci_Fragment2_to_add_Fragment);

            }
        });
        //副悬浮按钮“点击监听”事件
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("123456789","2");
                word.setId(18);
              wordViewModel.deleteWords(word);


            }
        });


    }


}
