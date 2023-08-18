-- mysql 8.0 操作日志表结构
CREATE TABLE `sys_op_log`
(
    `id`            varchar(40) NOT NULL COMMENT '主键',
    `op_user_id`    varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作人id\n',
    `op_user_name`  varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作人账号',
    `url`           varchar(255)                                                 DEFAULT NULL COMMENT '访问路径',
    `ip`            varchar(255)                                                 DEFAULT NULL COMMENT '客户端ip',
    `module`        varchar(255)                                                 DEFAULT NULL COMMENT '所属模块',
    `type`          varchar(255)                                                 DEFAULT NULL COMMENT '请求类型',
    `status`        tinyint                                                      DEFAULT NULL COMMENT '1成功，0失败',
    `title`         varchar(255)                                                 DEFAULT NULL COMMENT '日志标题',
    `message`       text COMMENT '成功或失败的信息',
    `op_time`       datetime                                                     DEFAULT NULL COMMENT '操作时间',
    `request_param` varchar(2000)                                                DEFAULT NULL COMMENT '请求参数\n',
    `request_body`  varchar(2000)                                                DEFAULT NULL COMMENT '请求body',
    `create_time`   datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `create_by`     varchar(40)                                                  DEFAULT NULL COMMENT '创建人',
    `update_time`   datetime                                                     DEFAULT NULL COMMENT '更新时间',
    `update_by`     varchar(40)                                                  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统操作日志表';