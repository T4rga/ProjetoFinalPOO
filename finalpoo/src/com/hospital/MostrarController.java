package com.hospital;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.PriorityQueue;

public class MostrarController {
    @FXML
    private ListView<String> listView;

    private Fila fila;

    public void setFila(Fila fila) {
        this.fila = fila;
        atualizarLista();
    }


    private void atualizarLista() {
        listView.getItems().clear();
        PriorityQueue<Paciente> pacientes = fila.getFila();
        for (Paciente paciente : pacientes) {
            String item = String.format("%s - Hora de Chegada: %s - Prioridade: %d",
                    paciente.getNome(),
                    paciente.getHoraChegada().toLocalTime(),
                    paciente.getPrioridade());
            listView.getItems().add(item);
        }
    }

    @FXML
    private void fechar() {
    Stage stage = (Stage) listView.getScene().getWindow();
    stage.close();
    }
}
