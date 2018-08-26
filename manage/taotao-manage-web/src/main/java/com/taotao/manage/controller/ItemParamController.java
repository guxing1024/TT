package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description //TODO 商品模板$
 * @author
 * @time 22:34
 */
@RequestMapping("item/param")
@Controller
public class ItemParamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);

    @Autowired
    private ItemParamService itemParamService;

    /**
     * @Description 根据类目ID查询模板
     * @Date 23:53
     * @Param [itemCatId]
     * @return org.springframework.http.ResponseEntity<com.taotao.manage.pojo.ItemParam>
     **/
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable(value = "itemCatId")Long itemCatId){
        try {
            LOGGER.info("查询模板入参itemCatId={}", itemCatId);
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            ItemParam itemParam1 = itemParamService.queryOne(itemParam);
            if (null == itemParam1){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParam1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Description 添加商品规格模板
     * @Date 00:02
     * @Param [cid, paramDate]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @RequestMapping(value = "{cid}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("cid")Long cid,
            @RequestParam("paramData")String paramDate){
        try {
            ItemParam itemParam = new ItemParam();
            itemParam.setId(null);
            itemParam.setItemCatId(cid);
            itemParam.setParamData(paramDate);
            this.itemParamService.save(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
