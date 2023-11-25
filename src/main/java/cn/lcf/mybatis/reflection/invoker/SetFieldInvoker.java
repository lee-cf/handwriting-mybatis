package cn.lcf.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:47
 * @description :
 * @modyified By:
 */
public class SetFieldInvoker implements Invoker {
    private final Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}