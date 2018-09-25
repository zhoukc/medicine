package com.example.medicine.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.medicine.model.framework.llpay.config.PartnerConfig;
import com.example.medicine.model.framework.llpay.config.ServerURLConfig;
import com.example.medicine.model.framework.llpay.conn.HttpRequestSimple;
import com.example.medicine.model.framework.llpay.features.PayResultEnum;
import com.example.medicine.model.framework.llpay.utils.LLPayUtil;
import com.example.medicine.model.framework.llpay.vo.OrderInfo;
import com.example.medicine.model.framework.llpay.vo.PayDataBean;
import com.example.medicine.model.framework.llpay.vo.PaymentInfo;
import com.example.medicine.model.framework.llpay.vo.RetBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/pay")
@Slf4j
public class PaymentController {


    @PostMapping(value = "plainPay", produces = "application/json")
    public void doPay(HttpServletRequest request) {

        OrderInfo order = createOrder();
        //构建支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setVersion(PartnerConfig.VERSION);
        paymentInfo.setOid_partner(PartnerConfig.OID_PARTNER);
        paymentInfo.setUser_id(request.getParameter("user_id"));
        paymentInfo.setSign_type(PartnerConfig.SIGN_TYPE);
        paymentInfo.setBusi_partner(PartnerConfig.BUSI_PARTNER);
        paymentInfo.setNo_order(order.getNo_order());
        paymentInfo.setDt_order(order.getDt_order());
        paymentInfo.setName_goods(order.getName_goods());
        paymentInfo.setInfo_order(order.getInfo_order());
        paymentInfo.setMoney_order(order.getMoney_order());
        paymentInfo.setNotify_url(PartnerConfig.NOTIFY_URL);
        paymentInfo.setUrl_return(PartnerConfig.URL_RETURN);
        paymentInfo.setUserreq_ip(LLPayUtil.getIpAddr(request));
        paymentInfo.setUrl_order("");
        // 单位分钟，可以为空，默认7天
        paymentInfo.setValid_order("10080");
        paymentInfo.setTimestamp(LLPayUtil.getCurrentDateTimeStr());
        paymentInfo.setRisk_item(createRiskItem());
        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                        .toJSONString(paymentInfo)), PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        paymentInfo.setSign(sign);
        if (!LLPayUtil.isnull(request.getParameter("no_agree"))) {
            paymentInfo.setNo_agree(request.getParameter("no_agree"));
            paymentInfo.setBack_url("http://www.lianlianpay.com/");
        } else {
            // 从系统中获取用户身份信息
            paymentInfo.setId_type("0");
            paymentInfo.setId_no("410782198912151334");
            paymentInfo.setAcct_name("连连");
            paymentInfo.setFlag_modify("1");
            paymentInfo.setCard_no(request.getParameter("card_no"));
            paymentInfo.setBack_url("http://www.lianlianpay.com/");
        }

        HttpRequestSimple instance = HttpRequestSimple.getInstance();
        instance.postSendHttp(ServerURLConfig.PAY_URL, paymentInfo);
    }

    /**
     * 模拟商户创建订单
     */
    private OrderInfo createOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setNo_order(LLPayUtil.getCurrentDateTimeStr());
        orderInfo.setDt_order(LLPayUtil.getCurrentDateTimeStr());
        orderInfo.setMoney_order("0.1");
        orderInfo.setName_goods("话费充值");
        orderInfo.setInfo_order("用户购买" + "话费");
        return orderInfo;
    }

    /**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     *
     * @return
     */
    private String createRiskItem() {
        JSONObject riskItemObj = new JSONObject();
        riskItemObj.put("user_info_full_name", "你好");
        riskItemObj.put("frms_ware_category", "1999");
        return riskItemObj.toString();
    }

    /**
     * 连连支付的异步通知
     */
    @RequestMapping("llPayNotify")
    public Object llPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入连连支付异步通知数据接收处理");
        RetBean retBean = new RetBean();
        String result = null;
        try {
            result = LLPayUtil.readReqStr(request);

            if (LLPayUtil.isnull(result)) {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                return retBean;
            }

            log.info("接收连连支付异步通知数据：【" + result + "】");

            if (!LLPayUtil.checkSign(result, PartnerConfig.YT_PUB_KEY,
                    PartnerConfig.MD5_KEY)) {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                log.info("支付异步通知验签失败");
                return retBean;
            }
        } catch (IOException e) {
            log.info("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            return retBean;
        }

        log.info("支付异步通知数据接收处理成功");
        // 解析异步通知对象
        PayDataBean payDataBean = JSON.parseObject(result, PayDataBean.class);
        //todo 更新订单状态 等一系列后续处理
        if (PayResultEnum.SUCCESS.getCode().equalsIgnoreCase(payDataBean.getResult_pay())) {
            //商户系统订单号
            String out_trade_no = payDataBean.getNo_order();
            //连连支付交易号
            String trade_no = payDataBean.getOid_paybill();
            //金额
            String total_amount = payDataBean.getMoney_order();

            log.info("进行订单后续处理");

            retBean.setRet_code("0000");
            retBean.setRet_msg("交易成功");
        } else {
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");

        }


        return retBean;
    }

    /**
     * 获取银行卡信息
     *
     * @param cardNo
     * @return
     */
    @PostMapping(value = "getCardBin", produces = "application/json")
    public String getCardBin(@Validated @RequestBody String cardNo) {
        JSONObject reqObj = new JSONObject();
        reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
        reqObj.put("card_no", cardNo);
        reqObj.put("sign_type", PartnerConfig.SIGN_TYPE);
        String sign = LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();
        log.info("银行卡卡bin信息查询请求报文[" + reqJSON + "]");
        String resJSON = HttpRequestSimple.getInstance().postSendHttp(
                ServerURLConfig.QUERY_BANKCARD_URL, reqJSON);
        log.info("银行卡卡bin信息查询响应报文[" + resJSON + "]");
        return reqJSON;
    }

    /**
     * 用户已绑定银行列表查询
     *
     * @return
     */
    @PostMapping(value = "getBankcardList", produces = "application/json")
    public String getBankcardList() {
        JSONObject reqObj = new JSONObject();
        reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
        reqObj.put("user_id", "商户系统中用户的id");
        reqObj.put("offset", "0");
        reqObj.put("sign_type", PartnerConfig.SIGN_TYPE);
        String sign = LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();
        log.info("用户已绑定银行列表查询请求报文[" + reqJSON + "]");
        String resJSON = HttpRequestSimple.getInstance().postSendHttp(
                ServerURLConfig.QUERY_USER_BANKCARD_URL, reqJSON);
        log.info("用户已绑定银行列表查询响应报文[" + resJSON + "]");
        return resJSON;
    }
}
