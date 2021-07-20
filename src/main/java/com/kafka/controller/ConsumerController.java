package com.kafka.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.kafka.model.Employee;
import com.kafka.model.Employee2;
import com.kafka.repositiory.EmployeeService;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@Component
@RestController
public class ConsumerController {

	private final EmployeeService empService;

	public ConsumerController(EmployeeService empService) {
		this.empService = empService;
	}

	static final String TOPIC = "topic3";

	static final String GROUP = "INFO";

	@GetMapping("/insertDataToDb")
//	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public String consumeEmployeeData() {

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
		topicCount.put("topic5", new Integer(1));
		try {

			Map<String, List<KafkaStream<byte[], byte[]>>> consumerStream = consumerConnector
					.createMessageStreams(topicCount);

			List<KafkaStream<byte[], byte[]>> kStreamList = consumerStream.get("topic5");

			for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {

				ConsumerIterator<byte[], byte[]> consumerIte = kStreams.iterator();

				while (consumerIte.hasNext()) {

					String content = new String(consumerIte.next().message());

					String str = content.toString();
					Employee data = new Gson().fromJson(str, Employee.class);

					Employee2 saveEmpData = new Employee2();
					saveEmpData.setEmployeeId(data.getEmployeeId());//
					saveEmpData.setEmpName(data.getEmpName());
					saveEmpData.setDesignation(data.getDesignation());
					saveEmpData.setJobLevel(data.getJobLevel());
					saveEmpData.setRating(data.getRating());
					saveEmpData.setSalary(data.getSalary());
					empService.saveEmployee(saveEmpData);
				}
			}
			if (consumerConnector != null)
				consumerConnector.shutdown();

			return "Employee Data Inserted Successfully";

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
