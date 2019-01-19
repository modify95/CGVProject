package controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.StatisticsVO;

public class StatisticsController implements Initializable {

	@FXML
	private TableColumn<StatisticsVO, String> colRank1;
	@FXML
	private TableColumn<StatisticsVO, String> colRank2;
	@FXML
	private TableColumn<StatisticsVO, String> colRank3;
	@FXML
	private TableColumn<StatisticsVO, String> colRank4;
	@FXML
	private TableColumn<StatisticsVO, String> colOpenDate1;
	@FXML
	private TableColumn<StatisticsVO, String> colOpenDate2;
	@FXML
	private TableColumn<StatisticsVO, String> colOpenDate3;
	@FXML
	private TableColumn<StatisticsVO, String> colOpenDate4;
	@FXML
	private TableColumn<StatisticsVO, String> colAttendance1;
	@FXML
	private TableColumn<StatisticsVO, String> colAttendance2;
	@FXML
	private TableColumn<StatisticsVO, String> colAttendance3;
	@FXML
	private TableColumn<StatisticsVO, String> colAttendance4;
	@FXML
	private TableColumn<StatisticsVO, String> colSales1;
	@FXML
	private TableColumn<StatisticsVO, String> colSales2;
	@FXML
	private TableColumn<StatisticsVO, String> colSales4;
	@FXML
	private TableColumn<StatisticsVO, String> colSales3;
	@FXML
	private TableColumn<StatisticsVO, String> colTitle4;
	@FXML
	private TableColumn<StatisticsVO, String> colTitle3;
	@FXML
	private TableColumn<StatisticsVO, String> colTitle2;
	@FXML
	private TableColumn<StatisticsVO, String> colTitle1;
	@FXML
	private TableView<StatisticsVO> tvCurrent;
	@FXML
	private TableView<StatisticsVO> tvDay;
	@FXML
	private TableView<StatisticsVO> tvMonth;
	@FXML
	private TableView<StatisticsVO> tvYear;
	@FXML
	private Button btnSaveCurrent;
	@FXML
	private Button btnSaveDay;
	@FXML
	private Button btnSaveMonth;
	@FXML
	private Button btnSaveYear;
	@FXML
	private Button btnSearchCurrent;
	@FXML
	private Button btnSearchDay;
	@FXML
	private Button btnSearchMonth;
	@FXML
	private Button btnSearchYear;
	@FXML
	private ComboBox<String> cbYear1;
	@FXML
	private ComboBox<String> cbYear2;
	@FXML
	private ComboBox<String> cbMonth;
	@FXML
	private DatePicker dpDay;
	@FXML
	private Label lbCurrentSales;
	@FXML
	private Label lbCurrentAttendance;
	@FXML
	private Label lbDaySales;
	@FXML
	private Label lbDayAttendance;
	@FXML
	private Label lbMonthSales;
	@FXML
	private Label lbMonthAttendance;
	@FXML
	private Label lbYearSales;
	@FXML
	private Label lbYearAttendance;

	ArrayList<String> cbDateList = new ArrayList<>();

	ObservableList<String> year = FXCollections.observableArrayList();
	ObservableList<String> month = FXCollections.observableArrayList();
	ObservableList<StatisticsVO> listCurrent = FXCollections.observableArrayList();
	ObservableList<StatisticsVO> listDay = FXCollections.observableArrayList();
	ObservableList<StatisticsVO> listMonth = FXCollections.observableArrayList();
	ObservableList<StatisticsVO> listYear = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colRank1.setCellValueFactory(new PropertyValueFactory<>("rank"));
		colTitle1.setCellValueFactory(new PropertyValueFactory<>("title"));
		colOpenDate1.setCellValueFactory(new PropertyValueFactory<>("openDate"));
		colSales1.setCellValueFactory(new PropertyValueFactory<>("sales"));
		colAttendance1.setCellValueFactory(new PropertyValueFactory<>("attendance"));

		colRank2.setCellValueFactory(new PropertyValueFactory<>("rank"));
		colTitle2.setCellValueFactory(new PropertyValueFactory<>("title"));
		colOpenDate2.setCellValueFactory(new PropertyValueFactory<>("openDate"));
		colSales2.setCellValueFactory(new PropertyValueFactory<>("sales"));
		colAttendance2.setCellValueFactory(new PropertyValueFactory<>("attendance"));

