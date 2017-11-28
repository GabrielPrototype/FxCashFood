/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cahsf.controller.fornecedor;

import com.cashf.model.cidade.Cidade;
import com.cashf.model.fornecedor.Fornecedor;
import com.cashf.model.telefone.Operadora;
import com.cashf.model.telefone.Telefone;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.impl.Disposer;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.PoupUpUtil;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class GerenciarFornecedoresFXMLController implements Initializable {

    @FXML
    private JFXTextField txtCnpj;
    @FXML
    private JFXTextField txtInscrEst;
    @FXML
    private JFXTextField txtObs;
    @FXML
    private TableView<Fornecedor> tbvFornecedores;
    @FXML
    private TableColumn<Fornecedor, String> tbcNome;
    @FXML
    private TableColumn<Fornecedor, String> tbcEmail;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXDatePicker dtpDataCad;
    @FXML
    private JFXTextField txtEndereco;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtComplemento;
    @FXML
    private JFXTextField txtCep;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXComboBox<Cidade> cbbCidade;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Pane panePhone;
    @FXML
    private JFXComboBox<Operadora> cbbOperadora;
    @FXML
    private JFXTextField txtDdd;
    @FXML
    private JFXTextField txtTelefone;
    @FXML
    private JFXButton btnAdicionar;
    @FXML
    private TableView<Telefone> tbvTelefone;
    @FXML
    private TableColumn<Telefone, String> tbcDdd;
    @FXML
    private TableColumn<Telefone, String> tbcNumero;
    @FXML
    private TableColumn btnDeletar;
    @FXML
    private JFXTextField txtRazao;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnExcluir;
    @FXML
    private JFXButton btnLimpar;
    //--------------------------------
    private FornecedorController controller = new FornecedorController();
    private String erros;
    private boolean flagButtons;
    private long idFornecedor = 0;
    private String cnpj;
    private String nomefantasia;
    private String razaoSocial;
    private String endereco;
    private String complemento;
    private Integer numero;
    private String cep;
    private String bairro;
    private String email;
    private String Observacao;
    private String inscrEst;
    //---
    private String telefone;
    private String ddd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setInputOff();
        loadCbbCidade();
        loadCbbOperadora();
        setUptableView();
        setUptableViewTelefone();
        loadTbv();
        loadTbvTelefone();
    }

    @FXML
    private void onKeyReleasedCep(KeyEvent event) {

    }

    @FXML
    private void onSelectCidade(ActionEvent event) {
        controller.setCidade(cbbCidade.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onAdicionar(ActionEvent event) {
        getDataTelefone();
        if (validateTeleofne()) {
            controller.setTelefone(0l, ddd, telefone);
            controller.inserTelefone();
        } else {
            PoupUpUtil.accessDenied(erros);
            erros = "";
        }
    }

    @FXML
    private void onSalvar(ActionEvent event) {
        getData();
        if (validateFields()) {
            controller.setFornecedor(idFornecedor, cnpj, inscrEst, nomefantasia, razaoSocial, endereco, complemento, numero, cep, bairro, email, Observacao, controller.getCidade(), controller.getListaTelefone());
            if (controller.getFornecedor().getIdFornecedor() == 0) {
                controller.insert();

                PoupUpUtil.poupUp("Fornecedor Cadastrado", "O Fornecedor foi cadastrado com sucesso.", "");

            } else {
                controller.update();
                PoupUpUtil.poupUp("Fornecedor Alterado", "O Fornecedor foi alterado com sucesso.", "");
            }
            loadTbv();
            clearFields();
        } else {
            PoupUpUtil.accessDenied(erros);
            erros = "";
        }

    }

    @FXML
    private void onNovo(ActionEvent event) {
        setInputON();
        clearFields();
        btnNovo.setDisable(true);
        btnExcluir.setDisable(true);
    }

    @FXML
    private void onExcluir(ActionEvent event) {
        if (controller.getFornecedor() != null) {
            controller.delete();
            PoupUpUtil.poupUp("Fornecedor Excluído", "O fornecedor foi excluído com sucesso.", "");
        }
        btnExcluir.setDisable(true);
        clearFields();
    }

    @FXML
    private void onLimpar(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtNome.clear();
        txtRazao.clear();
        txtEndereco.clear();
        txtComplemento.clear();
        txtNumero.clear();
        txtBairro.clear();
        txtCep.clear();
        txtCnpj.clear();
        txtDdd.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtInscrEst.clear();
        txtObs.clear();
        cbbCidade.setValue(null);
        cbbOperadora.setValue(null);
    }

    private void setInputON() {
        btnExcluir.setDisable(false);
        btnLimpar.setDisable(false);
        btnSalvar.setDisable(false);
        txtNome.setDisable(false);
        txtRazao.setDisable(false);
        txtEndereco.setDisable(false);
        txtComplemento.setDisable(false);
        txtNumero.setDisable(false);
        txtBairro.setDisable(false);
        txtCep.setDisable(false);
        txtCnpj.setDisable(false);
        txtDdd.setDisable(false);
        txtTelefone.setDisable(false);
        txtEmail.setDisable(false);
        txtInscrEst.setDisable(false);
        txtObs.setDisable(false);
        cbbCidade.setDisable(false);
        cbbOperadora.setDisable(false);
        flagButtons = true;
    }

    private void setInputOff() {
        btnExcluir.setDisable(true);
        btnLimpar.setDisable(true);
        btnSalvar.setDisable(true);
        txtNome.setDisable(true);
        txtRazao.setDisable(true);
        txtEndereco.setDisable(true);
        txtComplemento.setDisable(true);
        txtNumero.setDisable(true);
        txtBairro.setDisable(true);
        txtCep.setDisable(true);
        txtCnpj.setDisable(true);
        txtDdd.setDisable(true);
        txtTelefone.setDisable(true);
        txtEmail.setDisable(true);
        txtInscrEst.setDisable(true);
        txtObs.setDisable(true);
        cbbCidade.setDisable(true);
        cbbOperadora.setDisable(true);
        flagButtons = false;
    }

    private void getDataTelefone() {
        telefone = txtTelefone.getText();
        ddd = txtDdd.getText();
        controller.setOperadora(cbbOperadora.getSelectionModel().getSelectedItem());
    }

    private void getData() {
        idFornecedor = (controller.getFornecedor().getIdFornecedor() != 0) ? idFornecedor = controller.getFornecedor().getIdFornecedor() : 0l;
        nomefantasia = txtNome.getText();
        razaoSocial = txtRazao.getText();
        endereco = txtEndereco.getText();
        complemento = txtComplemento.getText();
        numero = new Integer(txtNumero.getText());
        bairro = txtBairro.getText();
        cep = txtCep.getText();
        cnpj = txtCnpj.getText();
        email = txtEmail.getText();
        inscrEst = txtInscrEst.getText();
        Observacao = txtObs.getText();
        controller.setCidade(cbbCidade.getSelectionModel().getSelectedItem());

    }

    public void loadDataToScreen() {
        txtNome.setText(controller.getFornecedor().getNomefantasia());
        txtRazao.setText(controller.getFornecedor().getRazaoSocial());
        txtEndereco.setText(controller.getFornecedor().getEndereco());
        txtComplemento.setText(controller.getFornecedor().getComplemento());
        txtNumero.setText(controller.getFornecedor().getNumero() + "");
        txtBairro.setText(controller.getFornecedor().getBairro());
        txtCep.setText(controller.getFornecedor().getCep());
        txtCnpj.setText(controller.getFornecedor().getCnpj());
        txtEmail.setText(controller.getFornecedor().getEmail());
        txtInscrEst.setText(controller.getFornecedor().getInscrEst());
        txtObs.setText(controller.getFornecedor().getObservacao());
        cbbCidade.setValue(controller.getFornecedor().getCidade());
    }

    private boolean validateFields() {
        boolean flag = true;
        if (nomefantasia == null || nomefantasia.equals("") || nomefantasia.length() < 3) {
            erros += "O nome fantasia deve conter um conteúdo válido! \n";
            flag = false;
        }
        if (razaoSocial == null || razaoSocial.equals("") || razaoSocial.length() < 3) {
            erros += "A razão social deve conter um nome válido! \n";
            flag = false;
        }
        if (endereco == null || endereco.equals("") || endereco.length() < 3) {
            erros += "O endereço deve ser preenchido corretamente! \n";
            flag = false;
        }

        if (bairro == null || bairro.equals("") || bairro.length() < 3) {
            erros += "O bairro deve ser preenchido corretamente! \n";
            flag = false;
        }
        if (cep == null || cep.equals("") || cep.length() < 3) {
            erros += "O CEP deve ser preenchido corretamente! \n";
            flag = false;
        }
        if (cnpj == null || cnpj.equals("") || cnpj.length() < 3) {
            erros += "O CNPJ deve ser preenchido corretamente! \n";
            flag = false;
        }
        if (email == null || email.equals("") || email.length() < 3) {
            erros += "O email deve ser preenchido corretamente! \n";
            flag = false;
        }
        if (inscrEst == null || inscrEst.equals("") || inscrEst.length() < 3) {
            erros += "A inscrição estadual deve ser preenchida corretamente! \n";
            flag = false;
        }

        if (cbbCidade.getSelectionModel().getSelectedItem() == null) {
            erros += "Você deve selecionar uma Cidade! \n";
            flag = false;
        }
        return flag;
    }

    private boolean validateTeleofne() {
        boolean flag = true;
        if (ddd == null || ddd.equals("") || ddd.length() < 2) {
            erros += "O DDD do telefone deve conter um número válido! \n";
            flag = false;
        }
        if (telefone == null || telefone.equals("") || telefone.length() < 3) {
            erros += "O telefone do Fornecedor deve conter um número válido! \n";
            flag = false;
        }

        if (cbbOperadora.getSelectionModel().getSelectedItem() == null) {
            erros += "Você deve selecionar uma operadora para este telefone! \n";
            flag = false;
        }
        return flag;
    }

    private void loadCbbCidade() {
        cbbCidade.setItems(controller.getListaCidade());
    }

    private void loadCbbOperadora() {
        cbbOperadora.getItems().addAll(Arrays.asList(Operadora.values()));
    }

    private void setUptableView() {
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nomefantasia"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbvFornecedores.getColumns().setAll(tbcNome, tbcEmail);
    }

    private void setUptableViewTelefone() {
        tbcDdd.setCellValueFactory(new PropertyValueFactory<>("ddd"));
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        btnDeletar.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCellDelete();
            }
        });
        tbvTelefone.getColumns().setAll(tbcDdd, tbcNumero, btnDeletar);
    }

    private void loadTbv() {
        tbvFornecedores.setItems(controller.getLista());
    }

    private void loadTbvTelefone() {
        tbvTelefone.setItems(controller.getListaTelefone());
    }

    @FXML
    private void onMouseClickedFornecedor(MouseEvent event) {
        if (tbvFornecedores.getSelectionModel().getSelectedItem() != null) {
            controller.setFornecedor(tbvFornecedores.getSelectionModel().getSelectedItem());
            controller.setListaTelefone(FXCollections.observableList(controller.getFornecedor().getTelefones()));
            loadDataToScreen();
            loadTbvTelefone();
            getData();
        }
    }

    public class ButtonCellDelete extends TableCell<Disposer.Record, Boolean> {

        Image img;
        ImageView imgv;
        JFXButton cellButton = new JFXButton("Desativar");
        Notifications notificationBuilder;

        public ButtonCellDelete() {
            this.img = new Image("Imagens/ic_delete_forever_black_24dp_1x.png");
            this.imgv = new ImageView(img);
            cellButton.setGraphic(imgv);
            cellButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Telefone currentPerson = (Telefone) ButtonCellDelete.this.getTableView().getItems().get(ButtonCellDelete.this.getIndex());
                    //remove selected item from the table list
                    if (currentPerson != null) {
                        controller.setTelefone(currentPerson);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Cofirmar Excluir Telefone!");
                        alert.setHeaderText("Deseja realmente Excluir?");
                        alert.setContentText("Numero:(" + currentPerson.getDdd() + ") - " + currentPerson.getNumero() + "");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            // ... user chose OK
                            controller.deleteTelefone();
                            notificationBuilder = Notifications.create().title("Telefone excluído!").
                                    text("telefone Excluido com sucesso.").
                                    hideAfter(Duration.seconds(2)).
                                    position(Pos.TOP_RIGHT).
                                    darkStyle();
                            notificationBuilder.showInformation();
                        } else {
                            alert.close();
                        }
                    } else {
                        notificationBuilder = Notifications.create().title("Nenhum item selecionado!").
                                text("Você deve selecionar Uma conta para Cancelar.").
                                hideAfter(Duration.seconds(2)).
                                position(Pos.TOP_RIGHT).
                                darkStyle();
                        notificationBuilder.showConfirm();
                    }

                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

}
