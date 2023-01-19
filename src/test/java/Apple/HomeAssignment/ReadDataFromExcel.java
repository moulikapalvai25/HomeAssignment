package Apple.HomeAssignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataFromExcel {
	
	public static Object[][] readData(String sheetName){
	try {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\TestData.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(file);
		XSSFSheet sheet=book.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		int colCount=sheet.getRow(1).getLastCellNum()-1;
		int ci=0,cj=0;
		int startRow=1,startCol=1;
		Object [][]data=new Object[rowCount][colCount];
		Cell cell=null;
		Row row=null;
		for(int i=startRow;i<=rowCount;i++,ci++) {
			     row=sheet.getRow(i);
			     cj=0;
			for(int j=startCol;j<=colCount;j++,cj++) {
				 cell=row.getCell(j);
				 if(cell.getCellType()==0)
				 data[ci][cj]=cell.getNumericCellValue();
				 else if(cell.getCellType()==1)
			     data[ci][cj]=cell.getStringCellValue();
				
			}
		}		
		
		return data;		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;

	}
	public static void main(String[] args) {

		
		readData("keyvalues");
			
		
		
		
	}
}
