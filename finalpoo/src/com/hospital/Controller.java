package com.hospital;

import java.util.PriorityQueue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
    private Fila fila = new Fila();

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button mostrarButton;
    @FXML
    private Button editarButton;
    @FXML
    private ListView<String> listView;

    @FXML
    private void adicionarPaciente() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adicionar.fxml"));
        Parent root = loader.load();
        AdicionarController adicionarController = loader.getController();
        adicionarController.setFila(fila); 
        Stage stage = new Stage();
        stage.setTitle("Adicionar Paciente");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void atenderPaciente() throws Exception {
        Paciente paciente = fila.atenderPaciente();
        if (paciente != null) {
            showAlert("Proxímo: " + paciente.getNome());
        } else {
            showAlert("Nenhum paciente na fila.");
        }
    }

    @FXML
    private void mostrarFila() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mostrar.fxml"));
        Parent root = loader.load();
        MostrarController mostrarController = loader.getController();
        mostrarController.setFila(fila);
        Stage stage = new Stage();
        stage.setTitle("Mostrar Fila");
        stage.setScene(new Scene(root));
        

        stage.setOnHidden(event -> {
            Paciente pacienteSelecionado = EditarController.getpacienteSelecionado();
            if (pacienteSelecionado != null) {
                try {
                    FXMLLoader editarLoader = new FXMLLoader(getClass().getResource("editar.fxml"));
                    Parent editarRoot = editarLoader.load();
                    EditarController editarController = editarLoader.getController();
                    editarController.setFila(fila);
                    editarController.setPaciente(pacienteSelecionado); // Passa o paciente selecionado
                    Stage editarStage = new Stage();
                    editarStage.setTitle("Editar Paciente");
                    editarStage.setScene(new Scene(editarRoot));
                    editarStage.show();
                } catch (Exception e) {
                    showAlert("Erro ao abrir");
                }
            } else {
                showAlert("selecione um paciente para editar.");
            }
        });

        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void editarPaciente() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editar.fxml"));
        Parent root = loader.load();
        EditarController editarController = loader.getController();
        editarController.setFila(fila);
        Stage stage = new Stage();
        stage.setTitle("Editar Paciente");
        stage.setScene(new Scene(root));
        stage.show();
    }

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
}