package sera.sse.community.dto;

import lombok.Data;

@Data
public class InvitationQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
