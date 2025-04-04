package com.work.motel.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entity.Reserva;
import com.work.motel.enums.QuartoStatus;
import com.work.motel.enums.QuartoTipo;
import com.work.motel.enums.ReservaStatus;
import javax.sql.DataSource;

@Repository
public class ReservaRepository {
  private final JdbcTemplate jdbcTemplate;

  // Injeção de dependência do JdbcTemplate
  @Autowired
  public ReservaRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource); // Usando o DataSource configurado
  }

  public List<Reserva> getAll(int page, int size) {
    // Calculando o offset para a paginação
    int offset = (page - 1) * size;

    String sql = "SELECT r.*, c.nome AS cliente_nome, q.numero AS quarto_numero, q.tipo AS quarto_tipo, q.status AS quarto_status " +
                 "FROM Reserva r " +
                 "LEFT JOIN Cliente c ON r.clienteId = c.id " +
                 "LEFT JOIN Quarto q ON r.quartoId = q.id " +
                 "ORDER BY r.id LIMIT ? OFFSET ?";

    List<Reserva> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
      Reserva reserva = new Reserva(
        rs.getInt("id"),
        ReservaStatus.valueOf(rs.getString("status")),
        rs.getTimestamp("data"),
        rs.getInt("funcionarioId"),
        rs.getInt("clienteId"),
        rs.getInt("quartoId"),
        rs.getString("cliente_nome"),
        rs.getString("quarto_numero"),
        QuartoTipo.valueOf(rs.getString("quarto_tipo")),
        QuartoStatus.valueOf(rs.getString("quarto_status"))
      );

      return reserva;
    }, size, offset);
  
    return results;
  }

  public Optional<Reserva> getById(Integer id) {
    String sql = "SELECT r.*, c.nome AS cliente_nome, q.numero AS quarto_numero, q.tipo AS quarto_tipo, q.status AS quarto_status " +
                 "FROM Reserva r " +
                 "LEFT JOIN Cliente c ON r.clienteId = c.id " +
                 "LEFT JOIN Quarto q ON r.quartoId = q.id " +
                 "WHERE r.id = ?";

    List<Reserva> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
      Reserva reserva = new Reserva(
        rs.getInt("id"),
        ReservaStatus.valueOf(rs.getString("status")),
        rs.getTimestamp("data"),
        rs.getInt("funcionarioId"),
        rs.getInt("clienteId"),
        rs.getInt("quartoId"),
        rs.getString("cliente_nome"),
        rs.getString("quarto_numero"),
        QuartoTipo.valueOf(rs.getString("quarto_tipo")),
        QuartoStatus.valueOf(rs.getString("quarto_status"))
      );

      return reserva;
    }, id);

    return results.stream().findFirst();
  }

  public List<Reserva> getByRoomId(Integer quartoId) {
    String sql = "SELECT r.*, c.nome AS cliente_nome, q.numero AS quarto_numero, q.tipo AS quarto_tipo, q.status AS quarto_status " +
                 "FROM Reserva r " +
                 "LEFT JOIN Cliente c ON r.clienteId = c.id " +
                 "LEFT JOIN Quarto q ON r.quartoId = q.id " +
                 "WHERE r.quartoId = ? " +
                 "ORDER BY r.id";

    List<Reserva> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
      Reserva reserva = new Reserva(
        rs.getInt("id"),
        ReservaStatus.valueOf(rs.getString("status")),
        rs.getTimestamp("data"),
        rs.getInt("funcionarioId"),
        rs.getInt("clienteId"),
        rs.getInt("quartoId"),
        rs.getString("cliente_nome"),
        rs.getString("quarto_numero"),
        QuartoTipo.valueOf(rs.getString("quarto_tipo")),
        QuartoStatus.valueOf(rs.getString("quarto_status"))
      );

      return reserva;
    }, quartoId);

    return results;
}

  public Optional<Reserva> create(Optional<Reserva> data) {
    if (data.isPresent()) {
      Reserva reserva = data.get();
      String sql = "INSERT INTO Reserva (funcionarioId, clienteId, quartoId) VALUES (?, ?, ?)";

      jdbcTemplate.update(
        sql,
        reserva.getFuncionarioId(),
        reserva.getClienteId(),
        reserva.getQuartoId()
      );

      return Optional.of(reserva);
    }
    return Optional.empty();
  }

  public void checkout(Integer id) {
    String sql = "UPDATE Reserva SET status = ? WHERE id = ?";
    jdbcTemplate.update(sql, ReservaStatus.FINALIZADA.toString(), id);
  }

  public Optional<Reserva> updateById(Integer id, Optional<Reserva> data) {
    if (data.isPresent()) {
      Reserva reserva = data.get();

      String sqlSelect = "SELECT COUNT(*) FROM Reserva WHERE id = ?";
      Integer count = jdbcTemplate.queryForObject(sqlSelect, Integer.class, id);

      if (count == null || count == 0) {
        return null;
      }

      String sqlUpdate = "UPDATE Reserva SET status = ?, funcionarioId = ?, clienteId = ?, quartoId = ? WHERE id = ?";

      jdbcTemplate.update(
        sqlUpdate,
        reserva.getStatus().toString(),
        reserva.getFuncionarioId(),
        reserva.getClienteId(),
        reserva.getQuartoId(),
        id
      );

      reserva.setId(id);
      return Optional.of(reserva);
    }

    return null;
  }

  public void deleteById(Integer id) {
    String sqlDeleteReserva = "DELETE FROM Reserva WHERE id = ?";
    int rowsAffectedReserva = jdbcTemplate.update(sqlDeleteReserva, id);

    if (rowsAffectedReserva == 0) {
      throw new RuntimeException("Reserva não encontrada para o ID: " + id);
    }
  }
}
