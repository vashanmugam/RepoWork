package com.vmetry.TestUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vmetry.TestBase.Intialization;

public class UtilClass extends Intialization {
	
	public static FileInputStream xlfis = null;
	public static boolean runverify = false;
	public static boolean acceptNextAlert = false;

	public static boolean runModeVerify(String module) throws IOException {

		xlfis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com\\vmetry\\Excel\\Controller1.xls");

		HSSFWorkbook wb = new HSSFWorkbook(xlfis);
		System.out.println(wb.getSheetAt(0));

		HSSFSheet sht = wb.getSheetAt(0);

		int row = sht.getLastRowNum() + 1;
		int col = sht.getRow(0).getLastCellNum();

		System.out.println("Row number is: " + row);
		System.out.println("col number is: " + col);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(sht.getRow(i).getCell(j));
				if ((sht.getRow(i).getCell(j).getStringCellValue()).equalsIgnoreCase(module)) {
					if ((sht.getRow(i).getCell(1).getStringCellValue()).equalsIgnoreCase("Y")) {
						runverify = true;

					} else {
						runverify = false;
					}
				}

			}

		}
		wb.close();
		return runverify;
	}

	// Passing Test data from Excel to Data Provider
	public static String[][] getExcelData(String Sheetname) throws IOException {

		// Xls File Initialization for Input
		xlfis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com\\vmetry\\Excel\\InputData.xls");

		String[][] arrayExcelData = null;
		System.out.println("Getting input data from getexceldata");
		HSSFWorkbook wb = new HSSFWorkbook(xlfis);
		Sheet sh = wb.getSheet(Sheetname);

		int totalNoOfRows = sh.getLastRowNum() + 1;
		int totalNoOfCols = sh.getRow(1).getLastCellNum();

		System.out.println(totalNoOfRows);
		System.out.println(totalNoOfCols);

		arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

		for (int i = 1; i < totalNoOfRows; i++) {

			for (int j = 0; j < (totalNoOfCols); j++) {
				arrayExcelData[i - 1][j] = sh.getRow(i).getCell(j).getStringCellValue();

			}

		}
		wb.close();
		return arrayExcelData;
	}

	// Writing Result Excel file
	public static void writeExcelFile(String Sheetname) throws IOException {
		try {

			FileInputStream file = new FileInputStream(new File(
					System.getProperty("user.dir") + "\\src\\test\\resources\\com\\vmetry\\Excel\\OutputData.xls"));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(Sheetname);
			System.out.println("sheet" + sheet.toString());
			Cell cell = null;
			int row = sheet.getLastRowNum() + 1;
			int col = sheet.getRow(1).getLastCellNum();

			// added
			CellStyle style = workbook.createCellStyle();
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
			style.setRightBorderColor(HSSFColor.BLACK.index);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setTopBorderColor(HSSFColor.BLACK.index);
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			// CellStyle style1 = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font font2 = workbook.createFont();
			font2.setBoldweight((short) org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			font2.setFontHeightInPoints((short) 10);
			// font2.setFontName("Calibri");
			style.setFont(font2);

			Row rows = sheet.getRow(0);
			Cell cell1 = rows.createCell(col);
			cell1.setCellValue("Result[" + dateFormat.format(dt) + "]");
			cell1.setCellStyle(style);

			// sheet.getRow(0).createCell(col).setCellValue("Result["+dateFormat.format(date)+"]");
			for (int rownum = 1; rownum < row; rownum++) {
				for (Entry<String, String> e : map.entrySet()) {
					String key = e.getKey().toString();
					String value = e.getValue().toString();
					String tcId = sheet.getRow(rownum).getCell(0).getStringCellValue();
					Row row1 = sheet.getRow(rownum);
					cell = row1.getCell(col);
					if (cell == null) {
						cell = row1.createCell(col);
					}
					if (tcId.equals(key)) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(value);
						cell.setCellStyle(style);
						System.out.println("key::" + key + "value::" + value);
					}
				}
			}

			file.close();

			FileOutputStream outFile = new FileOutputStream(new File(
					System.getProperty("user.dir") + "\\src\\test\\resources\\com\\vmetry\\Excel\\OutputData.xls"));
			System.out.println("verifying output writting path:" + (("user.dir") + "\\Setup\\Automation_Result.xls"));
			System.out.println("o/p written");
			workbook.write(outFile);
			workbook.close();
			outFile.close();
			System.out.println("o/p file closed");
			outFile.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TO VERIFY IF ALERT IS PRESENT AND ACCEPT IF IT IS
	
	public static void alertchkandaccept() throws IOException{
	if (UtilClass.isAlertPresent() == true) {
		String altmsg = UtilClass.closeAlertAndGetItsText();
		System.out.println(altmsg);
	}
	}
	public static String closeAlertAndGetItsText() {
		try {
			Alert alert = wd.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public static boolean isAlertPresent() throws IOException {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(wd, 5 /* timeout in seconds */);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
			acceptNextAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;

	}


}
