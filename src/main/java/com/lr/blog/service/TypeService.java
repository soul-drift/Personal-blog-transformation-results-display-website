package com.lr.blog.service;

import com.lr.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);      //根据ID查询Type

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);//分页查询

    List<Type> listType();

    List<Type> listTypeTop(Integer size);//拿到数据多少

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
