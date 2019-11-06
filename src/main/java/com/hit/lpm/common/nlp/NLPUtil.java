package com.hit.lpm.common.nlp;

import com.hankcs.hanlp.HanLP;
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
        List<Term> terms = HanLP.newSegment().enableNameRecognize(true).seg(sentence);
        for (Term term : terms) {
            if (StringUtil.isWord(term.word) && term.word.length() >= 2) {
                words.add(term.word);
            }
        }
        return words;
    }


}
