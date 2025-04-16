package com.work.motel.application.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import java.util.Date;

public class JwtUtil {
    public SecretKey secretKey;  // A chave secreta agora será do tipo SecretKey
    private long expirationTime = 86400000L; // 1 dia em milissegundos (24 horas)

    public JwtUtil() {
        // this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretKey = "36930c9f666269878f1b17f7d1b9c4f07f5c2569e19fd5e6bcbe60d5276951e4b7f59df92d5d90361be6a5c97bf47a7ad7f1b9248f116028f9314cf19fdd8be0d88fd8fcefa30cf93233567c2bb6b77b4f4480ab0b2f9f3f87c317ec50879bbe115cfdfa3c046c0329fe99448892edd0c7a39624cc43a5c7148b37316732535f9082f7a1260bae3ba3855ee4859f3c97f06d50703d5cec9360049a76c525cafba848d03eb7a31acb847f4ff951f7de9a2493e885a5ed3d09c1f49e3f9407af58e9b055f41a75114a72282ac5f3bd15f109d7c81b9b6f381ba30449c17cab60bfd4550d2478a68a097c31b5940a89d6811c76ae7c7c8e5f210213cc92ffcc290b";
        byte[] keyBytes = secretKey.getBytes();
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
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

    public Claims decodeJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
