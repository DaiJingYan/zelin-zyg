package com.zyg.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyg.page.entity.Student;
import com.zyg.page.entity.vo.StudentVo;
import com.zyg.mapper.ClassesMapper;
import com.zyg.mapper.StudentMapper;
import com.zyg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/11/22-10:09
 * ------------------------------
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;
    //1. 查询所有学生
    @Override
    public List<Student> findAll() {
        //1.1 得到所有学生
        List<Student> students = studentMapper.selectList(null);
        //1.2 为学生设置关联的班级
        for (Student student : students) {
            student.setCname(classesMapper.selectById(student.getCid()).getCname());
        }
        //1.3 返回学生列表
        return students;
    }

    //2. 根据学号查询所有学生
    @Override
    public Student findById(int sid) {
        return studentMapper.selectById(sid);
    }

    //3. 添加学生
    @Override
    public void add(Student student) {
        studentMapper.insert(student);
    }

    //4. 修改学生
    @Override
    public void update(Student student) {
        studentMapper.updateById(student);
    }

    //5. 删除学生
    @Override
    public void delete(int sid) {
        studentMapper.deleteById(sid);
    }

    //6. 分页查询
    @Override
    public IPage<Student> findByPage(int page, int pageSize) {
        //6.1 开始分页
        //6.1.1 封装分页参数
        IPage<Student> ipage = new Page<>(page,pageSize);
        //6.1.2 封装查询条件
        Wrapper<Student> wrapper = new QueryWrapper<>();
        //6.1.3 开始分页并返回
        return studentMapper.selectPage(ipage, wrapper);   //参数1：代表分页参数对象 参数2：代表条件查询参数对象


    }

    //7. 条件查询带分页
    @Override
    public IPage<Student> search(int page, int pageSize, StudentVo vo) {
        //7.1 开始分页
        //7.1.1 封装分页参数
        IPage<Student> ipage = new Page<>(page,pageSize);
        //7.1.2 封装查询条件
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if(vo != null){
            if(!StringUtils.isEmpty(vo.getSname())){
                wrapper.like("sname",vo.getSname());
            }
            if(!StringUtils.isEmpty(vo.getAddr())){
                wrapper.like("addr",vo.getAddr());
            }
            if(vo.getCid() != null){
                wrapper.like("cid",vo.getCid());
            }
        }
        //7.1.3 开始分页并返回
        return studentMapper.selectPage(ipage, wrapper);   //参数1：代表分页参数对象 参数2：代表条件查询参数对象
    }
}
