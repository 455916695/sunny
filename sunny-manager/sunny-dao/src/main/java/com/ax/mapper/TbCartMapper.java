package com.ax.mapper;

import com.ax.pojo.TbCart;
import com.ax.pojo.TbCartExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCartMapper {
    int countByExample(TbCartExample example);

    int deleteByExample(TbCartExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbCart record);

    int insertSelective(TbCart record);

    List<TbCart> selectByExample(TbCartExample example);

    TbCart selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbCart record, @Param("example") TbCartExample example);

    int updateByExample(@Param("record") TbCart record, @Param("example") TbCartExample example);

    int updateByPrimaryKeySelective(TbCart record);

    int updateByPrimaryKey(TbCart record);

    /**
     *  查询
     * */
    int selectCountByGoodsId(@Param("buyerId") Long buyerId, @Param("goodsId") Long goodsId,@Param("cartStatus") int cartStatus);

/**
 * 用于逻辑删除
 * */
    void updateStatus(@Param("ids") Long[] ids, @Param("status") int status);

    /**
     *根据买家id 和 商品id 更改购物车状态
     * */
    void updateStatusByBuyerIdAndGoodsId(@Param("buyerId") Long buyerId,@Param("goodsId") Long goodsId,@Param("status")int status);
}