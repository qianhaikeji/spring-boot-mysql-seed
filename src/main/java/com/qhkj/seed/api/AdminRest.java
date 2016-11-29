package com.qhkj.seed.api;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.qhkj.seed.auth.JwtAuthReq;
import com.qhkj.seed.auth.JwtAuthRsp;
import com.qhkj.seed.entity.BaseQueryParams;
import com.qhkj.seed.entity.ResetPassword;
import com.qhkj.seed.exceptions.ServiceException;
import com.qhkj.seed.models.Admin;
import com.qhkj.seed.models.Permission;
import com.qhkj.seed.models.Role;
import com.qhkj.seed.services.AdminService;
import com.qhkj.seed.utils.JwtTokenHelper;
import com.qhkj.seed.utils.RestfulHelper;

@Component
@Path("/admin")
public class AdminRest extends BaseRest {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenHelper jwtTokenUtil;

	@Autowired
	private AdminService adminService;

    @GET
    @Path("/users")
    //@PreAuthorize("hasAuthority('TEST')")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList(@BeanParam BaseQueryParams bps) {
        Page<Admin> list = adminService.getUserList(bps);
        System.out.println(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        return Response.ok(list).build();
    }
    
    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoleList(@BeanParam BaseQueryParams bps) {
        Page<Role> list = adminService.getRoleList(bps);
        return Response.ok(list).build();
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPermList(@BeanParam BaseQueryParams bps) {
        List<Permission> list = adminService.getPermsByRole(1);
        return Response.ok(list).build();
    }
    
    @POST
    @Path("/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRole(Role role) {
        try{
            role = adminService.addRole(role);
            return Response.created(null).entity(role).build();
        }catch(Exception e){
            logger.warn(e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
    
    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAdminUser(Admin user) {
        try{
            user = adminService.addUser(user);
            return Response.created(null).entity(user).build();
        }catch(Exception e){
            logger.warn(e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
	   
	@POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdminToken(JwtAuthReq authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
            authToken.setDetails("admin");
            
            // 验证登陆账户密码
            final Authentication authentication = authenticationManager.authenticate(authToken);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Admin user = adminService.getUser(username);
            // Reload password post-security so we can generate token
            final String token = jwtTokenUtil.generateToken(username);
            Role role = adminService.getRole(user.getRoleId());
            List<Permission> perms = adminService.getPermsByRole(user.getRoleId());

            // Return the token            
            return Response.ok(new JwtAuthRsp(username, token, role.getName(), perms)).build();
        } catch (AuthenticationException e) {
            logger.warn(e.toString());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(RestfulHelper.errorResult("Bad credentials".equals(e.getMessage())?"密码错误":e.getMessage()))
                    .build();
        }
    }
	
    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyAdminBaseInfo(@PathParam("id") Integer id, Admin user) throws AuthenticationException {
        try {
            adminService.modifyUser(id, user);
            return Response.noContent().build();
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
    
    @PUT
    @Path("/users/{id}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetPassword(@PathParam("id") Integer id, ResetPassword data) throws AuthenticationException {
        try {
            adminService.resetPassword(id, data.getOldPassword(), data.getNewPassword());
            return Response.noContent().build();
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
	
    @PUT
    @Path("/roles/{id}/perms")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyRolePerms(@PathParam("id") Integer id, Integer[] perms) throws AuthenticationException {
        try {
            adminService.modifyRolePerms(id, perms);
            return Response.noContent().build();
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
	
    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAdminUser(@PathParam("id") Integer id) {
        try {
            adminService.deleteUser(id);
            return Response.noContent().build();
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
	
    @DELETE
    @Path("/roles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRole(@PathParam("id") Integer id) {
        try {
            adminService.deleteRole(id);
            return Response.noContent().build();
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(RestfulHelper.errorResult(e.getMessage()))
                    .build();
        }
    }
	
}
