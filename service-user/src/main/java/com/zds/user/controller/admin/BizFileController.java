package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.user.po.BizFile;
import com.zds.user.service.BizFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "业务文件管理")
@RestController
@RequestMapping(value = "/tpc/admin/bizFile")
public class BizFileController {

    // 注入 Service
    @Resource
    private BizFileService bizFileService;

    // 上传
    @Authorization
    @ApiOperation("上传业务文件")
    @PostMapping("/upload")
    public BaseResult<BizFile> upload(@RequestBody BizFile bizFile) {
        // 只保存文件信息到数据库，不处理文件上传
        bizFileService.uploadFile(bizFile);
        return BaseResult.success(bizFile);
    }
    // 查询列表（走Service！）
    @Authorization
    @ApiOperation("根据业务ID/类型查询文件列表")
    @PostMapping(value = "/queryList")
    public BaseResult queryList(@RequestBody BizFile query) {
        return BaseResult.success(bizFileService.queryFileList(query));
    }

    // 删除（走Service！）
    @Authorization
    @ApiOperation("逻辑删除文件")
    @PostMapping("/delete")
    public BaseResult delete(@RequestBody BizFile bizFile) {
        boolean success = bizFileService.deleteFile(bizFile.getId());
        return BaseResult.judgeOperate(success);
    }
}