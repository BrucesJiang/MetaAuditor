package com.bruce.metaauditor.controller;

import com.bruce.metaauditor.dto.ItemInfo;
import com.bruce.metaauditor.service.HvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/19 21:21
 * @Version
 */
@Controller
public class ItemController {

    @Autowired
    private HvdService hvdService;

    @RequestMapping(value = "/hvd/audit")
    public boolean audit(HttpServletRequest request) {
        String[] args = request.getParameterValues("IDCheck");

        List<Integer> argList = new ArrayList<>();
        for(int i = 0; i < args.length; i ++) {
            argList.add(Integer.parseInt(args[i].trim()));
        }
        List<ItemInfo> list = hvdService.getHvds(argList);
        //检测结果

        return false;
    }

    @RequestMapping(value = "/hvd/allHvds")
    public List<ItemInfo> getAllHvds() {
        return hvdService.getAllHvds();
    }
}
