package sera.sse.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import sera.sse.community.model.Invitation;

@Mapper
public interface InvitationMapper {
    @Insert("insert into invitation (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Invitation invitation);
}
