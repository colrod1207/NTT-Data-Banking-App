package org.example.banking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class BankingApplication {

    private final DataSource dataSource;

    public BankingApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    // ✅ Este método prueba la conexión a la base de datos al iniciar la app
    @Bean
    CommandLineRunner testConnection() {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ Conexión exitosa a la base de datos: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.err.println("❌ Error de conexión: " + e.getMessage());
            }
        };
    }
}
