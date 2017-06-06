package com.liuwu.Helper;

import com.google.gson.Gson;
import com.liuwu.constant.Constant;
import com.liuwu.entity.Result;
import com.liuwu.util.CacheUtil;
import com.liuwu.util.CheckUtil;
import com.liuwu.util.IpUtil;
import com.liuwu.util.RateLimiterWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: 通用辅助类
 * @User: liuwu_eva@163.com
 * @Date: 2017-06-02 下午 6:12
 */
public class CommonHelper {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(CommonHelper.class);
    private static CacheUtil cacheUtil = CacheUtil.getInstance();

    static RateLimiterWrap rateLimiterWrap = new RateLimiterWrap(1, 1000 * 60 * 60 * 2, 5, 10);

    /**
     * 方法请求的频率检测
     *
     * @param userId
     * @param request
     * @return
     */
    public static Result checkHz(int userId, HttpServletRequest request) {
        //是否在黑名单中
        boolean target = CommonHelper.checkBackList(IpUtil.getIp());
        if (target) {
            return new Result(401, Constant.BLACK_LIST_TIP);
        }
        //频率检测
        String tip = CommonHelper.rateLimiter(userId, request);
        if (!CheckUtil.isEmpty(tip)) {
            return new Result(402, tip);
        }
        return new Result(200, "normal");
    }

    /**
     * 方法请求的频率检测
     *
     * @param userId  没有传-1
     * @param request 请求（记录日志用，追踪行为）
     * @return 返回提示内容
     */
    private static String rateLimiter(int userId, HttpServletRequest request) {
        String text = "";
        String ip = IpUtil.getIp();
        boolean[] ss = rateLimiterWrap.tryAcquire();
        logger.info("请求是否频繁:{}", gson.toJson(ss));
        if (ss[0] == false && ss[1] == false) {
            logger.error("访问过频繁，本次命令:{}， 将拉黑其IP", request.getRequestURI());
            text = "<span style='font-size:16px;color:red;'>对不起大家，由于我发消息太频繁，已经被服务器拉黑了，大家珍重</span>";
            cacheUtil.hset(Constant.BLACK_LIST, userId == -1 ? ip : String.valueOf(userId), ip); //加入黑名单
            return text;
        } else if (ss[0] == false && ss[1] == true) {
            logger.error("访问过频繁，本次命令:{}，将警告一次", request.getRequestURI());
            int warnCount = rateLimiterWrap.getWarnCount().get();
            int maxWarnCount = rateLimiterWrap.getMaxWarnCount();
            int residue = maxWarnCount - warnCount;
            text = "<div style='font-size:16px;color:chocolate;'>第" + warnCount + "次警告【" + ip + "】，还剩" + (residue + 1) + "次警告机会</div>";
            return text;
        }
        return text;
    }

    /**
     * 检测是否已在黑名单中
     */
    private static boolean checkBackList(String ip) {
        boolean target = false;
        Map<String, String> cacheMap = cacheUtil.hgetAll(Constant.BLACK_LIST);
        if (!CheckUtil.isEmpty(cacheMap)) {
            for (String key : cacheMap.keySet()) {
                if (ip.equals(cacheMap.get(key))) {
                    target = true;
                    break;
                }
            }
        }
        return target;
    }


}
