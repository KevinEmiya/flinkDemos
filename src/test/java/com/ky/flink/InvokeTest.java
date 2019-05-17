package com.ky.flink;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ky.flink.model.BytesFile;
import com.ky.flink.model.FileMetaInfo;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvokeTest {

    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        BytesFile bytesFile = new BytesFile();
        FileMetaInfo fileMetaInfo = new FileMetaInfo();
        fileMetaInfo.setFileInfoType("invTest");
        fileMetaInfo.setFileName("test.statusBin");
        fileMetaInfo.setRefId("invTest" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
//        fileMetaInfo.setFileType("mp4");
        fileMetaInfo.setFileType("statusBin");
        fileMetaInfo.setFileInfoType("testKY");

        //byte[] rawData = FileUtils.readFileToByteArray(new File(InvokeTest.class.getResource("/testvideo.mp4").getFile()));
        byte[] rawData = "test".getBytes();

        bytesFile.setMetaInfo(fileMetaInfo);
        bytesFile.setRawData(rawData);

        Gson gson = new GsonBuilder().create();

        final Request request = new Request.Builder()
                .url("http://localhost:10001/file/upload/bytes")
                .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gson.toJson(bytesFile)))
                .build();

        final Call call = okHttpClient.newCall(request);
        Response response =call.execute();
        System.out.println(response.body().string());
    }

}
