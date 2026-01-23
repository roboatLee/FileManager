package com.lee.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lee.dao.file.FileType;
import com.lee.dao.file.OnePathFileVo;
import com.lee.dao.file.OnePathFilesVo;
import com.lee.service.FileService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author KitenLee
 * * @date 2026/1/4
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void printNowFile(File file){
        System.out.println("目前操作的文件为"+file);
    }

    /**
     * 获取FileContent
     * */
    @Override
    public  String getFileContent(File f){
        Reader reader = null;
        try{
            reader = new FileReader(f);
        }catch (Exception e){
            System.out.println(e);
        }
        int n = 0;
        StringBuilder FileContent = new StringBuilder();
        for (;;){

            try {
                n = reader.read();
            }catch (Exception e){
                System.out.println(e);
            }
            if(n == -1){
                break;
            }
            FileContent.append((char) n);
        }

        //关闭reader
        try{
            reader.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return FileContent.toString();
    }

    /**
     * 根据路径和名字，获取FileContent
     * */
    @Override
    public String getFileContent(String path,String name){
        File f = new File(path+"\\"+name);
        return getFileContent(f);
    }

    /**
     * 获得当前文件所在目录的全部文件和文件夹
     * @param f：当前文件
     * @return List<File>: 文件列表
     * */
    @Override
    public List<File> getAllFileAndDir(File f){
        File parent =  f.getParentFile();
        return Arrays.asList(parent.listFiles());
    }


    /**
     * 将当前的文件的文件所在目录和所有文件以json格式展示
     * @return
     * {
     *     path: path
     *     file:[
     *     {
     *         name: filename1,
     *         type: file
     *     }
     *      ,
     *      {
     *          name: filename1,
     *          type: dir
     *      }
     *     ]
     * }
     * */
    @Override
    public JsonNode getAllFileAndDirInJsonAsJson(File f){
        List<File> files =  getAllFileAndDir(f);
        return getFiles2Json(files);
    }

    /**
     * 以VO层 返回 获得当前文件所在目录的全部文件和文件夹
     * @param f：当前文件
     * @return OnePathFilesVo: VO层
     */
    @Override
    public OnePathFilesVo getAllFileAndDirAsDao(File f){
        List<File> files =  getAllFileAndDir(f);
        OnePathFilesVo onePathFilesVo =  makeFiles2DDao(f,files);
        onePathFilesVo.setPath(f.getParent());
        return  onePathFilesVo;
    }
    /**
     * 以VO层 返回 获得当前文件所在目录的全部文件和文件夹
     * @param f：当前文件
     * @return OnePathFilesVo: VO层
     */
    @Override
    public OnePathFilesVo getAllFileAndDirInDirAsDao(File f){
        List<File> files = Arrays.asList(f.listFiles());
        OnePathFilesVo onePathFilesVo =  makeFiles2DDao(f,files);
        onePathFilesVo.setPath(f.getPath());
        return  onePathFilesVo;
    }

    @Override
    public OnePathFilesVo makeFiles2DDao(File f, List<File> files ){
        /**
         * 将Files变成OnePathFilesVo
         * */
        OnePathFilesVo onePathFilesVo = new OnePathFilesVo();
        List<OnePathFileVo> onePathFileVos = new ArrayList<>();


        for (File file : files){
            OnePathFileVo onePathFileVo = new OnePathFileVo();
            onePathFileVo.setName(file.getName());
            onePathFileVo.setType(isFile(file));
            onePathFileVos.add(onePathFileVo);
        }
        onePathFilesVo.setFiles(onePathFileVos);
        return onePathFilesVo;
    }

    @Override
    public JsonNode getDirAsJson(String  path){
        File file = new File(path);
        List<File> files = Arrays.asList(file.listFiles());
        return getFiles2Json(files);
    }

    @Override
    public OnePathFilesVo getDir(String  path){
        File file = new File(path);
        return  getAllFileAndDirInDirAsDao(file);
    }
    @Override
    public JsonNode getFiles2Json(List<File> files){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        String path = files.get(0).getParentFile().getPath();

        root.put("path",path);
        ArrayNode array = mapper.createArrayNode();

        for (File oneFile : files){
            ObjectNode file = mapper.createObjectNode();
            file.put("name",oneFile.getName());
            file.put("type",isFile(oneFile).name());
            array.add(file);
        }
        root.set("files",array);
        return  root;
    }
    @Override
    public JsonNode getDefaultFilesAsJson(){
        File file = new File("D:\\User\\document\\Json\\Probaility\\L01.1.json");
        return getAllFileAndDirInJsonAsJson(file);
    }
    /**
     *
     * */
    @Override
    public OnePathFilesVo getDefaultFiles(){
        File file = new File("D:\\User\\document\\Json\\Probaility\\L01.1.json");
        return getAllFileAndDirAsDao(file);
    }
    /**
     * 得到当前文件的所有文件
     * */
    @Override
    public OnePathFilesVo getParentFiles(String path){
        File file = new File(path);
        return getAllFileAndDirAsDao(file);
    }

    @Override
    public JsonNode getParentFilesAsJson(String path){
        File file = new File(path);
        return getAllFileAndDirInJsonAsJson(file);
    }

    /**
     *
     * */
    @Override
    public JsonNode getPathFiles(String path){
        File file = new File(path);

        return getAllFileAndDirInJsonAsJson(file);
    }

    @Override
    public FileType isFile (File file){
            if (file.isDirectory()) {
                return FileType.DIR;
            } else if (file.isFile()) {
                return FileType.FILE;
            }
            else return null;
    }
    @Override
    public List<String> getDisks(){
        File[] roots = File.listRoots();
        return Arrays.stream(roots)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

    }


}
