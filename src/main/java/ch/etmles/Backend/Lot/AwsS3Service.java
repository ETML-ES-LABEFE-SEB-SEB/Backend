package ch.etmles.Backend.Lot;

import org.hibernate.boot.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.UUID;

@Service
public class AwsS3Service {

    private S3Client s3client;
    private String bucket = "projlabefe.sebseb";

    public AwsS3Service() {

        // It takes the default credentials in ~/.aws/credentials
        s3client = S3Client.builder().build();
    }

    public String saveFileToBucket(MultipartFile file){
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return key;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to upload the picture to S3", e);
        }
    }
}
