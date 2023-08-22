package spring.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.val;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 造树结构工具
 */
public class MyTreeUtil {
    /**
     * 造树
     */
    public <T> List<T> buildTree(List<T> dataList,
                                    String rootFiled, Object rootFieldVal,
                                    String parentFiled, String orderByField) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 1、筛选出所有父节点
        List<T> parentNodeList = dataList.stream().filter(
                item -> ObjectUtil.equals(ReflectUtil.getFieldValue(item.getClass(), rootFiled), rootFieldVal)
        )
        .collect(Collectors.toList());

        // 2、构造子树



        return null;
    }
}
