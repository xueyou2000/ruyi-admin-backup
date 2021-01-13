package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.RoleDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与部门关联 数据访问层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 2:43 下午
 */
@Mapper
public interface RoleDeptMapper extends BaseMapper<RoleDept> {

    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleDept(Long[] ids);

    /**
     * 查询部门使用数量
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
    int batchRoleDept(List<RoleDept> roleDeptList);

}
