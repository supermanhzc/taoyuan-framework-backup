package com.taoyuan.framework.aaa.entity;

import lombok.Data;

import java.util.List;

@Data
public class TyMenu {

    private String path;
    private String name;
    private String component;
    private List<TyMenu> children;
}
