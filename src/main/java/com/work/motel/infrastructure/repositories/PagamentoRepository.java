package com.work.motel.infrastructure.repositories;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entities.Pagamento;
import com.work.motel.domain.enums.FormaPagamento;

import javax.sql.DataSource;

@Repository
public class PagamentoRepository {
  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Pagamento> rowMapper = (rs, rowNum) -> new Pagamento(
      rs.getInt("id"),
      rs.getInt("clienteId"),
      rs.getInt("reservaId"),
      rs.getInt("consumoId"),
      rs.getString("payment_provider_id"),
      FormaPagamento.valueOf(rs.getString("forma_pagamento")));

  @Autowired
  public PagamentoRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Pagamento> getAll(int page, int size) {
    String sql = "SELECT * FROM Pagamento p ORDER BY p.id LIMIT ? OFFSET ?";

    int offset = (page - 1) * size;
    List<Pagamento> results = jdbcTemplate.query(sql, rowMapper, size, offset);

    return results;
  }

  public Optional<Pagamento> getById(Integer id) {
    String sql = "SELECT * FROM Pagamento WHERE id = ?";
    List<Pagamento> results = jdbcTemplate.query(sql, rowMapper, id);
    return results.stream().findFirst();
  }

  public void addReservation(Integer paymentId, Integer reservationId) {
    String sql = "UPDATE Pagamento p " +
        "SET reservaId = ? " +
        "WHERE p.id = ?;";

    jdbcTemplate.update(sql, reservationId, paymentId);
  }

  public Optional<Pagamento> getByPaymentProviderId(String id) {
    String sql = "SELECT * FROM Pagamento WHERE payment_provider_id = ?";
    List<Pagamento> results = jdbcTemplate.query(sql, rowMapper, id);
    return results.stream().findFirst();
  }

  public Optional<Pagamento> create(Optional<Pagamento> data) {
    if (data.isPresent()) {
      Pagamento pagamento = data.get();
      String sql = "INSERT INTO Pagamento (clienteId, reservaId, consumoId, payment_provider_id, forma_pagamento) VALUES (?, ?, ?, ?, ?)";

      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
        ps.setInt(1, pagamento.getClienteId());
        ps.setNull(2, Types.NULL);
        ps.setNull(3, Types.NULL);
        ps.setString(4, pagamento.getPayment_provider_id());
        ps.setString(5, pagamento.getForma_Pagamento().toString());
        return ps;
      }, keyHolder);

      pagamento.setId(keyHolder.getKey().intValue());

      return Optional.of(pagamento);
    }
    return Optional.empty();
  }

  public void deleteById(Integer id) {
    String sqlDeletePagamento = "DELETE FROM Pagamento WHERE id = ?";
    int rowsAffectedPagamento = jdbcTemplate.update(sqlDeletePagamento, id);

    if (rowsAffectedPagamento == 0) {
      throw new RuntimeException("Pagamento não encontrado para o ID: " + id);
    }
  }
}
