package com.wxx.conference.controller.boardRoomManagement;

import com.wxx.conference.model.boardRoomManagement.boardRoomDevice;
import com.wxx.conference.service.boardRoomManagement.BRMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2017-9-8.
 */
@RestController
@RequestMapping(value="brm")
public class BRMController {

    @Autowired
    private BRMService service;

    @RequestMapping(value = "addDevice/{deviceName}")
    public Map addDevice(@PathVariable(value = "deviceName") String deviceName){
        Map map = new HashMap();
        boolean result = service.addDevice(deviceName);
        map.put("flag",result);
        return map;
    }
    @RequestMapping(value = "editDevice/{deviceId}/{deviceName}")
    public Map editDevice(@PathVariable(value = "deviceId") String deviceId,@PathVariable(value = "deviceName") String deviceName){
        Map map = new HashMap();
        boolean result = service.editDevice(deviceId, deviceName);
        map.put("flag",result);
        return map;
    }
    @RequestMapping(value = "delDevice/{deviceId}")
    public Map delDevice(@PathVariable(value = "deviceId") String deviceId){
        Map map = new HashMap();
        boolean result = service.delDevice(deviceId);
        map.put("flag",result);
        return map;
    }
    @RequestMapping(value = "getDeviceList")
    public ModelAndView getDeviceList(){

        ModelAndView mv = new ModelAndView();
        List deviceList = service.getDeviceList();
        mv.addObject("deviceList",deviceList);
        mv.setViewName("boardRoomManagement/boardRoomDevice");
        return mv;
    }
    @RequestMapping(value = "convert/{deviceId}")
    public ModelAndView convert(@PathVariable(value = "deviceId") String deviceId){

        ModelAndView mv = new ModelAndView();
        boardRoomDevice device =service.getDeviceById(deviceId);
        mv.addObject("device", device);
        mv.setViewName("boardRoomManagement/deviceDelete");
        return mv;
    }
}
