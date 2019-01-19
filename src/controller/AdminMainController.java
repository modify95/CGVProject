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

public class AdminMainController implements Initializable {

	@FXML
	private Button btnAddMovie;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnAddScheduel;
	@FXML
	private Button btnTicketing;
	@FXML
	private Button btnCancelTicketing;
	@FXML
	private Button btnStatistics;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnAddMovie.setOnAction(event -> handlerBtnAddMovie(event));
		btnLogout.setOnAction(event -> handlerBtnLogout(event));
		btnAddScheduel.setOnAction(event -> handlerBtnAddScheduel(event));
		btnTicketing.setOnAction(event -> handlerBtnTicketing(event));
		btnCancelTicketing.setOnAction(event -> handlerBtnCancelTicketing(event));
		btnStatistics.setOnAction(event -> handlerBtnStatistics(event));
	}

	public void handlerBtnStatistics(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("매출 통계");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnCancelTicketing(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/CancelTicketing.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("영화 예매 취소");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnTicketing(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Ticketing.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("영화 예매");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnAddScheduel(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduel.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("상영 일정 등록");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnLogout(ActionEvent event) {
		try {
			Stage LoginStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			LoginStage.setScene(scene);
			LoginStage.setTitle("로그인");
			LoginStage.setResizable(false);
			LoginStage.show();
			Stage oldStage = (Stage) btnLogout.getScene().getWindow();
			oldStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnAddMovie(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/Movie.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("영화 등록");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
