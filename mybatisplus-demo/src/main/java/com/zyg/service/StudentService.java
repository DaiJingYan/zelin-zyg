package com.zyg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyg.page.entity.Student;
import com.zyg.page.entity.vo.StudentVo;

import java.util.List;

/**
 * Created by WF on 2021/11/22 10:09
 */
public interface StudentService {

    List<Student> findAll();

    Student findById(int sid);

    void add(Student student);

    void update(Student student);

    void delete(int sid);

    IPage<Student> findByPage(int page, int pageSize);

    IPage<Student> search(int page, int pageSize, StudentVo vo);
}
