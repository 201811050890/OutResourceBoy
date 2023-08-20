package spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 返回前端menu类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO {
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuType;
    private String path;
    private String componentPath;
    private Integer orderNum;
    private String icon;
    private String perms;
    private MenuVO childMenu;
}
