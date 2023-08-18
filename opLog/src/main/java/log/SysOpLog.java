package log;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志表
 *  <p>
 *      对应sys_op_log
 *  </p>
 * @author OutResource Boy
 * @date 2023/07/01
 */
@Data
public class SysOpLog implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 操作人id
     */
    private String opId;

    /**
     * 操作人账号
     */
    private String opName;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 请求类型
     */
    private String type;

    /**
     * 1成功，0失败
     */
    private Integer status;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 成功或失败的信息
     */
    private String message;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 请求参数
     */
    private String requestParam;

    private String requestBody;

    private static final long serialVersionUID = 1L;

    // ------  使用拦截器填充  ------ //
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;
}