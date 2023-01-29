package com.example.book.repository;

import com.example.book.controller.MusicForm;
import com.example.book.domain.Music;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMusicRepository implements MusicRepository {
    private final DataSource dataSource;

    public JdbcMusicRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Music> findAll() {
        String sql = "select * from music";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Music> song = new ArrayList<>();
            while(rs.next()) { // 불러올 테이블 컬럼 목록
                Music music = new Music();
                music.setId(rs.getLong("id"));
                music.setName(rs.getString("name"));
                music.setSinger(rs.getString("singer"));
                music.setGenre(rs.getString("genre"));
                song.add(music);
            }
            return song;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
    public Music save(Music music) {
        String sql = "insert into music(name, singer, genre) values(?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, music.getName());
            pstmt.setString(2, music.getSinger());
            pstmt.setString(3, music.getGenre());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                music.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return music;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    public List<Music> findCond(MusicForm form) {
        String sql = "select * from music where 1 ";
        if (form.getName() != null)
            sql = sql + "AND name like '%" + form.getName() + "%' ";
        if (form.getSinger() != null)
            sql = sql + "AND singer like '%" + form.getSinger() + "%' ";
        if (form.getGenre() != null)
            sql = sql + "AND genre like '%" + form.getGenre() + "%' ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            rs = pstmt.executeQuery();
            List<Music> song = new ArrayList<>();
            while(rs.next()) { // 불러올 테이블 컬럼 목록
                Music music = new Music();
                music.setId(rs.getLong("id"));
                music.setName(rs.getString("name"));
                music.setSinger(rs.getString("singer"));
                music.setGenre(rs.getString("genre"));
                song.add(music);
            }
            return song;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
}