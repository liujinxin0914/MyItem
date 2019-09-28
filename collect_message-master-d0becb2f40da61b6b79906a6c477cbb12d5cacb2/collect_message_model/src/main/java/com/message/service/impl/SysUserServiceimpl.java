package com.message.service.impl;

import com.message.model.SysUser;
import com.message.dao.SysUserMapper;
import com.message.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@Service
public class SysUserServiceimpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
