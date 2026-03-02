package dataprovider;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExcelUtil;

public class loginDP {
	@DataProvider
	public Object[][] getLoginData() throws IOException{
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/testData/LoginData.xlsx";
		int row = ExcelUtil.getRows(excelPath, "testdata");
		int cols = ExcelUtil.getCols(excelPath, "testdata");
		
		Object[][] data = new Object[row][cols];
		for(int i=1; i<=row; i++) {
			data[i-1][0] = ExcelUtil.getCellData(excelPath, "testdata", i, 0);
			data[i-1][1] = ExcelUtil.getCellData(excelPath, "testdata", i, 1);
			data[i-1][2] = ExcelUtil.getCellData(excelPath, "testdata", i, 2);
			data[i-1][3] = i;
		}
		return data;
	}
}
