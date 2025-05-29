package com.work.motel.infrastructure.repositories;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entities.Consumo;

@Repository
public class ConsumoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConsumoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Consumo> getAll(String year, String produto_id, int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT cs.*, c.nome AS cliente_nome, p.nome AS produto_nome, s.nome AS servico_nome " +
                "FROM Consumo cs " +
                "LEFT JOIN Cliente c ON cs.clienteId = c.id " +
                "LEFT JOIN Produto p ON cs.produtoId = p.id " +
                "LEFT JOIN Servico s ON cs.servicoId = s.id " +
                "WHERE YEAR(cs.data_consumo) = ? AND " +
                "( (? = '0' AND (cs.produtoId IS NULL OR cs.produtoId LIKE '%')) OR (? != '0' AND cs.produtoId = ?) ) " +
                "ORDER BY cs.data_consumo " +
                "DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Consumo(
                    rs.getInt("id"),
                    (Integer) rs.getObject("produtoId"),
                    (Integer) rs.getObject("servicoId"),
                    rs.getInt("clienteId"),
                    rs.getTimestamp("data_consumo"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor"),
                    rs.getString("cliente_nome"),
                    rs.getString("produto_nome"),
                    rs.getString("servico_nome"));
        }, year, produto_id, produto_id, produto_id, size, offset);
    }

    public Optional<Consumo> getById(Integer id) {
        String sql = "SELECT cs.*, c.nome AS cliente_nome, p.nome AS produto_nome, s.nome AS servico_nome " +
                "FROM Consumo cs " +
                "LEFT JOIN Cliente c ON cs.clienteId = c.id " +
                "LEFT JOIN Produto p ON cs.produtoId = p.id " +
                "LEFT JOIN Servico s ON cs.servicoId = s.id " +
                "WHERE cs.id = ?";

        List<Consumo> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Consumo(
                    rs.getInt("id"),
                    (Integer) rs.getObject("produtoId"),
                    (Integer) rs.getObject("servicoId"),
                    rs.getInt("clienteId"),
                    rs.getTimestamp("data_consumo"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor"),
                    rs.getString("cliente_nome"),
                    rs.getString("produto_nome"),
                    rs.getString("servico_nome"));
        }, id);

        return results.stream().findFirst();
    }

    public List<Consumo> getByClienteId(Integer clienteId) {
        String sql = "SELECT cs.*, c.nome AS cliente_nome, p.nome AS produto_nome, s.nome AS servico_nome " +
                "FROM Consumo cs " +
                "LEFT JOIN Cliente c ON cs.clienteId = c.id " +
                "LEFT JOIN Produto p ON cs.produtoId = p.id " +
                "LEFT JOIN Servico s ON cs.servicoId = s.id " +
                "WHERE cs.clienteId = ? " +
                "ORDER BY cs.data_consumo DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Consumo(
                    rs.getInt("id"),
                    (Integer) rs.getObject("produtoId"),
                    (Integer) rs.getObject("servicoId"),
                    rs.getInt("clienteId"),
                    rs.getTimestamp("data_consumo"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor"),
                    rs.getString("cliente_nome"),
                    rs.getString("produto_nome"),
                    rs.getString("servico_nome"));
        }, clienteId);
    }

    public Optional<Consumo> create(Optional<Consumo> data) {
        if (data.isPresent()) {
            Consumo consumo = data.get();
            String sql = "INSERT INTO Consumo (produtoId, servicoId, clienteId, quantidade, valor) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
                ps.setObject(1, consumo.getProdutoId());
                ps.setObject(2, consumo.getServicoId());
                ps.setInt(3, consumo.getClienteId());
                ps.setInt(4, consumo.getQuantidade());
                ps.setDouble(5, consumo.getValor());
                return ps;
            }, keyHolder);

            consumo.setId(keyHolder.getKey().intValue());
            return Optional.of(consumo);
        }
        return Optional.empty();
    }

    public Optional<Consumo> updateById(Integer id, Optional<Consumo> data) {
        if (data.isPresent()) {
            Consumo consumo = data.get();
            String sqlSelect = "SELECT COUNT(*) FROM Consumo WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(sqlSelect, Integer.class, id);

            if (count == null || count == 0) {
                return Optional.empty();
            }

            String sqlUpdate = "UPDATE Consumo SET produtoId = ?, servicoId = ?, clienteId = ?, quantidade = ?, valor = ? WHERE id = ?";
            jdbcTemplate.update(
                    sqlUpdate,
                    consumo.getProdutoId(),
                    consumo.getServicoId(),
                    consumo.getClienteId(),
                    consumo.getQuantidade(),
                    consumo.getValor(),
                    id);

            consumo.setId(id);
            return Optional.of(consumo);
        }
        return Optional.empty();
    }

    public void deleteById(Integer id) {
        String sqlDelete = "DELETE FROM Consumo WHERE id = ?";
        int rows = jdbcTemplate.update(sqlDelete, id);
        if (rows == 0) {
            throw new RuntimeException("Consumo não encontrado para o ID: " + id);
        }
    }
}
