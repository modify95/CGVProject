package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CinemaVO;
import model.MovieVO;
import model.ScheduelVO;

public class ScheduelController implements Initializable {

	@FXML
	private Label lb_M_Director;
	@FXML
	private Label lb_M_Grade;
	@FXML
	private Label lb_M_Nation;
	@FXML
	private Label lb_M_OpenDate;
	@FXML
	private Label lb_M_Genre;
	@FXML
	private Label lb_M_RunTime;
	@FXML
	private Label lb_M_Title;
	@FXML
	private Label lb_M_CloseDate;
	@FXML
	private ComboBox<String> cbMovie;
	@FXML
	private ComboBox<String> cbCinema;
	@FXML
	private ComboBox<String> cbStartHour;
	@FXML
	private ComboBox<String> cbStartMin;
	@FXML
	private TextField txtFinishHour;
	@FXML
	private TextField txtFinishMin;
	@FXML
	private Button btnAddScheduel;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnDelete;
	@FXML
	private DatePicker dpStartTime;
	@FXML
	private TextField txtFinishTime;
	@FXML
	private ImageView imgPoster;
	@FXML
	private TextField txtAdultPay;
	@FXML
	private TextField txtChildPay;
	@FXML
	private TableView<ScheduelVO> tvScheduel = new TableView<>();
	@FXML
	private TableColumn<ScheduelVO, String> colMovie;
	@FXML
	private TableColumn<ScheduelVO, String> colOpenDate;
	@FXML
	private TableColumn<ScheduelVO, String> colCloseDate;
	@FXML
	private TableColumn<ScheduelVO, String> colCinemaName;

	ObservableList<String> hour = FXCollections.observableArrayList();
	ObservableList<String> min = FXCollections.observableArrayList();
	ObservableList<String> movieList = FXCollections.observableArrayList();
	ObservableList<String> cinemaList = FXCollections.observableArrayList();
	ObservableList<ScheduelVO> ScheduelList = FXCollections.observableArrayList();
	ArrayList<MovieVO> movieInfo = new ArrayList<>();
	ArrayList<CinemaVO> cinemaInfo = new ArrayList<>();
	ArrayList<ScheduelVO> SchedeulInfo = new ArrayList<>();

	MovieVO mvo;
	CinemaVO cvo;
	ScheduelVO selectScheduel;

	String startTime;
	String finishTime;

	boolean checkScheduel = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnAddScheduel.setOnAction(event -> handlerAddScheduel(event));
		btnExit.setOnAction(event -> handlerExit(event));
		cbMovie.setOnAction(event -> handlerCbMovie(event));
		cbCinema.setOnAction(event -> handlerCbCinema(event));
		dpStartTime.setOnAction(event -> handlerAutoCount(event));
		cbStartHour.setOnAction(event -> handlerAutoCount(event));
		cbStartMin.setOnAction(event -> handlerAutoCount(event));
		btnDelete.setOnAction(event -> handlerBtnDelete(event));
		tvScheduel.setOnMouseClicked(event -> handlerTvSchedeul(event));

		colMovie.setCellValueFactory(new PropertyValueFactory<>("s_StartTime2"));
		colCinemaName.setCellValueFactory(new PropertyValueFactory<>("s_FinishTime2"));
		colOpenDate.setCellValueFactory(new PropertyValueFactory<>("s_StartTime"));
		colCloseDate.setCellValueFactory(new PropertyValueFactory<>("s_FinishTime"));

		addTime();
		cbStartHour.setItems(hour);
		cbStartMin.setItems(min);

