package com.zds.user.controller.open;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.AuthKeyRes;
import com.zds.biz.vo.BaseResult;
import com.zds.user.service.DtGeoserverAuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "geoserver认证")
@RestController
@RequestMapping(value = "/open/dtGasApi/dtGeoserverAuth")
public class DtGeoserverAuthController {


    @Autowired
    private DtGeoserverAuthService dtGeoserverAuthService;

    /**
     * 获取间距详情
     */
    @GetMapping("/getAuthKey")
    public BaseResult<AuthKeyRes> getAuthKey(HttpServletRequest request){
        AuthKeyRes authKey = dtGeoserverAuthService.getAuthKey(request);
        BaseResult<AuthKeyRes> resultBase = new BaseResult<>();
        resultBase.setCode(200);
        resultBase.setMsg("获取成功");
        resultBase.setData(authKey);
        return resultBase;
    }

    /**
     * 检查 authKey 是否有效
     */
    @GetMapping("/checkAuthKeyD89E5923A2D2856")
    public BaseResult<String> checkAuthKey(@RequestParam("authKey") String authKey)throws Exception{
        boolean isOk = dtGeoserverAuthService.checkAuthKey(authKey);
        String result = "";
        if(isOk){
            result = "zdsoft";
        }
        BaseResult<String> resultBase = new BaseResult<>();
        resultBase.setCode(200);
        resultBase.setMsg("获取成功");
        resultBase.setData(result);
        return resultBase;
    }


}
