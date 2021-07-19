package com.bjpowernode;

import com.bjpowernode.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {
    //测试方法，测试功能
    @Test
    public void testInsert() throws IOException {

        //访问mybatis读取student数据
        //1.定义mybatis主配置文件的名称, 从类路径的根开始（target/clasess）
        String config="mybatis.xml";
        //2.读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建了SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder  = new SqlSessionFactoryBuilder();
        //4.创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //5.获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        //SqlSession sqlSession = factory.openSession();
        SqlSession sqlSession = factory.openSession(true);
        //6.【重要】指定要执行的sql语句的标识。  sql映射文件中的namespace + "." + 标签的id值
        String sqlId = "com.bjpowernode.dao.StudentDao.insertStudent";
        //7. 重要】执行sql语句，通过sqlId找到语句
        Student student  = new Student();
        student.setId(1006);
        student.setName("关羽");
        student.setEmail("guanyu@163.com");
        student.setAge(20);
        int nums = sqlSession.insert(sqlId,student);

        //mybatis默认不是自动提交事务的， 所以在insert ，update ，delete后要手工提交事务
        //sqlSession.commit();

        //8.输出结果
        System.out.println("执行insert的结果="+nums);

        //9.关闭SqlSession对象
        sqlSession.close();
    }
    @Test
    public void testUpdate() throws IOException {
        //1.mybatis 主配置文件
        String config = "mybatis.xml";
        //2.读取配置文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //4.获取 SqlSession
        SqlSession session = factory.openSession();
        //5.创建保存数据的对象
        Student student = new Student();
        student.setId(1006);//要修改的 id
        student.setAge(30); //要修改的年龄值
        //6.执行更新 update
        int rows = session.update(
                "com.bjpowernode.dao.StudentDao.updateStudent",student);
        //7.提交事务
        session.commit();
        System.out.println("修改记录的行数:"+rows);
        //8.关闭 SqlSession
        session.close();
    }

    @Test
    public void testDelete() throws IOException {
        //1.mybatis 主配置文件
        String config = "mybatis.xml";
        //2.读取配置文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //4.获取 SqlSession
        SqlSession session = factory.openSession();
        //5.删除的 id
        int id = 1001;
        //6.执行删除 delete
        int rows = session.delete(
                "com.bjpowernode.dao.StudentDao.deleteStudent",id);
        //7.提交事务
        session.commit();
        System.out.println("修改记录的行数:"+rows);
        //8.关闭 SqlSession
        session.close();
    }

}
