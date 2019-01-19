package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.MovieVO;
import model.ScheduelVO;
import model.TicketingVO;

public class TicketingController implements Initializable {

	@FXML
	private TableView<MovieVO> tvMovie = new TableView<MovieVO>();
	@FXML
	private TableView<ScheduelVO> tvDate = new TableView<ScheduelVO>();
	@FXML
	private TableColumn<MovieVO, String> colMovie;
	@FXML
	private TableColumn<ScheduelVO, String> colDate;
	@FXML
	private Label lbM_Grade;
	@FXML
	private Label lbS_Date;
	@FXML
	private Label lbM_Title;
	@FXML
	private ImageView imgPoster;
	@FXML
	private Label lbC_name;
	@FXML
	private Button btnReset;
	@FXML
	private VBox vbox;
	@FXML
	private AnchorPane main;
	@FXML
	private VBox vbTime;
	@FXML
	private AnchorPane apBtn;
	@FXML
	private HBox hbPerson;
	@FXML
	private VBox vbSeatNum;
	@FXML
	private VBox vbPay;

	static Button btnSelectSeat = new Button("좌석 선택");
	static Button btnPay = new Button("결제");
	static Label lbAdult = new Label();
	static Label lbChild = new Label();
	static Label lbSeatNum = new Label();
	static Label lbAdultPay = new Label("0원");
	static Label lbChildPay = new Label("0원");
	static Label lbTotalPay = new Label("0원");

	static Label leftSeatCount;
	static Parent root;

	File ticketDir = new File("C:/TicketImage");
	int dateIndex = -1;
	int movieIndex = -1;
	HashMap<Integer, Integer> scheduelCount;

	MovieVO selectMovie;
	ScheduelVO selectDate;
	static ScheduelVO selectTime;

	ArrayList<HBox> hbCinema = new ArrayList<>();
	ArrayList<HBox> hbTime = new ArrayList<>();
	ArrayList<ToggleButton> btnTime = new ArrayList<>();
	ArrayList<Tooltip> tooltip = new ArrayList<>();
	ArrayList<Label> lbLeftSeat = new ArrayList<>();

	ArrayList<ScheduelVO> timeList = new ArrayList<>();

	ArrayList<String> ticketInfo = new ArrayList<>();

	ToggleGroup group = new ToggleGroup();

	ObservableList<MovieVO> movieList = FXCollections.observableArrayList();
	ObservableList<ScheduelVO> dateList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		handlerBtnReset(null);
		btnSelectSeat.setPrefSize(100, 100);
		btnPay.setPrefSize(100, 100);
		btnSelectSeat.setDisable(true);
		btnSelectSeat.setVisible(true);
		btnPay.setDisable(true);
		btnPay.setVisible(false);
		lbSeatNum.setWrapText(true);
		apBtn.getChildren().addAll(btnSelectSeat, btnPay);
		hbPerson.getChildren().addAll(lbAdult, lbChild);
		vbSeatNum.getChildren().add(lbSeatNum);
		vbPay.getChildren().addAll(lbAdultPay, lbChildPay, lbTotalPay);

		tvMovie.setOnMouseClicked(event -> handlerTvMovie(event));
		tvDate.setOnMouseClicked(event -> handlerTvDate(event));
		btnReset.setOnAction(event -> handlerBtnReset(event));
		btnSelectSeat.setOnAction(event -> handlerBtnSelectSeat(event));
		btnPay.setOnAction(event -> handlerBtnPay(event));

		colMovie.setCellValueFactory(new PropertyValueFactory<>("m_Title"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("s_StartTime"));

		loadMovie();
		loadDate();

	}

