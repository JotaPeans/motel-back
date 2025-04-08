package com.work.motel.application.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private SecretKey secretKey;  // A chave secreta agora será do tipo SecretKey
    private long expirationTime = 86400000L; // 1 dia em milissegundos (24 horas)

    public JwtUtil() {
        // Gerando uma chave segura de 256 bits usando a classe Keys
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Método para gerar o JWT
    public String gerarToken(String email, Integer idFuncionario) {
        return Jwts.builder()
                .setSubject(email)  // Adiciona o email como o subject do JWT
                .claim("id", idFuncionario)  // Adiciona o ID do funcionário como uma claim personalizada
                .setIssuedAt(new Date())  // Data de criação do token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Define o tempo de expiração
                .signWith(secretKey)  // Usa a chave secreta para assinar o token
                .compact();  // Retorna o JWT compactado
    }

    // Método para verificar a validade do JWT
    public boolean verificarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
