package br.edu.insper.exercicio.viagens;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeOrigem;
    private String nomeDestino;
    private String descricao;
    private String modoTransporte;

    public Viagem() {
    }

    public Viagem(String nomeOrigem, String nomeDestino, String descricao, String modoTransporte) {
        this.nomeOrigem = nomeOrigem;
        this.nomeDestino = nomeDestino;
        this.descricao = descricao;
        this.modoTransporte = modoTransporte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeOrigem() {
        return nomeOrigem;
    }

    public void setNomeOrigem(String nomeOrigem) {
        this.nomeOrigem = nomeOrigem;
    }

    public String getNomeDestino() {
        return nomeDestino;
    }

    public void setNomeDestino(String nomeDestino) {
        this.nomeDestino = nomeDestino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModoTransporte() {
        return modoTransporte;
    }

    public void setModoTransporte(String modoTransporte) {
        this.modoTransporte = modoTransporte;
    }
}

