package com.taoyuan.framework.bs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taoyuan.framework.common.entity.TyPageEntity;
import com.taoyuan.framework.common.exception.ValidateException;
import com.taoyuan.framework.common.http.TySession;
import com.taoyuan.framework.common.util.TyIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
public class TyBaseController {

    /**
     * 获取分页参数，必填
     *
     * @param pageEntity
     * @return
     */
    public Page getPage(TyPageEntity pageEntity) {
        Page page = new Page();
        if (null == pageEntity) {
            return page;
        }

        if (null != pageEntity.getPegeIndex()) {
            page.setCurrent(pageEntity.getPegeIndex());
            if (null != pageEntity.getPageSize()) {
                page.setSize(Long.valueOf(pageEntity.getPageSize()));
            } else {
                throw new ValidateException("分页参数pageSize缺失。");
            }
        } else if (null != pageEntity.getPageSize()) {
            throw new ValidateException("分页参数paseIndex缺失。");
        }

        return page;
    }

    /**
     * 获取当前用户ID
     * @return
     */
    public Long getCurrentUserId() {
        log.info("current user id:{}", TySession.getCurrentUser().getUserId());
        return TySession.getCurrentUser().getUserId();
    }

    /**
     * 获取当前用户名称
     * @return
     */
    public String getCurrentUserName() {
        return TySession.getCurrentUser().getName();
    }

    /**
     * 获取当前用户IP
     * @return
     */
    public String getCurrentIp() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return TyIpUtil.getIpAddr(request);
    }

    public Date getCurrentDate(){
        return new Date();
    }
}
