package ch.etmles.Backend;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64DecodedMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final String fileExtension;

    public Base64DecodedMultipartFile(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
        this.fileExtension = contentType.split("/")[1];
        this.name = "upload" + "." + this.fileExtension;
        this.originalFilename = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public void transferTo(File dest) throws IOException {
        try (OutputStream out = new FileOutputStream(dest)) {
            out.write(content);
        }
    }
}
