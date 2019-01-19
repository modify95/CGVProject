package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class SeatController implements Initializable {

	@FXML
	private Label lbLeftSeat;
	@FXML
	private Label lbCinema;
	@FXML
	private Label lbScreenTime;
	@FXML
	private Label lbChild;
	@FXML
	private Button btnPre;
	@FXML
	private HBox hbAdult;
	@FXML
	private HBox hbChild;
	@FXML
	private HBox hbLineA;
	@FXML
	private HBox hbLineB;
	@FXML
	private HBox hbLineC;
	@FXML
	private HBox hbLineD;
	@FXML
	private HBox hbLineE;
	@FXML
	private HBox hbLineF;
	@FXML
	private HBox hbLineG;

	ArrayList<Button> tbAdult = new ArrayList<>();
	ArrayList<Button> tbChild = new ArrayList<>();
	ArrayList<ToggleButton> tbSeatList = new ArrayList<>();
	ArrayList<HBox> hbSeatList = new ArrayList<>();
	ArrayList<String> seatNameList = new ArrayList<>();
	static ArrayList<Integer> seatList = new ArrayList<>();

	int count = 0;
	int adultCount = 0;
	int childCount = 0;
	int totalCount = 0;
	int adultCheck = 0;
	int childCheck = 0;
	static int total;

	SeatDAO sDao = new SeatDAO();
	boolean checkGrade = sDao.checkGrade(TicketingController.selectTime);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		addSeatToggleBtn();
		addPersonBtn();
		btnPre.setOnAction(event -> handlerBtnPre(event));

		tbAdult.get(0).setStyle("-fx-background-color : black");
		tbAdult.get(0).setTextFill(Paint.valueOf("white"));

		if (checkGrade) {
			lbChild.setVisible(true);
			tbChild.get(0).setStyle("-fx-background-color : black");
			tbChild.get(0).setTextFill(Paint.valueOf("white"));
		}

		lbScreenTime.setText(TicketingController.selectTime.getS_StartTime().substring(0, 11) + " "
				+ TicketingController.selectTime.getS_StartTime2() + " ~ "
				+ TicketingController.selectTime.getS_FinishTime2());
		lbLeftSeat.setText(TicketingController.leftSeatCount.getText());
		lbCinema.setText(TicketingController.selectTime.getC_Num() + "관");

		disableSeat();

	}

	public void handlerBtnPre(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane) btnPre.getScene().getRoot();
			root.getChildren().remove(TicketingController.root);
			TicketingController.btnSelectSeat.setVisible(true);
			TicketingController.btnPay.setVisible(false);
			TicketingController.lbSeatNum.setText("");
			TicketingController.lbAdultPay.setText("0원");
			TicketingController.lbChildPay.setText("0원");
			TicketingController.lbTotalPay.setText("0원");
			TicketingController.lbAdult.setText("");
			TicketingController.lbChild.setText("");
			seatList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handlerBtnSeat(ActionEvent event) {
		ToggleButton tb = (ToggleButton) event.getSource();

		if (tb.isSelected()) {
			if (count < totalCount) {
				tb.setStyle("-fx-background-color : black");
				tb.setTextFill(Paint.valueOf("white"));
				++count;
				if (count <= adultCount) {
					TicketingController.lbAdultPay
							.setText(TicketingController.selectTime.getS_AdultPay() * count + "원");
				} else {
					TicketingController.lbChildPay
							.setText(TicketingController.selectTime.getS_ChildPay() * (count - adultCount) + "원");
				}

				TicketingController.lbTotalPay
						.setText((Integer.parseInt(TicketingController.lbAdultPay.getText().replaceAll("원", ""))
								+ Integer.parseInt(TicketingController.lbChildPay.getText().replaceAll("원", "")))
								+ "원");
				seatList.add(Integer.parseInt(tb.getId()));
				Collections.sort(seatList);

			} else {
				tb.setSelected(false);
			}
		} else {
			if (count <= totalCount) {
				tb.setStyle("-fx-background-color : null");
				tb.setStyle("-fx-border-color : null");
				tb.setTextFill(Paint.valueOf("black"));
				--count;
				if (count >= adultCount) {
					TicketingController.lbChildPay
							.setText(TicketingController.selectTime.getS_ChildPay() * (count - adultCount) + "원");
				} else {
					TicketingController.lbAdultPay
							.setText(TicketingController.selectTime.getS_AdultPay() * count + "원");
				}

				TicketingController.lbTotalPay
						.setText((Integer.parseInt(TicketingController.lbAdultPay.getText().replaceAll("원", ""))
								+ Integer.parseInt(TicketingController.lbChildPay.getText().replaceAll("원", "")))
								+ "원");
				for (int i = 0; i < seatList.size(); i++) {
					if (seatList.get(i) == Integer.parseInt(tb.getId())) {
						seatList.remove(i);
					}
				}
				Collections.sort(seatList);

			}
		}

		if (count == totalCount && totalCount != 0) {
			TicketingController.btnPay.setDisable(false);
		} else {
			TicketingController.btnPay.setDisable(true);
		}

		String lineName = "";
		String SeatName = "";
		for (int i = 0; i < seatList.size(); i++) {
			int number = seatList.get(i);
			if (0 <= number && number <= 15) {
				lineName = "A" + seatNameList.get(number);
			} else if (16 <= number && number <= 31) {
				lineName = "B" + seatNameList.get(number);
			} else if (32 <= number && number <= 47) {
				lineName = "C" + seatNameList.get(number);
			} else if (48 <= number && number <= 63) {
				lineName = "D" + seatNameList.get(number);
			} else if (64 <= number && number <= 78) {
				lineName = "E" + seatNameList.get(number);
			} else if (79 <= number && number <= 93) {
				lineName = "F" + seatNameList.get(number);
			} else {
				lineName = "G" + seatNameList.get(number);
			}

			SeatName = SeatName.concat(lineName + ",");
		}

		try {
			TicketingController.lbSeatNum.setText(SeatName.substring(0, (SeatName.length() - 1)));
		} catch (StringIndexOutOfBoundsException e) {
		}
	}

	public void handlerBtnAdult(ActionEvent event) {

		Button btn = (Button) event.getSource();
		adultCheck = Integer.parseInt(btn.getId());

		total = adultCheck + childCount;

		if (total < count) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("좌석 선택");
			alert.setHeaderText("인원 초과");
			alert.setContentText("선택한 좌석이 예매 인원 보다 많습니다.");
			alert.showAndWait();
			return;
		}
		if (total > 8) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("좌석 선택");
			alert.setHeaderText("인원 초과");
			alert.setContentText("최대 예매 가능한 인원수는 8명 까지 입니다.");
			alert.showAndWait();
			return;
		} else {
			adultCount = adultCheck;
			totalCount = adultCount + childCount;
			if (adultCount != 0) {
				TicketingController.lbAdult.setText("일반 " + adultCount + "명");
			} else {
				TicketingController.lbAdult.setText("");
			}
		}

		for (int i = 0; i < tbAdult.size(); i++) {
			tbAdult.get(i).setStyle("-fx-background-color : null");
			tbAdult.get(i).setStyle("-fx-border-color : null");
			tbAdult.get(i).setTextFill(Paint.valueOf("black"));
		}

		btn.setStyle("-fx-background-color : black");
		btn.setTextFill(Paint.valueOf("white"));

		if (count >= adultCount) {
			TicketingController.lbAdultPay.setText(TicketingController.selectTime.getS_AdultPay() * adultCount + "원");
			TicketingController.lbChildPay
					.setText(TicketingController.selectTime.getS_ChildPay() * (count - adultCount) + "원");
		} else {
			TicketingController.lbAdultPay.setText(TicketingController.selectTime.getS_AdultPay() * count + "원");
			TicketingController.lbChildPay.setText("0원");
		}
		
		if (count == totalCount && totalCount != 0) {
			TicketingController.btnPay.setDisable(false);
		} else {
			TicketingController.btnPay.setDisable(true);
		}

	}

	public void handlerBtnChild(ActionEvent event) {

		Button btn = (Button) event.getSource();
		childCheck = Integer.parseInt(btn.getId());

		total = adultCount + childCheck;
		if (total < count) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("좌석 선택");
			alert.setHeaderText("인원 초과");
			alert.setContentText("선택한 좌석이 예매 인원 보다 많습니다.");
			alert.showAndWait();
			return;
		}
		if (total > 8) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("좌석 선택");
			alert.setHeaderText("인원 초과");
			alert.setContentText("최대 예매 가능한 인원수는 8명 까지 입니다.");
			alert.showAndWait();
			return;
		} else {
			childCount = childCheck;
			totalCount = adultCount + childCount;
			if (childCount != 0) {
				TicketingController.lbChild.setText(" 청소년 " + childCount + "명");
			} else {
				TicketingController.lbChild.setText("");
			}
		}

		for (int i = 0; i < tbChild.size(); i++) {
			tbChild.get(i).setStyle("-fx-background-color : null");
			tbChild.get(i).setStyle("-fx-border-color : null");
			tbChild.get(i).setTextFill(Paint.valueOf("black"));
		}

		btn.setStyle("-fx-background-color : black");
		btn.setTextFill(Paint.valueOf("white"));
		
		if (count == totalCount && totalCount != 0) {
			TicketingController.btnPay.setDisable(false);
		} else {
			TicketingController.btnPay.setDisable(true);
		}

	}

	public void addPersonBtn() {

		for (int i = 0; i < 9; i++) {
			tbAdult.add(new Button(i + ""));
			tbAdult.get(i).setId(i + "");
			tbAdult.get(i).setOnAction(event -> handlerBtnAdult(event));
			if (checkGrade) {
				tbChild.add(new Button(i + ""));
				tbChild.get(i).setId(i + "");
				tbChild.get(i).setOnAction(event -> handlerBtnChild(event));
			}
		}
		hbAdult.getChildren().addAll(tbAdult);
		hbChild.getChildren().addAll(tbChild);
	}

	public void addSeatToggleBtn() {
		int hbCount = 0;
		int tbCount = 0;
		int i = 0;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineA.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineA.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 16; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineA.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineB.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineB.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 16; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineB.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineC.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineC.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 16; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineC.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineD.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineD.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 16; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineD.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineE.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineE.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 15; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineE.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineF.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineF.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 15; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineF.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;

		hbSeatList.add(new HBox());
		for (i = 1; i <= 4; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineG.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
		hbSeatList.add(new HBox());
		for (i = 5; i <= 12; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineG.getChildren().add(hbSeatList.get(hbCount));
		hbSeatList.add(new HBox());
		++hbCount;
		for (i = 13; i <= 16; i++) {
			tbSeatList.add(new ToggleButton(i + ""));
			tbSeatList.get(tbCount).setPrefSize(32, 32);
			tbSeatList.get(tbCount).setId(tbCount + "");
			seatNameList.add(i + "");
			tbSeatList.get(tbCount).setOnAction(event -> handlerBtnSeat(event));
			hbSeatList.get(hbCount).getChildren().add(tbSeatList.get(tbCount));
			++tbCount;
		}
		hbLineG.getChildren().add(hbSeatList.get(hbCount));
		++hbCount;
	}

	public void disableSeat() {
		SeatDAO sDao = new SeatDAO();

		ArrayList<Integer> list = sDao.checkLeftSeat(TicketingController.selectTime);

		for (int i = 0; i < list.size(); i++) {
			tbSeatList.get(list.get(i)).setDisable(true);
		}
	}

}
