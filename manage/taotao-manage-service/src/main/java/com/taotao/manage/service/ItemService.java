package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemMapper itemMapper;


    /**
     * @Description 保存商品
     * @Date 22:36
     * @Param [item, desc]
     * @return boolean
     **/
    public boolean saveItem(Item item, String desc) {
        item.setStatus(1);
        item.setId(null);
        Integer count1 = super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = itemDescService.saveSelective(itemDesc);
        return count1.intValue() == 1 && count2.intValue() == 1;
    }

    /**
     * @Description 查询商品按添加时间倒叙
     * @Date 22:36
     * @Param []
     * @return com.taotao.common.bean.EasyUIResult
     *
     * @param page
     * @param rows*/
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("created DESC");
        List<Item> itemList = this.itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }

    public boolean updateItem(Item item, String desc) {
        Integer count1 = super.updateSelect(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = this.itemDescService.updateSelect(itemDesc);
        return count1.intValue() == 1 && count2.intValue() == 1;
    }
}
