package com.lee.service.impl;

import com.lee.entity.FileInfo;
import com.lee.entity.User;
import com.lee.mapper.UserMapper;
import com.lee.security.JwtUtil;
import com.lee.service.IFileInfoService;
import com.lee.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.service.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2026-03-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private MinioService minioService;
    private IFileInfoService fileInfoService;

    public UserServiceImpl(MinioService minioService, IFileInfoService fileInfoService) {
        this.minioService = minioService;
        this.fileInfoService = fileInfoService;
    }

    @Override
    public String uploadAvatar(Long userId, MultipartFile file) {
        String objectName = "avatar/" + userId + ".png";
        String url = minioService.upload(file, objectName);
        return url;
    }

    @Override
    public String uploadAvatar(MultipartFile file, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId =   JwtUtil.getUserIdInt(token);
        String objectName = "avatar/" + userId + ".png";
        String url = "";

        try{
            url = minioService.upload(file, objectName);
        }catch (Exception e){
            System.out.println(e);
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setCreateTime(LocalDateTime.now());
        fileInfo.setUploaderId(userId);
        fileInfo.setUrl(url);
        fileInfo.setObjectName(objectName);
        fileInfoService.save(fileInfo);

        Long avatarId = fileInfo.getId();
        updateAvatarId(userId,avatarId);

        return url;
    }

    public String tokenToUserId(){
        return null;
    }

    public void updateAvatarId(Long userId, Long avatarId) {
        User user = new User();
        user.setId(userId);
        user.setAvatarId(avatarId);
        this.updateById(user);
    }

}
