package cn.lcf.mybatis.executor.result;

import cn.lcf.mybatis.session.ResultContext;

/**
 * @author : lichaofeng
 * @date :2023/12/4 10:33
 * @description :
 * @modyified By:
 */
public class DefaultResultContext<T> implements ResultContext<T> {
    private T resultObject;
    private int resultCount;
    private boolean stopped;

    public DefaultResultContext() {
        resultObject = null;
        resultCount = 0;
        stopped = false;
    }

    @Override
    public T getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    public void nextResultObject(T resultObject) {
        resultCount++;
        this.resultObject = resultObject;
    }

    @Override
    public void stop() {
        this.stopped = true;
    }

}