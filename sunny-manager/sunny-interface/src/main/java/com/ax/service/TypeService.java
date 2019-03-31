package com.ax.service;

import com.ax.entity.Result;
import com.ax.pojogroup.Type;
import com.ax.pojo.TbType;

import java.util.List;

/**
 * 类型接口
 */
public interface TypeService {

    /**
     * 查询类型
     */
    public List<TbType> findType(Long parentId);

    /**
     * 添加
     */
    public Result add(TbType tbType);


    /**
     * 删除
     */
    public Result delete(TbType tbType);

    /**
     * 更新
     */
    public Result update(TbType tbType);

    /**
     * 查询类型
     */
    public List<Type> findAll();

    /**
     * 根据子类查询父级type
     * */
    TbType findParentType(Long id);

}
