package demo;

import java.util.List;

public interface IUserMapper {
    List<String> queryUserName(Integer id);

    Integer queryUserAge(Integer id);
}
