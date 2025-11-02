package br.edu.insper.exercicio.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public List<Pessoa> getPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa createPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa getPessoa(Integer id) {
        return pessoaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }
}
