package com.message.service.impl;

import com.message.model.Images;
import com.message.dao.ImagesMapper;
import com.message.service.ImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@Service
public class ImagesServiceimpl extends ServiceImpl<ImagesMapper, Images> implements ImagesService {

}
