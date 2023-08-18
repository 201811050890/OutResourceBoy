package log;

import java.util.Date;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import utils.GlobalWebVarUtil;
import utils.SnowFlakeIdGenerateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author OutResource Boy
 * 来自：ruoyi
 * 重要依赖：hu-tool
 * @date 2023/8/17 22:43
 */
@Aspect
@Component
public class OpLogAspect {

    /**
     * 处理完请求后执行
     *
     * @param opLog      操作日志
     * @param jsonResult json结果
     */
    @AfterReturning(pointcut = "@annotation(opLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, OpLog opLog, Object jsonResult) {
        handleLog(joinPoint, opLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param e     异常
     * @param opLog 操作日志
     */
    @AfterThrowing(pointcut = "@annotation(opLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OpLog opLog, Exception e) {
        handleLog(joinPoint, opLog, e, null);
    }

    /**
     * 处理日志
     *
     * @param joinPoint  连接点
     * @param opLog      操作日志注解
     * @param e          异常
     * @param jsonResult json结果
     */
    private void handleLog(JoinPoint joinPoint, OpLog opLog, Exception e, Object jsonResult) {
        try {
            // 记录到数据库时，return的数据 或者 异常的 限制长度
            int msgMaxSize = 500;

            // 1、生成日志Id
            String id = SnowFlakeIdGenerateUtils.getSnowFlakeId();
            // 2、操作人Id：这个需要获取当前登录的用户，这里不设置
            String opId = StrUtil.EMPTY;
            // 3、和操作人Id一样
            String opName = StrUtil.EMPTY;
            // 4、获取请求Uri
            HttpServletRequest request = GlobalWebVarUtil.getRequest();
            String requestUri = request.getRequestURI();
            // 5、获取请求者IP地址
            String ip = ServletUtil.getClientIP(request);
            // 6、获取请求模块
            String module = opLog.module();
            // +1、请求方式
            String requestMethod = request.getMethod();
            // 7、获取请求主题
            String title = opLog.title();
            // 8、获取请求Param参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            //  处理成json串
            String requestParam = JSONUtil.toJsonPrettyStr(parameterMap);
            // 9、获取Body参数--> 这个不能从request中获取，因为request的body只能被读取一次，所有采用从方法的形参中读取
            String bodyParam = StrUtil.EMPTY;
            if (opLog.useBody()) {
                Object[] args = joinPoint.getArgs();
                bodyParam = JSONUtil.toJsonPrettyStr(args[opLog.bodyIndex()]);
            }
            // 10、处理记录的message、状态
            String message;
            int status;
            if (ObjectUtil.isNull(e)) {
                status = OpLogEnum.SUCCESS.getFLAG();
                String resString = JSONUtil.toJsonPrettyStr(jsonResult);
                message = StrUtil.length(resString) > msgMaxSize ? StrUtil.sub(resString, 0, msgMaxSize) : resString;
            } else {
                status = OpLogEnum.FAIL.getFLAG();
                String resString = JSONUtil.toJsonPrettyStr(e);
                message = StrUtil.length(resString) > msgMaxSize ? StrUtil.sub(resString, 0, msgMaxSize) : resString;
            }
            // 记录日志
            SysOpLog sysOpLog = new SysOpLog();
            sysOpLog.setId(id);
            sysOpLog.setOpId(opId);
            sysOpLog.setOpName(opName);
            sysOpLog.setUrl(requestUri);
            sysOpLog.setIp(ip);
            sysOpLog.setModule(module);
            sysOpLog.setType(requestMethod);
            sysOpLog.setStatus(status);
            sysOpLog.setTitle(title);
            sysOpLog.setMessage(message);
            sysOpLog.setOpTime(new Date());
            sysOpLog.setRequestParam(requestParam);
            sysOpLog.setRequestBody(bodyParam);
            // --- 下面这几个我觉得放在mybatis拦截器里面比较好，这里就不设置了 --- //
            sysOpLog.setCreateTime(new Date());
            sysOpLog.setCreateBy("");
            sysOpLog.setUpdateTime(new Date());
            sysOpLog.setUpdateBy("");
        }catch (Exception exp){
            // 打一下异常..
        }

    }

}
