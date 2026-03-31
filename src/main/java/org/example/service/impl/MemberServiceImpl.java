package org.example.service.impl;

import org.example.dto.request.MemberRequest;
import org.example.service.MemberService;
import org.example.config.DBConnectionPool;
import org.example.dao.MemberDAO;
import org.example.dto.response.MemberResponse;
import org.example.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;

import java.sql.Connection;

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
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<MemberResponse> members = memberDAO.findAll(conn);
            conn.commit();
            return members;
        } catch (Exception e) {
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
