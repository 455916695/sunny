package com.ax.mapper;

import com.ax.pojo.TbType;
import com.ax.pojo.TbTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbTypeMapper {
    int countByExample(TbTypeExample example);

    int deleteByExample(TbTypeExample example);

    int insert(TbType record);

    int insertSelective(TbType record);

    List<TbType> selectByExample(TbTypeExample example);

    int updateByExampleSelective(@Param("record") TbType record, @Param("example") TbTypeExample example);

    int updateByExample(@Param("record") TbType record, @Param("example") TbTypeExample example);
}