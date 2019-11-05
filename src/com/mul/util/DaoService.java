package com.mul.util;

import java.util.List;

public interface DaoService<E> {
        List<E> showAll();
        int addData(E object);
        int deleteData(E object);
        int updateData(E object);
}
