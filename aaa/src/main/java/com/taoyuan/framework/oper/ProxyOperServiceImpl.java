package com.taoyuan.framework.oper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.common.entity.TyProxyOperation;
import org.springframework.stereotype.Service;

@Service
public class ProxyOperServiceImpl extends ServiceImpl<ProxyOperMapper, TyProxyOperation> implements IProxyOperService {
}
