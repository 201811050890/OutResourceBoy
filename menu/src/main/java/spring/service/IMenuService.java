package spring.service;

import spring.domain.MenuVO;

import java.util.List;

public interface IMenuService {
    /**
     * 列出菜单信息
     * @return list
     */
    List<MenuVO> listMenu();
}
