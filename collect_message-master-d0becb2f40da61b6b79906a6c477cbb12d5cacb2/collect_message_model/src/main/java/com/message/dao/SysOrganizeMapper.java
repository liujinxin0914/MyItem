package com.message.dao;

import com.message.model.SysOrganize;
import com.message.model.SysOrganizeVo;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author fanxialong
 * @since 2019-05-30
 */
public interface SysOrganizeMapper extends BaseMapper<SysOrganize> {

    List<SysOrganizeVo> seletSysOrganizeByPid(String pid);

    SysOrganizeVo selectInfoById(String id);

    SysOrganize selectSysOrganizeById(String id);

}
