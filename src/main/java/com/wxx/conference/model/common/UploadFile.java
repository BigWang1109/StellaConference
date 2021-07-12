package com.wxx.conference.model.common;

/**
 * Created by thinkpad on 2021-6-9.
 */
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.util.Date;


//使用默认大小写属性转换json
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name = "oabi.BD_UPLOADFILE")
public class UploadFile {
    private static final long serialVersionUID = 1L;

    public UploadFile() {
        super();
    }

    @Id
    @Column(name = "FILEID")
    private String fileId;

    @Column(name = "FOREIGNID")
    private String foreignId;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "FULLFILENAME")
    private String fullFileName;

    @Column(name = "EXTENSION")
    private String extension;

    @Column(name = "FILELENGTH")
    private long fileLength;

    @Column(name = "FILESIZE")
    private String fileSize;

    @Column(name = "CATEGORY")
    private String category;

    //0临时文件 1正式文件 2删除文件
    @Column(name = "STATE")
    private Integer state;

    @Column(name = "ORDERINDEX")
    private Integer orderIndex;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATEUSERID")
    private String createUserId;

    @Column(name = "CREATEUSERNAME")
    private String createUserName;

    @Column(name = " CREATEDATE ")
    private Date createDate;

    @Column(name = "CONTENT")
    private String content;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
