package spring.domain;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 返回前端menu类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO {
    @Alias("menuId")
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuType;
    private String path;
    private String component;
    private Integer orderNum;
    private String icon;
    private String perms;
    private List<MenuVO> childMenu;
}
