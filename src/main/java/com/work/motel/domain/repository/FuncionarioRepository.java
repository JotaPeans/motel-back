package com.work.motel.domain.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entity.Funcionario;

@Repository
public class FuncionarioRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Funcionario> rowMapper = (rs, rowNum) -> new Funcionario(
        rs.getInt("id"),
        rs.getString("nome"),
        rs.getString("email"),
        rs.getString("senha")
    );

    @Autowired
    public FuncionarioRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Funcionario> findByEmail(String email) {
        String sql = "SELECT * FROM Funcionario WHERE email = ?";
        List<Funcionario> results = jdbcTemplate.query(sql, rowMapper, email);
        return results.stream().findFirst();
    }

        // Método para salvar um funcionário no banco
    public Funcionario salvar(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (nome, email, senha) VALUES (?, ?, ?)";

        // Salva o novo funcionário no banco
        jdbcTemplate.update(sql, funcionario.getName(), funcionario.getEmail(), funcionario.getPassword());

        // Para pegar o id do novo funcionário, podemos fazer uma consulta após o insert.
        String sqlSelect = "SELECT LAST_INSERT_ID()";
        Integer id = jdbcTemplate.queryForObject(sqlSelect, Integer.class);

        funcionario.setId(id);
        return funcionario;
    }



    // Se necessário, pode incluir outros métodos, como salvar, atualizar, etc.
}
