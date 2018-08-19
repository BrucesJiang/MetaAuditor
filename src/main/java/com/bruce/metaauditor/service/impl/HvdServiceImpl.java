package com.bruce.metaauditor.service.impl;

import com.bruce.metaauditor.contract.HVDAuditor;
import com.bruce.metaauditor.dto.ItemInfo;
import com.bruce.metaauditor.service.HvdService;
import org.springframework.stereotype.Service;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/19 20:53
 * @Version
 */
@Service
public class HvdServiceImpl implements HvdService{
    private HVDAuditor hvdAuditor;
    @Override
    public void addHvd(ItemInfo itemInfo) {
        hvdAuditor.addHVD(itemInfo.getUsername(), itemInfo.getHvd(), itemInfo.getSalt(), itemInfo.getSize());
    }

    @Override
    public List<ItemInfo> getHvds(List<Integer> list) {
        List<ItemInfo> hvdList = new ArrayList<>();
        for(int i = 0; i < list.size(); i ++) {
            hvdList.add(getHvd(list.get(i)));
        }
        return hvdList;
    }

    @Override
    public ItemInfo getHvd(int id) {
        BigInteger idx = new BigInteger(new Integer(id).toString());
        ItemInfo itemInfo = new ItemInfo();
        try{
            Tuple3<String, String, String> tuple3 = hvdAuditor.getHvdAndSalt(idx).send();
            itemInfo.setIndex(id);
            itemInfo.setHvd(tuple3.getValue1());
            itemInfo.setSalt(tuple3.getValue2());
            itemInfo.setSize(tuple3.getValue3());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return itemInfo;
    }

    @Override
    public List<ItemInfo> getAllHvds() {
        //List<ItemInfo> list = new ArrayList<>();

        //int total = getTotal();

        //for(int i = 0; i < total;  i++ ) {
        //    list.add(getHvd(i));
        //}
        return data();
    }

    @Override
    public int getTotal() {
        try{
            return hvdAuditor.returnTotal().send().intValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static List<ItemInfo> data() {
        List<ItemInfo> list = new ArrayList<>();

        Random random = new Random();
        ItemInfo info = null;
        for(int i = 0; i < 10; i++) {
            info = new ItemInfo();
            info.setHvd("haha");
            info.setIndex(i);
            info.setSalt(random.nextInt() + "");
            info.setSize(random.nextInt() + "");
            list.add(info);
        }
        return list;
    }
}
