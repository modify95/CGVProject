package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccountVO;

public class LoginController implements Initializable {

	@FXML
	private TextField txtId;
	@FXML
	private PasswordField txtPw;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegiste;

	static int num;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnLogin.setOnAction(event -> handlerBtnLogin(event));
		btnRegiste.setOnAction(event -> handlerBtnRegiste(event));
		txtPw.setOnKeyPressed(event -> handlerTxtPw(event));
		txtId.setOnKeyPressed(event -> handlerTxtId(event));

	}

	public void handlerTxtId(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			txtPw.requestFocus();
		}
	}

	public void handlerTxtPw(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			txtPw.setText(UnicodeKorean.Unicode(txtPw.getText().trim()));
			handlerBtnLogin(null);
		}

	}

	public void handlerBtnLogin(ActionEvent event) {
		txtPw.setText(UnicodeKorean.Unicode(txtPw.getText().trim()));

		LoginDAO lDao = new LoginDAO();

		if (txtId.getText().isEmpty() || txtPw.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인");
			alert.setHeaderText("로그인 실패");
			alert.setContentText("빈칸을 모두 채워주세요.");
			alert.showAndWait();
		} else {
			AccountVO aVo = new AccountVO(txtId.getText().trim(), SecurityUtil.encrypt(txtPw.getText().trim()));

			AccountVO success = lDao.Login(aVo);

			if (success != null) {
				num = success.getNum();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("로그인");
				alert.setHeaderText("로그인 성공");
				alert.setContentText("로그인을 성공하였습니다.");
				alert.showAndWait();
				try {
					Stage mainStage = new Stage();
					if (success.getAuthority() == 1) {
						Parent root = FXMLLoader.load(getClass().getResource("/view/AdminMain.fxml"));
						Scene scene = new Scene(root);
						mainStage.setScene(scene);
						mainStage.setTitle("CGV 프로그램(관리자)");
						mainStage.setResizable(false);
						mainStage.show();
					} else if (success.getAuthority() == 2) {
						Parent root = FXMLLoader.load(getClass().getResource("/view/StaffMain.fxml"));
						Scene scene = new Scene(root);
						mainStage.setScene(scene);
						mainStage.setTitle("CGV 프로그램(직원)");
						mainStage.setResizable(false);
						mainStage.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("로그인");
				alert.setHeaderText("로그인 실패");
				alert.setContentText("아이디 또는 비밀번호가 일치하지 않습니다.");
				alert.showAndWait();
				txtPw.setText("");
			}
		}

	}

	public void handlerBtnRegiste(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/StaffRegiste.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("직원 등록");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
