package com.taotao.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @Description //TODO 内容分类$
 * @time 23:31
 */
@Service
public class ContentService extends BaseService<Content> {
    @Autowired
    private ContentMapper contentMapper;

    public EasyUIResult queryListByContentCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Content> list = contentMapper.queryListByContentCategoryId(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<>(list);
        EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
        return easyUIResult;
    }
}
