package com.hospital;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Fila {
    private PriorityQueue<Paciente> fila;

    public Fila() {
        fila = new PriorityQueue<>(Comparator.comparingInt(Paciente::getPrioridade)
                .thenComparing(Paciente::getHoraChegada));
    }

    public void adicionarPaciente(Paciente paciente) {
        fila.add(paciente);
    }

    public Paciente atenderPaciente() {
        return fila.poll();
    }

    public PriorityQueue<Paciente> getFila() {
        return fila;
    }

    public void atualizarPaciente(Paciente pacienteSelecionado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarPaciente'");
    }
}