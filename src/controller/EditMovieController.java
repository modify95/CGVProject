package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.MovieVO;

public class EditMovieController implements Initializable {

	@FXML
	private DatePicker dpCloseDate;
	@FXML
	private Button btnEdit;
	@FXML
	private TextField txtDirector;
	@FXML
	private TextArea txtStory;
	@FXML
	private TextField txtGenre;
	@FXML
	private TextField txtRunTime;
	@FXML
	private ImageView imgPoster;
	@FXML
	private Button btnAddPoster;
	@FXML
	private ComboBox<String> cbGrade;
	@FXML
	private DatePicker dpOpenDate;
	@FXML
	private TextField txtNation;
	@FXML
	private Button btnExit;
	@FXML
	private TextField txtTitle;

	private File selectPoster;
	private File dirSave = new File("C:/MoviePoster");

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnAddPoster.setOnAction(event -> handlerBtnAddPoster(event));
		btnEdit.setOnAction(event -> handlerBtnEdit(event));
		btnExit.setOnAction(event -> handlerBtnExit(event));
		loadMovie();

		cbGrade.setItems(FXCollections.observableArrayList("전체 관람가", "12세이상관람가", "15세이상관람가", "청소년관람불가"));

		txtRunTime.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[0-9]*")) {
					txtRunTime.setText(oldValue);
				}
			}
		});
	}

	public void handlerBtnEdit(ActionEvent event) {
		MovieDAO amDao = new MovieDAO();
	
		boolean success;
		String poster;
	
		if (txtTitle.getText().isEmpty() || txtGenre.getText().isEmpty() || imgPoster.getImage() == null
				|| dpOpenDate.getValue().toString().isEmpty() || txtRunTime.getText().isEmpty()
				|| txtNation.getText().isEmpty() || cbGrade.getSelectionModel().getSelectedItem().isEmpty()
				|| txtDirector.getText().isEmpty() || txtStory.getText().isEmpty()
				|| dpCloseDate.getValue().toString().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("영화 등록");
			alert.setHeaderText("영화 등록 실패");
			alert.setContentText("빈칸을 모두 채워주세요.");
			alert.showAndWait();
		} else {
			// 개봉일 체크
			if (checkDate()) {
				// 폴더 생성
				if (!dirSave.exists()) {
					dirSave.mkdir();
				}
	
				if (selectPoster != null) {
					poster = savePoster(selectPoster);
					deletePoster(MovieController.selectMovie.getM_Poster());
	
				} else {
					poster = MovieController.selectMovie.getM_Poster();
				}
				MovieVO mVo = new MovieVO(txtTitle.getText().trim(), txtGenre.getText().trim(),
						dpOpenDate.getValue().toString(), dpCloseDate.getValue().toString(),
						Integer.parseInt(txtRunTime.getText().trim()), txtNation.getText().trim(),
						cbGrade.getSelectionModel().getSelectedItem(), txtDirector.getText().trim(),
						txtStory.getText().trim(), poster);
	
				success = amDao.editMovie(mVo, MovieController.selectMovie.getM_Num());
	
				if (success) {
					// 이미지 저장
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("영화 수정");
					alert.setHeaderText("영화 수정 성공");
					alert.setContentText("영화를 수정 하였습니다.");
					alert.showAndWait();
					MovieController.selectMovie = null;
					Stage stage = (Stage) btnEdit.getScene().getWindow();
					stage.close();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("영화 수정");
					alert.setHeaderText("영화 수정 실패");
					alert.setContentText("영화 수정에 실패하였습니다.\n다시 시도해 주세요.");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("영화 수정");
				alert.setHeaderText("영화 수정 실패");
				alert.setContentText("개봉일이 상영 종료일보다 클 수 없습니다.");
				alert.showAndWait();
			}
	
		}
	}

	public void handlerBtnAddPoster(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new ExtensionFilter("이미지 파일", "*.jpg", "*.bmp", "*.png", "*.gif"),
					new ExtensionFilter("모든 파일", "*.*"));
			fc.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop")); // 초기화면을 바탕화면으로 지정
			selectPoster = fc.showOpenDialog(null);
			if (selectPoster != null) {
				String url = selectPoster.toURI().toURL().toString();
				Image poster = new Image(url);
				imgPoster.setImage(poster);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void handlerBtnExit(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

	public String savePoster(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data;

		String fileName = null;
		try {
			fileName = System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "/" + fileName));

			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	public boolean checkDate() {
		boolean success = false;

		int openDate = Integer.parseInt(dpOpenDate.getValue().toString().replaceAll("-", ""));
		int closeDate = Integer.parseInt(dpCloseDate.getValue().toString().replaceAll("-", ""));

		if (openDate < closeDate) {
			success = true;
		}
		return success;
	}

	public boolean deletePoster(String fileName) {
		boolean success = false;

		try {
			File file = new File(dirSave.getAbsolutePath() + "/" + fileName);
			if (file.exists() && file.isFile()) {
				success = file.delete();
			}
		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	public void loadMovie() {
		try {
			MovieVO movie = MovieController.selectMovie;

			Image img = new Image("file:/C:/MoviePoster/" + movie.getM_Poster());

			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(df.parse(movie.getM_OpenDate()));
			cal2.setTime(df.parse(movie.getM_CloseDate()));

			LocalDate date1 = LocalDate.of(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH) + 1,
					cal1.get(Calendar.DAY_OF_MONTH));
			LocalDate date2 = LocalDate.of(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH) + 1,
					cal2.get(Calendar.DAY_OF_MONTH));

			imgPoster.setImage(img);
			txtTitle.setText(movie.getM_Title());
			txtGenre.setText(movie.getM_Genre());
			dpOpenDate.setValue(date1);
			dpCloseDate.setValue(date2);
			txtRunTime.setText(movie.getM_RunTime() + "");
			txtNation.setText(movie.getM_Nation());
			cbGrade.setValue(movie.getM_Grade());
			txtDirector.setText(movie.getM_Director());
			txtStory.setText(movie.getM_Story());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
