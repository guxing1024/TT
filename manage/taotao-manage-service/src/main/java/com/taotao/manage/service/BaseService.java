package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

import java.util.Date;
import java.util.List;

public abstract class BaseService <T extends BasePojo> {

    public abstract Mapper<T> getMapper();

    /**
     * 方法：
     * 1、	queryById
     * 2、	queryAll
     * 3、	queryOne
     * 4、	queryListByWhere
     * 5、	queryPageListByWhere
     * 6、	save
     * 7、	update
     * 8、	deleteById
     * 9、	deleteByIds
     * 10、	deleteByWhere
     */
    /**
     * 根据主键ID查询对象
     * @param id
     * @return
     */
    public T queryById(Long id){
        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * 查询所有对象
     * @return
     */
    public List<T> queryAll(){
        return this.getMapper().select(null);
    }

    /**
     * 根据条件查询单个对象
     * @param record
     * @return
     */
    public T queryOne(T record){
        return this.getMapper().selectOne(record);
    }

    /**
     * 根据条件查询多条数据
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record){
        return this.getMapper().select(record);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record){
        PageHelper.startPage(page, rows);
        return new PageInfo<>(this.getMapper().select(record));
    }

    /**
     * 保存数据
     * @param record
     * @return
     */
    public Integer save(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.getMapper().insert(record);
    }

    /**
     * 只保存字段不为空的值
     * @param record
     * @return
     */
    public Integer saveSelective(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.getMapper().insertSelective(record);
    }

    /**
     * 修改数据返回修改成功的条数
     * @param record
     * @return
     */
    public Integer update(T record){
        record.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     * @param record
     * @return
     */
    public Integer updateSelect(T record){
        record.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKeySelective(record);
    }
    /*deleteById
     * 9、	deleteByIds
     * 10、	deleteByWhere*/

    /**
     * 根据Id对象
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
        return this.getMapper().deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param ids
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property,List<Object> ids){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, ids);
        return this.getMapper().deleteByExample(example);
    }

    public Integer deleteByWhere(T record){
        return this.getMapper().delete(record);
    }

}
