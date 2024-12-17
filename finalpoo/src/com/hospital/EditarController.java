package com.hospital;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.PriorityQueue;

public class EditarController {

    static Paciente getpacienteSelecionado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @FXML
    private ListView<String> pacienteListView;
    @FXML
    private TextField nomeField;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField horaField;
    @FXML
    private TextField prioridadeField;

    private Fila fila;
    private Paciente pacienteSelecionado;

    public void setFila(Fila fila) {
        this.fila = fila;
        atualizarLista();
        
        pacienteListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pacienteSelecionado();
        });
    }
    public void setPaciente(Paciente paciente) {
        this.pacienteSelecionado = paciente;
        nomeField.setText(paciente.getNome());
        dataPicker.setValue(paciente.getHoraChegada().toLocalDate());
        horaField.setText(paciente.getHoraChegada().toLocalTime().toString());
        prioridadeField.setText(String.valueOf(paciente.getPrioridade()));
    }

    private void atualizarLista() {
        pacienteListView.getItems().clear();
        PriorityQueue<Paciente> pacientes = fila.getFila();
        for (Paciente paciente : pacientes) {
            String item = String.format("%s - Hora de Chegada: %s - Prioridade: %d",
                    paciente.getNome(),
                    paciente.getHoraChegada().toLocalTime(),
                    paciente.getPrioridade());
            pacienteListView.getItems().add(item);
        }
    }

    private void pacienteSelecionado() {
        int selectedIndex = pacienteListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            pacienteSelecionado = getPacienteByIndex(selectedIndex);
            if (pacienteSelecionado != null) {
                
                nomeField.setText(pacienteSelecionado.getNome());
                dataPicker.setValue(pacienteSelecionado.getHoraChegada().toLocalDate());
                horaField.setText(pacienteSelecionado.getHoraChegada().toLocalTime().toString());
                prioridadeField.setText(String.valueOf(pacienteSelecionado.getPrioridade()));
            }
        }
    }

    @FXML
private void editarPaciente() throws Exception {
    int selectedIndex = listView.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        Paciente pacienteSelecionado = getPacienteByIndex(selectedIndex);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editar.fxml"));
        Parent root = loader.load();
        EditarController editarController = loader.getController();
        editarController.setFila(fila);
        editarController.setPaciente(pacienteSelecionado); 
        Stage stage = new Stage();
        stage.setTitle("Editar Paciente");
        stage.setScene(new Scene(root));
        stage.show();
    } else {
        showAlert("Por favor, selecione um paciente para editar.");
    }
}

private Paciente getPacienteByIndex(int index) {
    PriorityQueue<Paciente> pacientes = fila.getFila();
    return pacientes.stream().skip(index).findFirst().orElse(null);
}

    public Paciente getPacienteSelecionado() {
        return pacienteSelecionado;
    }
    @FXML
    private void salvar() {
        if (pacienteSelecionado == null) {
            showAlert("Por favor, selecione um paciente para editar.");
            return;
        }
    
        String nome = nomeField.getText();
        LocalDate data = dataPicker.getValue();
        String horaTexto = horaField.getText();
        int prioridade;
    
        try {
            prioridade = Integer.parseInt(prioridadeField.getText());
        } catch (NumberFormatException e) {
            showAlert("Por favor, coloque a prioridade como um número.");
            return;
        }
    
        try {
            LocalTime hora = LocalTime.parse(horaTexto);
            LocalDateTime horaChegada = LocalDateTime.of(data, hora);
            
            pacienteSelecionado.setNome(nome);
            pacienteSelecionado.setHoraChegada(horaChegada);
            pacienteSelecionado.setPrioridade(prioridade);
            
            atualizarLista();
            fecharJanela();
        } catch (Exception e) {
            showAlert("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}