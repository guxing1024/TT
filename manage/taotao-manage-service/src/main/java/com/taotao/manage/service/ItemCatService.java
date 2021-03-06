package com.taotao.manage.service;

import com.github.abel533.mapper.Mapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCatService extends BaseService<ItemCat>{

    @Autowired
    private ItemCatMapper itemCatMapper;

    /*public List<ItemCat> queryItemCatByPid(Long pid) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(pid);
        return this.itemCatMapper.select(itemCat);
    }

    @Override
    public Mapper<ItemCat> getMapper() {
        return this.itemCatMapper;
    }
    */
    public ItemCatResult queryAllToTree(){
        ItemCatResult result = new ItemCatResult();
        //查询所有数据
        List<ItemCat> cats = super.queryAll();
        //转换为Map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())){
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }
        //封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setname("<a href='"+itemCatData.getUrl()+"'>"+itemCat.getName()+"</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()){
                continue;
            }
            //封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            ArrayList<ItemCatData> itemCatData2 = new ArrayList<>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setname(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if(itemCat2.getIsParent()){
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|"+itemCat3.getName());
                    }
                }
            }
            if(result.getItemCats().size() >= 14){
                break;
            }
        }
        return result;
    }
}
