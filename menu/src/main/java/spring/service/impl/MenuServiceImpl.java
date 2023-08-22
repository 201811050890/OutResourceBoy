package spring.service.impl;

import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.json.JSONUtil;
import spring.domain.MenuVO;
import org.springframework.stereotype.Service;
import spring.domain.SysMenuPO;
import spring.mapper.SysMenuMapper;
import spring.service.IMenuService;
import spring.util.MyTreeUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    SysMenuMapper menuMapper;

    /**
     * 列出菜单
     *
     * @return list
     */
    @Override
    public List<MenuVO> listMenu(){
        // 1、获取当前登陆用户的角色信息
        Integer roleId = 2;
        // 2、根据角色id关联查询出所有的菜单信息
        List<SysMenuPO> list = menuMapper.listAllMapper(roleId);

        // 3、构造树结构
        List<MenuVO> menuVOList = MyTreeUtil.beanToList(list, MenuVO.class);
        System.out.println(JSONUtil.toJsonStr(menuVOList));
        List<MenuVO> resMenu = MyTreeUtil.buildTree(menuVOList, "menuId", "parentId", 0, "orderNum", "childMenu");
//        System.out.println(JSONUtil.toJsonPrettyStr(list1));
        return resMenu;
    }
}
