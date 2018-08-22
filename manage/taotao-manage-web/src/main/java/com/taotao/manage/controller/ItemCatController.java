package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * @Description 根据父ID查询商品类目集合
     * @Date 23:47
     * @Param [pid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.taotao.manage.pojo.ItemCat>>
     **/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatByPid(@RequestParam(value = "id",defaultValue = "0") Long pid){
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(pid);
            List<ItemCat> list = itemCatService.queryListByWhere(itemCat);
            //List<ItemCat> list = itemCatService.queryItemCatByPid(pid);
            if(null == list || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/getCname/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getCname(@PathVariable("cid") Long cid){
        ItemCat itemCat = new ItemCat();
        itemCat.setId(cid);
        ItemCat itemCat1 = itemCatService.queryById(cid);
        return ResponseEntity.ok(itemCat1.getName());
    }
}
