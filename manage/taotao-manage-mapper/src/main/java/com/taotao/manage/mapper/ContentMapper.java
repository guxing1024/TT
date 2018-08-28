package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;

import java.util.List;

public interface ContentMapper extends Mapper<Content> {

    List<Content> queryListByContentCategoryId(Long categoryId);
}
