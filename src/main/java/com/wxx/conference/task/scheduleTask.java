package com.wxx.conference.task;

import com.wxx.conference.service.HR.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by thinkpad on 2020-8-21.
 */
@Service
public class scheduleTask {
    @Autowired
    private CVService cvService;

    public void generateDB(){
        cvService.updatePY();
        //office文件保存至本地以后无法正常打开，暂时停用
//        cvService.updateContent();
//        cvService.createPsndocIndex();
//        cvService.createWorkIndex();
//        cvService.createEduIndex();
//        cvService.createKpiIndex();
//        cvService.createContIndex();
//        cvService.createTrainIndex();
//        cvService.createTitleIndex();
    }
}
