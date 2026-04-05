package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.MemberRequest;
import org.example.dto.response.MemberResponse;
import org.example.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberResponse addMember(@RequestBody MemberRequest request) throws Exception {
        log.info("Adding member: {}", request);
        return memberService.addMember(request);
    }

    @GetMapping
    public List<MemberResponse> findAllMembers() throws Exception {
        log.info("Finding all members");
        return memberService.findAllMembers();
    }
}
