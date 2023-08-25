package my.domain;

import lombok.Data;

import java.util.List;

/**
 * @author OutResource Boy
 * @date 2023/8/18 15:21
 */
@Data
public class MyUser {
    private String id;
    private String userName;
    private List<String> roleList;
}
