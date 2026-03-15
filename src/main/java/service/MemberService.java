package service;

import dto.MemberResponse;
import model.Member;
import java.util.List;

public interface MemberService {

    void addMember(Member member) throws Exception;

    List<MemberResponse> findAllMembers() throws Exception;
}
