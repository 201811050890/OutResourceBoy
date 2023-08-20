package spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring.domain.SysMenuPO;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    /**
     * 不要* ...【要改掉】
     * @param roleId roleId
     * @return list
     */
    @Select("select * from sys_menu m inner join sys_role_menu rm on m.menu_id = rm.menu_id \n" +
            "where rm.role_id  = #{roleId}")
    List<SysMenuPO> listAllMapper(@Param("roleId") Integer roleId);

}
