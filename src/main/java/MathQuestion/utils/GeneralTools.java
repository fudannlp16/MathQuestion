package MathQuestion.utils;

/**
 * Created by liuxiaoyu on 16-11-7.
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class GeneralTools {
    /**
     * 根据Value之从大到小排序
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> List<K> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });

        List<K> kList=new ArrayList<K>();
        for (Map.Entry<K, V> entry : list) {
            kList.add(entry.getKey());
        }
        return kList;
    }

    public static int getCount(String str,String substr) {
        int count=0;
        Pattern pattern=Pattern.compile(substr);
        Matcher matcher=pattern.matcher(str);
        while (matcher.find())
            count++;
        return count;
    }

    public static String getNum(String str) {
        Pattern pattern=Pattern.compile("\\D*([\\d]+)\\D*");
        Matcher matcher=pattern.matcher(str);
        if (matcher.find())
            return matcher.group(1);
        else
            return null;
    }
    //n也表示数字
    public static String getNum2(String str) {
        Pattern pattern=Pattern.compile("\\D*([\\d]+)\\D*");
        Matcher matcher=pattern.matcher(str);
        String result=null;
        while (matcher.find())
            result=matcher.group(1);
        return result;
    }

    public static int countNum(String str) {
        int count=0;
        Pattern pattern=Pattern.compile("\\d+[^年月日\\d]");
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static boolean isNum(String str) {
        Pattern pattern=Pattern.compile(".*\\d+.*");
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }

    //判断字符串是否为数字
    public static boolean str_is_Num(String str) {
        Pattern pattern=Pattern.compile("^\\d+$");
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }

    public static void main(String[] args) {

        System.out.println(countNum("5456年"));
    }
}
