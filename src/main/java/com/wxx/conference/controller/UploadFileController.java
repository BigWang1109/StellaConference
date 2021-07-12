package com.wxx.conference.controller;


import com.wxx.conference.model.common.UploadFile;
import com.wxx.conference.service.UploadFileService;
import com.wxx.conference.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by chairui on 16-1-15.
 */
@RestController
public class UploadFileController {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 显示附件文件页面, 根据业务情况参考该方法在业务系统内实现
     *
     */
    @RequestMapping(value = "/upload/view", method = RequestMethod.GET)
    public ModelAndView uploadView(String jsp, String foreignid) {
        ModelAndView mv = new ModelAndView();
        //初始化业务对象主键作为上传对象的外键
        mv.addObject("foreignid", foreignid);
        //初始化扩展数据(可选)
        mv.addObject("category", "sfz");

        //查询已经上传的并且有效的数据
        List<UploadFile> list = uploadFileService.loadFiles(foreignid, 1);
        mv.addObject("list", list);

        mv.setViewName("demo/" + jsp);
        return mv;
    }

    /**
     * 显示附件文件页面, 根据业务情况参考该方法在业务系统内实现
     *
     */
    @RequestMapping(value = "/upload/window", method = RequestMethod.GET)
    public ModelAndView uploadView(String foreignid) {
        ModelAndView mv = new ModelAndView();
        //初始化业务对象主键作为上传对象的外键
        mv.addObject("foreignid", foreignid);
        //初始化扩展数据(可选)
        mv.addObject("category", "sfz");

        //查询已经上传的并且有效的数据
        List<UploadFile> list = uploadFileService.loadFiles(foreignid, 1);
        mv.addObject("list", list);

        mv.setViewName("demo/uploadView1");
        return mv;
    }


