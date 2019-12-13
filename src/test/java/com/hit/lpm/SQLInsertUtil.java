package com.hit.lpm;


import com.hit.lpm.common.nlp.NLPUtil;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.StudentCourseRelation;
import com.hit.lpm.portrait.model.Topic;
import com.hit.lpm.portrait.service.CourseService;
import com.hit.lpm.portrait.service.StudentCourseRelationService;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.hit.lpm.common.utils.StringUtil;
import com.hit.lpm.portrait.model.Course;
import com.hit.lpm.portrait.model.Student;
import com.hit.lpm.portrait.model.Topic;
import com.hit.lpm.portrait.service.CourseService;

import com.hit.lpm.portrait.service.StudentService;
import com.hit.lpm.portrait.service.TopicService;
import com.hit.lpm.system.model.User;
import com.hit.lpm.system.service.UserRoleService;
import com.hit.lpm.system.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: lmp-web
 * @description:文件导入数据库工具
 * @author: guoyang
 * @create: 2019-10-26 16:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SQLInsertUtil {

    //@Autowired
    CourseService courseService;
    @Autowired
    TopicService topicService;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentCourseRelationService studentCourseRelationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    public static void main(String[] args) throws IOException {
        courseSegment();
    }

    @Test
    public void insertTopic() throws IOException {
        List<Topic> topics = courseSegment();
        topicService.insertBatch(topics);
    }

    //对课程信息进行分词
    private static List<Topic> courseSegment() throws IOException {
        Map<String, Topic> wordMap = new TreeMap<>();
        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\1_课程信息\\1_课程信息_0.csv");//课程信息
        File outFile = new File("termsFromCourse.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        int line = 0;
        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext() && line < 100000) {
                //课程id,课程名称,课程类别,授课教师,开课机构org,开课机构名称,开课时间,结课时间,先修要求,课程介绍
                String[] cols = iterator.next();
                if (line != 0) {
                    List<String> termList = new ArrayList<>();
                    if (!cols[1].isEmpty())
                        termList.addAll(NLPUtil.segment(cols[1]));//课程名称
                    if (!cols[3].isEmpty())
                        termList.addAll(NLPUtil.segment(cols[3]));//授课教师
                    if (!cols[9].isEmpty())
                        termList.addAll(NLPUtil.segment(cols[9]));//课程介绍
                    String[] domains = {"其他"};
                    if (!cols[2].isEmpty())
                        domains = cols[2].split("[^\\dA-Za-z\\u3007\\u4E00-\\u9FCB\\uE815-\\uE864]");//课程类别
                    for (String term : termList) {
                        Topic topic;
                        if (wordMap.get(term) != null) {
                            topic = wordMap.get(term);
                            topic.setCount(topic.getCount() + 1);
                            Map<String, Integer> domainMap = topic.getDomainMap();
                            for (String domain : domains) {
                                domainMap.put(domain, domainMap.getOrDefault(domain, 0) + 1);
                            }
                        } else {
                            topic = new Topic();
                            topic.setTopicName(term);
                            topic.setCount(1);
                            Map<String, Integer> domainMap = new HashMap<>();
                            for (String domain : domains) {
                                domainMap.put(domain, 1);
                            }
                            topic.setDomainMap(domainMap);
                            wordMap.put(term, topic);
                        }
                    }
                }
                line++;
                if (line % 100 == 0) System.out.println("当前执行到" + line + "行！");
            }
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Topic> topics = new ArrayList<>();
        for (String k : wordMap.keySet()) {
            topics.add(wordMap.get(k));
        }
        topics.sort(new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return o2.getCount().compareTo(o1.getCount());
            }
        });
        for (Topic topic : topics) {
            writer.write(topic.getTopicName() + ":" + topic.getDomain() + " " + topic.getCount() + " " + topic.getDomainMap() + "\n");
        }
        reader.close();
        writer.flush();
        writer.close();
        return topics;
    }

    //将课程信息写入数据库
    //@Test
    public void insertCourse() {

        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\1_课程信息\\1_课程信息_0.csv");
        String str = null;
        int line = 0;
        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                //课程id,课程名称,课程类别,授课教师,开课机构org,开课机构名称,开课时间,结课时间,先修要求,课程介绍
                String[] cols = iterator.next();
                if (line != 0) {
                    Course course = new Course();
                    if (!cols[0].isEmpty()) course.setCourseId(cols[0]);
                    if (!cols[1].isEmpty()) course.setCourseName(cols[1]);
                    if (!cols[2].isEmpty()) course.setCourseType(cols[2]);
                    if (!cols[3].isEmpty()) course.setTeacher(cols[3]);
                    if (!cols[5].isEmpty()) course.setCourseSchool(cols[5]);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (!cols[6].isEmpty()) course.setStartTime(sdf.parse(cols[6]));
                    if (!cols[7].isEmpty()) course.setEndTime(sdf.parse(cols[7]));
                    if (!cols[8].isEmpty()) course.setPrerequisites(cols[8]);
                    if (!cols[9].isEmpty()) course.setCourseInfo(cols[9]);
                    if (courseService.selectById(course.getCourseId()) == null) courseService.insert(course);
                }
                line++;
                if (line % 100 == 0) System.out.println("当前执行到" + line + "行！");
            }
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将学生选课信息导入数据库
    @Test
    public void insertSC() throws FileNotFoundException {
        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\4_用户选退课信息\\4_用户选退课信息_0.csv");//课程信息
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int line = 0;
        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            List<StudentCourseRelation> scrs = new ArrayList<>();
            while (iterator.hasNext() && line < 100000000) {
                //课程id,用户id,是否处在选课状态,选课时间,退课时间,视频观看率,是否获得证书,获得证书日期,成绩,成绩级别（F为未通过）
                String[] cols = iterator.next();
                if (line != 0) {
                    String courseId = cols[0];
                    Integer stuId = Integer.valueOf(cols[1]);
                    StudentCourseRelation scr = new StudentCourseRelation();
                    scr.setCourseId(courseId);
                    scr.setStudentId(stuId);
                    scrs.add(scr);
                }
                line++;
                if (line % 10000 == 0) {
                    System.out.println("当前执行到" + line + "行！");
                    if (scrs.size() > 0) studentCourseRelationService.insertBatch(scrs);
                    scrs.clear();
                }
            }
            if (scrs.size() > 0) studentCourseRelationService.insertBatch(scrs);
            System.out.println(line);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void count() {
        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\12_用户信息\\12_用户信息_0.csv");
        String str = null;
        int line = 0;//3704497
        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                //用户id,姓名,昵称,性别,出生日期,教育程度,所在国家,所在省份,所在城市
                String[] cols = iterator.next();
                line++;
            }
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将学生信息写入数据库
    @Test
    public void insertStudent() {
        File file = new File("D:\\doc\\实验室\\教育大数据\\科委数据20160101-20181231\\12_用户信息\\12_用户信息_0.csv");
        String str = null;
        int line = 0;//3704497
        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            List<Student> students = new ArrayList<>();
            List<User> users = new ArrayList<>();
            while (iterator.hasNext()) {
                //用户id,姓名,昵称,性别,出生日期,教育程度,所在国家,所在省份,所在城市
                String[] cols = iterator.next();
                if (line != 0) {
                    Student student = new Student();
                    if (!cols[0].isEmpty()) student.setStudentId(Integer.valueOf(cols[0]));
                    if (!cols[0].isEmpty()) student.setUserId(Integer.valueOf(cols[0]));
                    if (!cols[1].isEmpty()) student.setStudentName(cols[1]);
                    if (!cols[2].isEmpty()) student.setNickname(cols[2]);
                    if (!cols[3].isEmpty()) student.setGender(cols[3]);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    if (!cols[4].isEmpty()) student.setBirthday(sdf.parse(cols[4]));
                    if (!cols[5].isEmpty()) student.setEducation(cols[5]);
                    if (!cols[6].isEmpty()) student.setCountry(cols[6]);
                    if (!cols[7].isEmpty()) student.setProvince(cols[7]);
                    if (!cols[8].isEmpty()) student.setCity(cols[8]);
                    if (studentService.selectById(student.getStudentId()) == null) {
                        //添加用户登录信息
                        User user = new User();
                        user.setUsername(student.getStudentId() + "");
                        if (student.getStudentName() != null && !student.getStudentName().isEmpty()) {
                            if (student.getStudentName().length() < 30) user.setNickName(student.getStudentName());
                            else user.setNickName("昵称过长");
                        } else user.setNickName("账号无昵称");
                        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
                        user.setState(null);
                        user.setEmailVerified(null);
                        students.add(student);
                        users.add(user);
                    }
                }
                line++;
                if (line % 10000 == 0) {
                    System.out.println("当前执行到" + line + "行！");
                    if (users.size() > 0) userService.insertBatch(users);
                    if (students.size() > 0) studentService.insertBatch(students);
                    users.clear();
                    students.clear();
                }
            }
            userService.insertBatch(users);
            studentService.insertBatch(students);
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void insertStudentUser() {
//        int page = 0;//第几页
//        int limit = 1000;//每页1000条
//        Page<Student> studentPage = new Page<>(page, limit);
//        do {
//            studentPage = new Page<>(page, limit);
//            studentService.selectPage(studentPage);
//            List<Student> students = studentPage.getRecords();
//            for (Student student : students) {
//                User user = new User();
//                user.setUsername(student.getStudentId() + "");
//                if (student.getStudentName() != null && !student.getStudentName().isEmpty()) {
//                    if (student.getStudentName().length() < 30) user.setNickName(student.getStudentName());
//                    else user.setNickName("昵称过长");
//                } else user.setNickName("账号无昵称");
//                user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//                user.setState(null);
//                user.setEmailVerified(null);
//                userService.insert(user);
//                student.setUserId(user.getUserId());
//            }
//            studentService.updateBatchById(students);
//            page++;
//        } while (studentPage.getTotal() > 0);
//
//    }
}
