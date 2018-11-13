package com.taoyuan.framework.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taoyuan.framework.common.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class TyPageUtil {
    private static final String PAGE_INDEX = "pageIndex";

    private static final String PAGE_SIZE = "pageSize";

    public static Page getPage(Map map) {
        Page page = new Page();
        if (map.containsKey(PAGE_INDEX)) {
            long pageIndex = Long.valueOf(map.get(PAGE_INDEX).toString());
            page.setCurrent(pageIndex);

            if (map.containsKey(PAGE_SIZE)) {
                page.setSize(Long.valueOf(map.get(PAGE_SIZE).toString()));
            } else {
                throw new ValidateException(10000001, "分页参数不能为空。", PAGE_SIZE);
            }
        } else {
            throw new ValidateException(10000001, "分页参数不能为空。", PAGE_INDEX);
        }

        return page;
    }
}
