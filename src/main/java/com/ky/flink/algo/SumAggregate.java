package com.ky.flink.algo;

import com.ky.flink.model.CalcModel;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class SumAggregate implements AggregateFunction<CalcModel, Tuple2<String, Integer>, Tuple2<String, Integer>> {

    @Override
    public Tuple2<String, Integer> createAccumulator() {
        return Tuple2.of("", 0);
    }

    @Override
    public Tuple2<String, Integer> add(CalcModel calcModel, Tuple2<String, Integer> delta) {
        return Tuple2.of(calcModel.getGroup(), calcModel.getValue() + delta.f1);
    }

    @Override
    public Tuple2<String, Integer> getResult(Tuple2<String, Integer> result) {
        return result;
    }

    @Override
    public Tuple2<String, Integer> merge(Tuple2<String, Integer> a, Tuple2<String, Integer> b) {
        return Tuple2.of(a.f0, a.f1 + b.f1);
    }
}
