package com.work.motel.domain.repository;

import java.util.List;
import java.util.Optional;

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
  public Optional<Quarto> getById(Integer id) {
    String sql = "SELECT * FROM Quarto WHERE id = ?";
    List<Quarto> results = jdbcTemplate.query(sql, rowMapper, id);
    return results.stream().findFirst();
  }

  @Override
  public Optional<Quarto> create(Optional<Quarto> data) {
    if (data.isPresent()) {
      Quarto quarto = data.get();
      String sql = "INSERT INTO Quarto (numero, tipo, status) VALUES (?, ?, ?)";

      jdbcTemplate.update(
        sql,
        quarto.getNumero(),
        QuartoTipo.SUITE.toString(),
        QuartoStatus.DISPONIVEL.toString()
      );
      return Optional.of(quarto);
    }
    return null;
  }

  @Override
  public Optional<Quarto> updateById(Integer id, Quarto data) {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void deleteById(Integer id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