		colRank3.setCellValueFactory(new PropertyValueFactory<>("rank"));
		colTitle3.setCellValueFactory(new PropertyValueFactory<>("title"));
		colOpenDate3.setCellValueFactory(new PropertyValueFactory<>("openDate"));
		colSales3.setCellValueFactory(new PropertyValueFactory<>("sales"));
		colAttendance3.setCellValueFactory(new PropertyValueFactory<>("attendance"));

		colRank4.setCellValueFactory(new PropertyValueFactory<>("rank"));
		colTitle4.setCellValueFactory(new PropertyValueFactory<>("title"));
		colOpenDate4.setCellValueFactory(new PropertyValueFactory<>("openDate"));
		colSales4.setCellValueFactory(new PropertyValueFactory<>("sales"));
		colAttendance4.setCellValueFactory(new PropertyValueFactory<>("attendance"));

		btnSaveCurrent.setOnAction(event -> handlerBtnSaveCurrent(event));
		btnSaveDay.setOnAction(event -> handlerBtnSaveDay(event));
		btnSaveMonth.setOnAction(event -> handlerBtnSaveMonth(event));
		btnSaveYear.setOnAction(event -> handlerBtnSaveYear(event));
		btnSearchCurrent.setOnAction(event -> handlerBtnSearchCurrent(event));
		btnSearchDay.setOnAction(event -> handlerBtnSearchDay(event));
		btnSearchMonth.setOnAction(event -> handlerBtnSearchMonth(event));
		btnSearchYear.setOnAction(event -> handlerBtnSearchYear(event));

