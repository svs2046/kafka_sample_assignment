package com.kafka.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.kafka.model.Employee;
import com.kafka.repositiory.EmployeeService;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@Component
@RestController
public class TopicsController {

	private final EmployeeService empService;

	public TopicsController(EmployeeService empService) {
		this.empService = empService;
	}

	static final String TOPIC = "topic3";

	static final String GROUP = "INFO";

	private static Producer<String, String> producer;

	@GetMapping("/pushToAnotherTopic")
	public String publishToAnotherTopic() {

		ConsumerConnector consumerConnector = null;

		Properties props = new Properties();
		props.put("log.retention.ms", "60000");
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "BASIS");
		props.put("zookeeper.sync.time.ms", "300");
		props.put("auto.commit.interval.ms", "1000");

		kafka.consumer.ConsumerConfig conConfig = new ConsumerConfig(props);
		consumerConnector = Consumer.createJavaConsumerConnector(conConfig);

		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		topicCount.put(TOPIC, new Integer(1));
		try {

			Map<String, List<KafkaStream<byte[], byte[]>>> consumerStream = consumerConnector
					.createMessageStreams(topicCount);

			List<KafkaStream<byte[], byte[]>> kStreamList = consumerStream.get("topic3");

			for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {

				ConsumerIterator<byte[], byte[]> consumerIte = kStreams.iterator();

				while (consumerIte.hasNext()) {

					Properties prop = new Properties();
					prop.setProperty("bootstrap.servers", "localhost:9092");
					prop.setProperty("kafka.topic.name", "topic5");
					prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
					prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
					prop.setProperty("request.required.acks", "1");
					String content = new String(consumerIte.next().message());
					System.out.println("another toic content:" + content);

					String str = content.toString();
					Employee data = new Gson().fromJson(str, Employee.class);

					BigDecimal sal = data.getSalary();
					BigDecimal a = new BigDecimal(10);
					BigDecimal b = new BigDecimal(100);

					BigDecimal bonusSal = sal.add(sal.multiply(a.divide(b)));

					Employee emp = new Employee();
					emp.setEmployeeId(data.getEmployeeId());
					emp.setEmpName(data.getEmpName());
					emp.setDesignation(data.getDesignation());
					emp.setJobLevel(data.getJobLevel());
					emp.setRating(data.getRating());
					emp.setSalary(bonusSal);
					String empId = Long.toString(data.getEmployeeId());
					producer = new KafkaProducer<String, String>(prop);

					ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic5", empId,
							emp.toString());
					producer.send(record, new Callback() {

						@Override
						public void onCompletion(RecordMetadata metadata, Exception exception) {
							if (exception == null) {
								System.out.println("Msg Published to topic=" + metadata.topic() + " , partition= "
										+ metadata.partition() + ", offset=" + metadata.offset() + " , timestamp="
										+ metadata.timestamp());
							} else {
								System.out.println("Error Occurd:" + exception.getMessage());
							}
						}
					});

					producer.flush();
					producer.close();

				}
			}
			if (consumerConnector != null)
				consumerConnector.shutdown();

			return "Employee Data Pushed To Another Topic Successfully";

		} catch (Exception e) {
			e.printStackTrace();

			return "Exception has occured";
		}
	}

//	public void getDataFallBack() {
//		
//		System.out.println("Inside Fall Back...");;
//		
//	}
}
