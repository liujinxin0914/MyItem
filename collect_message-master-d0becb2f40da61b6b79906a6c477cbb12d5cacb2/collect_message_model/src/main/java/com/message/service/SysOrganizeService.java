package com.message.service;

import com.message.model.SysOrganize;
import com.message.model.SysOrganizeVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author fanxialong
 * @since 2019-06-02
 */
public interface SysOrganizeService extends IService<SysOrganize> {
    /**
     * 根据pid查询子机构
     *
     * @param pid
     * @return
     */
    List<SysOrganizeVo> seletSysOrganizeByPid(String pid);

    /**
     * 根据id查询 机构加强类
     *
     * @param orgId
     * @return
     */
    SysOrganizeVo selectInfoById(String orgId);


    SysOrganize selectSysOrganizeById(String id);

}
