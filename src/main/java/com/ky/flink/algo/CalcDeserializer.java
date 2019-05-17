package com.ky.flink.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;
import com.ky.flink.model.CalcModel;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class CalcDeserializer implements DeserializationSchema<CalcModel>, SerializationSchema<CalcModel> {

    static Gson gson = new GsonBuilder().create();

    @Override
    public CalcModel deserialize(byte[] bytes) throws IOException {
        String raw = new String(bytes);
        try {
            CalcModel calcModel = gson.fromJson(raw, CalcModel.class);
            return calcModel;
        }
        catch (Exception e) {
            System.err.println("解析失败：" + e.getMessage());
            e.printStackTrace();
        }
        CalcModel failedModel = new CalcModel();
        failedModel.setGroup("failed");
        failedModel.setValue(1);
        return failedModel;
    }

    @Override
    public boolean isEndOfStream(CalcModel s) {
        return false;
    }

    @Override
    public TypeInformation<CalcModel> getProducedType() {
        return TypeInformation.of(CalcModel.class);
    }

    @Override
    public byte[] serialize(CalcModel calcModel) {
        return gson.toJson(calcModel).getBytes();
    }
}
