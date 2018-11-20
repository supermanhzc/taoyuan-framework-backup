package com.taoyuan.framework.mail;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.common.entity.TyVerificationCode;
import org.springframework.stereotype.Service;

@Service
public class TyVerificationCodeServiceImpl extends ServiceImpl<TyVerificationCodeMapper,TyVerificationCode> implements TyVerificationCodeService {
}
