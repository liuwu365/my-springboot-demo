package com.liuwu;

import com.liuwu.biz.UserService;
import com.liuwu.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = StartApplication.class)
@ContextConfiguration(locations = {
        "classpath:application-context.xml",
        "classpath*:application-dao.xml"
})
public class StartApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void excelTest() {
        try {
            List<User> userList = userService.getAllUsers();
            ExportParams params = new ExportParams();
            params.setType(ExcelType.XSSF);
            Workbook workbook = ExcelExportUtil.exportExcel(params, User.class, userList);
            //String nowFileName = CommonUtil.createFile("E:/");
            //String fileName = nowFileName + "//" + DateUtil.now() + ".xls";

            OutputStream out = new FileOutputStream("E://liuwu_1.xlsx");
            workbook.write(out);
            System.out.println("执行完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
