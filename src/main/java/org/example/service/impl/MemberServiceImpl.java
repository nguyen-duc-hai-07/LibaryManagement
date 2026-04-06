package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.MemberRequest;
import org.example.service.MemberService;
import org.example.config.DBConnectionPool;
import org.example.dao.MemberDAO;
import org.example.dto.response.MemberResponse;
import org.example.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;

import java.sql.Connection;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public MemberResponse addMember(MemberRequest request) throws Exception {
        Connection conn = null;
        Member member = new Member(request.getName(), request.getEmail(), request.getPhone());
        log.debug("Adding member: {}", member);
        try {
            conn = pool.getConnection();
            memberDAO.insert(conn,member);
            conn.commit();
            return new MemberResponse(
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    member.getPhone()
            );
        } catch (Exception e) {
            log.error("Error adding member: {}", e.getMessage(), e);
            if(conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }

    public List<MemberResponse> findAllMembers() throws Exception {
        log.debug("Finding all members");
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<MemberResponse> members = memberDAO.findAll(conn);
            conn.commit();
            return members;
        } catch (Exception e) {
            log.error("Error finding members: {}", e.getMessage(), e);
            if(conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }
}
