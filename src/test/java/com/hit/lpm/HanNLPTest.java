package com.hit.lpm;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hit.lpm.common.utils.StringUtil;
import com.hit.lpm.portrait.service.TopicService;

import java.io.*;
import java.util.*;


/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-10-22 14:46
 **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HanNLPTest {
    //@Autowired
    private TopicService topicService;

    //写入数据库
//    @Test
//    public void insertTopic() throws IOException {
//        generateShortFile();
//        testSegment();
//        File file = new File("terms.txt");
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        String str = null;
//        while ((str = reader.readLine()) != null) {
//            Topic topic = new Topic();
//            topic.setTopicName(str.split(":")[0]);
//            topic.setCount(Integer.valueOf(str.split(":")[1]));
//            topicService.insert(topic);
//        }
//        reader.close();
//    }

    //通过课程文件生成领域关键词
    public static void segmentDomain() throws IOException {
        Map<String, Integer> wordMap = new HashMap<>();
        File file = new File("course_type.txt");
        File outFile = new File("domain.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String str = null;
        while ((str = reader.readLine()) != null) {
            String[] words = StringUtil.spilt(str);
            List<String> termList = new ArrayList<String>(Arrays.asList(words));
            for (String term : termList) {
                wordMap.put(term, wordMap.getOrDefault(term, 0) + 1);
            }
        }
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> mapping : list) {
            writer.write(mapping.getKey() + ":" + mapping.getValue() + "\n");
        }
        reader.close();
        writer.flush();
        writer.close();
    }


    //对测试文件进行分词
    private static void testSegment() throws IOException {
        Map<String, Integer> wordMap = new HashMap<>();
        File file = new File("keywords.txt");//搜索日志
        File outFile = new File("terms.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String str = null;
        while ((str = reader.readLine()) != null) {
            //List<Term> termList = SpeedTokenizer.segment(str);
            // List<Term> termList = NLPTokenizer.segment(str);
            List<Term> termList = HanLP.newSegment().enableNameRecognize(true).seg(str);
            for (Term term : termList) {
                if (term.word.length() >= 2 && StringUtil.isWord(term.word)) {
                    wordMap.put(term.word, wordMap.getOrDefault(term.word, 0) + 1);
                }

            }
        }
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> mapping : list) {
            writer.write(mapping.getKey() + ":" + mapping.getValue() + "\n");
        }
        reader.close();
        writer.flush();
        writer.close();

    }

    //生成一个短测试文件
    private static void generateShortFile() throws IOException {
        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\10_搜索日志\\10_搜索日志_0.csv");
        File outFile = new File("searchLogSample.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String str = null;
        //16882392行
        int line = 0;
        while ((str = reader.readLine()) != null && line < 100000) {
            String word = str.split(",")[2];
            if (!word.isEmpty()) writer.write(word + "\n");
            line++;
        }
//        System.out.println(line);
        reader.close();
        writer.flush();
        writer.close();
    }

    private static void countKeyword() throws IOException {
        Map<String, Integer> wordMap = new TreeMap<>();
        File file = new File("searchLogSample.txt");
        File outFile = new File("keywords.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String str = null;
        while ((str = reader.readLine()) != null) {
            if (!str.isEmpty()) wordMap.put(str, wordMap.getOrDefault(str, 0) + 1);
        }
        for (String k : wordMap.keySet()) {
            writer.write(k + ":" + wordMap.get(k) + "\n");
        }
        reader.close();
        writer.flush();
        writer.close();
    }



    public static void main(String[] args) throws IOException {
        System.out.println(StringUtil.isWord("哈哈哈"));
        System.out.println(StringUtil.isWord("word"));
        //segmentDomain();
        //countKeyword();
//        generateShortFile();
        testSegment();
//        List<Term> termList = StandardTokenizer.segment("商品和服务");
//        for (Term term : termList) {
//            System.out.println(term + " " + term.getFrequency());
//        }
    }

}
