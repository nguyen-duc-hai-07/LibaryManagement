package org.example.dao.impl;

import org.example.dao.MemberDAO;
import org.example.model.Member;
import org.example.dto.response.MemberResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Repository
public class MemberDAOImpl implements MemberDAO {
    public void insert(Connection conn , Member member) throws Exception {
        String sql = "INSERT INTO members(name,email,phone) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    member.setId(rs.getInt(1));
                }
            }
        }
    }

    public Member findById(Connection conn , int id) throws Exception {
        String sql = "SELECT * FROM members WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Member(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
                }
            }
        }
        return null;
    }

    public List<MemberResponse> findAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM members";
        List<MemberResponse> members = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while(rs.next()) {
                members.add(new MemberResponse(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
            }
        }
        return members;
    }
}

