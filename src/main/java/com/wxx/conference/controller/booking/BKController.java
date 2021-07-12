package com.wxx.conference.controller.booking;


import com.wxx.conference.model.booking.bookingRegion;
import com.wxx.conference.service.booking.BKService;
import com.wxx.conference.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2017-8-14.
 */
@RestController
@RequestMapping(value = "booking")
public class BKController {

    @Autowired
    private BKService service;

    @RequestMapping(value = "enter")
    public ModelAndView bookingEnter(){
        ModelAndView mv = new ModelAndView();
//        mv.setViewName("boardRoomManagement/boardRoomAdd");
        mv.setViewName("boardRoomManagement/boardRoomSearch");
        return mv;
    }
    @ResponseBody
    @RequestMapping(value="checkRegion/{floor}")
    public List checkRegion(@PathVariable(value = "floor") String floor){

//        ModelAndView mv = new ModelAndView();
        List regionList = service.getRegionList(floor);
//        mv.addObject("regionList", regionList);
//        mv.setViewName("booking/booking");
        return regionList;

    }
    @ResponseBody
    @RequestMapping(value = "initDual/{date}/{regionId}")
    public List initDual(@PathVariable(value = "date") String date,@PathVariable(value = "regionId") String regionId){

        List dualList = service.getDualList(date,regionId);

        return dualList;
    }
    @ResponseBody
    @RequestMapping(value="loadDual")
    public List loadDual(){

        List dualList = service.loadDual();

        return dualList;
    }
    //unused
    @ResponseBody
    @RequestMapping(value="booking")
    public Map booking(String jsonList){

        List<bookingRegion> bookingList = JsonUtils.json2List(jsonList, bookingRegion.class);
        Map map = new HashMap();

        boolean flag = service.booking(bookingList);
        if(flag){
            map.put("flag","success");
        }else{
            map.put("flag","error");
        }

        return map;

    }
    //提交预约前先进行检查，避免重复预约
    @ResponseBody
    @RequestMapping(value = "checkBookingRegion")
    public Map checkBookingRegion(String jsonList){

        List<bookingRegion> bookingList = JsonUtils.json2List(jsonList, bookingRegion.class);
        Map map = new HashMap();
        String dual = service.checkBookingRegion(bookingList);

        if(!"".equals(dual) && dual!=null && dual.length()!=0){
            map.put("flag",dual);
        }else{
            boolean flag = service.booking(bookingList);
            if(flag){
                map.put("flag","success");
            }else{
                map.put("flag","error");
            }
//            map.put("flag","success");
        }
        return map;
    }
    //获取已预订
    @RequestMapping(value = "getBookingList")
    public ModelAndView getBookingList(){

        ModelAndView mv = new ModelAndView();
        List bookingList = service.getBookingList();
        mv.addObject("bookingList",bookingList);
        mv.setViewName("booking/bookingList");

        return mv;
    }
    //归还会议室
    @RequestMapping(value = "cancelBooking/{bookingId}")
    public Map cancelBooking(@PathVariable(value = "bookingId") String bookingId){

        Map map = new HashMap();
        boolean flag = service.cancelBooking(bookingId);
        if(flag){
           map.put("flag","success");
        }else{
            map.put("flag","error");
        }
        return map;
    }
    //获取预定详细信息
    @RequestMapping(value = "getBookingDetail/{bookingId}")
    public ModelAndView getBookingDetail(@PathVariable(value = "bookingId") String bookingId){

        ModelAndView mv = new ModelAndView();
        bookingRegion bookingRegion = service.getBookingRegion(bookingId);

        mv.addObject("bookingRegion",bookingRegion);
        mv.setViewName("booking/bookingDetail");

        return mv;
    }
    //进入后台
    @RequestMapping(value = "adminEnter")
    public ModelAndView adminEnter(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("booking/adminLogin");
        return mv;
    }
    //后台登陆
    @RequestMapping(value = "adminLogin/{account}/{password}")
    public Map adminLogin(@PathVariable(value = "account") String account,@PathVariable(value = "password") String password){

        Map map = new HashMap();
        String mess = service.adminLogin(account,password);
        if(mess!=null && !mess.equals("")){
            map.put("flag",mess);
        }else{
            map.put("flag","success");
        }

        return map;
    }
}
