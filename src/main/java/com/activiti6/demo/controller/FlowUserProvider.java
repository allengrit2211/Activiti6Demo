package com.activiti6.demo.controller;

import com.activiti6.demo.service.ActivitiService;
import com.activiti6.demo.util.R;
import com.activiti6.demo.vo.GroupReq;
import com.activiti6.demo.vo.MembershipReq;
import com.activiti6.demo.vo.UserReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: Allen
 * @Date: 2018/7/30 14:33
 * @Description: 用户接口定义
 */
@RestController
@Slf4j
@Api(value = "用户相关控制类")
@RequestMapping({"/flowUser"})
public class FlowUserProvider{

    @Autowired
    ActivitiService activitiService;

    @Autowired
    IdentityService identityService;


    /***
     * 新增组
     * @param groupReq
     */
    @PostMapping(value = "/newGroup")
    @ApiOperation(value = "新增用户组", notes = "新增用户组")
    public R newGroup(@RequestBody @ApiParam(value = "新增用户组请求") @Valid GroupReq groupReq) {
        try {

            if (StringUtils.isBlank(groupReq.getName())) {
                return R.error(-1, "用户组名称不能为空");
            }

            //验证是否保存成功
            Group groupInDb = identityService.createGroupQuery().groupId(groupReq.getId()).singleResult();
            if (groupInDb != null) {
                return R.error(-2, "ID已存在");
            }


            Group group = identityService.newGroup(groupReq.getId());
            group.setName(groupReq.getName());
            group.setType("assignment");
            //保存用户组
            identityService.saveGroup(group);

            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/deleteGroup")
    @ApiOperation(value = "删除用户组", notes = "删除用户组")
    public R deleteGroup(@RequestBody @ApiParam(value = "新增用户组ID") @Valid GroupReq groupReq) {
        try {
            identityService.deleteGroup(groupReq.getId());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/newUser")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public R newUser(@RequestBody @ApiParam(value = "新增用户请求对象") @Valid UserReq userReq) {
        try {


            //验证是否保存成功
            User userInDb = identityService.createUserQuery().userId(userReq.getId()).singleResult();
            if (userInDb != null) {
                return R.error(-2, "用户ID已存在");
            }

            User user = identityService.newUser(userReq.getId());
            user.setEmail(userReq.getEmail());
            user.setFirstName(userReq.getFirstName());
            user.setLastName(userReq.getLastName());
            user.setPassword(userReq.getPassword());
            identityService.saveUser(user);

            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }


    @PostMapping(value = "/deleteUser")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public R deleteUser(@RequestBody @ApiParam(value = "删除用户请求对象") @Valid UserReq userReq) {
        try {
            identityService.deleteUser(userReq.getId());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/createMembership")
    @ApiOperation(value = "新增用户与组关联", notes = "新增用户与组关联")
    public R createMembership(@RequestBody @ApiParam(value = "用户与组关联请求对象") @Valid MembershipReq membershipReq) {
        try {

            User userInDb = identityService.createUserQuery().userId(membershipReq.getUserId()).singleResult();
            if (userInDb == null) {
                return R.error(-2, "用户ID不存在");
            }

            //验证是否保存成功
            Group groupInDb = identityService.createGroupQuery().groupId(membershipReq.getGroupId()).singleResult();
            if (groupInDb == null) {
                return R.error(-2, "用户组ID不存在");
            }

            long count = identityService.createUserQuery().userId(membershipReq.getUserId()).memberOfGroup(membershipReq.getGroupId()).count();
            if (count > 0) {
                return R.error(-2, "用户与组关联ID已存在");
            }

            identityService.createMembership(membershipReq.getUserId(), membershipReq.getGroupId());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }


    @PostMapping(value = "/deleteMembership")
    @ApiOperation(value = "删除用户与组关联", notes = "删除用户与组关联")
    public R deleteMembership(@RequestBody @ApiParam(value = "用户与组关联请求对象") @Valid MembershipReq membershipReq) {
        try {
            identityService.deleteMembership(membershipReq.getUserId(), membershipReq.getGroupId());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }


}
