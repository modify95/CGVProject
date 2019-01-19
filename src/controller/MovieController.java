package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MovieVO;

public class MovieController implements Initializable {

	@FXML
	private TableView<MovieVO> tvMovieInfo;
	@FXML
	private TableColumn<MovieVO, String> colDirector;
	@FXML
	private TableColumn<MovieVO, String> colCloseDate;
	@FXML
	private TableColumn<MovieVO, String> colNation;
	@FXML
	private TableColumn<MovieVO, String> colGrade;
	@FXML
	private TableColumn<MovieVO, String> colGenre;
	@FXML
	private TableColumn<MovieVO, String> colTitle;
	@FXML
	private TableColumn<MovieVO, String> colOpenDate;
	@FXML
	private ComboBox<String> cbGrade;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAddMovie;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnAddPoster;
	@FXML
	private Button btnExit;
	@FXML
	private TextField txtDirector;
	@FXML
	private TextField txtGenre;
	@FXML
	private TextField txtRunTime;
	@FXML
	private TextField txtNation;
	@FXML
	private TextField txtTitle;
	@FXML
	private DatePicker dpCloseDate;
	@FXML
	private TextArea txtStory;
	@FXML
	private ImageView imgPoster;
	@FXML
	private DatePicker dpOpenDate;
	
	File selectPoster;
	File dirSave = new File("C:/MoviePoster");

	ObservableList<MovieVO> movieList = FXCollections.observableArrayList();

	static MovieVO selectMovie;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colTitle.setCellValueFactory(new PropertyValueFactory<>("m_Title"));
		colGenre.setCellValueFactory(new PropertyValueFactory<>("m_Genre"));
		colOpenDate.setCellValueFactory(new PropertyValueFactory<>("m_OpenDate"));
		colCloseDate.setCellValueFactory(new PropertyValueFactory<>("m_CloseDate"));
		colNation.setCellValueFactory(new PropertyValueFactory<>("m_Nation"));
		colGrade.setCellValueFactory(new PropertyValueFactory<>("m_Grade"));
		colDirector.setCellValueFactory(new PropertyValueFactory<>("m_Director"));

		btnAddMovie.setOnAction(event -> handlerBtnMovie(event));
		btnAddPoster.setOnAction(event -> handlerBtnPoster(event));
		btnExit.setOnAction(event -> handlerBtnExit(event));
		btnEdit.setOnAction(event -> handlerBtnEdit(event));
		btnDelete.setOnAction(event -> handlerBtnDelete(event));
		tvMovieInfo.setOnMouseClicked(event -> handlerTvMovieInfo(event));

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

	public void handlerBtnDelete(ActionEvent event) {
		MovieDAO mDao = new MovieDAO();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("영화 삭제");
		alert.setHeaderText(null);
		alert.setContentText("\"" + selectMovie.getM_Title() + "\" 을(를) 삭제 하시겠습니까?");
		ButtonType okButton = new ButtonType("예", ButtonData.YES);
		ButtonType noButton = new ButtonType("아니오", ButtonData.NO);
		alert.getButtonTypes().setAll(okButton, noButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			boolean success = mDao.deleteMovie(selectMovie);
			if (success) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("영화 삭제");
				alert2.setHeaderText(null);
				alert2.setContentText("영화 삭제 완료.");
				alert2.showAndWait();
			}
		}
		btnDelete.setDisable(true);
		btnEdit.setDisable(true);
		loadMovie();
	}

	public void handlerBtnEdit(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/EditMovie.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("영화 수정");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			loadMovie();
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerTvMovieInfo(MouseEvent event) {
		int index = tvMovieInfo.getSelectionModel().getSelectedIndex();

		if (index != -1) {
			selectMovie = movieList.get(index);
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		}
	}

	public void handlerBtnMovie(ActionEvent event) {

		MovieDAO amDao = new MovieDAO();

		boolean success;

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
		} else if (imgPoster.getImage() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("영화 등록");
			alert.setHeaderText("영화 등록 실패");
			alert.setContentText("포스터를 추가해 주세요.");
			alert.showAndWait();
		} else {
			// 개봉일 체크
			if (checkDate()) {
				// 폴더 생성
				if (!dirSave.exists()) {
					dirSave.mkdir();
				}

				String poster = savePoster(selectPoster);
				MovieVO mVo = new MovieVO(txtTitle.getText().trim(), txtGenre.getText().trim(),
						dpOpenDate.getValue().toString(), dpCloseDate.getValue().toString(),
						Integer.parseInt(txtRunTime.getText().trim()), txtNation.getText().trim(),
						cbGrade.getSelectionModel().getSelectedItem(), txtDirector.getText().trim(),
						txtStory.getText().trim(), poster);

				success = amDao.AddMovie(mVo);

				if (success) {
					// 이미지 저장
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("영화 등록");
					alert.setHeaderText("영화 등록 성공");
					alert.setContentText("영화를 추가 하였습니다.");
					alert.showAndWait();

					txtTitle.setText("");
					txtDirector.setText("");
					txtGenre.setText("");
					txtNation.setText("");
					txtRunTime.setText("");
					txtStory.setText("");
					imgPoster.setImage(null);
					dpOpenDate.setValue(null);
					dpCloseDate.setValue(null);
					cbGrade.setValue(null);
					selectPoster = null;

					loadMovie();

				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("영화 등록");
					alert.setHeaderText("영화 등록 실패");
					alert.setContentText("영화 등록에 실패하였습니다.\n다시 시도해 주세요.");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("영화 등록");
				alert.setHeaderText("영화 등록 실패");
				alert.setContentText("개봉일이 상영 종료일보다 클 수 없습니다.");
				alert.showAndWait();
			}

		}
	}

	public void handlerBtnPoster(ActionEvent event) {
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

	public void loadMovie() {
		movieList.removeAll(movieList);
		MovieDAO mDao = new MovieDAO();

		ArrayList<MovieVO> list = mDao.loadMovie();

		for (int i = 0; i < list.size(); i++) {
			movieList.add(list.get(i));
		}
		tvMovieInfo.setItems(movieList);
	}

}
