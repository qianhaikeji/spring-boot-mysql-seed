package com.qhkj.zyzb.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qhkj.zyzb.auth.CustomUserDetails;
import com.qhkj.zyzb.entity.BaseQueryParams;
import com.qhkj.zyzb.exceptions.ServiceException;
import com.qhkj.zyzb.models.Admin;
import com.qhkj.zyzb.models.Permission;
import com.qhkj.zyzb.models.Role;
import com.qhkj.zyzb.models.RolePermRel;
import com.qhkj.zyzb.repository.AdminRepo;
import com.qhkj.zyzb.repository.PermissionRepo;
import com.qhkj.zyzb.repository.RolePermRelRepo;
import com.qhkj.zyzb.repository.RoleRepo;

@Service
public class AdminService extends BaseService implements UserDetailsService {
	@Autowired private AdminRepo adminRepo;
	@Autowired private RoleRepo roleRepo;
	@Autowired private PermissionRepo permissionRepo;
	@Autowired private RolePermRelRepo rolePermRelRepo;
	
	@Inject
    private PasswordEncoder passwordEncoder;
		
    private static final String SUPER_USER_NAME = "admin";
    private static final String SUPER_USER_PASSWORD = "admin123";
    private static final String SUPER_USER_EMAIL = "admin@qq.com";
    private static final String SUPER_USER_PHONE = "";
    private static final String SUPER_USER_ROLE = "超级管理员";
    
    // 权限列表
    private static final String[][] PERM_LIST = {
            {"测试", "Test"},
            {"测试1", "Test1"},
            {"测试2", "Test2"}
    }; 
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户 '%s' 不存在.", username));
        } else {
            return new CustomUserDetails(user.getUsername(), user.getPassword(), getAuthorities(user.getRoleId()));
        }
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities(Integer roleId) {        
        Collection<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
        //get permissions from role
        List<Permission> perms = getPermsByRole(roleId);
        for (Permission perm : perms){
            authList.add(new SimpleGrantedAuthority(perm.getPerm()));
        }
        return authList;
    }

    public Admin getSuperUser(){
    	Admin user = adminRepo.findByUsername(SUPER_USER_NAME);
    	 if (user == null) {
    	     Role superRole = roleRepo.findByName(SUPER_USER_ROLE);
             user = addUser(new Admin(SUPER_USER_NAME, SUPER_USER_EMAIL, SUPER_USER_PHONE, SUPER_USER_PASSWORD, superRole.getId()));             
         }    
        return user;
    }
    
    public void initSuperRolePerm() {
        List<Permission> permList = permissionRepo.findAll();
        if (permList.size() <= 0){
            for(String[] a : PERM_LIST){
                Permission perm = new Permission(a[0], a[1]);
                perm = addPermission(perm);
                permList.add(perm);
            }
        }
        
        List<Role> roleList = roleRepo.findAll();
        if (roleList.size() <= 0){
            Role role = new Role(SUPER_USER_ROLE);
            addRole(role);
        }
        
        List<RolePermRel> list = rolePermRelRepo.findAll();
        Role superRole = roleRepo.findByName(SUPER_USER_ROLE);
        if (list.size() <= 0){
            for (Permission perm : permList){
                RolePermRel r = new RolePermRel(superRole.getId(), perm.getId());  
                addRolePermRel(r);
            }
        }
    }
    
    public List<Permission> getPermsByRole(Integer rid) {
        List<Permission> list = permissionRepo.findByRoleId(rid);
        return list;
    }
    
    public Admin getUser(String username) {
    	return adminRepo.findByUsername(username);
    }
    
    public Role getRole(Integer id) {
        return roleRepo.findOne(id);
    }
    
    public Page<Admin> getUserList(BaseQueryParams bps) {
        Pageable pageable = new PageRequest(bps.getPage(), bps.getLimit());
        return adminRepo.findAll(pageable);
    }
    
    public Page<Role> getRoleList(BaseQueryParams bps) {
        Pageable pageable = new PageRequest(bps.getPage(), bps.getLimit());
        return roleRepo.findAll(pageable);
    }
    
	public Admin addUser(Admin data) throws ServiceException {
	    Admin user = new Admin(data.getUsername(), data.getEmail(), data.getPhone(), passwordEncoder.encode(data.getPassword()), data.getRoleId());
	    try{
	    	return adminRepo.save(user);
	    } catch (Exception e) {
	        throw new ServiceException(e.getMessage());
	    }
	}
	
    public Role addRole(Role data) throws ServiceException {
        try{
            return roleRepo.save(data);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    
    public Permission addPermission(Permission data) throws ServiceException {
        try{
            return permissionRepo.save(data);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    
    public RolePermRel addRolePermRel(RolePermRel data) throws ServiceException {
        try{
            return rolePermRelRepo.save(data);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public void modifyUser(Integer id, Admin user) throws ServiceException {
    	Admin exist = adminRepo.findOne(id);
        if (exist == null) {
            throw new ServiceException("用户 [" + id + "] 不存在!");
        }
        
        if (!roleRepo.exists(user.getRoleId())) {
            throw new ServiceException("角色 [" + id + "] 不存在!");
        }
        
        exist.setPhone(user.getPhone());
        exist.setEmail(user.getEmail());
        exist.setRoleId(user.getRoleId());

        try {
        	adminRepo.save(exist);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
	
	@Transactional
	public void modifyRolePerms(Integer id, Integer[] perms) throws ServiceException {
        Role exist = roleRepo.findOne(id);
        if (exist == null) {
            throw new ServiceException("角色 [" + id + "] 不存在!");
        }
        
        List<RolePermRel> rprList = rolePermRelRepo.findByRoleId(id);
        for (RolePermRel rpr : rprList) {
            rolePermRelRepo.delete(rpr.getId());
        }
        
        for (Integer pid : perms){
            if(!permissionRepo.exists(pid)){
                throw new ServiceException("权限 [" + pid + "] 不存在!");
            }
            
            RolePermRel rpr = new RolePermRel(id, pid);
            try {
                rolePermRelRepo.save(rpr);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    public void deleteUser(Integer id) throws ServiceException {
        Admin exist = adminRepo.findOne(id);
        if (exist == null){
            throw new ServiceException("用户 [" + id + "] 不存在!");
        }

        if (SUPER_USER_NAME.equals(exist.getUsername())){
            throw new ServiceException("超级管理员不可删除!");
        }

        try {
            adminRepo.delete(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    
    public void deleteRole(Integer id) throws ServiceException {
        Role exist = roleRepo.findOne(id);
        if (exist == null){
            throw new ServiceException("角色 [" + id + "] 不存在!");
        }

        if (SUPER_USER_ROLE.equals(exist.getName())){
            throw new ServiceException("超级管理员角色不可删除!");
        }

        try {
            roleRepo.delete(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void resetPassword(Integer uid, String password, String newPassword) throws ServiceException {
        if (password == null || newPassword == null || password.isEmpty() || newPassword.isEmpty()){
            throw new ServiceException("无效的密码");
        }
    
        Admin user = adminRepo.findOne(uid);
        if (user == null){
            throw new ServiceException("用户 [" + uid + "] 不存在!");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new ServiceException("旧密码错误!");
        }
            
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            adminRepo.save(user);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
