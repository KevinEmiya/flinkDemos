package com.ky.flink.model;

import lombok.Data;

@Data
public class BytesFile {

    byte[] rawData;

    FileMetaInfo metaInfo;

}
