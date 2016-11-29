package com.qhkj.zyzb.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhkj.zyzb.utils.ConfigHelper;

/**
 * 
 * @author denghan
 * 系统初始化服务
 */
@Service
public class InitialService extends BaseService {
    @Autowired private AdminService adminService;
    
    @Autowired private ConfigHelper configHelper;
    
    @PostConstruct
    protected void initialize() {
        initAdmin();
        
        /**
         * 此处添加测试所需的测试函数
         */
        if (configHelper.isMockEnable()){
            
        }
    }
    
    private void initAdmin(){
        adminService.initSuperRolePerm();
        adminService.getSuperUser();
    }
}
