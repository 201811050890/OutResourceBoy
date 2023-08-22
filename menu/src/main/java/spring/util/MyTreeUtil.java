package spring.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 树结构工具
 *
 * @author hao.sun
 * @date 2023-08-22 18:14:45
 */
public class MyTreeUtil {
    /**
     *
     * 造树
     *
     * @param dataList     数据列表
     * @param rootFiled    根节点字段名
     * @param rootFieldVal 根节点值
     * @param parentFiled  父节点字段名
     * @param orderByField order by字段名
     * @param childField   子节点名
     * @return {@link List}<{@link T}>
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     * @throws NoSuchFieldException   没有该字段异常
     */
    public static <T> List<T> buildTree(List<T> dataList,
                                    String rootFiled, Object rootFieldVal,
                                    String parentFiled, String orderByField, String childField) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 1、筛选出所有父节点
        List<T> parentNodeList = dataList.stream().filter(
                item -> ObjectUtil.equals(ReflectUtil.getFieldValue(item.getClass(), rootFiled), rootFieldVal)
        )
        .collect(Collectors.toList());
        // 2、父节点列表排序, quickSort
        List<T> sortedList = ListUtil.sortByProperty(parentNodeList, orderByField);
        // 3、构建树形结构的逻辑...
        getChildTree(sortedList, parentFiled, dataList, orderByField, childField);
        return sortedList;

    }

    /**
     *
     * 构造树
     * @param sortedList  排序完的List
     * @param parentFiled 父节点
     * @param dataList 原数据 所有
     */
    private static <T> void getChildTree(List<T> sortedList, String parentFiled, List<T> dataList, String orderByField, String childField) {
        for (T exportOrganization : sortedList) {
            List<T> subList = dataList.stream().filter(o -> Objects.nonNull(ReflectUtil.getFieldValue(o, parentFiled)) && ReflectUtil.getFieldValue(o, parentFiled)
                    .equals(ReflectUtil.getFieldValue(exportOrganization, parentFiled))).collect(Collectors.toList());
            // 排序
            ListUtil.sortByProperty(subList, orderByField);
            ReflectUtil.setFieldValue(exportOrganization, childField, subList);
            if (CollectionUtil.isNotEmpty(subList)) {
                getChildTree(subList, parentFiled, dataList, orderByField, childField);
            }
        }
    }
}
