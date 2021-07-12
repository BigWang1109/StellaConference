package com.wxx.conference.service;



import com.wxx.conference.model.common.UploadFile;
import com.wxx.conference.service.portal.PortalBaseService;
import com.wxx.conference.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chairui on 16-1-19.
 */
@Service
public class UploadFileService {

    /**
     * 查询某个业务分类文件的最大排序号码
     *
     * @param foreignid 业务对象的主键
     * @param category 分类参数
     * @return
     */
    public int getMaxOrderIndex(String foreignid, String category){

        HashMap<String, Object> param= new HashMap<String, Object>();
        param.put("foreignid", foreignid);

        String hql = "select count(*) from UploadFile where foreignid=:foreignid";
        if(category != null && category.length() >0){
            hql += " and category=:category";
            param.put("category", category);
        }

        int count = PortalBaseService.getCountByHql(hql, param);
        count++;
        return count;
    }


    public void addFile(UploadFile uploadFile){
        PortalBaseService.add(uploadFile);
    }

    /**
     * 标记删除某个文件记录
     *
     * @param fileid 主键
     * @return
     */
    public void markDeleteFile(String fileid){
        UploadFile uploadFile = (UploadFile)PortalBaseService.get(UploadFile.class, fileid);
        uploadFile.setState(2);
        PortalBaseService.update(uploadFile);
    }


    /**
     * 删除某个文件记录及物理文件
     *
     * @param fileid 主键
     * @return
     */
    public void deleteFile(String fileid){
        Object uploadFile = PortalBaseService.get(UploadFile.class, fileid);
        PortalBaseService.delete(uploadFile);
        //TODO:删除文件
    }

    /**
     * 根据某个业务主键 foreignid 查询所有有效状态的文件对象
     *
     * @param foreignid 业务主键
     * @return
     */
    public List<UploadFile> loadFiles(String foreignid, int state){
        String hql = "from UploadFile where state=:state and foreignid = :foreignid";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("foreignid", foreignid);
        param.put("state", state);
        List<UploadFile> list = PortalBaseService.queryByHql(hql, param);
        return list;
    }

    /**
     * 根据某个业务主键 foreignid 查询所有文件对象
     *
     * @param foreignid 业务主键
     * @return
     */
    public List<UploadFile> loadFiles(String foreignid){
        String hql = "from UploadFile where foreignid = :foreignid";
        HashMap<String, Object> param= new HashMap<String, Object>();
        param.put("foreignid", foreignid);
        List<UploadFile> list = PortalBaseService.queryByHql(hql, param);
        return list;
    }

    /**
     * 根据某个业务主键 foreignid 查询所有文件对象
     *
     * @param fileid 业务主键
     * @return
     */
    public UploadFile getFile(String fileid){
        UploadFile file = (UploadFile)PortalBaseService.get(UploadFile.class, fileid);
        return file;
    }

    /**
     * 批量更新文件对象状态
     *
     * @param files 文件对象
     * @return
     */
    public boolean updateFiles(List<UploadFile> files){
        Session session = null;
        Transaction ts = null;
        boolean result = false;
        try{
            session = HibernateUtil.getSession();
            ts = session.beginTransaction();
            for(UploadFile file :files){
                session.update(file);
            }
            ts.commit();
            result = true;
        }
        catch (Exception ex) {
            if(ts != null) {
                ts.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            if(session != null) {
                session.close();
            }
        }
        return result;

    }
}
