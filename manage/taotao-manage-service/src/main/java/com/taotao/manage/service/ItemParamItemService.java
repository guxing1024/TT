package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description //TODO 商品模板关系表$
 * @time 22:31
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;


    public Integer updateItemParamItem(Long id, String paramJson) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(paramJson);
        itemParamItem.setUpdated(new Date());
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId",id);

        return itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
    }
}
