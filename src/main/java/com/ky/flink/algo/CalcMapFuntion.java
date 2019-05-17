package com.ky.flink.algo;

import com.ky.flink.model.CalcModel;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class CalcMapFuntion implements FlatMapFunction<Tuple2<String, Integer>, CalcModel> {

    @Override
    public void flatMap(Tuple2<String, Integer> input, Collector<CalcModel> out) throws Exception {
        CalcModel calcModel = new CalcModel();
        calcModel.setValue(input.f1);
        calcModel.setGroup(input.f0);
        out.collect(calcModel);
    }
}
