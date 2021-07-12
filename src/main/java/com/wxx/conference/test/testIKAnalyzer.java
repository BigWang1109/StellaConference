package com.wxx.conference.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by thinkpad on 2020-9-10.
 */
public class testIKAnalyzer {
    public static void main(String[] args) throws Exception{
        //创建一个标准分析器对象
//        Analyzer analyzer=new StandardAnalyzer();
        Analyzer analyzer=new IKAnalyzer();
        //获取tokenStream对象
        //参数1域名 2要分析的文本内容
        TokenStream tokenStream=analyzer.tokenStream("","test a lucene 程序,杯莫停");
        //添加引用,用于获取每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到列表的头部
        tokenStream.reset();
        //遍历关键词列表,incrementToken判断是否结束
        while (tokenStream.incrementToken()) {
            System.out.println("开始--->"+offsetAttribute.startOffset());
            System.out.println(charTermAttribute);
            System.out.println("结束--->"+offsetAttribute.endOffset());
        }
        tokenStream.close();
    }
}
