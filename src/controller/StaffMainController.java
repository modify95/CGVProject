package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StaffMainController implements Initializable {

	@FXML
	private Button btnLogout;
	@FXML
	private Button btnTicketing;
	@FXML
	private Button btnCancelTicketing;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnTicketing.setOnAction(event -> handlerBtnTicketing(event));
		btnCancelTicketing.setOnAction(event -> handlerBtnCancelTicketing(event));
		btnLogout.setOnAction(event -> handlerBtnLogout(event));
	}

	public void handlerBtnCancelTicketing(ActionEvent event) {
		try {
			Stage Stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/CancelTicketing.fxml"));
			Scene scene = new Scene(root);
			Stage.setScene(scene);
			Stage.setTitle("영화 예매");
			Stage.setResizable(false);
			Stage.initModality(Modality.APPLICATION_MODAL);
			Stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnTicketing(ActionEvent event) {
		try {
			Stage Stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Ticketing.fxml"));
			Scene scene = new Scene(root);
			Stage.setScene(scene);
			Stage.setTitle("영화 예매 취소");
			Stage.setResizable(false);
			Stage.initModality(Modality.APPLICATION_MODAL);
			Stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnLogout(ActionEvent event) {
		try {
			Stage Stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			Stage.setScene(scene);
			Stage.setTitle("로그인");
			Stage.setResizable(false);
			Stage.show();
			Stage oldStage = (Stage) btnLogout.getScene().getWindow();
			oldStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
