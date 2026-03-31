package org.example.service;

import org.example.dto.request.MemberRequest;
import org.example.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    MemberResponse addMember(MemberRequest request) throws Exception;

    List<MemberResponse> findAllMembers() throws Exception;
}
