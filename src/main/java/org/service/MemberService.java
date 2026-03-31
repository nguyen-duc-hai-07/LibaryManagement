package org.service;

import org.dto.response.MemberResponse;
import org.model.Member;
import java.util.List;

public interface MemberService {

    void addMember(Member member) throws Exception;

    List<MemberResponse> findAllMembers() throws Exception;
}
