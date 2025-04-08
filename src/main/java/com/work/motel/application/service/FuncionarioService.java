package com.work.motel.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entity.Funcionario;
import com.work.motel.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    @Autowired
    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    // Usar o PasswordEncoder para validar as senhas
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Funcionario> authenticate(String email, String password) {
        Optional<Funcionario> funcionarioOpt = repository.findByEmail(email);

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            
            // Verificar se a senha bate com a senha criptografada
            if (passwordEncoder.matches(password, funcionario.getPassword())) {
                return Optional.of(funcionario);
            }
        }
        return Optional.empty();  // Retorna vazio caso o login falhe
    }

    // Método para cadastrar um novo funcionário
    public Funcionario cadastrarFuncionario(String nome, String email, String senha) {
        // Criptografando a senha
        String senhaCriptografada = passwordEncoder.encode(senha);

        // Criar uma nova entidade Funcionario com as informações
        Funcionario funcionario = new Funcionario(null, nome, email, senhaCriptografada);

        // Chamar o repositório para salvar no banco
        // Assumindo que há um método no repositório para salvar
        return repository.salvar(funcionario);
    }
}
