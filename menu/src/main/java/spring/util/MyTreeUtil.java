package spring.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import spring.domain.SysMenuPO;

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
     * @param parentFiled  父节点字段名
     * @param orderByField order by字段名
     * @param childField   子节点名
     * @return {@link List}<{@link T}>

     */
    public static <T> List<T> buildTree(List<T> dataList,  String rootField,String parentFiled, Object rootParentVal,
                                     String orderByField, String childField){
        // 1、筛选出所有父节点
        List<T> parentNodeList = dataList.stream().filter(
                item -> ObjectUtil.equals(ReflectUtil.getFieldValue(item, parentFiled).toString(), String.valueOf(rootParentVal))
        ).collect(Collectors.toList());
        // 2、父节点列表排序
        List<T> sortedList = ListUtil.sortByProperty(parentNodeList, orderByField);
        // 3、构建树形结构的逻辑...
        getChildTree(sortedList, dataList, rootField, parentFiled, orderByField, childField);
        return sortedList;

    }

    /**
     *
     * 构造树
     * @param sortedList  排序完的List
     * @param parentFiled 父节点
     * @param dataList 原数据 所有
     */
    private static <T> void getChildTree(List<T> sortedList, List<T> dataList,  String rootField, String parentFiled, String orderByField, String childField) {
        for (T exportOrganization : sortedList) {
            List<T> subList = dataList.stream().filter(o -> Objects.nonNull(ReflectUtil.getFieldValue(o, parentFiled)))
                    .filter(o -> ObjectUtil.equals(StrUtil.toString(ReflectUtil.getFieldValue(o, parentFiled)), StrUtil.toString(ReflectUtil.getFieldValue(exportOrganization, rootField))))
                    .collect(Collectors.toList());
            // 排序
            List<T> ts = ListUtil.sortByProperty(subList, orderByField);
            ReflectUtil.setFieldValue(exportOrganization, childField, ts);
            if (CollectionUtil.isNotEmpty(ts)) {
                getChildTree(ts, dataList, rootField, parentFiled, orderByField, childField);
            }
        }
    }

    /**
     * hu-tool 的beanToList方法
     * <p>
     *     这里是将数据库实体类，转成VO
     * </p>
     * @param list 原始数据
     * @param menuVOClass 要转类的Class对象
     * @param <T> 泛型
     * @return 转换后的List
     */
    public static <T> List<T> beanToList(List<SysMenuPO> list, Class<T> menuVOClass) {
        return BeanUtil.copyToList(list, menuVOClass);
    }
}
