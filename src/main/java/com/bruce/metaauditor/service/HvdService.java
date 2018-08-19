package com.bruce.metaauditor.service;

import com.bruce.metaauditor.dto.ItemInfo;

import java.util.List;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/19 20:51
 * @Version
 */
public interface HvdService {
    public void addHvd(ItemInfo itemInfo);

    public List<ItemInfo> getHvds(List<Integer> list);

    public ItemInfo getHvd(int id);

    public int getTotal();

    public List<ItemInfo> getAllHvds();
}
