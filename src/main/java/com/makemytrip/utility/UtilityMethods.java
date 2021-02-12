package com.makemytrip.utility;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class UtilityMethods extends PageElements{

	// This method will accepts an excel file,sheet and a data pointer and will give you that particular row number 
	public static int getRowNumber(String excelFile, String excelSheet, String dataPointer)throws Exception{
		int rowNumber = -1;
		File file = new File(excelFile);
		if(file.exists()){		
			InputStream isr = new FileInputStream(file);
			XSSFWorkbook workBook = new XSSFWorkbook(isr);
			XSSFSheet sheet = workBook.getSheet(excelSheet);
			Row row = sheet.getRow(0);
			String cellData=null;
			int numOfRows = sheet.getLastRowNum();
			//System.out.println("Number Of Rows in Excel :" + numOfRows);
			for(int rowNum = 0 ; rowNum < (numOfRows+1) ; rowNum++){
				row = sheet.getRow(rowNum);
				for(int colNum = 0 ; colNum < row.getLastCellNum() ; colNum++){
					Cell cell = row.getCell(colNum); // cell parameter value should be dynamic
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell == null){
						cellData = "";
					}else if(cell.getCellType() == cell.CELL_TYPE_BLANK){
						cellData = "";
					}else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
						cellData = NumberToTextConverter.toText(cell.getNumericCellValue());
					}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
						cellData = cell.getStringCellValue();
					}
					/*System.out.println("Cell Data : "+cellData);
					System.out.println("Result : "+ cell.getStringCellValue().equalsIgnoreCase(dataPointer));*/
					if(cell.getStringCellValue().equalsIgnoreCase(dataPointer))
						rowNumber = rowNum;				
					if(rowNumber != -1)
						break;
				}
			}
		}
		//System.out.println("Row Number of "+dataPointer+" is : "+rowNumber);
		return rowNumber;
	}

	// This method will accepts an excel file and sheet and a column name and will give you that particular column number
	public static int getColumnNumber(String excelFile, String excelSheet, String columnName)throws Exception{		
		/*System.out.println("********** get col num**********");
		System.out.println("Excel File : "+excelFile);
		System.out.println("Excel Sheet : "+excelSheet);
		System.out.println("Column name : "+columnName);*/
		int colNumber = -1;
		File file = new File(excelFile);
		/*System.out.println("Excel Path : "+file.getAbsolutePath());
		System.out.println("FIle Exists? :"+file.exists());*/
		if(file.exists()){		
			//System.out.println("File Existed");
			InputStream isr = new FileInputStream(file);
			XSSFWorkbook workBook = new XSSFWorkbook(isr);
			/*System.out.println("Work Book : "+workBook.getClass());
			System.out.println("No. Of Sheets : "+workBook.getNumberOfSheets());
			System.out.println("Sheet Names :");
			for(int i = 0 ; i <workBook.getNumberOfSheets() ; i++){
				System.out.println(workBook.getSheetName(i));
			}*/
			XSSFSheet sheet = workBook.getSheet(excelSheet);
			
			for(int rowNum = 0 ; rowNum < (sheet.getLastRowNum()+1) ; rowNum++){
				Row row = sheet.getRow(rowNum); // parameter value should be dynamic
				String cellData=null;
				for(int colNum = 0 ; colNum < sheet.getRow(rowNum).getLastCellNum() ; colNum++){
					Cell cell = row.getCell(colNum);
					//System.out.println("Cell Data : "+cell.getStringCellValue());
					if(cell == null){
						cellData = "";
					}else if(cell.getCellType() == cell.CELL_TYPE_BLANK){
						cellData = "";
					}else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
						cellData = NumberToTextConverter.toText(cell.getNumericCellValue());
					}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
						cellData = cell.getStringCellValue();
					}//System.out.println("Column Name : "+cell.getStringCellValue());
					if(cell.getStringCellValue().trim().equalsIgnoreCase(columnName))
						colNumber = colNum;
					if(colNumber != -1)
						break;
				}
			}
		}
		//System.out.println("Column Number of "+columnName+" is : "+colNumber);
		return colNumber;
	}

	// This method will accepts an excel file , sheet , row Number and a column name and will give you that particular cell data 
	public static String readExcelCellData(String excelFile,String excelSheet,String dataPointer,String columnName) throws Exception{
		String cellData= "";
			
		// get the columns count
		int colNum = getColumnNumber(excelFile, excelSheet, columnName);
		int rowNumber = getRowNumber(excelFile, excelSheet, dataPointer);
		
		if(colNum> -1 && rowNumber > -1){
			File file = new File(excelFile);
			if(file.exists()){
				InputStream myXlsx = new FileInputStream(excelFile);
				XSSFWorkbook workBook = new XSSFWorkbook(myXlsx);
				XSSFSheet sheet = workBook.getSheet(excelSheet);
				Row row = sheet.getRow(rowNumber);
				Cell cell = row.getCell(colNum);
				if(cell == null){
					cellData = "";
				}else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
					cellData = NumberToTextConverter.toText(cell.getNumericCellValue());
				}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
					cellData = cell.getStringCellValue();
				}else if(cell.getCellType() == cell.CELL_TYPE_BLANK){
					cellData = "";
				}
				myXlsx.close();
			}
		}else {
			cellData = "";
		}
		//System.out.println("Cell Data : "+cellData+" ,  Row Number : "+rowNumber+" and Column Number = "+colNum);
		//System.out.println("CellData : "+cellData);
		return cellData;
	}

	// This method will accepts web driver and screen shot file name and captures a screen shot
	public static void capctureScreenShot(WebDriver driver, String imageName, String imageMessage){

		/*System.out.println("Entered Into Capcture Screen");

		System.out.println("Directory : "+System.getProperty("user.dir"));*/
		try {
			File src= ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
			File file = new File(PageElements.screensPath+"\\"+imageName+".png");
			if(file.exists()){
				file.delete();
			}
			PageElements.pdfReportMap.put(file, imageMessage);
			FileUtils.copyFile(src, file);

		}catch(Exception e ){
			System.out.println("Exception occured while capcturing screenshot");
			e.printStackTrace();
		}
		//System.out.println("Done With  Capcture Screen");
	}

	public static void generatePdfReport(String reportFileName, Font font){
		try{
			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, new FileOutputStream(PageElements.testResultPath+"\\"+reportFileName+".pdf"));
			document.open();

			//System.out.println("No. Of Screens Stored : "+PageElements.pdfReportMap.size());

			for(Entry<File, String> entry:PageElements.pdfReportMap.entrySet()){

				/*System.out.println(entry.getKey());
				System.out.println(entry.getValue());*/

				Image image = Image.getInstance(entry.getKey().getAbsolutePath());
				image.setAbsolutePosition(0, 0);
				document.setPageSize(image);
				document.newPage();
				//Font font = new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.BLUE);
				Paragraph para = new Paragraph(entry.getValue(),font);
				para.setAlignment(Paragraph.ALIGN_TOP);
				para.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(para);
				document.add(image);
			}


			document.close();
		}catch (Exception e) {
			System.out.println("Exception Occured while preparing PDF report");
			System.out.println(e);
		}
	}

	// This method will accepts an excel file and sheet and gives us number of columns
	public int getNumberOfColumns(String excelFile,String excelSheet) throws Exception{

				
		int numOfColumns = -1;
		File file = new File(excelFile);
		if(file.exists()){
			InputStream isr = new FileInputStream(excelFile);
			XSSFWorkbook workBook = new XSSFWorkbook(isr);
			XSSFSheet sheet = workBook.getSheet(excelSheet);
			//int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			System.out.println("Last Row Number : "+lastRow);
			int lastColumn;
			for(int i = 0 ; i < (lastRow+1) ; i++){
				Row row = sheet.getRow(i);
				//System.out.print("Row : "+i+" ");
				lastColumn = row.getLastCellNum();
				//System.out.println("Last Column : "+lastColumn+" ");
				if(lastColumn > numOfColumns){
					numOfColumns = lastColumn;
					/*System.out.println("No Of COlumns : "+numOfColumns);
					System.out.println("********************");*/
				}
			}
		}
		return numOfColumns;
	}

	// This method will copy data from one excel to another excel file
	public void copyDataFromExcelToExcel(String sourceExcel,String sourceSheet,String destExcel, String destSheet)throws Exception{
		//System.out.println("Copy Method");
		int numOfRows = -1,numOfColumns = -1;

		// code to read data from excel and store in String[][]
		String excellData[][] = null;
		File sourceFile = new File(sourceExcel);
		if(sourceFile.exists()){
			InputStream isr = new FileInputStream(sourceFile);
			XSSFWorkbook workBook = new XSSFWorkbook(isr);
			XSSFSheet sheet = workBook.getSheet(sourceSheet);
			numOfRows = sheet.getPhysicalNumberOfRows();
			numOfColumns = getNumberOfColumns(sourceExcel, sourceSheet);
			//System.out.println("Rows : "+numOfRows+" , Columns : "+numOfColumns);
			excellData = new String[numOfRows][numOfColumns];
			for(int i = 0 ; i < numOfRows ; i++){
				Row row = sheet.getRow(i);
				for(int j = 0 ; j < numOfColumns ; j++){
					//System.out.println(row.getCell(j).toString());
					excellData[i][j] = row.getCell(j).toString();
				}
				//System.out.println();
			}
		}

		// code to store data into an excel from String[][]
		File destFile = new File(destExcel);
		destFile.createNewFile();
		if(destFile.exists()){
			OutputStream isr = new FileOutputStream(destExcel);
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet();
			//System.out.println("Rows : "+numOfRows+" , Columns : "+numOfColumns);
			for(int i = 0 ; i < numOfRows ; i++){
				Row row = sheet.createRow(i);
				for(int j = 0 ; j < numOfColumns ; j++){
					Cell cell = row.createCell(j);
					cell.setCellValue(excellData[i][j]);
				}
			}
			FileOutputStream fos = new FileOutputStream(destFile);
			workBook.write(fos);
			fos.close();
			System.out.println("Successfully Copied");
		}
	}

	// method to load the configuration
	public void loadConfiguration() throws Exception{
		//System.out.println("Load Configuration Method");
		String excelFile = PageElements.defaultSheetPath;
		String excelSheet = PageElements.defaultSheetName;
		/*System.out.println("Excel File : "+excelFile);
		System.out.println("Excel Sheet : "+excelSheet);*/
		File tempFile = new File(excelFile);

		if(tempFile.exists()){
			System.out.println("File Exists");
			PageElements.chromeDriverPath = readExcelCellData(excelFile, excelSheet, "ChromeDriverPath", "Config_Value");
			PageElements.mozillaDriverPath = readExcelCellData(excelFile, excelSheet, "MozillaDriverPath", "Config_Value");
			PageElements.ieDriverPath = readExcelCellData(excelFile, excelSheet, "IEDriverPath", "Config_Value");
			PageElements.appUrl = readExcelCellData(excelFile, excelSheet, "AppUrl", "Config_Value");
			PageElements.screensPath = readExcelCellData(excelFile, excelSheet, "ScreensPath", "Config_Value");
			PageElements.testResultPath = readExcelCellData(excelFile, excelSheet, "TestReportsPath", "Config_Value");
			PageElements.testBrowser = readExcelCellData(excelFile, excelSheet, "TestBrowser", "Config_Value");

			/*System.out.println("Chrome : "+chromeDriverPath);
			System.out.println("url : "+appUrl);
			System.out.println("Screen Path : "+screensPath);*/
		}else{
			System.out.println("Configuration file does not exist");
		}
	}

	// method to close all child windows except parent
	public void closeAllChildWindows(WebDriver driver){

		String parent = driver.getWindowHandle();
		Set<String> childs = driver.getWindowHandles();
		Iterator<String> itr = childs.iterator();
		while(itr.hasNext()){
			String child = itr.next();
			if(!parent.equals(child)){
				driver.switchTo().window(child);
				driver.close();
			}
			driver.switchTo().window(parent);
		}

	}
	
	public static boolean SelectValueFromDropdown(String excelFile, String excelSheet,String dataPointer,String columnName,String optionsLocation) throws Exception{
		boolean status = false;
		String excelValue = UtilityMethods.readExcelCellData(excelFile, excelSheet, dataPointer, columnName);
		List<WebElement> allElements = driver.findElements(By.xpath(PageElements.fromCity_Options));
		ListIterator<WebElement> itr = allElements.listIterator();
		while(itr.hasNext()){
			System.out.println(itr.next().getText());
			String dropDownValue = itr.next().getText();
			if(dropDownValue.contains(excelValue)){
				itr.previous().click();
			}
		}
		return status;	
	}
}