package sera.sse.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sera.sse.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into _user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
    @Select("select * from _user where token=#{token}")
    User findByToken(@Param("token") String token);
}