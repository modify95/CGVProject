package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CancelTicketingController implements Initializable {

	@FXML
	private TextField txtTNum1;
	@FXML
	private TextField txtTNum3;
	@FXML
	private TextField txtTNum2;
	@FXML
	private TextField txtTNum4;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnCancelTicketing;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnCancelTicketing.setOnAction(event -> handlerBtnCancelTicketing(event));
		btnExit.setOnAction(event -> handlerBtnExit(event));
		txtTNum1.setOnKeyReleased(event -> handlerTxtNum(event));
		txtTNum2.setOnKeyReleased(event -> handlerTxtNum(event));
		txtTNum3.setOnKeyReleased(event -> handlerTxtNum(event));

		txtTNum1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
					txtTNum1.setText(oldValue);
				}
			}
		});

		txtTNum2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
					txtTNum2.setText(oldValue);
				}
			}
		});

		txtTNum3.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
					txtTNum3.setText(oldValue);
				}
			}
		});

		txtTNum4.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
					txtTNum4.setText(oldValue);
				}
			}
		});

	}

	public void handlerTxtNum(KeyEvent event) {
		TextField txt = (TextField) event.getSource();
		if (txt.getText().length() == 4) {
			if (txt.getId().equals("txtTNum1")) {
				txtTNum2.requestFocus();
			} else if (txt.getId().equals("txtTNum2")) {
				txtTNum3.requestFocus();
			} else {
				txtTNum4.requestFocus();
			}
		}
	}

	public void handlerBtnCancelTicketing(ActionEvent event) {
		TicketingDAO tDao = new TicketingDAO();
	
		if (txtTNum1.getText().isEmpty() || txtTNum2.getText().isEmpty() || txtTNum3.getText().isEmpty()
				|| txtTNum4.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("예매 취소");
			alert.setHeaderText("예매 취소 실패");
			alert.setContentText("입장권 번호를 정확히 입력해주세요.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("예매 취소");
			alert.setHeaderText(null);
			alert.setContentText("영화 예매를 취소하시겠습니까?");
			ButtonType okButton = new ButtonType("예", ButtonData.YES);
			ButtonType noButton = new ButtonType("아니오", ButtonData.NO);
			alert.getButtonTypes().setAll(okButton, noButton);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == okButton) {
				boolean checkTime = tDao.cancelTimeConfirm(
						txtTNum1.getText() + txtTNum2.getText() + txtTNum3.getText() + txtTNum4.getText());
				boolean success = tDao.cancelTicketing(
						txtTNum1.getText() + txtTNum2.getText() + txtTNum3.getText() + txtTNum4.getText());
				if (checkTime) {
					if (success) {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("예매 취소");
						alert2.setHeaderText(null);
						alert2.setContentText("예매 취소가 완료되었습니다.");
						alert2.showAndWait();
						Stage stage = (Stage) btnCancelTicketing.getScene().getWindow();
						stage.close();
					}
				} else {
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("예매 취소");
					alert2.setHeaderText(null);
					alert2.setContentText("이미 상영중인 영화이거나  존재하지 않는 예매 번호입니다.\n예매 취소가 불가능 합니다.");
					alert2.showAndWait();
				}
			}
		}
	}

	public void handlerBtnExit(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

}
