package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class TyBaseEntity implements Serializable {

    private static final long serialVersionUID = 4125096758372084309L;

    public boolean canUpdate() {
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field: fields){
            if(field.isAnnotationPresent(TableId.class)) continue;
            if(field.isAnnotationPresent(TableField.class)){
                Annotation annotation = field.getAnnotation(TableField.class);
                if(!((TableField)annotation).exist()) continue;
            }

            Object obj = null;
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), this.getClass());
                Method method = pd.getReadMethod();
                obj = method.invoke(this);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if(null != obj){
                return true;
            }
        }
        return false;
    }
}
