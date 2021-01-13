package com.xueyou.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 部门 业务层
 *
 * @author xueyou
 * @date 2020-12-29
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 分页查询部门
     */
    IPage<Dept> selectDeptByPage(IPage<Dept> page, Dept dept);

    /**
     * 查询部门列表
     */
    List<Dept> selectDeptList(Dept dept);

    /**
     * 查询部门列表
     */
    List<Dept> selectDeptAll();

    /**
     * 新增部门
     *
     * @param dept 部门
     * @return 结果
     */
    boolean insertDept(Dept dept);

    /**
     * 修改部门
     *
     * @param dept 部门
     * @return 结果
     */
    boolean updateDept(Dept dept);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    boolean checkDeptNameUnique(Dept dept);

    /**
     * 根据角色ID查询部门编号
     *
     * @param roleId 角色编号
     */
    Set<Long> roleDeptIds(Long roleId);

}