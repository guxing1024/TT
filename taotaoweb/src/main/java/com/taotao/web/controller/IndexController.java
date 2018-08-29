package com.taotao.web.controller;

import com.taotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author
 * @Description //TODO 首页$
 * @time 23:09
 */
@RequestMapping("index")
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        String indexAD1 = indexService.queryIndexAD1();
        mv.addObject("indexAD1", indexAD1);
        return mv;
    }
}
