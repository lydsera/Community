package sera.sse.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import sera.sse.community.dto.InvitationQueryDTO;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.InvitationExample;

import java.util.List;

public interface InvitationExtMapper {

    int incView(Invitation record);
    int incCommentCount(Invitation record);
    List<Invitation> selectRelated(Invitation invitation);

    Integer countBySearch(InvitationQueryDTO invitationQueryDTO);

    List<Invitation> selectBySearch(InvitationQueryDTO invitationQueryDTO);
}