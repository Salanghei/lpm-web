package com.hit.lpm.common.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hit.lpm.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: lmp-web
 * @description: 自然语言处理工具
 * @author: guoyang
 * @create: 2019-11-05 20:30
 **/
public class NLPUtil {

    //项目统一使用的分词方法，具体哪种方法更加优秀，可以通过后续测试再进行改进
    public static List<String> segment(String sentence) {
        List<String> words = new ArrayList<>();
       // List<Term> terms = HanLP.newSegment().enableNameRecognize(true).seg(sentence);
        DijkstraSegment segment = new DijkstraSegment();
        List<Term> terms = segment.enableNameRecognize(true).seg(sentence);
        for (Term term : terms) {
            if (StringUtil.isChineseWord(term.word) && term.word.length() >= 2 && (term.nature.startsWith('n') || term.nature.startsWith('g'))) {
                words.add(term.word);
            }
        }
        return words;
    }

    public static void main(String[] args) {
//        System.out.println(segment("精算师因其高薪和大量的晋升机会而被CareerCast.com评为2015年最佳职业。你可能听说过精算学，或者你可能认识一位精算师，但是你是否知道什么是精算师？通过本课程的学习，学员可以了解精算师职业的各个方面。"));
//        System.out.println(segment(" 大数据 机器学习 自动控制元件 概率论 计算机组成原理 水环境 容量"));
//        System.out.println(segment("窃听大数据：互联时代的隐私与监听"));
        int max = 8999;
        int count =200000;

        System.out.println((double) max/count);

    }
}
