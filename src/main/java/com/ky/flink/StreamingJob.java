/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ky.flink;

import com.ky.flink.algo.CalcDeserializer;
import com.ky.flink.algo.CalcMapFuntion;
import com.ky.flink.algo.SumAggregate;
import com.ky.flink.model.CalcModel;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.SessionWindowTimeGapExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.1.123:9092");
        properties.setProperty("group.id", "flink");

        FlinkKafkaConsumer<CalcModel> kafkaConsumer = new FlinkKafkaConsumer<>("flinkTest", new CalcDeserializer(), properties);
        SumAggregate sumAggregate = new SumAggregate();

        FlinkKafkaProducer<CalcModel> kafkaProducer = new FlinkKafkaProducer<CalcModel>("192.168.1.123:9092", "flinkTestResult", new CalcDeserializer());

        DataStream<Tuple2<String, Integer>> result = env.addSource(kafkaConsumer)
                .keyBy(calcModel -> calcModel.getGroup()).timeWindow(Time.seconds(1)).aggregate(sumAggregate);

        result.print();
        result.flatMap(new CalcMapFuntion()).addSink(kafkaProducer);
        env.execute("Kafka sum test");
    }
}
