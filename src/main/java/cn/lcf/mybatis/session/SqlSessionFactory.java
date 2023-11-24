package cn.lcf.mybatis.session;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:37
 * @description :
 * @modyified By:
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}