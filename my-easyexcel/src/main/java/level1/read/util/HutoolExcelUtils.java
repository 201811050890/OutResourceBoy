package level1.read.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import level1.read.annotation.HutoolExcelAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Sun
 * @date 2023/10/5 11:20
 */
public class HutoolExcelUtils {
    public static <T> Map<String, String> aliasAnnotation2Map(Class<T> myClass){
        Field[] fields = ReflectUtil.getFields(myClass);
        Map<String, String> map = MapUtil.newHashMap();
        for (Field f: fields){
            if (AnnotationUtil.hasAnnotation(f, HutoolExcelAlias.class)){
                HutoolExcelAlias annotation = AnnotationUtil.getAnnotation(f, HutoolExcelAlias.class);
                map.put(annotation.alias(), f.getName());
            }
        }
        return map;
    }
}
