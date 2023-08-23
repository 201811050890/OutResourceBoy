package spring.result;


import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 * @author OutResource Boy
 * @date 2022/6/29 1:07 PM
 */
@Setter
@Getter
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public ServiceException(Integer code, String message) {

        this.code = code;
        this.message = message;
    }

    public ServiceException(RespBean error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
