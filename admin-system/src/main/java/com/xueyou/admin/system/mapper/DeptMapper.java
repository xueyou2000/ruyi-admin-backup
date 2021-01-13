package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 部门 数据访问层
 *
 * @author xueyou
 * @date 2020-12-29
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

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
    List<Dept> selectDeptAll(String dataScope);

    /**
     * 修改父级部门状态
     */
    void updateDeptStatus(Dept dept);

    /**
     * 根据ID查询所有子部门
     */
    List<Dept> selectChildrenDeptById(Long id);

    /**
     * 修改子元素关系
     */
    int updateDeptChildren(@Param("depts") List<Dept> depts);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    Dept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色编号查询所有部门ID
     */
    Set<Long> selectRoleDeptIds(Long roleId);

}