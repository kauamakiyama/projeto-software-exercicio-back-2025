package br.edu.insper.exercicio.viagens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    public List<Viagem> getViagens() {
        return viagemRepository.findAll();
    }

    public Viagem createViagem(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    public Viagem getViagem(Integer id) {
        return viagemRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));
    }

    public void deleteViagem(Integer id) {
        if (!viagemRepository.existsById(id)) {
            throw new RuntimeException("Viagem não encontrada");
        }
        viagemRepository.deleteById(id);
    }
}

