package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @Description //TODO 内容分类$
 * @time 23:31
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    public void saveAll(ContentCategory contentCategory) {
        super.save(contentCategory);
        //根据父类ID查询是否是父目录
        ContentCategory query = this.queryById(contentCategory.getParentId());
        if(!query.getIsParent()){
            ContentCategory recode = new ContentCategory();
            recode.setId(contentCategory.getParentId());
            recode.setIsParent(true);
           this.updateSelect(recode);
        }
    }

    public void deleteAll(Long parentId, Long id) {
        List<Object> ids = new ArrayList();
        ids.add(id);
        this.findSubNode( ids, id);
        this.deleteByIds(ContentCategory.class,"id", ids);

        ContentCategory recode = new ContentCategory();
        recode.setParentId(parentId);
        List<ContentCategory> list = this.queryListByWhere(recode);
        if (null == list || list.isEmpty()){
            ContentCategory parent = new ContentCategory();
            parent.setIsParent(false);
            parent.setId(parentId);
            this.updateSelect(parent);
        }
    }

    public void findSubNode(List<Object> ids, Long id){
        ContentCategory recode = new ContentCategory();
        recode.setParentId(id);
        List<ContentCategory> list = this.queryListByWhere(recode);
        for (ContentCategory contentCategory : list) {
            ids.add(contentCategory.getId());
            if (contentCategory.getIsParent()){
                findSubNode(ids, contentCategory.getId());
            }
        }
    }
}
