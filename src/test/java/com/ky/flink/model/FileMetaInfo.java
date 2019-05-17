package com.ky.flink.model;

import lombok.Data;

import java.util.Date;

@Data
public class FileMetaInfo {

    //"业务类型")
    String fileInfoType;

    //"关联业务ID")
    String refId;

    //"创建人ID")
    private String creatorId;

    //"修改人ID")
    private String operator;

    //"创建时间")
    private Date makeTime;

    //"最后修改时间")
    private Date modifyTime;

    //"备注")
    private String remark;

    //"文件类型")
    private String fileType;

    //"文件名")
    private String fileName;

    //"文件大小")
    private Long fileSize;

}
