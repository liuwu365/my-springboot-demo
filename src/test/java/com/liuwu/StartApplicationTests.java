package com.liuwu;

import com.google.gson.Gson;
import com.liuwu.biz.UserService;
import com.liuwu.entity.Message;
import com.liuwu.entity.User;
import com.liuwu.mq.MQReceive;
import com.liuwu.mq.MQSend;
import com.liuwu.mq.MqMessage;
import com.liuwu.notify.NotifyUserInfo;
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
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = StartApplication.class)
@ContextConfiguration(locations = {
        "classpath:application-context.xml",
        "classpath*:application-dao.xml"
})
public class StartApplicationTests {
    private static final Gson gson = new Gson();
    @Autowired
    private MQSend mqSend;
    @Autowired
    private MQReceive mqReceive;
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

    @Test
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

    }




}
