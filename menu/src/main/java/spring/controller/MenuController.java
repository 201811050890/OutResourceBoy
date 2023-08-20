package spring.controller;

import spring.domain.MenuVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.result.RespBean;
import spring.service.IMenuService;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/menu")
@RestController
public class MenuController {

    @Resource
    IMenuService menuService;


    @GetMapping("/list")
    public RespBean menuList(){
        List<MenuVO> menuVOList = menuService.listMenu();
        return RespBean.success(menuVOList);
    }

}
