package sera.sse.community.mapper;

import org.apache.ibatis.annotations.*;
import sera.sse.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user1 (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user1 where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user1 where id=#{id}")
    User findById(@Param("id") Integer id);
    @Select("select * from user1 where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
    @Update("update user1 set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);
}
