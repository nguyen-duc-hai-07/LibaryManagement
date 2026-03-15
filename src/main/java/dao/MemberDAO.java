package dao;

import dto.MemberResponse;
import model.Member;

import java.util.*;
import java.sql.Connection;

public interface MemberDAO {
    void insert(Connection conn , Member member) throws Exception;

    Member findById(Connection conn , int id) throws Exception;

    List<MemberResponse> findAll(Connection conn) throws Exception;
}
