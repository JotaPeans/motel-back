package com.work.motel.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entities.Manager;

@Repository
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Manager> rowMapper = (rs, rowNum) -> new Manager(
        rs.getInt("id"),
        rs.getInt("funcionarioId")
    );

    @Autowired
    public ManagerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Manager> getAll() {
        String sql = "SELECT * FROM Gerente";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Manager> getById(Integer id) {
        String sql = "SELECT * FROM Gerente WHERE id = ?";
        List<Manager> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.stream().findFirst();
    }

    public Optional<Manager> create(Optional<Manager> data) {
        if (data.isPresent()) {
            Manager manager = data.get();

            String sql = "INSERT INTO Gerente (funcionarioId) VALUES (?)";
            jdbcTemplate.update(sql, manager.getFuncionarioId());

            return Optional.of(manager);
        }
        return Optional.empty();
    }

    public Optional<Manager> updateById(Integer id, Optional<Manager> data) {
        if (data.isPresent()) {
            Manager manager = data.get();

            String sqlSelect = "SELECT COUNT(*) FROM Gerente WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(sqlSelect, Integer.class, id);

            if (count == null || count == 0) {
                return Optional.empty();
            }

            String sqlUpdate = "UPDATE Gerente SET funcionarioId = ? WHERE id = ?";
            jdbcTemplate.update(sqlUpdate, manager.getFuncionarioId(), id);

            manager.setId(id);
            return Optional.of(manager);
        }
        return Optional.empty();
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM Gerente WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new RuntimeException("Gerente não encontrado para o ID: " + id);
        }
    }
}
