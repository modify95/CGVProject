package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AccountVO;

public class RegisteController implements Initializable {

	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private PasswordField txtPwConfirm;
	@FXML
	private PasswordField txtPw;
	@FXML
	private Button btnRegiste;
	@FXML
	private Button btnOverlap;
	@FXML
	private Button btnExit;
	@FXML
	private ComboBox<String> cbDay;
	@FXML
	private ComboBox<String> cbMonth;
	@FXML
	private ComboBox<String> cbYear;

	boolean overlap = false;
	String finalId = null;

	ObservableList<String> year = FXCollections.observableArrayList();
	ObservableList<String> month = FXCollections.observableArrayList();
	ObservableList<String> day = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		addDate();

		btnOverlap.setOnAction(event -> handlerBtnOverlap(event));
		btnRegiste.setOnAction(event -> handlerBtnRegiste(event));
		btnExit.setOnAction(event -> handlerBtnExit(event));

		cbYear.setItems(year);
		cbMonth.setItems(month);
		cbDay.setItems(day);

		txtPhone.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*")) {
					txtPhone.setText(oldValue);
				}
			}
		});

	}

	public void handlerBtnOverlap(ActionEvent event) {

		RegisteDAO rDao = new RegisteDAO();

		if (idCheck()) {

			overlap = rDao.overlap(txtId.getText().trim());

			if (overlap) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("중복확인 성공");
				alert.setContentText("사용가능한 아이디 입니다.");
				alert.showAndWait();
				finalId = txtId.getText().trim();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("중복확인 실패");
				alert.setContentText("이미 존재하는 아이디입니다.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("직원 등록");
			alert.setHeaderText("직원 등록 실패");
			alert.setContentText("아이디 형식이 올바르지 않습니다.");
			alert.showAndWait();
			txtId.setText("");
		}

	}

	public void handlerBtnRegiste(ActionEvent event) {

		txtPw.setText(UnicodeKorean.Unicode(txtPw.getText().trim()));
		txtPwConfirm.setText(UnicodeKorean.Unicode(txtPwConfirm.getText().trim()));
		RegisteDAO rDao = new RegisteDAO();

		if (txtId.getText().isEmpty() || txtPw.getText().isEmpty() || txtName.getText().isEmpty()
				|| txtPwConfirm.getText().isEmpty() || txtPhone.getText().isEmpty()
				|| cbYear.getSelectionModel().isEmpty() || cbMonth.getSelectionModel().isEmpty()
				|| cbDay.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("직원 등록");
			alert.setHeaderText("직원 등록 실패");
			alert.setContentText("빈칸을 모두 채워주세요.");
			alert.showAndWait();
		} else {
			if (overlap && changedIdCheck() && pwConfirm() && phoneCheck() && pwCheck()) {
				AccountVO aVo = new AccountVO(txtId.getText().trim(), SecurityUtil.encrypt(txtPw.getText().trim()),
						txtName.getText().trim(),
						cbYear.getSelectionModel().getSelectedItem() + "-"
								+ cbMonth.getSelectionModel().getSelectedItem() + "-"
								+ cbDay.getSelectionModel().getSelectedItem(),
						txtPhone.getText().trim());

				boolean success = rDao.staffRegister(aVo);

				if (success) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("직원 등록");
					alert.setHeaderText("직원 등록 성공");
					alert.setContentText("직원이 등록 되었습니다.");
					alert.showAndWait();
					Stage stage = (Stage) btnRegiste.getScene().getWindow();
					stage.close();
				}
			} else if (!overlap) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("중복확인을 눌러주세요.");
				alert.showAndWait();
			} else if (!changedIdCheck()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("아이디가 변경 되었습니다.\n다시 중복확인을 눌러주세요.");
				alert.showAndWait();
			} else if (!pwConfirm()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("비밀번호가 서로 일치하지 않습니다.");
				alert.showAndWait();
			} else if (!phoneCheck()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("핸드폰번호를 형식에 맞게 입력하세요.");
				alert.showAndWait();
			} else if (!pwCheck()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("비밀번호 형식이 올바르지 않습니다.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("직원 등록에 실패하였습니다.\n다시 시도해주세요.");
				alert.showAndWait();
			}

		}

	}

	public void handlerBtnExit(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

	public void addDate() {
		String str;
		for (int i = 1918; i <= 2018; i++) {
			str = i + "";
			year.add(str);
		}

		for (int i = 1; i <= 12; i++) {
			str = i + "";
			month.add(str);
		}

		for (int i = 1; i <= 31; i++) {
			str = i + "";
			day.add(str);
		}
	}

	public boolean changedIdCheck() {
		boolean confirm = finalId.equals(txtId.getText().trim());
		return confirm;
	}

	public boolean pwConfirm() {
		boolean confirm = txtPw.getText().trim().equals(txtPwConfirm.getText().trim());
		return confirm;
	}

	public boolean phoneCheck() {
		String phone = txtPhone.getText().trim();
		boolean confirm = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phone);
		return confirm;
	}

	public boolean idCheck() {
		String id = txtId.getText().trim();
		boolean confirm = false;
		if (id.length() >= 6 && id.length() <= 20) {
			confirm = Pattern.matches("^(?:[a-zA-Z0-9])(?:.*[0-9])[a-zA-Z0-9]*$", id);
		}
		return confirm;
	}

	public boolean pwCheck() {
		String pw = txtPw.getText().trim();
		boolean confirm = false;
		if (pw.length() >= 8 && pw.length() <= 20) {
			confirm = Pattern.matches("^(?:[a-z0-9])(?:.*[0-9])[a-z0-9]*$", pw);
		}
		return confirm;
	}

}
