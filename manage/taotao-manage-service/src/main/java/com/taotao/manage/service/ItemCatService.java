package com.taotao.manage.service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatService extends BaseService<ItemCat>{

    @Autowired
    private ItemCatMapper itemCatMapper;

    public List<ItemCat> queryItemCatByPid(Long pid) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(pid);
        return this.itemCatMapper.select(itemCat);
    }

    @Override
    public Mapper<ItemCat> getMapper() {
        return this.itemCatMapper;
    }
}
