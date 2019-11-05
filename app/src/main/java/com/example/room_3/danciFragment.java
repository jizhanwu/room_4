package com.example.room_3;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class danciFragment extends Fragment {
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private ShiPeiQi shiPeiQi1,shiPeiQi2;
    private FloatingActionButton floatingActionButton;



    public danciFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danci_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置变量赋值
        wordViewModel = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        shiPeiQi1 = new ShiPeiQi(true,wordViewModel);
        shiPeiQi2 = new ShiPeiQi(false,wordViewModel);
        recyclerView.setAdapter(shiPeiQi1);

        wordViewModel.cangku_Neibu_Liebiao_Neirong().observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = shiPeiQi1.getItemCount();
                shiPeiQi1.setAllWords(words);
                shiPeiQi2.setAllWords(words);
                if (temp!=words.size() ){
                    shiPeiQi1.notifyDataSetChanged();
                    shiPeiQi2.notifyDataSetChanged();
                }

            }
        });
        //添加底部+号按钮点击监听事件
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义导航管理器变量，并实例赋值
                 NavController navController = Navigation.findNavController(v);
                 //点击跳转到添加单词页面
                navController.navigate(R.id.action_danci_Fragment2_to_add_Fragment);

            }
        });

    }


}
