package br.edu.insper.exercicio.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> getPessoas() {
        return pessoaService.getPessoas();
    }

    @PostMapping
    public Pessoa createPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.createPessoa(pessoa);
    }

    @GetMapping("/{id}")
    public Pessoa getPessoa(@PathVariable Integer id) {
        return pessoaService.getPessoa(id);
    }
}
