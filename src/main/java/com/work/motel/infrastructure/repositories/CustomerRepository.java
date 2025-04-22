package com.work.motel.infrastructure.repositories;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entities.Customer;

import javax.sql.DataSource;

@Repository
public class CustomerRepository {
  private final JdbcTemplate jdbcTemplate;
  
  private final RowMapper<Customer> rowMapper = (rs, rowNum) -> new Customer(
    rs.getInt("id"),
    rs.getString("nome"),
    rs.getString("telefone"),
    rs.getString("email"),
    rs.getString("CPF"),
    rs.getString("RG")
  );

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public CustomerRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Customer> getCustomerByRoomId(Integer id) {
    String sql = "SELECT c.* FROM Cliente c " +
                 "JOIN Reserva r ON c.id = r.clienteId " +
                 "WHERE r.quartoId = ?";
    List<Customer> results = jdbcTemplate.query(sql, rowMapper, id);
    return results.stream().toList();
  }

  public List<Customer> getAllByName(String nome) {
    String sql;
    Map<String, Object> params = new HashMap<>();
    
    if (nome == null || nome.isEmpty()) {
        sql = "SELECT * FROM Cliente c";
    } else {
        sql = "SELECT * FROM Cliente WHERE nome LIKE :nome";
        params.put("nome", "%" + nome + "%");
    }

    List<Customer> results = namedParameterJdbcTemplate.query(sql, params, rowMapper);

    return results;
}

  public Optional<Customer> getById(Integer id) {
    String sql = "SELECT * FROM Cliente WHERE id = ?";
    List<Customer> results = jdbcTemplate.query(sql, rowMapper, id);
    return results.stream().findFirst();
  }

  public Optional<Customer> create(Optional<Customer> data) {
    if (data.isPresent()) {
      Customer customer = data.get();
      String sql = "INSERT INTO Cliente (nome, telefone, email, CPF, RG) VALUES (?, ?, ?, ?, ?)";

      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
        ps.setString(1, customer.getNome());
        ps.setString(2, customer.getTelefone());
        ps.setString(3, customer.getEmail());
        ps.setString(4, customer.getCPF());
        ps.setString(5, customer.getRG());
        return ps;
      }, keyHolder);

      customer.setId(keyHolder.getKey().intValue());

      return Optional.of(customer);
    }
    return Optional.empty();
  }

  public Optional<Customer> updateById(Integer id, Optional<Customer> data) {
    if (data.isPresent()) {
      Customer customer = data.get();

      String sqlSelect = "SELECT COUNT(*) FROM Cliente WHERE id = ?";
      Integer count = jdbcTemplate.queryForObject(sqlSelect, Integer.class, id);

      if (count == null || count == 0) {
        return Optional.empty();
      }

      String sqlUpdate = "UPDATE Cliente SET nome = ?, telefone = ?, email = ?, CPF = ?, RG = ? WHERE id = ?";

      jdbcTemplate.update(
        sqlUpdate,
        customer.getNome(),
        customer.getTelefone(),
        customer.getEmail(),
        customer.getCPF(),
        customer.getRG(),
        id
      );

      customer.setId(id);
      return Optional.of(customer);
    }

    return Optional.empty();
  }

  public void deleteById(Integer id) {
    String sqlDeleteReserva = "DELETE FROM Reserva WHERE clienteId = ?";
    jdbcTemplate.update(sqlDeleteReserva, id);

    String sqlDeleteConsumo = "DELETE FROM Consumo WHERE clienteId = ?";
    jdbcTemplate.update(sqlDeleteConsumo, id);

    String sqlDeleteCustomer = "DELETE FROM Cliente WHERE id = ?";
    int rowsAffectedCustomer = jdbcTemplate.update(sqlDeleteCustomer, id);

    if (rowsAffectedCustomer == 0) {
      throw new RuntimeException("Cliente não encontrado para o ID: " + id);
    }
  }
}
