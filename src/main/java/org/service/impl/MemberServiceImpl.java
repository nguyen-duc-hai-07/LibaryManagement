package org.service.impl;

import org.service.MemberService;
import org.config.DBConnectionPool;
import org.dao.MemberDAO;
import org.dto.response.MemberResponse;
import org.model.Member;
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

    public void addMember(Member member) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            memberDAO.insert(conn, member);
            conn.commit();
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
