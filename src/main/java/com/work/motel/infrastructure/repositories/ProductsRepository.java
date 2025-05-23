package com.work.motel.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.work.motel.domain.entities.Product;

@Repository
public class ProductsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductsRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> getAll() {
        String sql = "SELECT id, nome, tipo, valor, custo FROM Produto";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getDouble("valor"),
                rs.getDouble("custo")
            )
        );
    }

    public Optional<Product> getById(Integer id) {
        String sql = "SELECT id, nome, tipo, valor, custo FROM Produto WHERE id = ?";
        List<Product> products = jdbcTemplate.query(
            sql,
            new Object[]{id},
            (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getDouble("valor"),
                rs.getDouble("custo")
            )
        );
        return products.stream().findFirst();
    }

    public Optional<Product> create(Optional<Product> data) {
        if (data.isPresent()) {
            Product product = data.get();
            String sql = "INSERT INTO Produto (nome, tipo, valor, custo) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(
                sql,
                product.getNome(),
                product.getTipo(),
                product.getValor(),
                product.getCusto()
            );
            return Optional.of(product);
        }
        return null;
    }

    public Optional<Product> updateById(Integer id, Optional<Product> data) {
        if (data.isPresent()) {
            Product product = data.get();

            String sqlSelect = "SELECT COUNT(*) FROM Produto WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(sqlSelect, Integer.class, id);
            if (count == null || count == 0) {
                return null;
            }

            String sqlUpdate = "UPDATE Produto SET nome = ?, tipo = ?, valor = ?, custo = ? WHERE id = ?";
            jdbcTemplate.update(
                sqlUpdate,
                product.getNome(),
                product.getTipo(),
                product.getValor(),
                product.getCusto(),
                id
            );

            product.setId(id);
            return Optional.of(product);
        }
        return null;
    }

    public void deleteById(Integer id) {
        // Remover consumos associados
        String sqlDeleteConsumo = "DELETE FROM Consumo WHERE produtoId = ?";
        jdbcTemplate.update(sqlDeleteConsumo, id);

        // Remover relacionamento com quartos
        String sqlDeletePossui = "DELETE FROM Possui WHERE produtoId = ?";
        jdbcTemplate.update(sqlDeletePossui, id);

        // Remover o produto
        String sqlDeleteProduto = "DELETE FROM Produto WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sqlDeleteProduto, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Produto não encontrado para o ID: " + id);
        }
    }
}
