package com.hit.lpm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hit.lpm.common.nlp.NLPUtil;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.StudentPortrait;
import com.hit.lpm.portrait.model.Topic;
import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.portrait.service.TopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: lmp-web
 * @description:
 * @author: guoyang
 * @create: 2019-11-06 20:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TopicService topicService;

    @Test
    public void insertStudent() {
        Student student = studentService.selectById(1);
        StudentPortrait studentPortrait = new StudentPortrait(student);
        mongoTemplate.insert(studentPortrait);
    }

    //生成兴趣意图
    @Test
    public void generateStudentTopics() {
        Map<String, Topic> topicMap = getTopicMap();
        int line = 0;
        List<StudentPortrait> studentPortraitList = mongoTemplate.findAll(StudentPortrait.class);
        System.out.println(studentPortraitList.size());
        for (StudentPortrait studentPortrait : studentPortraitList) {
            // StudentPortrait studentPortrait = mongoTemplate.findOne(Query.query(Criteria.where("studentId").is(1)), StudentPortrait.class);
            List<String> keywords = studentService.selectKeywordsByStuId(studentPortrait.getStudentId());
            Map<String, Integer> words = new HashMap<>();
            for (String keyword : keywords) {
                for (String word : NLPUtil.segment(keyword)) {
                    words.put(word, words.getOrDefault(word, 0) + 1);
                }
            }
            List<Topic> topics = new LinkedList<>();
            for (String k : words.keySet()) {
                Topic topic = topicMap.get(k);
                if (topic != null) {
                    topic.setCount(words.get(k));
                    topics.add(topic);
                }
            }
            studentPortrait.setTopics(topics);
            mongoTemplate.save(studentPortrait);
            if (line % 1000 == 0) System.out.println(line);
            line++;
        }
    }

    private Map<String, Topic> getTopicMap() {
        Map<String, Topic> topicMap = new HashMap<>();
        int page = 0;
        int limit = 10000;
        EntityWrapper<Topic> wrapper = new EntityWrapper<>();
        Page<Topic> topicPage;
        do {
            topicPage = new Page<>(page, limit);
            System.out.println("当前查询到第" + (page * limit) + "行");
            topicService.selectPage(topicPage, wrapper);
            List<Topic> topicList = topicPage.getRecords();
            for (Topic topic : topicList) {
                topicMap.put(topic.getTopicName(), topic);
            }
            page++;
        } while (topicPage.hasNext());
        return topicMap;
    }

    //生成基础画像
    @Test
    public void generateStudentPortrait() {
        int page = 0;
        int limit = 10000;
        EntityWrapper<Student> wrapper = new EntityWrapper<>();
        Page<Student> studentPage;
        do {
            studentPage = new Page<>(page, limit);
            System.out.println("当前查询到第" + (page * limit) + "行");
            studentService.selectPage(studentPage, wrapper);
            List<Student> studentList = studentPage.getRecords();
            List<StudentPortrait> studentPortraits = new LinkedList<>();
            for (Student student : studentList) {
                StudentPortrait studentPortrait = new StudentPortrait(student);
                studentPortraits.add(studentPortrait);
            }
            mongoTemplate.insertAll(studentPortraits);
            page++;
        } while (studentPage.hasNext());
    }



    public static void main(String[] args) {

    }
}
