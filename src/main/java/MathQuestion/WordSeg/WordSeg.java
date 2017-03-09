package MathQuestion.WordSeg;

import MathQuestion.tntity.Word;
import MathQuestion.utils.GeneralTools;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.cn.tag.NERTagger;
import org.fnlp.util.exception.LoadModelException;

import java.util.*;

/**
 * Created by liuxiaoyu on 16-11-7.
 */

/**
 * 分词器
 */
public class WordSeg {

    //获取带有词性标注的分词结果
    public static List<Word> wordSegment(String sentence,String resources) {
        List<Word> wordsList=new ArrayList<Word>();
        String path=resources+"/models";
        try {
            CNFactory factory= CNFactory.getInstance(path);
            String results=factory.tag2String(sentence);
            String[] words=results.split("\\s+");
            for (String word:words) {
                String[] s=word.split("\\/");
                Word w=new Word();
                w.setContent(s[0]);
                w.setTag(s[1]);
                wordsList.add(w);
            }
        } catch (LoadModelException e) {
            e.printStackTrace();
        }
        return wordsList;

    }

    //单词计数
    public static List<String> wordsSortByFreWithTag(List<Word> words,String tag) {
        List<String> list=new ArrayList<String>();
        Map<String,Integer> map=new LinkedHashMap<String, Integer>();
        //统计单词频率
        for (Word word:words) {
            if(word.getTag().equals(tag)) {
                String content=word.getContent();
                if (map.containsKey(content)) {
                    int c = map.get(content);
                    map.put(content,c+1);
                }
                else {
                    map.put(content,1);
                }
            }
        }

        //根据单词频率排序
        list= GeneralTools.sortByValue(map);
        return list;

    }
}
