package com.ax.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.ax.entity.PageResult;
import com.ax.mapper.TbImageMapper;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbImageExample;
import com.ax.pojo.TbUser;
import com.ax.service.ImageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private TbImageMapper imageMapper;

    private static final int STATUS_VALUE_NORMAL = 1;
    private static final int STATUS_VALUE_DELETE = 2;

    /**
     * 查询全部
     */
    @Override
    public List<TbImage> findAll() {
        return imageMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbImage> page = (Page<TbImage>) imageMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbImage image) {
        image.setStatus(STATUS_VALUE_NORMAL);
        image.setCreateTime(new Date());
        image.setUpdateTime(new Date());
        imageMapper.insert(image);
    }

    /**
     * 修改
     */
    @Override
    public void update(TbImage image) {
        String address = image.getAddress();
        if (!StringUtils.isEmpty(address)) {
            String[] images = address.split("images");
            address = images[images.length - 1];
            image.setAddress(address);
        }

        image.setUpdateTime(new Date());
        imageMapper.updateByPrimaryKeySelective(image);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbImage findOne(Long id) {
        return imageMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            imageMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbImage image, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbImageExample example = new TbImageExample();
        TbImageExample.Criteria criteria = example.createCriteria();

        if (image != null) {
            if (image.getAddress() != null && image.getAddress().length() > 0) {
                criteria.andAddressLike("%" + image.getAddress() + "%");
            }

        }

        Page<TbImage> page = (Page<TbImage>) imageMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public File upload(String IMAGE_SERVER_PATH, String originalFilename, int kind, TbUser user) {

        if (StringUtils.isEmpty(originalFilename) || user == null) {
            return null;
        }

        //此处存在一个参数判断是否合法，暂时省略
        StringBuffer path = new StringBuffer(IMAGE_SERVER_PATH);
//        path.append("images/").append(user.getUsername()).append("/");
        path.append("images/").append(user.getId()).append("/"); //用户id 是唯一不变的
        switch (kind) {
            case 1:
                path.append("head").append("/");
                break; // 1 表示头像图片
            case 2:
                path.append("shop").append("/");
                break; // 2 表示商品图片
            default:
                path.append("error").append("/");
        }
        File file = new File(path.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = originalFilename.substring(originalFilename.lastIndexOf('.'));
        path.append(System.currentTimeMillis()).append(name);
        file = new File(path.toString());
        return file;
    }


// ========我是神奇的分界线====================================================================================================

    /**
     * 添加商品图片
     *
     * @param imageList 商品图片集合
     * @param goodId    商品id
     */
    public void addImageList(List<TbImage> imageList, Long goodId) {
//        private Integer kind;
//        private Long kindId;
//        private Integer status;
//        private Date createTime;
//        private Date updateTime;
        ListIterator<TbImage> tbImageListIterator = imageList.listIterator();

        while (tbImageListIterator.hasNext()) {
            TbImage next = tbImageListIterator.next();
            next.setKind(2);     //2 表示商品图片
            next.setKindId(goodId);
            next.setStatus(STATUS_VALUE_NORMAL);
            next.setCreateTime(new Date());
            next.setUpdateTime(new Date());

            imageMapper.insert(next);
        }
    }

    @Override
    public List<TbImage> findByKindId(Long kindId) {

        TbImageExample tie = new TbImageExample();
        TbImageExample.Criteria criteria = tie.createCriteria();

        criteria.andKindIdEqualTo(kindId);

        List<TbImage> images = imageMapper.selectByExample(tie);

        return images;
    }

    @Override
    public void addImage(String address, Long id, Integer kind) {
        if (!StringUtils.isEmpty(address)) {
            String[] images = address.split("images");
            address = images[images.length - 1];
        }

        //添加商品图片
        TbImage tbImage = new TbImage();
        tbImage.setAddress(address);
        tbImage.setKind(kind);
        tbImage.setKindId(id);
        tbImage.setStatus(STATUS_VALUE_NORMAL);
        tbImage.setCreateTime(new Date());
        tbImage.setUpdateTime(new Date());

        imageMapper.insert(tbImage);
    }

    /**
     * 查询image 地址
     */
    public TbImage findImageAddress(TbImage image) {

        TbImage tbImage = imageMapper.selectOneByKindId(image.getKindId());

        return tbImage;
    }


}
