package com.lee.dao.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/22
 */
@Data
@Schema(description = "一个文件夹中所有文件信息")
public class OnePathFilesVo {
    @Schema(description = "路径")
    private String path;

    @Schema(description = "展示文件")
    private List<OnePathFileVo> files;
}
