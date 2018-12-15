package com.taoyuan.framework.bs.service.verificationcode.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.bs.mapper.verificationcode.TyVerificationCodeMapper;
import com.taoyuan.framework.bs.service.TyBaseServiceImpl;
import com.taoyuan.framework.bs.service.verificationcode.TyVerificationCodeService;
import com.taoyuan.framework.common.entity.TyVerificationCode;
import org.springframework.stereotype.Service;

@Service
public class TyVerificationCodeServiceImpl extends TyBaseServiceImpl<TyVerificationCodeMapper,TyVerificationCode> implements TyVerificationCodeService {
}
