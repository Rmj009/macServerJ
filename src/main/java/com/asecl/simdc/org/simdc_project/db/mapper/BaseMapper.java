package com.asecl.simdc.org.simdc_project.db.mapper;
import java.util.List;

public interface BaseMapper<T> {
    void insert(T type);

    List<T> getAll();

    int getTotalCount();

    void update(T type);

    void deleteByID(long id);

}
