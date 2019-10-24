package com.example.suanshuyouxi;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class WordViewModel extends AndroidViewModel {
    // 对仓库类文件，进行变量实例化
    private  WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        // 对仓库类文件，进行变量赋值
      wordRepository = new WordRepository(application);
    }
     //借助LiveData 的方法，找仓库要数据，一但数据有变化会自动通过UI层更新
     LiveData<List<Word>> cangku_Neibu_Liebiao_Neirong() {
        //返回一个，去调取仓库内的（当前列表内容）
        return wordRepository.cangkuLiebiao();
     }

     //主线程（UI线程）为这个方法传递参数（word1,word2)
    void insterWords(Word...words) {
        //让仓库去根据参数执行，并得到数据
        wordRepository.insterWords(words);
    }
    void updateWords(Word...words){
        wordRepository.updateWords(words);
    }
    void deleteWords(Word...words){wordRepository.deleteWords(words);   }
    void deleteAllWords(Word...words){wordRepository.deleteAllWords();   }



}
