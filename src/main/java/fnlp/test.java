package fnlp;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;

/**
 * Created by liuxiaoyu on 16-9-7.
 */
public class test {
    public static void main(String[] args) throws LoadModelException {
        String path=test.class.getClassLoader().getResource("models").getPath();
        CNFactory factory = CNFactory.getInstance(path);
        String[] words = factory.seg("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。");

        // 打印分词结果
        for(String word : words) {
            if (word.length()>=2)
            System.out.print(word + " ");
        }
        System.out.println();
    }
}
