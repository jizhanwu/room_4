package com.example.room_4;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class WordViewModel extends AndroidViewModel {
    private  WordRepository wordRepository;
    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

     //借助LiveData 的方法，找仓库要数据，一但数据有变化会自动通过UI层更新
     LiveData<List<Word>> cangku_Neibu_Liebiao_Neirong() {
        //返回一个，去调取仓库内的（当前列表内容）
        return wordRepository.cangkuLiebiao();
     }
     LiveData<List<Word>> findWordsWithPatten(String patten){
        return wordRepository.findWordsWithPatten(patten);
     };

     //主线程（UI线程）为这个方法传递参数（word1,word2),//带着参数让仓库去执行，并得到数据
    void insterWords(Word...words) { wordRepository.insterWords(words); }
    void updateWords(Word...words){ wordRepository.updateWords(words);  }
    void deleteWords(Word...words){wordRepository.deleteWords(words);}
    void deleteAllWords(Word...words){ wordRepository.deleteAllWords();  }


    }




