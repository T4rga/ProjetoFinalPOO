package com.hospital;

import java.time.LocalDateTime;

public class Paciente {
    private String nome;
    private LocalDateTime horaChegada;
    private int prioridade;

    public Paciente(String nome, LocalDateTime horaChegada, int prioridade) {
        this.nome = nome;
        this.horaChegada = horaChegada;
        this.prioridade = prioridade;
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getHoraChegada() {
        return horaChegada;
    }

    public int getPrioridade() {
        return prioridade;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setHoraChegada(LocalDateTime horaChegada) {
        this.horaChegada = horaChegada;
    }
}
