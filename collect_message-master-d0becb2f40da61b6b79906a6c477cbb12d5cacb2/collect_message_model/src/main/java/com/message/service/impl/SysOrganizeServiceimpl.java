package com.message.service.impl;

import com.message.model.SysOrganize;
import com.message.model.SysOrganizeVo;
import com.message.dao.SysOrganizeMapper;
import com.message.service.SysOrganizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
@Service
public class SysOrganizeServiceimpl extends ServiceImpl<SysOrganizeMapper, SysOrganize> implements SysOrganizeService {


    @Autowired
    private SysOrganizeMapper sysOrganizeMapper;

    @Override
    public List<SysOrganizeVo> seletSysOrganizeByPid(String pid) {
        // TODO Auto-generated method stub
        return sysOrganizeMapper.seletSysOrganizeByPid(pid);
    }

    @Override
    public SysOrganizeVo selectInfoById(String orgId) {
        // TODO Auto-generated method stub
        return sysOrganizeMapper.selectInfoById(orgId);
    }

    @Override
    public SysOrganize selectSysOrganizeById(String id) {
        // TODO Auto-generated method stub
        return sysOrganizeMapper.selectSysOrganizeById(id);
    }

}
