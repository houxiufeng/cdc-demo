package com.example.demo;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void initCDC() throws IOException {
		// Define the configuration for the Debezium Engine with MySQL connector...
		final Properties props = new Properties();
		props.setProperty("name", "engine");
		props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
		props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "/Users/houxiufeng/test/offsets.dat");
		props.setProperty("offset.flush.interval.ms", "1000");
		/* begin connector properties */
		props.setProperty("database.hostname", "192.168.123.184");
		props.setProperty("database.port", "3306");
		props.setProperty("database.user", "root");
		props.setProperty("database.password", "~Yagamihxf0");
		props.setProperty("database.include.list", "test_db");
		props.setProperty("database.server.id", "10181");
		props.setProperty("include.schema.changes", "false");
		props.setProperty("database.server.name", "fitz-engine");
		props.setProperty("database.history", "io.debezium.relational.history.FileDatabaseHistory");
		props.setProperty("database.history.file.filename", "/Users/houxiufeng/test/dbhistory.dat");

		// Create the engine with this configuration ...
		try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
				.using(props)
				.notifying(record -> {
					System.out.println(record.value());
				}).build()
		) {
			// Run the engine asynchronously ...
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(engine);

			// Do something else or wait for a signal or an event
		}
		// Engine is stopped when the main code is finished
		System.out.println("here is end");

	}

}