		loadCbDate();

	}

	public void handlerBtnSearchCurrent(ActionEvent event) {
		listCurrent.removeAll(listCurrent);
		StatisticsDAO sDao = new StatisticsDAO();
		ArrayList<StatisticsVO> list = sDao.loadCurrent();
		for (int i = 0; i < list.size(); i++) {
			listCurrent.add(list.get(i));
		}
		tvCurrent.setItems(listCurrent);

		int sumSales = 0;
		int sumAttendance = 0;

		for (int i = 0; i < list.size(); i++) {
			sumSales += Integer.parseInt(listCurrent.get(i).getSales().replaceAll(",", ""));
			sumAttendance += Integer.parseInt(listCurrent.get(i).getAttendance().replaceAll(",", ""));
		}
		lbCurrentSales.setText(numFormat(sumSales) + "원");
		lbCurrentAttendance.setText(numFormat(sumAttendance) + "명");

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("실시간 예매 조회");
		alert.setHeaderText(null);
		alert.setContentText("검색이 완료되었습니다.");
		alert.showAndWait();

	}

	public void handlerBtnSearchDay(ActionEvent event) {
		if (dpDay.getValue() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("일별 조회");
			alert.setHeaderText("검색 실패");
			alert.setContentText("검색할 날짜를 선택해주세요.");
			alert.showAndWait();
		} else {
			Calendar cal = Calendar.getInstance();
			LocalDate date = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH));
			if (dpDay.getValue().compareTo(date) >= 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("일별 조회");
				alert.setHeaderText("일별 조회 오류");
				alert.setContentText("현재 날짜 이전 날짜만 선택해주세요.");
				alert.showAndWait();
			} else {
				listDay.removeAll(listDay);
				StatisticsDAO sDao = new StatisticsDAO();
				ArrayList<StatisticsVO> list = sDao.loadDay(dpDay.getValue());
				for (int i = 0; i < list.size(); i++) {
					listDay.add(list.get(i));
				}
				tvDay.setItems(listDay);

				int sumSales = 0;
				int sumAttendance = 0;

				for (int i = 0; i < list.size(); i++) {
					sumSales += Integer.parseInt(listDay.get(i).getSales().replaceAll(",", ""));
					sumAttendance += Integer.parseInt(listDay.get(i).getAttendance().replaceAll(",", ""));
				}
				lbDaySales.setText(numFormat(sumSales) + "원");
				lbDayAttendance.setText(numFormat(sumAttendance) + "명");

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("일별 조회");
				alert.setHeaderText(null);
				alert.setContentText("검색이 완료되었습니다.");
				alert.showAndWait();
			}
		}
	}

	public void handlerBtnSearchMonth(ActionEvent event) {
		if (cbYear1.getSelectionModel().getSelectedItem() == null
				|| cbMonth.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("월별 조회");
			alert.setHeaderText("검색 실패");
			alert.setContentText("검색할 날짜를 선택해주세요.");
			alert.showAndWait();
		} else {
			listMonth.removeAll(listMonth);
			StatisticsDAO sDao = new StatisticsDAO();
			ArrayList<StatisticsVO> list = sDao.loadMonth(cbYear1.getSelectionModel().getSelectedItem() + "."
					+ cbMonth.getSelectionModel().getSelectedItem());
			for (int i = 0; i < list.size(); i++) {
				listMonth.add(list.get(i));
			}
			tvMonth.setItems(listMonth);

			int sumSales = 0;
			int sumAttendance = 0;

			for (int i = 0; i < list.size(); i++) {
				sumSales += Integer.parseInt(listMonth.get(i).getSales().replaceAll(",", ""));
				sumAttendance += Integer.parseInt(listMonth.get(i).getAttendance().replaceAll(",", ""));
			}
			lbMonthSales.setText(numFormat(sumSales) + "원");
			lbMonthAttendance.setText(numFormat(sumAttendance) + "명");

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("월별 조회");
			alert.setHeaderText(null);
			alert.setContentText("검색이 완료되었습니다.");
			alert.showAndWait();
		}
	}

	public void handlerBtnSearchYear(ActionEvent event) {
		if (cbYear2.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("연도별 조회");
			alert.setHeaderText("검색 실패");
			alert.setContentText("검색할 날짜를 선택해주세요.");
			alert.showAndWait();
		} else {
			listYear.removeAll(listYear);
			StatisticsDAO sDao = new StatisticsDAO();
			ArrayList<StatisticsVO> list = sDao.loadYear(cbYear2.getSelectionModel().getSelectedItem());
			for (int i = 0; i < list.size(); i++) {
				listYear.add(list.get(i));
			}
			tvYear.setItems(listYear);

			int sumSales = 0;
			int sumAttendance = 0;

			for (int i = 0; i < list.size(); i++) {
				sumSales += Integer.parseInt(listYear.get(i).getSales().replaceAll(",", ""));
				sumAttendance += Integer.parseInt(listYear.get(i).getAttendance().replaceAll(",", ""));
			}
			lbYearSales.setText(numFormat(sumSales) + "원");
			lbYearAttendance.setText(numFormat(sumAttendance) + "명");

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("연도별 조회");
			alert.setHeaderText(null);
			alert.setContentText("검색이 완료되었습니다.");
			alert.showAndWait();
		}
	}

	public void handlerBtnSaveCurrent(ActionEvent event) {
		saveExel(listCurrent);
	}

	public void handlerBtnSaveDay(ActionEvent event) {
		saveExel(listDay);
	}

	public void handlerBtnSaveMonth(ActionEvent event) {
		saveExel(listMonth);
	}

	public void handlerBtnSaveYear(ActionEvent event) {
		saveExel(listYear);
	}

	public void loadCbDate() {
		StatisticsDAO sDao = new StatisticsDAO();
		ArrayList<String> listOpenDate = sDao.loadOpenDate();
		ArrayList<String> listCloseDate = sDao.loadCloseDate();
		HashSet<String> distinct;

		for (int i = 0; i < listOpenDate.size(); i++) {
			cbDateList.add(listOpenDate.get(i));
		}

		for (int i = 0; i < listCloseDate.size(); i++) {
			cbDateList.add(listCloseDate.get(i));
		}

		distinct = new HashSet<>(cbDateList);
		cbDateList = new ArrayList<>(distinct);

		for (int i = 0; i < cbDateList.size(); i++) {
			year.add(cbDateList.get(i).substring(0, 4));
			month.add(cbDateList.get(i).substring(5, 7));
		}

		distinct = new HashSet<>(year);
		year = FXCollections.observableArrayList(distinct);
		distinct = new HashSet<>(month);
		month = FXCollections.observableArrayList(distinct);

		cbYear1.setItems(year);
		cbYear2.setItems(year);
		cbMonth.setItems(month);
	}

	public File selectDir() {
		FileChooser dc = new FileChooser();
		dc.getExtensionFilters().addAll(new ExtensionFilter("엑셀 파일", "*.xlsx"));
		dc.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		File saveName = dc.showSaveDialog(null);
		return saveName;
	}

	public void saveExel(List<StatisticsVO> list) {
		StatisticsExel sExel = new StatisticsExel();

		if (list.size() != 0) {
			File saveName = selectDir();

			if (saveName != null) {
				boolean success = sExel.exelWriter(list, saveName.toString());

				if (success) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("엑셀 저장");
					alert.setHeaderText("엑셀 저장 성공");
					alert.setContentText("저장이 완료되었습니다.");
					alert.showAndWait();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("엑셀 저장");
			alert.setHeaderText("엑셀 저장 실패");
			alert.setContentText("검색된 데이터가 없습니다.");
			alert.showAndWait();
		}
	}

	public static String numFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
}