package service.impl;

import service.MemberService;
import config.DBConnectionPool;
import dao.MemberDAO;
import dto.MemberResponse;
import model.Member;
import java.util.List;

import java.sql.Connection;

public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;
    private final DBConnectionPool pool;
    public MemberServiceImpl(MemberDAO memberDAO, DBConnectionPool pool) {
        this.memberDAO = memberDAO;
        this.pool = pool;
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
