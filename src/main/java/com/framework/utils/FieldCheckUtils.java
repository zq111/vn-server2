package com.framework.utils;

import com.framework.Exception.ValidatorException;

import javax.validation.ConstraintViolation;
import javax.validation.UnexpectedTypeException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

//Jsr属性校验工具类
public class FieldCheckUtils {

    /**
     * 根据传入的对象,校验字段上的注解条件是否满足
     * 如果不满足,直接抛出自定义异常
     * 此异常包含该对象所有不满足注解条件的信息
     * @param target
     * @param <T>
     * @throws Exception
     */
    public static <T> void validate(T target) throws Exception{
        //自定义注解实现方法
        // Field[] fields = t.getDeclaredFields();//根据传入Class获取所有Field
        //遍历所有字段,检查字段是否包含属性非空检查注解
        //for (Field field : fields)  ==>
        // TokenValidate check = field.getAnnotation(TokenValidate.class);获取字段上的注解(如果存在)
        //根据注解类型,执行不同的判断逻辑
        //field.get(target);可反射获取字段的值,用以校验

        StringBuilder sb = new StringBuilder("[");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations;
        try {
            violations = validator.validate(target);
        } catch (UnexpectedTypeException e) {
            throw new InvocationTargetException(new ValidatorException("参数校验类型错误"));
        } catch (IllegalArgumentException e) {
            throw new InvocationTargetException(new ValidatorException("参数异常,请校验请求参数"));
        }catch (Exception e) {
            throw new InvocationTargetException(new ValidatorException("参数校验异常"));
        }

        if (null != violations && !violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage());
                sb.append(",");
            }
            //删除最后一个逗号
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            throw new InvocationTargetException(new ValidatorException(sb.toString()));
        }
    }

}
