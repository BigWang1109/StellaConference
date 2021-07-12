package com.wxx.conference.test;

import com.wxx.conference.model.HR.zgfh_bi_psndoc;
import com.wxx.conference.util.HibernateHRDBUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2020-9-9.
 */
public class testLucene {
    public static void main(String[] args) {
//        createIndex();
        searchIndex();
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
                StringBuffer sb = new StringBuffer();
                sb.append(checkIsNull(obj[0]));
                sb.append(checkIsNull(obj[1]));
                sb.append(checkIsNull(obj[2]));
                sb.append(checkIsNull(obj[3]));
                sb.append(checkIsNull(obj[4]));
                sb.append(checkIsNull(obj[5]));
                sb.append(checkIsNull(obj[6]));
                sb.append(checkIsNull(obj[7]));
                sb.append(checkIsNull(obj[8]));
                sb.append(checkIsNull(obj[9]));
                sb.append(checkIsNull(obj[10]));
                sb.append(checkIsNull(obj[11]));
                sb.append(checkIsNull(obj[12]));
                sb.append(checkIsNull(obj[13]));
                sb.append(checkIsNull(obj[14]));
                sb.append(checkIsNull(obj[15]));
                sb.append(checkIsNull(obj[16]));
                sb.append(checkIsNull(obj[17]));
                sb.append(checkIsNull(obj[18]));
                sb.append(checkIsNull(obj[19]));
                psndoc.setSEARCH_INFO(sb.toString());
                res.add(psndoc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null) session.close();
        }
        return res;
    }
    /**
     * 创建信息表索引
     * */
    public static void createIndex(){
        List<zgfh_bi_psndoc> array = queryPsndoc();
        List<Document> docList = new ArrayList<Document>();
        IndexWriter indexWriter = null;
        try{
            for(zgfh_bi_psndoc psndoc : array){
                Document document = new Document();

//                Field CORPNAME = new TextField("CORPNAME",psndoc.getCORPNAME(),Field.Store.YES);
//                Field GW = new TextField("GW",psndoc.getGW(),Field.Store.YES);
//                Field ZW = new TextField("ZW",psndoc.getZW(),Field.Store.YES);
//                Field SY = new TextField("SY",psndoc.getSY(),Field.Store.YES);
//                Field HY = new TextField("HY",psndoc.getHY(),Field.Store.YES);
//                Field MZ = new TextField("MZ",psndoc.getMZ(),Field.Store.YES);
//                Field SEX = new TextField("SEX",psndoc.getSEX(),Field.Store.YES);
//                Field XL = new TextField("XL",psndoc.getXL(),Field.Store.YES);
                Field SCHOOL = new TextField("SCHOOL",psndoc.getSCHOOL(),Field.Store.YES);
                Field PSNCODE = new TextField("PSNCODE",psndoc.getPSNCODE(),Field.Store.YES);
                Field SEARCH_INFO = new TextField("SEARCH_INFO",psndoc.getSEARCH_INFO(), Field.Store.YES);
                Field PSNNAME = new TextField("PSNNAME",psndoc.getPSNNAME(),Field.Store.YES);
//                Field JG = new TextField("JG",psndoc.getJG(),Field.Store.YES);
//                Field HK = new TextField("HK",psndoc.getHK(),Field.Store.YES);
//                Field ZZMM = new TextField("ZZMM",psndoc.getZZMM(),Field.Store.YES);
//                Field ZY = new TextField("ZY",psndoc.getZY(),Field.Store.YES);
//                Field BIRTHDAY = new TextField("BIRTHDAY",psndoc.getBIRTHDAY(),Field.Store.YES);
//                Field RSDATE = new TextField("RSDATE",psndoc.getRSDATE(),Field.Store.YES);
//                Field WORKDATE = new TextField("WORKDATE",psndoc.getWORKDATE(),Field.Store.YES);
//                Field AGE = new TextField("AGE",psndoc.getAGE(),Field.Store.YES);

//                document.add(CORPNAME);
//                document.add(GW);
//                document.add(ZW);
//                document.add(SY);
//                document.add(HY);
//                document.add(MZ);
//                document.add(SEX);
//                document.add(XL);
                document.add(SCHOOL);
                document.add(PSNCODE);
                document.add(SEARCH_INFO);
                document.add(PSNNAME);
//                document.add(JG);
//                document.add(HK);
//                document.add(ZZMM);
//                document.add(ZY);
//                document.add(BIRTHDAY);
//                document.add(RSDATE);
//                document.add(WORKDATE);
//                document.add(AGE);

                docList.add(document);
            }
            Analyzer analyzer = new IKAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //清空索引，创建新的索引
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc");
            Directory directory = FSDirectory.open(indexfile);
            indexWriter = new IndexWriter(directory,config);

            for(Document doc : docList){
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
        }catch (Exception e){
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
        try{
//            QueryParser parser = new QueryParser("SCHOOL",new StandardAnalyzer());
            QueryParser parser = new QueryParser("SEARCH_INFO",new IKAnalyzer());
            org.apache.lucene.search.Query query = parser.parse("吉林大学AND王晓旭");
            Path indexfile = Paths.get("E:\\search-index\\data\\zgfh_bi_psndoc");
            Directory directory = FSDirectory.open(indexfile);
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(reader);
            TopDocs topDocs = indexSearcher.search(query,20);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for(ScoreDoc scoreDoc : scoreDocs){
                int docId = scoreDoc.doc;

                Document doc = indexSearcher.doc(docId);
//                System.out.println(doc.get("CORPNAME"));
//                System.out.println(doc.get("GW"));
//                System.out.println(doc.get("ZW"));
//                System.out.println(doc.get("SY"));
//                System.out.println(doc.get("HY"));
//                System.out.println(doc.get("MZ"));
//                System.out.println(doc.get("SEX"));
//                System.out.println(doc.get("XL"));
                System.out.println(doc.get("SCHOOL"));
                System.out.println(doc.get("PSNCODE"));
                System.out.println(doc.get("PSNNAME"));
//                System.out.println(doc.get("JG"));
//                System.out.println(doc.get("HK"));
//                System.out.println(doc.get("ZZMM"));
//                System.out.println(doc.get("ZY"));
//                System.out.println(doc.get("BIRTHDAY"));
//                System.out.println(doc.get("RSDATE"));
//                System.out.println(doc.get("WORKDATE"));
//                System.out.println(doc.get("AGE"));
                System.out.println("**************************");
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
