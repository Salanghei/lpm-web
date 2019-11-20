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
        //List<Term> terms = HanLP.newSegment().enableNameRecognize(true).seg(sentence);
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
        //System.out.println(segment("精算师因其高薪和大量的晋升机会而被CareerCast.com评为2015年最佳职业。你可能听说过精算学，或者你可能认识一位精算师，但是你是否知道什么是精算师？通过本课程的学习，学员可以了解精算师职业的各个方面。不要害怕这门课程将有“一大堆数学知识”。我们将共同超越数学，学习与风险有关的精算方法，利用来自以下领域的示例：财务投资银行保险学员将学习到精算学如何利用数学和统计学方法在这些行业和其他专业领域进行风险评估。学员也将利用Excel（或类似的电子表格工具）体验“实操性”学习方法，通过模拟方法，对公司的财务状况做出预测和调查，为公司选取相应的战略。本课程为学员精心设计了多种背景，而只将中学/高中数学水平作为唯一的数学背景设定。如果学员没有学习过任何微积分等专业课程，按照课程的设计，学员可以跳过这些章节而不会影响对于课程其余内容的理解。对于数学基础较好的学员来说，我们提供了扩展性问题做进一步测试。无论学员的基础如何，都可以学习到大量关于精算学的知识！欢迎立即参加。在课程学习之前和学习过程中与课程社区进行沟通，加入我们的Facebook小组，并通过#actuarialedX在推特上联系我们。"));
        System.out.println(segment(" 大数据 机器学习 自动控制元件 概率论 计算机组成原理 水环境 容量"));

    }
}
