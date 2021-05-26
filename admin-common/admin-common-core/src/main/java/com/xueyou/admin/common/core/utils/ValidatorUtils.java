package com.xueyou.admin.common.core.utils;

import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author xueyou
 * @date 2020/12/28
 */
public class ValidatorUtils {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws BusinessRuntimeException 校验不通过，则报BaseException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws BusinessRuntimeException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator()
                    .next();
            throw new BusinessRuntimeException(constraint.getMessage());
        }
    }

}
