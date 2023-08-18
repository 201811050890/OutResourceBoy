package utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花id生成工具
 *
 * @author Brian Sun
 * @date 2023/6/28 17:06
 */
public class SnowFlakeIdGenerateUtils {
    public static String getSnowFlakeId(){
        //参数1为终端ID, 参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextIdStr();
    }
}
