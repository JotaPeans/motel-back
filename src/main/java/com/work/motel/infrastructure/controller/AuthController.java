package com.work.motel.infrastructure.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.work.motel.application.service.FuncionarioService;
import com.work.motel.domain.entity.Funcionario;
import com.work.motel.domain.entity.LoginRequest;
import com.work.motel.infrastructure.Serializers.CadastroRequest;
import com.work.motel.infrastructure.Serializers.CadastroResponse;
import com.work.motel.application.util.JwtUtil;
import com.work.motel.application.util.JwtResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private FuncionarioService service;
    
    @Autowired
    private final JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Funcionario> funcionarioOpt = service.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        jwtUtil.init();

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();

            // Gerar o JWT após a autenticação
            String token = jwtUtil.gerarToken(funcionario.getEmail(), funcionario.getId());

            // Retornar o token no corpo da resposta
            return ResponseEntity.ok().body(new JwtResponse(token));  // Retorna o token com o prefixo "Bearer"
        }

        return ResponseEntity.status(401).body("Email ou senha inválidos");  // Erro
    }

    // Endpoint de cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody CadastroRequest cadastroRequest) {
        // Chama o serviço para cadastrar um novo funcionário
        System.out.println(cadastroRequest.getName() + " - " + cadastroRequest.getEmail() + " - " + cadastroRequest.getPassword());
        Funcionario funcionario = service.cadastrarFuncionario(
            cadastroRequest.getName(),
            cadastroRequest.getEmail(),
            cadastroRequest.getPassword()
        );

        CadastroResponse res = new CadastroResponse(funcionario.getName(), funcionario.getEmail());
        return ResponseEntity.ok(res);
    }
}