	public void handlerBtnPay(ActionEvent event) {
		TicketingDAO tDao = new TicketingDAO();
		TicketingVO tVo;

		int index = SeatController.seatList.toString().length();
		String seat = SeatController.seatList.toString().substring(1, index - 1);
		seat = seat.replaceAll("\\s", "");
		tVo = new TicketingVO(Integer.parseInt(lbTotalPay.getText().replaceAll("원", "")), seat, LoginController.num,
				selectTime.getM_Num(), selectTime.getS_Num(), SeatController.total, lbM_Title.getText());

		boolean success = tDao.addTicketing(tVo);
		String ticketNum = tDao.TicketNumber(selectTime);

		Calendar cal = Calendar.getInstance();
		ticketInfo
				.add(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH)
						+ " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
		ticketInfo.add(lbM_Grade.getText());
		ticketInfo.add(lbM_Title.getText());
		ticketInfo.add(selectTime.getS_StartTime().substring(0, 11) + selectTime.getS_StartTime2() + "~"
				+ selectTime.getS_FinishTime2());
		ticketInfo.add(selectTime.getC_Num() + "관 " + lbSeatNum.getText());
		ticketInfo.add(lbAdult.getText() + lbChild.getText());
		ticketInfo.add(ticketNum.substring(0, 4) + "-" + ticketNum.substring(4, 8) + "-" + ticketNum.substring(8, 12)
				+ "-" + ticketNum.substring(12, 16));

		if (success) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("예매");
			alert.setHeaderText("예매 완료");
			alert.setContentText("예매가 완료 되었습니다.");
			alert.showAndWait();
			Stage stage = (Stage) btnPay.getScene().getWindow();
			stage.close();
			Ticket();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("예매");
			alert.setHeaderText("예매 실패");
			alert.setContentText("예매를 실패 하였습니다.\n다시 시도해 주세요");
			alert.showAndWait();
		}
	}

	public void handlerBtnSelectSeat(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/view/Seat.fxml"));
			root.setLayoutX(11);
			root.setLayoutY(52);
			main.getChildren().add(root);
			btnSelectSeat.setVisible(false);
			btnPay.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handlerBtnReset(ActionEvent event) {

		main.getChildren().remove(root);
		loadMovie();
		loadDate();
		btnSelectSeat.setDisable(true);
		btnSelectSeat.setVisible(true);
		btnPay.setVisible(false);
		imgPoster.setImage(null);
		lbM_Title.setText("");
		lbM_Grade.setText("");
		lbS_Date.setText("");
		lbC_name.setText("");
		lbSeatNum.setText("");
		lbAdultPay.setText("0원");
		lbChildPay.setText("0원");
		lbTotalPay.setText("0원");
		lbAdult.setText("");
		lbChild.setText("");

		selectMovie = null;
		selectDate = null;
		selectTime = null;

		vbox.getChildren().removeAll(hbCinema);
		vbox.getChildren().removeAll(hbTime);
		hbCinema.clear();
		hbTime.clear();
		btnTime.clear();
		tooltip.clear();
		lbLeftSeat.clear();
		SeatController.seatList.clear();
	}

	public void handlerTvDate(MouseEvent event) {
		TicketingDAO tDao = new TicketingDAO();
		dateIndex = tvDate.getSelectionModel().getSelectedIndex();

		if (dateIndex != -1) {
			selectDate = dateList.get(dateIndex);

			ArrayList<MovieVO> list = tDao.selectDate(selectDate);

			movieList.removeAll(movieList);
			btnSelectSeat.setDisable(true);
			selectTime = null;

			for (int i = 0; i < list.size(); i++) {
				movieList.add(list.get(i));
			}
			tvMovie.setItems(movieList);

			if (selectMovie != null) {
				int select = 0;
				for (int i = 0; i < movieList.size(); i++) {
					MovieVO mvo = movieList.get(i);
					if (selectMovie.getM_Title().equals(mvo.getM_Title())) {
						select = i;
					}
				}
				tvMovie.getSelectionModel().select(select);
			}

			lbS_Date.setText(selectDate.getS_StartTime());
		}
		loadScheduel();
	}

	public void handlerTvMovie(MouseEvent event) {

		TicketingDAO tDao = new TicketingDAO();
		movieIndex = tvMovie.getSelectionModel().getSelectedIndex();
		if (movieIndex != -1) {
			selectMovie = movieList.get(movieIndex);

			ArrayList<ScheduelVO> list = tDao.selectMovie(selectMovie);

			dateList.removeAll(dateList);
			btnSelectSeat.setDisable(true);
			selectTime = null;

			for (int i = 0; i < list.size(); i++) {
				dateList.add(list.get(i));
			}
			tvDate.setItems(dateList);

			if (selectDate != null) {
				int select = 0;
				for (int i = 0; i < dateList.size(); i++) {
					ScheduelVO svo = dateList.get(i);
					if (selectDate.getS_StartTime().equals(svo.getS_StartTime())) {
						select = i;
					}
				}
				tvDate.getSelectionModel().select(select);
			}

			Image poster = new Image("file:/C:/MoviePoster/" + selectMovie.getM_Poster());
			imgPoster.setImage(poster);
			lbM_Title.setText(selectMovie.getM_Title());
			lbM_Grade.setText(selectMovie.getM_Grade());
		}
		loadScheduel();
	}

	public void loadMovie() {
		TicketingDAO tDao = new TicketingDAO();

		ArrayList<MovieVO> list = tDao.screenMovie();

		movieList.removeAll(movieList);
		for (int i = 0; i < list.size(); i++) {
			movieList.add(list.get(i));
		}
		tvMovie.setItems(movieList);
	}

	public void loadDate() {
		TicketingDAO tDao = new TicketingDAO();

		ArrayList<ScheduelVO> list = tDao.screenDate();

		dateList.removeAll(dateList);
		for (int i = 0; i < list.size(); i++) {
			dateList.add(list.get(i));
		}
		tvDate.setItems(dateList);
	}

	public void loadScheduel() {

		vbox.getChildren().removeAll(hbCinema);
		vbox.getChildren().removeAll(hbTime);
		hbCinema.clear();
		hbTime.clear();
		btnTime.clear();
		tooltip.clear();
		lbLeftSeat.clear();

		if (movieIndex != -1 && dateIndex != -1 && selectMovie != null && selectDate != null) {
			TicketingDAO tDao = new TicketingDAO();
			SeatDAO sDao = new SeatDAO();
			ArrayList<Integer> leftSeat = new ArrayList<>();

			timeList = tDao.screenTime(selectMovie.getM_Title(), selectDate.getS_StartTime());
			scheduelCount = tDao.countTime(selectMovie.getM_Title(), selectDate.getS_StartTime());

			for (int i = 0; i < timeList.size(); i++) {
				int count = sDao.leftSeat(timeList.get(i));
				leftSeat.add(count);
			}
			int cCount = 0;
			int hbTimeCount;
			int timeCount = 0;
			int count3 = 0;

			for (int i = 1; i <= 7; i++) {
				if (scheduelCount.get(i) != null) {
					hbCinema.add(new HBox());
					hbCinema.get(cCount).getChildren().add(new Label(i + "관"));

					int count1 = scheduelCount.get(i);
					int count2;

					vbox.getChildren().add(hbCinema.get(cCount));

					cCount++;
					if ((scheduelCount.get(i) % 3) != 0) {
						hbTimeCount = (scheduelCount.get(i) / 3) + 1;
					} else {
						hbTimeCount = scheduelCount.get(i) / 3;
					}

					for (int j = 0; j < hbTimeCount; j++) {
						hbTime.add(new HBox());
						hbTime.get(j).setSpacing(10);

						if ((count1 / 3) > 0) {
							count2 = 3;
							count1 -= 3;
						} else {
							count2 = count1 % 3;
						}
						for (int k = 0; k < count2; k++) {
							tooltip.add(new Tooltip(timeList.get(timeCount).getS_FinishTime2()));
							btnTime.add(new ToggleButton(timeList.get(timeCount).getS_StartTime2()));
							lbLeftSeat.add(new Label(leftSeat.get(timeCount) + "석"));
							btnTime.get(timeCount).setToggleGroup(group);
							btnTime.get(timeCount).setTooltip(tooltip.get(timeCount));
							btnTime.get(timeCount).setOnAction(event -> handlerTbtnSchedeul(event));
							btnTime.get(timeCount).setId(timeCount + "");
							hbTime.get(count3).setSpacing(10);
							hbTime.get(count3).setAlignment(Pos.CENTER_LEFT);
							hbTime.get(count3).getChildren().addAll(btnTime.get(timeCount), lbLeftSeat.get(timeCount));
							timeCount++;
						}
						vbox.getChildren().add(hbTime.get(count3));
						count3++;
					}
				}
			}
		}
		checkLeftSeatCount();
	}

	public void handlerTbtnSchedeul(ActionEvent event) {

		btnSelectSeat.setDisable(false);
		ToggleButton tbtn = (ToggleButton) event.getSource();
		int count = 0;
		for (int i = 0; i < 7; i++) {
			if (scheduelCount.get(i) != null) {
				count += scheduelCount.get(i);
			}
		}
		for (int i = 0; i < count; i++) {
			btnTime.get(i).setStyle("-fx-background-color : null");
			btnTime.get(i).setStyle("-fx-border-color : null");
			btnTime.get(i).setTextFill(Paint.valueOf("black"));
		}
		if (tbtn.isSelected()) {
			tbtn.setStyle("-fx-background-color : black");
			tbtn.setTextFill(Paint.valueOf("white"));
		}

		selectTime = timeList.get(Integer.parseInt(tbtn.getId()));

		lbC_name.setText(selectTime.getC_Num() + "관");
		leftSeatCount = lbLeftSeat.get(Integer.parseInt(tbtn.getId()));
	}

	public void checkLeftSeatCount() {
		for (int i = 0; i < lbLeftSeat.size(); i++) {
			if (lbLeftSeat.get(i).getText().equals("0석")) {
				lbLeftSeat.get(i).setText("예매 완료");
				btnTime.get(i).setDisable(true);
			}
		}
	}

	public void Ticket() {
		try {
			Stage stage = new Stage();
			Parent ticket = FXMLLoader.load(getClass().getResource("/view/Ticket.fxml"));
			Scene scene = new Scene(ticket);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("영화관람권");

			Label tSysdate = (Label) ticket.lookup("#lbSysdate");
			Label tGrade = (Label) ticket.lookup("#lbGrade");
			Label tTitle = (Label) ticket.lookup("#lbTitle");
			Label tScreenTime = (Label) ticket.lookup("#lbScreenTime");
			Label tCinemaSeat = (Label) ticket.lookup("#lbCinemaSeat");
			Label tTotalCount = (Label) ticket.lookup("#lbTotalCount");
			Label tNum = (Label) ticket.lookup("#lbTNum");

			tSysdate.setText(ticketInfo.get(0));
			tGrade.setText(ticketInfo.get(1));
			tTitle.setText(ticketInfo.get(2));
			tScreenTime.setText(ticketInfo.get(3));
			tCinemaSeat.setText(ticketInfo.get(4));
			tTotalCount.setText(ticketInfo.get(5));
			tNum.setText(ticketInfo.get(6));

			if (!ticketDir.exists()) {
				ticketDir.mkdir();
			}

			WritableImage snapShot = scene.snapshot(null);
			ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
					new File(ticketDir.getAbsolutePath() + "/ticket.png"));

			stage.show();

			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			PdfWriter.getInstance(document, new FileOutputStream(ticketDir.getAbsolutePath() + "/ticket.pdf"));
			document.open();

			final String ticketUrl = ticketDir.getAbsolutePath() + "/ticket.png";
			com.itextpdf.text.Image ticketImage;

			try {
				if (com.itextpdf.text.Image.getInstance(ticketUrl) != null) {
					ticketImage = com.itextpdf.text.Image.getInstance(ticketUrl);
					ticketImage.setAlignment(Element.ALIGN_CENTER);
					document.add(ticketImage);
				}
			} catch (IOException e) {

			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
