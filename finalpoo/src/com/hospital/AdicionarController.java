package com.hospital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdicionarController {
    @FXML
    private TextField nomeField;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField horaField;
    @FXML
    private TextField prioridadeField;

    private Fila fila;

    public void setFila(Fila fila) {
        this.fila = fila;
    }

    @FXML
    private void adicionar() {
        String nome = nomeField.getText();
        LocalDate data = dataPicker.getValue();
        String horaTexto = horaField.getText();
        String prioridadeTexto = prioridadeField.getText();

        if (!nome.isEmpty() && data != null && !horaTexto.isEmpty() && !prioridadeTexto.isEmpty()) {
            try {
                LocalTime hora = LocalTime.parse(horaTexto);
                LocalDateTime horaChegada = LocalDateTime.of(data, hora);
                int prioridade = Integer.parseInt(prioridadeTexto);
                Paciente paciente = new Paciente(nome, horaChegada, prioridade);
                fila.adicionarPaciente(paciente);
                nomeField.clear();
                dataPicker.setValue(null);
                horaField.clear();
                prioridadeField.clear();
                showAlert("Paciente adicionado: " + nome);
            } catch (Exception e) {
                showAlert("Erro ao adicionar paciente. Verifique os campos.");
            }
        } else {
            showAlert("Por favor, preencha todos os campos.");
        }
    }


    
    @FXML
    private void cancelar() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}