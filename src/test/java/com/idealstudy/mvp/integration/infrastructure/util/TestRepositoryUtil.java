package com.idealstudy.mvp.integration.infrastructure.util;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 더 원활한 통합테스트를 위해 개선된 모델인 DummyMemberGenerator로 대체됨.
 */
@Deprecated
@Component
public class TestRepositoryUtil {

    @PersistenceContext
    private EntityManager entityManager;

    private final DataSource dataSource;

    private final HttpServletRequest request;

    @Autowired
    public TestRepositoryUtil(DataSource dataSource, HttpServletRequest request) {
        this.dataSource = dataSource;
        this.request = request;
    }

    public Long getAutoIncrement(String tableName) {
        fetchTable(tableName);
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = '"+tableName+"'";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    private void fetchTable(String tableName) {
        String sql = "ANALYZE TABLE " + tableName;
        executeDDL(sql);
    }

    private void executeDDL(String query) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setToken(String userId, Role role) {

        JwtPayloadDto payload = JwtPayloadDto.builder()
                .sub(userId)
                .role(role)
                .build();

        request.setAttribute("jwtUtil", payload);
    }

}
