package com.ax.service.impl;

import com.ax.entity.Result;
import com.ax.pojo.TbImageExample;
import com.ax.pojogroup.Type;
import com.ax.mapper.TbTypeMapper;
import com.ax.pojo.TbType;
import com.ax.pojo.TbTypeExample;
import com.ax.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TbTypeMapper typeMapper;


    private static final long DEFAULT_PARENT_ID = 0;  //一级类型 父级id 为0

    /**
     * 查询分类
     */
    @Override
    public List<TbType> findType(Long parentId) {

        List<TbType> tbTypes = null;

        TbTypeExample tbTypeExample = new TbTypeExample();
        TbTypeExample.Criteria criteria = tbTypeExample.createCriteria();

        if (parentId == null) {
            criteria.andParentIdEqualTo(DEFAULT_PARENT_ID);   // 默认查询
        } else {
            criteria.andParentIdEqualTo(parentId);  //指定查询
        }

        tbTypes = typeMapper.selectByExample(tbTypeExample);

        return tbTypes;
    }


    /**
     * 增加
     */
    public Result add(TbType tbType) {

        Result result = null;
        if (tbType != null && !StringUtils.isEmpty(tbType.getType())) {
            if (tbType.getParentId() == null) tbType.setParentId(DEFAULT_PARENT_ID);
            typeMapper.insert(tbType);
            result = new Result(true, "增加成功");
        } else {
            result = new Result(false, "增加失败:参数异常");
        }

        return result;

    }

    @Override
    public Result delete(TbType tbType) { //TODO  暂时不开发
        return null;
    }

    @Override
    public Result update(TbType tbType) { //TODO 暂时不开发
        return null;
    }


    /**
     * 查询全部类型
     */
    public List<Type> findAll() {

        TbTypeExample tte = new TbTypeExample();
        TbTypeExample.Criteria criteria = tte.createCriteria();
        criteria.andParentIdEqualTo(0L); //查询所有 父类id 为0 的type

        List<TbType> list = typeMapper.selectByExample(tte);

        List<Type> resultList = new ArrayList<Type>();
        for (TbType tbType : list) {
            Type type = new Type();
            type.setTbType(tbType);
            type.setTbTypeList(findType(tbType.getId())); //查询相关子类型
            resultList.add(type);
        }
        return resultList;
    }


    public TbType findParentType(Long id) {

        TbType type = typeMapper.selectParentType(id);

        return type;
    }
}
