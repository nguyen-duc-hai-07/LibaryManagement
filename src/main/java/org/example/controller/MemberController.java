package org.example.controller;

import org.example.dto.request.MemberRequest;
import org.example.dto.response.MemberResponse;
import org.example.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberResponse addMember(MemberRequest request) throws Exception {
        return memberService.addMember(request);
    }

    @GetMapping
    public List<MemberResponse> findAllMembers() throws Exception {
        return memberService.findAllMembers();
    }
}
