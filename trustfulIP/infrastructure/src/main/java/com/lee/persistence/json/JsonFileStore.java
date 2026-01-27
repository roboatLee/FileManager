package com.lee.persistence.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
public class JsonFileStore {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path filePath;

    public JsonFileStore(Path filePath) {
        this.filePath = filePath;
    }

    public synchronized <T> List<T> readList(Class<T> type) {
        if (Files.notExists(filePath)) {
            return new ArrayList<>();
        }
        try {
            String json = new String(
                    Files.readAllBytes(filePath),
                    StandardCharsets.UTF_8
            );

            return mapper.readValue(
                    json,
                    mapper.getTypeFactory()
                            .constructCollectionType(List.class, type)
            );
        } catch (IOException e) {
            throw new RuntimeException("读取 JSON 文件失败: " + filePath, e);
        }
    }

    public synchronized <T> void writeList(List<T> data) {
        try {
            Files.createDirectories(filePath.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException("写入 JSON 文件失败: " + filePath, e);
        }
    }
}
