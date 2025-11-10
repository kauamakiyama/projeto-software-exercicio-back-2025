package br.edu.insper.exercicio.viagens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @GetMapping
    public List<Viagem> getViagens() {
        return viagemService.getViagens();
    }

    @PostMapping
    public Viagem createViagem(@RequestBody Viagem viagem) {
        return viagemService.createViagem(viagem);
    }

    @GetMapping("/{id}")
    public Viagem getViagem(@PathVariable Integer id) {
        return viagemService.getViagem(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViagem(@PathVariable Integer id) {
        viagemService.deleteViagem(id);
        return ResponseEntity.noContent().build();
    }
}

