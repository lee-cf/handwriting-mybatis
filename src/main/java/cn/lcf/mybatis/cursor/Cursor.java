package cn.lcf.mybatis.cursor;

import java.io.Closeable;

public interface Cursor<T> extends Closeable, Iterable<T> {
}
