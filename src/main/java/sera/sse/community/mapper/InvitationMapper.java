package sera.sse.community.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.InvitationExample;

public interface InvitationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    long countByExample(InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int deleteByExample(InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int insert(Invitation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int insertSelective(Invitation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    List<Invitation> selectByExampleWithBLOBsWithRowbounds(InvitationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    List<Invitation> selectByExampleWithBLOBs(InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    List<Invitation> selectByExampleWithRowbounds(InvitationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    List<Invitation> selectByExample(InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    Invitation selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByExampleSelective(@Param("record") Invitation record, @Param("example") InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByExampleWithBLOBs(@Param("record") Invitation record, @Param("example") InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByExample(@Param("record") Invitation record, @Param("example") InvitationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByPrimaryKeySelective(Invitation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByPrimaryKeyWithBLOBs(Invitation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INVITATION
     *
     * @mbg.generated Tue Jan 10 17:56:28 CST 2023
     */
    int updateByPrimaryKey(Invitation record);
}