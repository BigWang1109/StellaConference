package com.wxx.conference.test;

import com.wxx.conference.model.HR.*;
import com.wxx.conference.util.HibernateHRDBUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by thinkpad on 2020-9-10.
 */
public class manualCreateIndex {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(manualCreateIndex.class);

    public static void main(String[] args) {
//        createPsndocIndex();
//        createWorkIndex();
//        createEduIndex();
//        createKpiIndex();
//        createContIndex();
//        createTrainIndex();
//        createTitleIndex();
        searchIndex();
//        searchWorkIndex();
    }
    /**
     * 查询信息表所有数据
     * */
    public static List<zgfh_bi_psndoc> queryPsndoc(){
        Session session = null;
        List<zgfh_bi_psndoc> res = new ArrayList<zgfh_bi_psndoc>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select CORPNAME,GW,ZW,SY,HY,MZ,SEX,XL,SCHOOL,PSNCODE,PSNNAME,JG,HK,ZZMM,ZY,BIRTHDAY,RSDATE,RDDATE,WORKDATE,to_char(sysdate,'yyyy') - to_char(to_date(BIRTHDAY,'yyyy-MM-dd'),'yyyy') from oabi.zgfh_bi_psndoc2020 order by corpname,psncode";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc psndoc = new zgfh_bi_psndoc();
                psndoc.setCORPNAME(checkIsNull(obj[0]));
                psndoc.setGW(checkIsNull(obj[1]));
                psndoc.setZW(checkIsNull(obj[2]));
                psndoc.setSY(checkIsNull(obj[3]));
                psndoc.setHY(checkIsNull(obj[4]));
                psndoc.setMZ(checkIsNull(obj[5]));
                psndoc.setSEX(checkIsNull(obj[6]));
                psndoc.setXL(checkIsNull(obj[7]));
                psndoc.setSCHOOL(checkIsNull(obj[8]));
                psndoc.setPSNCODE(checkIsNull(obj[9]));
                psndoc.setPSNNAME(checkIsNull(obj[10]));
                psndoc.setJG(checkIsNull(obj[11]));
                psndoc.setHK(checkIsNull(obj[12]));
                psndoc.setZZMM(checkIsNull(obj[13]));
                psndoc.setZY(checkIsNull(obj[14]));
                psndoc.setBIRTHDAY(checkIsNull(obj[15]));
                psndoc.setRSDATE(checkIsNull(obj[16]));
                psndoc.setRDDATE(checkIsNull(obj[17]));
                psndoc.setWORKDATE(checkIsNull(obj[18]));
                psndoc.setAGE(checkIsNull(obj[19]));
                res.add(psndoc);
            }
            logger.info("基本信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询工作履历表所有数据
     * */
    public static List<zgfh_bi_psndoc_work> queryWork(){
        Session session = null;
        List<zgfh_bi_psndoc_work> res = new ArrayList<zgfh_bi_psndoc_work>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,WORKCORP,WORKPOST from oabi.zgfh_bi_psndoc_work";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_psndoc_work work = new zgfh_bi_psndoc_work();
                work.setPSNCODE(checkIsNull(obj[0]));
                work.setBEGINDATE(checkIsNull(obj[1]));
                work.setENDDATE(checkIsNull(obj[2]));
                work.setWORKCORP(checkIsNull(obj[3]));
                work.setWORKPOST(checkIsNull(obj[4]));
                res.add(work);
            }
            logger.info("工作履历表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询学历信息表所有数据
     * */
    public static List<zgfh_bi_edu> queryEdu(){
        Session session = null;
        List<zgfh_bi_edu> res = new ArrayList<zgfh_bi_edu>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,SCHOOL,MAJOR,EDUCATION,DEGREE from oabi.zgfh_bi_edu";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                zgfh_bi_edu edu = new zgfh_bi_edu();
                Object[] obj = (Object[]) array.get(i);
                edu.setPSNCODE(checkIsNull(obj[0]));
                edu.setBEGINDATE(checkIsNull(obj[1]));
                edu.setENDDATE(checkIsNull(obj[2]));
                edu.setSCHOOL(checkIsNull(obj[3]));
                edu.setMAJOR(checkIsNull(obj[4]));
                edu.setEDUCATION(checkIsNull(obj[5]));
                edu.setDEGREE(checkIsNull(obj[6]));

                res.add(edu);
            }
            logger.info("学历信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询考核表所有数据
     * */
    public static List<zgfh_bi_kpi> queryKpi(){
        Session session = null;
        List<zgfh_bi_kpi> res = new ArrayList<zgfh_bi_kpi>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,KPI_YEAR,DEGREE,REWARD from oabi.zgfh_bi_kpi";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_kpi kpi = new zgfh_bi_kpi();
                kpi.setPSNCODE(checkIsNull(obj[0]));
                kpi.setKPI_YEAR(checkIsNull(obj[1]));
                kpi.setDEGREE(checkIsNull(obj[2]));
                kpi.setREWARD(checkIsNull(obj[3]));

                res.add(kpi);
            }
            logger.info("考核表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询合同信息表所有数据
     * */
    public static List<zgfh_bi_cont> queryCont(){
        Session session = null;
        List<zgfh_bi_cont> res = new ArrayList<zgfh_bi_cont>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,VCONTRACTNUM,TERMNAME from oabi.zgfh_bi_cont";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(0);
                zgfh_bi_cont cont = new zgfh_bi_cont();
                cont.setPSNCODE(checkIsNull(obj[0]));
                cont.setBEGINDATE(checkIsNull(obj[1]));
                cont.setENDDATE(checkIsNull(obj[2]));
                cont.setVCONTRACTNUM(checkIsNull(obj[3]));
                cont.setTERMNAME(checkIsNull(obj[4]));

                res.add(cont);
            }
            logger.info("合同信息表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询教育培训表所有数据
     * */
    public static List<zgfh_bi_train> queryTrain(){
        Session session = null;
        List<zgfh_bi_train> res = new ArrayList<zgfh_bi_train>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,BEGINDATE,ENDDATE,VTRA_ACT,TRM_CLASS_NAMES from oabi.zgfh_bi_train";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_train train = new zgfh_bi_train();
                train.setPSNCODE(checkIsNull(obj[0]));
                train.setBEGINDATE(checkIsNull(obj[1]));
                train.setENDDATE(checkIsNull(obj[2]));
                train.setVTRA_ACT(checkIsNull(obj[3]));
                train.setTRM_CLASS_NAMES(checkIsNull(obj[4]));
                res.add(train);
            }
            logger.info("教育培训表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 查询职称表所有数据
     * */
    public static List<zgfh_bi_title> queryTitle(){
        Session session = null;
        List<zgfh_bi_title> res = new ArrayList<zgfh_bi_title>();
        try{
            session = HibernateHRDBUtil.getSession();
            String sql = "select PSNCODE,P_NAME,DOCNAME,P_ORG,P_DATE from oabi.zgfh_bi_title";
            Query query = session.createSQLQuery(sql);
            List array = query.list();
            for(int i=0;i<array.size();i++){
                Object[] obj = (Object[]) array.get(i);
                zgfh_bi_title title = new zgfh_bi_title();
                title.setPSNCODE(checkIsNull(obj[0]));
                title.setP_NAME(checkIsNull(obj[1]));
                title.setDOCNAME(checkIsNull(obj[2]));
                title.setP_ORG(checkIsNull(obj[3]));
                title.setP_DATE(checkIsNull(obj[4]));

                res.add(title);
            }
            logger.info("职称表中共获取："+res.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 定时创建信息表索引
     * */
    public static void createPsndocIndex(){
        List<zgfh_bi_psndoc> array = queryPsndoc();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_psndoc psndoc : array){
                Document document = new Document();

                Field CORPNAME = new TextField("CORPNAME",psndoc.getCORPNAME(),Field.Store.YES);
                Field GW = new TextField("GW",psndoc.getGW(),Field.Store.YES);
                Field ZW = new TextField("ZW",psndoc.getZW(),Field.Store.YES);
                Field SY = new TextField("SY",psndoc.getSY(),Field.Store.YES);
                Field HY = new TextField("HY",psndoc.getHY(),Field.Store.YES);
                Field MZ = new TextField("MZ",psndoc.getMZ(),Field.Store.YES);
                Field SEX = new TextField("SEX",psndoc.getSEX(),Field.Store.YES);
                Field XL = new TextField("XL",psndoc.getXL(),Field.Store.YES);
                Field SCHOOL = new TextField("SCHOOL",psndoc.getSCHOOL(),Field.Store.YES);
                Field PSNCODE = new TextField("PSNCODE",psndoc.getPSNCODE(),Field.Store.YES);
                Field PSNNAME = new TextField("PSNNAME",psndoc.getPSNNAME(),Field.Store.YES);
                Field JG = new TextField("JG",psndoc.getJG(),Field.Store.YES);
                Field HK = new TextField("HK",psndoc.getHK(),Field.Store.YES);
                Field ZZMM = new TextField("ZZMM",psndoc.getZZMM(),Field.Store.YES);
                Field ZY = new TextField("ZY",psndoc.getZY(),Field.Store.YES);
                Field BIRTHDAY = new TextField("BIRTHDAY",psndoc.getBIRTHDAY(),Field.Store.YES);
                Field RSDATE = new TextField("RSDATE",psndoc.getRSDATE(),Field.Store.YES);
                Field WORKDATE = new TextField("WORKDATE",psndoc.getWORKDATE(),Field.Store.YES);
                Field AGE = new TextField("AGE",psndoc.getAGE(),Field.Store.YES);

                document.add(CORPNAME);
                document.add(GW);
                document.add(ZW);
                document.add(SY);
                document.add(HY);
                document.add(MZ);
                document.add(SEX);
                document.add(XL);
                document.add(SCHOOL);
                document.add(PSNCODE);
                document.add(PSNNAME);
                document.add(JG);
                document.add(HK);
                document.add(ZZMM);
                document.add(ZY);
                document.add(BIRTHDAY);
                document.add(RSDATE);
                document.add(WORKDATE);
                document.add(AGE);

                docList.add(document);
            }
            Analyzer analyzer = new IKAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }

    }
    /**
     * 定时创建工作履历表索引
     * */
    public static void createWorkIndex(){
        List<zgfh_bi_psndoc_work> array = queryWork();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_psndoc_work work : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",work.getPSNCODE(),Field.Store.YES);
                Field WORKCORP = new TextField("WORKCORP",work.getWORKCORP(),Field.Store.YES);
                Field WORKPOST = new TextField("WORKPOST",work.getWORKPOST(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(WORKCORP);
                document.add(WORKPOST);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc_work");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("工作履历表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("工作履历表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建学历信息表索引
     * */
    public static void createEduIndex(){
        List<zgfh_bi_edu> array = queryEdu();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_edu edu : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",edu.getPSNCODE(),Field.Store.YES);
                Field SCHOOL = new TextField("SCHOOL",edu.getSCHOOL(), Field.Store.YES);
                Field MAJOR = new TextField("MAJOR",edu.getMAJOR(),Field.Store.YES);
                Field EDUCATION = new TextField("EDUCATION",edu.getEDUCATION(),Field.Store.YES);
                Field DEGREE = new TextField("DEGREE",edu.getDEGREE(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(SCHOOL);
                document.add(MAJOR);
                document.add(EDUCATION);
                document.add(DEGREE);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_edu");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("学历信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("学历信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建考核表索引
     * */
    public static void createKpiIndex(){
        List<zgfh_bi_kpi> array = queryKpi();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_kpi kpi : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",kpi.getPSNCODE(),Field.Store.YES);
                Field DEGREE = new TextField("DEGREE",kpi.getDEGREE(),Field.Store.YES);
                Field REWARD = new TextField("REWARD",kpi.getREWARD(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(DEGREE);
                document.add(REWARD);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_kpi");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("考核表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("考核表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建合同信息表索引
     * */
    public static void createContIndex(){
        List<zgfh_bi_cont> array = queryCont();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_cont cont : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",cont.getPSNCODE(),Field.Store.YES);
                Field VCONTRACTNUM = new TextField("VCONTRACTNUM",cont.getVCONTRACTNUM(),Field.Store.YES);
                Field TERMNAME = new TextField("TERMNAME",cont.getTERMNAME(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(VCONTRACTNUM);
                document.add(TERMNAME);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_cont");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("合同信息表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("合同信息表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建教育培训表索引
     * */
    public static void createTrainIndex(){
        List<zgfh_bi_train> array = queryTrain();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_train train : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",train.getPSNCODE(),Field.Store.YES);
                Field VTRA_ACT = new TextField("VTRA_ACT",train.getVTRA_ACT(),Field.Store.YES);
                Field TRM_CLASS_NAMES = new TextField("TRM_CLASS_NAMES",train.getTRM_CLASS_NAMES(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(VTRA_ACT);
                document.add(TRM_CLASS_NAMES);

                docList.add(document);

            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_train");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("教育培训表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("教育培训表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 定时创建职称表索引
     * */
    public static void createTitleIndex(){
        List<zgfh_bi_title> array = queryTitle();
        List<Document> docList = new ArrayList<Document>();
        try{
            for(zgfh_bi_title title : array) {
                Document document = new Document();

                Field PSNCODE = new TextField("PSNCODE",title.getPSNCODE(),Field.Store.YES);
                Field P_NAME = new TextField("P_NAME",title.getP_NAME(),Field.Store.YES);
                Field DOCNAME = new TextField("DOCNAME",title.getDOCNAME(),Field.Store.YES);
                Field P_ORG = new TextField("P_ORG",title.getP_ORG(),Field.Store.YES);

                document.add(PSNCODE);
                document.add(P_NAME);
                document.add(DOCNAME);
                document.add(P_ORG);

                docList.add(document);
            }
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_title");
            Directory directory = FSDirectory.open(indexfile);
            IndexWriter indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            logger.info("职称表索引创建成功");
            indexWriter.close();
        }catch (Exception e){
            logger.error("职称表索引创建失败:"+e.toString());
            e.printStackTrace();
        }
    }
    public static String checkIsNull(Object obj){
        if(obj != null){
            return obj.toString();
        }else{
            return "---";
        }
    }

    public static void searchIndex(){
        HashSet set = new HashSet();
        try{
            Path indexFile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc");
            Directory directory = FSDirectory.open(indexFile);
            IndexReader reader = DirectoryReader.open(directory);
            // 索引搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);
            String[] fields = {"CORPNAME","GW","ZW","MZ",
                    "XL","SCHOOL","PSNCODE","PSNNAME","ZZMM","ZY","PY","QPY"};
            BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                    BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                    BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                    BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                    BooleanClause.Occur.SHOULD};
            Map<String, Float> boosts = new HashMap<String, Float>();
            boosts.put("SCHOOL", 100f);
            boosts.put("PSNNAME", 300f);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,new IKAnalyzer(),boosts);
//            org.apache.lucene.search.Query query = parser.parse("吉大",fields,flags,new IKAnalyzer());
            org.apache.lucene.search.Query query = parser.parse("吉林大学 AND 王");
//            parser.setDefaultOperator(QueryParser.Operator.OR);


//            org.apache.lucene.search.Query query = MultiFieldQueryParser.parse("吉大",fields,flags,new IKAnalyzer());

            TopDocs topDocs = searcher.search(query,50);
            System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for(ScoreDoc scoreDoc : scoreDocs){
                int docID = scoreDoc.doc;
                // 根据编号去找文档
                Document doc = reader.document(docID);
                System.out.println(doc.get("PSNNAME"));
                System.out.println(doc.get("PSNCODE"));
                System.out.println(doc.get("SCHOOL"));
                System.out.println("*************");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void searchWorkIndex(){
        try{
            Path indexFile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc_work");
            Directory directory = FSDirectory.open(indexFile);
            IndexReader reader = DirectoryReader.open(directory);
            // 索引搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);
            String[] fields = {"WORKCORP","WORKPOST","PSNCODE"};
            BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                    BooleanClause.Occur.SHOULD};
            Map<String, Float> boosts = new HashMap<String, Float>();
            boosts.put("WORKCORP", 200f);
//            boosts.put("PSNNAME", 300f);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,new IKAnalyzer(),boosts);
//            org.apache.lucene.search.Query query = parser.parse("吉大",fields,flags,new IKAnalyzer());
            org.apache.lucene.search.Query query = parser.parse("中国泛海");
//            parser.setDefaultOperator(QueryParser.Operator.OR);


//            org.apache.lucene.search.Query query = MultiFieldQueryParser.parse("吉大",fields,flags,new IKAnalyzer());

            TopDocs topDocs = searcher.search(query,50);
            System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for(ScoreDoc scoreDoc : scoreDocs){
                int docID = scoreDoc.doc;
                // 根据编号去找文档
                Document doc = reader.document(docID);
                System.out.print(doc.get("PSNCODE")+":");
                System.out.print(doc.get("WORKCORP")+":");
                System.out.print(doc.get("WORKPOST"));
                System.out.println("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
