import cn.lcf.mybatis.io.Resources;
import cn.lcf.mybatis.session.SqlSession;
import cn.lcf.mybatis.session.SqlSessionFactory;
import cn.lcf.mybatis.session.SqlSessionFactoryBuilder;
import demo.IUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:11
 * @description :
 * @modyified By:
 */

@Slf4j
public class ApiTest {
    @Test
    public void test_MapperProxyFactory() throws IOException {
// 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserMapper userDao = sqlSession.getMapper(IUserMapper.class);

        // 3. 测试验证
       List<String> ss =  userDao.queryUserName(3);
        System.out.println(Arrays.toString(ss.toArray()));
    }
}