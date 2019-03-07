package com.ax.mapper;

import com.ax.pojo.TbImage;
import com.ax.pojo.TbImageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbImageMapper {
    int countByExample(TbImageExample example);

    int deleteByExample(TbImageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbImage record);

    int insertSelective(TbImage record);

    List<TbImage> selectByExample(TbImageExample example);

    TbImage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbImage record, @Param("example") TbImageExample example);

    int updateByExample(@Param("record") TbImage record, @Param("example") TbImageExample example);

    int updateByPrimaryKeySelective(TbImage record);

    int updateByPrimaryKey(TbImage record);
}