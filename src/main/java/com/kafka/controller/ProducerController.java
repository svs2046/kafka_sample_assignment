package com.kafka.controller;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.model.Employee;
import com.kafka.repositiory.EmployeeService;

@Component
@RestController
public class ProducerController {

	private final EmployeeService empService;

	public ProducerController(EmployeeService empService) {
		this.empService = empService;
	}

	static final String TOPIC = "topic3";

	static final String GROUP = "INFO";

	private static Producer<String, String> producer;

	@GetMapping("/fetchDataFromDb")
//	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public String publishToTopic() throws Throwable {

		List<Employee> fetchEmpData = empService.getEmployee();

		List<Employee> empData;

		fetchEmpData.parallelStream().forEach(System.out::println);

		try {
			empData = fetchEmpData.stream().parallel().collect(Collectors.toList());
		} catch (Throwable e) {
			throw e.getCause();
		}
		Properties prop = new Properties();
		prop.setProperty("bootstrap.servers", "localhost:9092");
		prop.setProperty("kafka.topic.name", "topic3");
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.setProperty("request.required.acks", "1");
		producer = new KafkaProducer<String, String>(prop);
		System.out.println("Inside fetch Data from DB...");
		for (Employee str : empData) {
			String empId = Long.toString(str.getEmployeeId());
			ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic3", empId, str.toString());
			producer.send(record, new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception == null) {
						System.out.println(
								"Msg Published to topic=" + metadata.topic() + " , partition= " + metadata.partition()
										+ ", offset=" + metadata.offset() + " , timestamp=" + metadata.timestamp());
					} else {
						System.out.println("Error Occurd:" + exception.getMessage());
					}
				}
			});

		}
		producer.flush();
		producer.close();

		return "Data Published to Kafka Topic Successfully";
	}
}
