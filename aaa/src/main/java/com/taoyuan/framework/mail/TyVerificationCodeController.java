package com.taoyuan.framework.mail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.common.entity.TyVerificationCode;
import com.taoyuan.framework.common.exception.TyBusinessException;
import com.taoyuan.framework.common.exception.ValidateException;
import com.taoyuan.framework.common.util.TyDateUtils;
import com.taoyuan.framework.common.util.TyRandomUtil;
import com.taoyuan.framework.mail.config.AppConfig;
import com.taoyuan.framework.mail.lib.MessageSend;
import com.taoyuan.framework.mail.utils.ConfigLoader;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class TyVerificationCodeController {

    @Autowired
    private TyVerificationCodeService verificationCodeService;

    @RequestMapping(value = "/verificationcode/generate", method = RequestMethod.POST)
    public TyResponse getMailVerificationCode(@RequestBody TyVerificationCode tyVerificationCode) {
        String dest = tyVerificationCode.getDest();
        if (StringUtils.isEmpty(dest)) {
            throw new ValidateException("手机号码不能为空。");
        }
        int type = tyVerificationCode.getType();
        if (type != 1 && type != 2 && type != 3) {
            throw new ValidateException("type非法[1:注册，2:找回密码，3:兑奖]。");
        }

        AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
        MessageSend submail = new MessageSend(config);
        submail.addTo(dest);
        String vCode = TyRandomUtil.getRandomStr(6);
        submail.addContent("【88】尊敬的会员您好！您的验证码是" + vCode + ",请在1分钟内输入验证。");
        String rturnMsg = submail.send();
        if (!rturnMsg.contains("success")) {
            throw new TyBusinessException(200001, "发送验证码失败。");
        }

        tyVerificationCode.setVCode(vCode);
        if (StringUtils.isEmpty(tyVerificationCode.getInfName())) {
            //默认为短信方式
            tyVerificationCode.setInfName("短信");
        }

        verificationCodeService.saveOrUpdate(tyVerificationCode);
        return new TySuccessResponse(true);
    }

    @RequestMapping(value = "/verificationcode/validate", method = RequestMethod.POST)
    public TyResponse validateVerificationCode(@RequestBody TyVerificationCode tyVerificationCode) {
        String dest = tyVerificationCode.getDest();
        if (StringUtils.isEmpty(dest)) {
            throw new ValidateException("手机号码不能为空。");
        }

        int type = tyVerificationCode.getType();
        if (type != 1 && type != 2 && type != 3) {
            throw new ValidateException("type非法[1:注册，2:找回密码，3:兑奖]。");
        }

        Date date = TyDateUtils.getDateAfterMinutes(-1);

        QueryWrapper<TyVerificationCode> wrapper = new QueryWrapper<TyVerificationCode>();
        wrapper.lambda().eq(TyVerificationCode::getDest, dest).eq(TyVerificationCode::getType, type).eq(TyVerificationCode::getVCode, tyVerificationCode.getVCode()).between(TyVerificationCode::getTime, date, Calendar.getInstance().getTime());

        List<TyVerificationCode> vCodeList = verificationCodeService.list(wrapper);
        if (CollectionUtils.isEmpty(vCodeList)) {
            throw new ValidateException("验证码错误。");
        }

        return new TySuccessResponse(true);
    }

    public static void main(String[] args) {
//        for(int i =0;i<10; i++) {
//            System.out.println(new Random().nextInt(899999) + 100000);
//        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            System.out.println(TyRandomUtil.getRandomStr(6));
        }
    }

}
