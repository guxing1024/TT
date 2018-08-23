package com.taotao.manage.controller;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

   /**
    * @Description 保存商品
    * @Param [item, desc]
    * @return org.springframework.http.ResponseEntity<java.lang.Void>
    **/
    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc){
        if(null == item){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            boolean bool = itemService.saveItem(item,desc);
           if(!bool){
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
           }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows){
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("查询商品入参，page = {}, rows = {}", page, rows);
            }
            return ResponseEntity.ok(itemService.queryItemList(page,rows));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc){
        if(null == item){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            boolean bool = itemService.updateItem(item,desc);
            if(!bool){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
