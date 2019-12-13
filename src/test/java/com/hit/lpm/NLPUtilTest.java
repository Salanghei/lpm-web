package com.hit.lpm;

import com.hit.lpm.common.nlp.NLPUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-11-20 15:45
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class NLPUtilTest {

    @Test
    public void nlptest() {
        System.out.println(NLPUtil.segment(" 大数据 机器学习 自动控制元件 概率论 计算机组成原理 水环境 容量"));
    }

    public static void main(String[] args) throws IOException {
        String[] files = {"THUOCL_animal", "THUOCL_caijing", "THUOCL_car", "THUOCL_chengyu", "THUOCL_diming",
                "THUOCL_food", "THUOCL_IT", "THUOCL_law", "THUOCL_lishimingren", "THUOCL_medical", "THUOCL_poem"};
        for (String file : files) {
            transfer(file);
        }
    }

    private static void transfer(String path) throws IOException {
        String dir = "D:\\doc\\GitHub\\LPM-WEB\\data\\dictionary\\thu\\";
        File file = new File(dir + path + ".txt");
        File outFile = new File(dir + "n_" + path + ".txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String line = null;
        int num = 0;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\t");
            if (words.length >= 2) {
                String newLine = words[0] + "n" +words[1];
                writer.write(newLine + "\n");
            }
            num++;

        }
        reader.close();
        writer.flush();
        writer.close();
    }


}
