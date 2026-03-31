package org.dao;

import org.dto.response.MemberResponse;
import org.model.Member;

import java.util.*;
import java.sql.Connection;

public interface MemberDAO {
    void insert(Connection conn , Member member) throws Exception;

    Member findById(Connection conn , int id) throws Exception;

    List<MemberResponse> findAll(Connection conn) throws Exception;
}
