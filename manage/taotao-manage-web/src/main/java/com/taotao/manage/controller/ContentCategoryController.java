package com.taotao.manage.controller;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author
 * @Description //TODO 内容分类$
 * @time 23:32
 */
@RequestMapping("/content/category")
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * @Description 根据父ID查询商品类目
     * @author
     * @Date 23:54
     * @Param [pid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.taotao.manage.pojo.ContentCategory>>
     **/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByParamId(@RequestParam(value = "id", defaultValue = "0")Long pid){
        try {
            ContentCategory recode = new ContentCategory();
            recode.setParentId(pid);
            List<ContentCategory> list = contentCategoryService.queryListByWhere(recode);
            if (null != list && !list.isEmpty()){
                return ResponseEntity.ok(list);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Description 添加类别
     * @author
     * @Date 00:16
     * @Param [contentCategory]
     * @return org.springframework.http.ResponseEntity<com.taotao.manage.pojo.ContentCategory>
     **/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
        try {
            contentCategory.setIsParent(false);
            contentCategory.setSortOrder(1);
            contentCategory.setStatus(1);
            contentCategory.setId(null);
            contentCategoryService.saveAll(contentCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Description 重命名
     * @author
     * @Date 00:23
     * @Param [id, name]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(@RequestParam("id")Long id, @RequestParam("name")String name){
        try {
            ContentCategory recode = new ContentCategory();
            recode.setId(id);
            recode.setName(name);
            contentCategoryService.updateSelect(recode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAll(@RequestParam("parentId")Long parentId, @RequestParam("id")Long id){
        try {
            contentCategoryService.deleteAll(parentId, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
