package com.lee.convert;

import com.lee.entity.FileInfo;
import com.lee.entity.User;
import com.lee.entity.user.UserVo;
import com.lee.service.IFileInfoService;
import com.lee.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author KitenLee
 * * @date 2026/3/19
 */
@Service
public class UserConvert {

    private IUserService userService;
    private IFileInfoService fileInfoService;

    public UserConvert(IUserService userService, IFileInfoService fileInfoService) {
        this.userService = userService;
        this.fileInfoService = fileInfoService;
    }

    public String convertUserId2UserName(Long id){
        User user =  userService.getById(id);
        return user.getUsername();
    }

    public  UserVo Entity2Vo(User user){
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setId(user.getId());
        userVo.setRole(user.getRole());
        FileInfo fileInfo = fileInfoService.getById(user.getAvatarId());
        System.out.println(fileInfo);
        userVo.setAvatarUrl(fileInfo.getUrl());
        return userVo;
    }
}