    /**
     * 上传文件, 文件状态标记为临时
     *
     */
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(UploadFile uploadFile, HttpServletRequest request){

        boolean result = true;
        String errormsg = "";

        String datePath = DateTimeUtil.getCurDateFShort().replace("-", "/") + "/";
        String path = PropertiesUtil.getProperty("UPLOAD_FOLDER_ROOT") + datePath;
        File file = new File(path);
        if (!file.exists())
        {
            file.mkdirs();
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("qqfile");

        //获取上传文件的属性
        String fileid = request.getParameter("qquuid");
        String foreignid = request.getParameter("foreignid");
        String wmtext = request.getParameter("wmtext");
        String isShowTime = request.getParameter("isShowTime");
        String fontSize = request.getParameter("wFontSize");
        String opacity = request.getParameter("transparency");
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
        //IE 上传的文件有原始路径， chorme 上传的文件没有路径
        int beginIndex = multipartFile.getOriginalFilename().lastIndexOf('\\');
        if (beginIndex == -1)
        {
            beginIndex = 0;
        }
        else
        {
            beginIndex++;
        }
        String oldFileName = multipartFile.getOriginalFilename().substring(beginIndex);
        oldFileName = oldFileName.substring(0, oldFileName.lastIndexOf('.'));
        String newFileName = path + fileid + extension;
        String fullFileName = datePath + fileid + extension;
        long fileLength = multipartFile.getSize();
        String fileSize = FileSizeUtil.formatFileSize(fileLength);

        //保存文件
        try {
//            FileUtil.saveFileFromInputStream(multipartFile.getInputStream(), newFileName);
            FileUtil.saveFileFromInputStream(multipartFile.getInputStream(), path+oldFileName+extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传后的文件添加水印
//        WaterMarkUtil.addTxtWaterMaker(newFileName, path+oldFileName+extension, "handsomejack");
        if(isShowTime.equals("true")){
//            WaterMarkUtil.addTxtWaterMaker(path+oldFileName+extension, newFileName, wmtext+DateTimeUtil.formatCNTimeWithoutSeconds(new Date()));
            WaterMarkUtil.addTxtWaterMakerPro(path + oldFileName + extension, newFileName, wmtext + DateTimeUtil.formatCNTimeWithoutSeconds(new Date()),fontSize,opacity);
        }else{
//            WaterMarkUtil.addTxtWaterMaker(path+oldFileName+extension, newFileName, wmtext);
            WaterMarkUtil.addTxtWaterMakerPro(path + oldFileName + extension, newFileName, wmtext,fontSize ,opacity );
        }
        delFile(path+oldFileName+extension);
        //获取最大排序号
        int orderIndex = uploadFileService.getMaxOrderIndex(uploadFile.getForeignId(), uploadFile.getCategory());
//        int orderIndex = 0;

        //保存数据
        uploadFile.setFileId(fileid);
        uploadFile.setFileName(oldFileName);
        uploadFile.setFullFileName(fullFileName);
        uploadFile.setExtension(extension);
        uploadFile.setFileLength(fileLength);
        uploadFile.setFileSize(fileSize);
        uploadFile.setContent(wmtext);
        //标记为临时文件
        uploadFile.setState(0);
        uploadFile.setOrderIndex(orderIndex);
        //TODO:修改为提交用户名和密码
        //uploadFile.setCreateUserId();
        //uploadFile.setCreateUseName();
        uploadFile.setCreateDate(new Date());
        uploadFileService.addFile(uploadFile);

        Map<String, Object> json = new Hashtable<String, Object>(1);
        json.put("success", result);
        json.put("errormsg", errormsg);
        json.put("qquuid", fileid);
        json.put("qqfilename", oldFileName + extension);
        json.put("qqtotalfilesize", fileSize);
        json.put("extension", extension.toLowerCase());
        json.put("orderindex", orderIndex);

        return json;
    }

    /**
     * 删除文件, 文件状态标记为删除, 文件和数据并不删除
     *
     */
    @RequestMapping(value = "/upload/delete", method = RequestMethod.POST)
    public Map<String, Object> uploadDelete(String fileid, HttpServletRequest request){

        boolean result = true;
        String errormsg = "";

        //标记删除
        uploadFileService.markDeleteFile(fileid);

        Map<String, Object> json = new Hashtable<String, Object>(1);
        json.put("result", result);
        json.put("errormsg", errormsg);
        return json;
    }

    /**
     * 更新文件状态, 删除和新增的文件, 最终已提交的ids为主进行处理
     *
     */
    @RequestMapping(value = "/upload/submit", method = RequestMethod.POST)
    public Map<String, Object> uploadSubmit(String foreignid, String ids, HttpServletRequest request){

        boolean result = true;
        String errormsg = "";

        //查询数据
        List<UploadFile> list = uploadFileService.loadFiles(foreignid);
        List<String> array = Arrays.asList(ids.split(","));
        //更新状态
        for(int i=0; i< list.size(); i++){
            if(array.contains(list.get(i).getFileId())){
                list.get(i).setState(1);
            }
            else{
                list.get(i).setState(2);
            }
        }
        //更新到数据库
        uploadFileService.updateFiles(list);

        Map<String, Object> json = new Hashtable<String, Object>(1);
        json.put("result", result);
        json.put("errormsg", errormsg);
        return json;
    }

    /**
     * 下载文件
     *
     */
    @RequestMapping(value = "/upload/download/{fileId}", method = RequestMethod.GET)
    public void download(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        UploadFile file = uploadFileService.getFile(fileId);

        String filepath = PropertiesUtil.getProperty("UPLOAD_FOLDER_ROOT") + file.getFullFileName();
        String filename = file.getFileName() + file.getExtension();
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        }

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            File f = new File(filepath);
            response.setContentType("text/html;charset=UTF-8");
            response.setContentType("application/x-msdownload;");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setHeader("Content-Length",String.valueOf(f.length()));

            in = new BufferedInputStream(new FileInputStream(f));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len=in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
                delFile(filepath);
            }
        }
    }
    /**
     *
     *
     * */
    public void delFile(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }
}