		loadMovie();
		cbMovie.setItems(movieList);
		loadCinema();
		cbCinema.setItems(cinemaList);
		loadSchedeul();
		tvScheduel.setItems(ScheduelList);

	}

	public void handlerTvSchedeul(MouseEvent event) {
		int index = tvScheduel.getSelectionModel().getSelectedIndex();

		if (index != -1) {
			selectScheduel = ScheduelList.get(index);
			btnDelete.setDisable(false);
		}
	}

	public void handlerBtnDelete(ActionEvent event) {
		ScheduelDAO sDao = new ScheduelDAO();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("상영 일정 삭제");
		alert.setHeaderText(null);
		alert.setContentText("예매한 고객이 있는경우 모두 예매가 취소됩니다.\n정말로 상영 일정을 삭제 하시겠습니까?");
		ButtonType okButton = new ButtonType("예", ButtonData.YES);
		ButtonType noButton = new ButtonType("아니오", ButtonData.NO);
		alert.getButtonTypes().setAll(okButton, noButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			sDao.deleteTicketing(selectScheduel);
			boolean success = sDao.deleteScheduel(selectScheduel);
			if (success) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("상영 일정 삭제");
				alert2.setHeaderText(null);
				alert2.setContentText("상영 일정 삭제 완료.");
				alert2.showAndWait();
				btnDelete.setDisable(true);
			}
		}
		loadSchedeul();
	}

	public void handlerBtnEdit(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/EditScheduel.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("상영 일정 수정");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			loadMovie();
			btnDelete.setDisable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerAutoCount(ActionEvent event) {
		int adultPay = 0;
		int childPay = 0;
		try {
			if (dpStartTime.getValue() != null && cbStartHour.getSelectionModel().getSelectedItem() != null
					&& cbStartMin.getSelectionModel().getSelectedItem() != null
					&& cbMovie.getSelectionModel().getSelectedItem() != null) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHHmm");
				startTime = dpStartTime.getValue().toString() + cbStartHour.getSelectionModel().getSelectedItem()
						+ cbStartMin.getSelectionModel().getSelectedItem();
				cal.setTime(df.parse(startTime));
				startTime = df.format(cal.getTime());
				cal.setTime(df.parse(startTime));
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				cal.add(Calendar.MINUTE, (mvo.getM_RunTime() + 10));
				finishTime = df.format(cal.getTime());
				txtFinishTime.setText(finishTime.substring(0, 10));
				txtFinishHour.setText(finishTime.substring(10, 12));
				txtFinishMin.setText(finishTime.substring(12, 14));

				int payHour = Integer.parseInt(startTime.substring(10, 12));
				int payMin = Integer.parseInt(startTime.substring(12, 14));

				if (payHour < 10 && payHour >= 6 || (payHour == 10 && payMin == 0)) {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 7000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 6000;
						} else {
							childPay = 0;
						}
					} else {
						adultPay = 8000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 6000;
						} else {
							childPay = 0;
						}
					}
				} else if ((payHour == 10 && payMin == 1) || (payHour < 13 && payHour >= 10)
						|| (payHour == 13 && payMin == 0)) {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 8000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 7000;
						} else {
							childPay = 0;
						}

					} else {
						adultPay = 11000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 9000;
						} else {
							childPay = 0;
						}
					}
				} else if ((payHour == 13 && payMin == 1) || (payHour < 16 && payHour >= 13)
						|| (payHour == 16 && payMin == 0)) {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 9000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 7000;
						} else {
							childPay = 0;
						}
					} else {
						adultPay = 11000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 9000;
						} else {
							childPay = 0;
						}
					}
				} else if ((payHour == 16 && payMin == 1) || (payHour < 22 && payHour >= 16)
						|| (payHour == 22 && payMin == 0)) {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 10000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 8000;
						} else {
							childPay = 0;
						}
					} else {
						adultPay = 11000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 9000;
						} else {
							childPay = 0;
						}
					}
				} else if ((payHour == 22 && payMin == 1) || (payHour < 24 && payHour >= 22)
						|| (payHour == 24 && payMin == 0)) {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 9000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 7000;
						} else {
							childPay = 0;
						}
					} else {
						adultPay = 11000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 9000;
						} else {
							childPay = 0;
						}
					}
				} else {
					if (dayOfWeek > 1 && dayOfWeek < 6) {
						adultPay = 8000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 7000;
						} else {
							childPay = 0;
						}
					} else {
						adultPay = 10000;
						if (!mvo.getM_Grade().equals("청소년관람불가")) {
							childPay = 8000;
						} else {
							childPay = 0;
						}
					}
				}
				txtAdultPay.setText(adultPay + "");
				txtChildPay.setText(childPay + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerCbCinema(ActionEvent event) {
		int index = cbCinema.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			cvo = cinemaInfo.get(index);
		}
	}

	public void handlerCbMovie(ActionEvent event) {
		int index = cbMovie.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			mvo = movieInfo.get(index);
			Image poster = new Image("file:/C:/MoviePoster/" + mvo.getM_Poster());

			lb_M_Title.setText(mvo.getM_Title());
			lb_M_Genre.setText(mvo.getM_Genre());
			lb_M_Nation.setText(mvo.getM_Nation());
			lb_M_RunTime.setText(mvo.getM_RunTime() + "분");
			lb_M_OpenDate.setText(mvo.getM_OpenDate());
			lb_M_CloseDate.setText(mvo.getM_CloseDate());
			lb_M_Grade.setText(mvo.getM_Grade());
			lb_M_Director.setText(mvo.getM_Director());
			imgPoster.setImage(poster);

			handlerAutoCount(null);
		}
	}

	public void handlerAddScheduel(ActionEvent event) {
		ScheduelDAO sDao = new ScheduelDAO();

		boolean success;

		if (cbMovie.getSelectionModel().getSelectedItem() == null
				|| cbCinema.getSelectionModel().getSelectedItem() == null || dpStartTime.getValue() == null
				|| cbStartHour.getSelectionModel().getSelectedItem() == null
				|| cbStartMin.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("상영 일정 등록");
			alert.setHeaderText("상영 일정 등록 실패");
			alert.setContentText("빈칸을 모두 채워주세요.");
			alert.showAndWait();
		} else {
			checkScheduel = sDao.checkSchedeul(cvo.getC_num(), startTime, finishTime);
			if (checkOpenDate() && checkCloseDate() && checkScheduel && checkTime()) {
				ScheduelVO sVo = new ScheduelVO(startTime, finishTime, Integer.parseInt(txtAdultPay.getText()),
						Integer.parseInt(txtChildPay.getText()), cvo.getC_num(), mvo.getM_Num());

				success = sDao.addScheduel(sVo);

				if (success) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("상영 일정 등록");
					alert.setHeaderText("상영 일정 등록 성공");
					alert.setContentText("상영 일정 등록을 성공하였습니다.");
					alert.showAndWait();
					ScheduelList.removeAll(ScheduelList);
					loadSchedeul();
					dpStartTime.setValue(null);
					cbMovie.setValue(null);
					cbStartHour.setValue(null);
					cbStartMin.setValue(null);
					cbCinema.setValue(null);
					imgPoster.setImage(null);
					lb_M_CloseDate.setText("");
					lb_M_Director.setText("");
					lb_M_Genre.setText("");
					lb_M_Grade.setText("");
					lb_M_Nation.setText("");
					lb_M_OpenDate.setText("");
					lb_M_RunTime.setText("");
					lb_M_Title.setText("");
					txtAdultPay.setText("");
					txtChildPay.setText("");
					txtFinishHour.setText("");
					txtFinishMin.setText("");
					txtFinishTime.setText("");
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("상영 일정 등록");
					alert.setHeaderText("상영 일정 등록 실패");
					alert.setContentText("상영 일정 등록을 실패하였습니다.\n다시 시도해 주세요.");
					alert.showAndWait();
				}
			} else if (!checkOpenDate()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상영 일정 등록");
				alert.setHeaderText("상영 일정 등록 실패");
				alert.setContentText("상영 시작 시간을 개봉일보다 빠르게 설정할 수 없습니다.");
				alert.showAndWait();
			} else if (!checkCloseDate()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상영 일정 등록");
				alert.setHeaderText("상영 일정 등록 실패");
				alert.setContentText("상영 시작 시간을 상영 종료일보다 늦게 설정할 수 없습니다.");
				alert.showAndWait();
			} else if (!checkScheduel) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상영 일정 등록");
				alert.setHeaderText("상영 일정 등록 실패");
				alert.setContentText("상영관에 이미 상영하는 영화가 존재합니다.\n상영시간을 다시 설정해주세요.");
				alert.showAndWait();
			} else if (!checkTime()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상영 일정 등록");
				alert.setHeaderText("상영 일정 등록 실패");
				alert.setContentText(
						"상영 시작 시간이 현재 시간보다 느립니다.\n상영 시작 시간을 현재 시간 이후로 설정해주세요.\n(현재날짜보다 최소 다음일부터 등록 할 수 있습니다. 예 : 현재날짜 2018-08-19 ,등록 가능 날짜  2018-08-20)");
				alert.showAndWait();
			}
		}
	}

	public void handlerExit(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
	}

	public void addTime() {
		String num;
		for (int i = 0; i <= 23; i++) {
			num = i + "";
			num = num.replaceAll("^[0-9]$", "0" + num);
			hour.add(num + "");
		}

		for (int i = 0; i <= 59; i++) {
			num = i + "";
			num = num.replaceAll("^[0-9]$", "0" + num);
			min.add(num + "");
		}
	}

	public void loadMovie() {
		ScheduelDAO sDao = new ScheduelDAO();

		MovieVO mvo;

		movieInfo = sDao.loadMovie();

		for (int i = 0; i < movieInfo.size(); i++) {
			mvo = movieInfo.get(i);
			movieList.add(mvo.getM_Title());
		}
	}

	public void loadCinema() {
		ScheduelDAO sDao = new ScheduelDAO();

		CinemaVO cvo;

		cinemaInfo = sDao.loadCinema();

		for (int i = 0; i < cinemaInfo.size(); i++) {
			cvo = cinemaInfo.get(i);
			cinemaList.add(cvo.getC_name());
		}
	}

	public void loadSchedeul() {
		ScheduelList.removeAll(ScheduelList);

		ScheduelDAO sDao = new ScheduelDAO();

		ScheduelVO svo;

		SchedeulInfo = sDao.loadScheduel();

		for (int i = 0; i < SchedeulInfo.size(); i++) {
			svo = SchedeulInfo.get(i);
			ScheduelList.add(svo);
		}
	}

	public boolean checkOpenDate() {
		int openDate = Integer.parseInt(mvo.getM_OpenDate().replaceAll("-", ""));
		int startDate = Integer.parseInt(dpStartTime.getValue().toString().replaceAll("-", ""));
		boolean success = false;

		if (openDate <= startDate) {
			success = true;
		}
		return success;
	}

	public boolean checkCloseDate() {
		int closeDate = Integer.parseInt(mvo.getM_CloseDate().replaceAll("-", ""));
		int startDate = Integer.parseInt(dpStartTime.getValue().toString().replaceAll("-", ""));

		boolean success = false;

		if (closeDate >= startDate) {
			success = true;
		}
		return success;
	}

	public boolean checkTime() {
		boolean success = false;
		if (dpStartTime.getValue().compareTo(LocalDate.now()) > 0) {
			success = true;
		}
		return success;
	}

}
