package com.zyg.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyg.page.entity.Student;
import com.zyg.page.entity.vo.StudentVo;
import com.zyg.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/11/22-10:07
 * ------------------------------
 */
@SpringBootTest
public class TestMyBatisPlus {
    @Autowired
    private StudentService studentService;

    //1. 列表学生
    @Test
    public void test01(){
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }
    //2. 查询单个学生
    @Test
    public void test02(){
        //2.1 定义要查询的学生id
        int sid = 59;
        //2.2 根据学号进行查询
        Student student = studentService.findById(sid);
        //2.3 打印
        System.out.println(student);
    }
    //3. 添加学生
    @Test
    public void test03(){
        //3.1 构造要添加的学生
        Student student = new Student("李世民","男",200,"长安",1);
        //3.2 开始添加学生
        studentService.add(student);
    }

    //4. 修改学生
    @Test
    public void test04(){
        //4.1 根据学号查询出要修改的学生
        int sid = 62;
        Student student = studentService.findById(sid);
        //4.2 如果存在此学生就修改
        if(student != null){
            student.setSname("李小民");
            student.setAge(500);
            student.setAddr("西安");
            studentService.update(student);
        }else{
            System.out.println("学生不存在！");
        }
    }

    //5. 删除学生
    @Test
    public void test05(){
        //5.1 定义要删除的学生学号
        int sid = 62;
        //5.2 开始删除
        studentService.delete(sid);
    }

    //6. 进行分页查询
    @Test
    public void test06(){
        //6.1 定义分页查询参数
        int page = 1;       //当前页
        int pageSize = 5;   //每页大小
        //6.2 开始分页
        IPage<Student> studentIPage = studentService.findByPage(page,pageSize);
        //6.3 查看分页信息
        long pages = studentIPage.getPages();
        long total = studentIPage.getTotal();
        List<Student> records = studentIPage.getRecords();
        System.out.println("pages = " + pages);
        System.out.println("total = " + total);
        System.out.println("records = " + records);
    }

    //7. 分页带条件查询
    @Test
    public void test07(){
        //7.1 定义分页查询参数
        int page = 1;       //当前页
        int pageSize = 5;   //每页大小
        //7.2 封装查询条件对象
        StudentVo vo = new StudentVo();
        vo.setSname("小");
        vo.setAddr("州");
        //7.2 开始分页
        IPage<Student> studentIPage = studentService.search(page,pageSize,vo);
        List<Student> records = studentIPage.getRecords();
        System.out.println("pages = " + studentIPage.getPages());
        System.out.println("total = " + studentIPage.getTotal());
        System.out.println("records = " + records);
    }
}
