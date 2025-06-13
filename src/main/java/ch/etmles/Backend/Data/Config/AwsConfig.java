package ch.etmles.Backend.Data.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

import java.io.File;

@Configuration
public class AwsConfig {

    @Bean
    public AwsCredentialsProvider getAwsCredentials() {
        String home = System.getProperty("user.home");
        File credentialsFile = new File(home + "/.aws/credentials");

        if (credentialsFile.exists()) {
            // Credentials from ~/.aws/credentials
            return ProfileCredentialsProvider.create();
        } else {
            // Credentials from .env file
            String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
            String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

            if (accessKey == null || secretKey == null) {
                throw new RuntimeException("AWS credentials not found in environment variables");
            }

            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        }
    }
}
