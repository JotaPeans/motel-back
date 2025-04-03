package com.work.motel.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.work.motel.domain.entity.Quarto;
import com.work.motel.domain.interfaces.IBaseRepository;
import com.work.motel.enums.QuartoStatus;
import com.work.motel.enums.QuartoTipo;
import javax.sql.DataSource;

@Repository
public class QuartoRepository implements IBaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Quarto> rowMapper = (rs, rowNum) -> new Quarto(
      rs.getInt("id"),
      rs.getInt("numero"),
      QuartoTipo.valueOf(rs.getString("tipo")),
      QuartoStatus.valueOf(rs.getString("status"))
    );

    // Injeção de dependência do JdbcTemplate
    @Autowired
    public QuartoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); // Usando o DataSource configurado
    }

    @Override
    public List<Quarto> getAll() {
        String sql = "SELECT * FROM Quarto";
        List<Quarto> results = jdbcTemplate.query(sql, rowMapper);
        return results;
    }

    @Override
    public Quarto getById() {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Quarto create() {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Quarto update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
