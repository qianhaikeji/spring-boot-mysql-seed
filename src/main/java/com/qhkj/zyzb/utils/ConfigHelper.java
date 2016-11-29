package com.qhkj.zyzb.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigHelper extends BaseHelper {
	
	@Value("${server.upload.dir}")
	private String uploadDir;
	
	@Value("${server.mockEnable}")
	private boolean mockEnable;
    
	public String getUploadDir(){
		return this.uploadDir;
	}
	
	public boolean isMockEnable(){
	    return this.mockEnable;
	}
	
}
