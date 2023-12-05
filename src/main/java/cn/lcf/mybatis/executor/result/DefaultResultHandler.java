package cn.lcf.mybatis.executor.result;

import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.session.ResultContext;
import cn.lcf.mybatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/12/4 11:27
 * @description :
 * @modyified By:
 */
public class DefaultResultHandler implements ResultHandler<Object> {
    private final List<Object> list;

    public DefaultResultHandler() {
        list = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public DefaultResultHandler(ObjectFactory objectFactory) {
        list = objectFactory.create(ArrayList.class);
    }

    @Override
    public void handleResult(ResultContext<?> context) {
        list.add(context.getResultObject());
    }

    public List<Object> getResultList() {
        return list;
    }

}