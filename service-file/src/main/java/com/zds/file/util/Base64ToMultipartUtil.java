package com.zds.file.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64ToMultipartUtil implements MultipartFile {

    private final byte[] fileContent;

    private final String extension;

    private final String contentType;

    private final String originalFileName;

    /**
     * @param base64
     * @param dataUri 格式类似于: data:image/png;base64
     */
    public Base64ToMultipartUtil(String base64, String dataUri, String originalFileName) {
        this.fileContent = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        this.extension = dataUri.split(";")[0].split("/")[1];
        this.contentType = dataUri.split(";")[0].split(":")[1];
        this.originalFileName = originalFileName;
    }

    public Base64ToMultipartUtil(String base64, String extension, String contentType, String originalFileName) {
        this.fileContent = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        this.extension = extension;
        this.contentType = contentType;
        this.originalFileName = originalFileName;
    }

    @Override
    public String getName() {
        return "param_" + System.currentTimeMillis();
    }

    @Override
    public String getOriginalFilename() {
        if (originalFileName != null) {
            return originalFileName;
        } else if (extension != null && !"".equals(extension)) {
            return "file_" + System.currentTimeMillis() + "." + extension;
        } else {
            return "file_" + System.currentTimeMillis() + contentType;
        }
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public ByteArrayInputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent);
        }
    }
}
