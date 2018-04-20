package mybatis;

import domain.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestMybatis {
    public static void main(String[] args) throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sessionFactory.openSession();

        try{
//            Book book = sqlSession.selectOne("BookMapper.selectBook",1);
//            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
//            Book book = bookMapper.selectBook(1);

            PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

            Person p = personMapper.selectPersonById(1);

            System.out.println(p);



        }finally {
            sqlSession.commit();
            sqlSession.close();
        }


    }
}
