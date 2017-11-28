package com.liuwu;

import com.google.gson.Gson;
import com.liuwu.entity.PrizeBonus;
import com.liuwu.entity.User;
import com.liuwu.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//import com.liuwu.mq.MQReceive;
//import com.liuwu.mq.MQSend;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = StartApplication.class)
@ContextConfiguration(locations = {
        "classpath:application-context.xml",
        "classpath*:application-dao.xml"
})
public class StartApplicationTests {
    private static final Gson gson = new Gson();
    /*@Autowired
    private MQSend mqSend;
    @Autowired
    private MQReceive mqReceive;*/
    @Autowired
    private UserService userService;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r);
        t.setName("Processor");
        return t;
    });

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

    @Test
    public void testExcelImport() {
        try {
            //MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //MultipartFile file = multipartRequest.getFile("importExcelFile");
            //if (file.isEmpty()) {
            //    System.out.println("文件是空的");
            //}
            ImportParams params = new ImportParams();
            params.setHeadRows(1);
            params.setNeedSave(true);
            //String path = request.getSession().getServletContext().getRealPath("");
            //String path = "E:";
            //File f = new File(path + "/excel/" + file.getOriginalFilename());
            File f = new File("E://excel/红利发放.xls");
            //if (!f.exists()) {
            //    File dir = new File(path + "/excel/");
            //    dir.mkdirs();
            //    if (f.createNewFile()) {
            //        System.out.println("创建文件成功");
            //    } else {
            //        System.out.println("创建文件失败");
            //    }
            //}
            //file.transferTo(f);
            List<PrizeBonus> list = ExcelImportUtil.importExcel(f, PrizeBonus.class, params);
            System.out.println("输出值:" + gson.toJson(list));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void mqTest() {
        try {
            NotifyUserInfo userInfo = new NotifyUserInfo();
            User user = userService.getUserById(1);
            userInfo.setMsg("发消息了");
            userInfo.setUser(user);
            mqSend.sendNotifyMessage(user, 1l);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/


}
