package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.StatisticsVO;

public class StatisticsExel {

	public boolean exelWriter(List<StatisticsVO> list, String saveDir) {
		// 워크북 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 워크 시트 생성
		XSSFSheet sheet = workbook.createSheet();
		// 행 생성
		XSSFRow row = sheet.createRow(0);
		// 셀 생성
		XSSFCell cell;
		// 헤더 정보 구성
		cell = row.createCell(0);
		cell.setCellValue("순위");
		cell = row.createCell(1);
		cell.setCellValue("영화명");
		cell = row.createCell(2);
		cell.setCellValue("개봉일");
		cell = row.createCell(3);
		cell.setCellValue("매출액");
		cell = row.createCell(4);
		cell.setCellValue("관객수");

		int sumSales = 0;
		int sumAttendance = 0;

		StatisticsVO vo;
		for (int i = 0; i < list.size(); i++) {
			vo = list.get(i);

			// 행 생성
			row = sheet.createRow(i + 1);

			cell = row.createCell(0);
			cell.setCellValue(vo.getRank());
			cell = row.createCell(1);
			cell.setCellValue(vo.getTitle());
			cell = row.createCell(2);
			cell.setCellValue(vo.getOpenDate());
			cell = row.createCell(3);
			cell.setCellValue(vo.getSales());
			cell = row.createCell(4);
			cell.setCellValue(vo.getAttendance());

			sumSales += Integer.parseInt(vo.getSales().replaceAll(",", ""));
			sumAttendance += Integer.parseInt(vo.getAttendance().replaceAll(",", ""));
		}

		row = sheet.createRow(list.size() + 2);
		cell = row.createCell(0);
		cell.setCellValue("총 매출");
		cell = row.createCell(1);
		cell.setCellValue(StatisticsController.numFormat(sumSales));
		row = sheet.createRow(list.size() + 3);
		cell = row.createCell(0);
		cell.setCellValue("총 관객수");
		cell = row.createCell(1);
		cell.setCellValue(StatisticsController.numFormat(sumAttendance));

		FileOutputStream fos = null;
		File file = new File(saveDir);

		boolean success = false;

		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				workbook.write(fos);
				success = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}
}
