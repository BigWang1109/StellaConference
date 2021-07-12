package com.wxx.conference.model.common;

/**
 * Created by thinkpad on 2020-10-20.
 */
public class pdfvo {
    private String fileId;//文件ID
    private String fileName;//文件名
    private String fileType;//文件类型
    private String content;//pdf中内容
    private String outputFile;//输出路径(保存路径)

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
