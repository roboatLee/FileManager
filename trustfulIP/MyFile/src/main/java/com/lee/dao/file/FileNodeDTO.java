package com.lee.dao.file;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * @author KitenLee
 * * @date 2026/1/22
 */


@Data
@Schema(description = "文件节点信息")
public class FileNodeDTO {

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "类型")
    private FileType type;
}