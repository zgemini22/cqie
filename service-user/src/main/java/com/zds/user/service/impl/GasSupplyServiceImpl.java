package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zds.biz.vo.BaseResult;
import com.zds.user.dao.GasSupplyDao;
import com.zds.biz.constant.GasSupplyEnum;
import com.zds.user.po.GasSupplyPo;
import com.zds.user.service.GasSupplyService;
import com.zds.biz.vo.GasSupplyQueryRequest;
import com.zds.biz.vo.GasSupplyRequest;
import com.zds.biz.vo.GasSupplyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GasSupplyServiceImpl extends ServiceImpl<GasSupplyDao, GasSupplyPo> implements GasSupplyService {

    @Override
    public IPage<GasSupplyResponse> selectPage(GasSupplyQueryRequest request) {
        // 使用自定义的 XML 查询方法，因为需要关联查询企业名称和地址
        Page<GasSupplyPo> page = new Page<>(request.getPage(), request.getPageSize());

        // 调用 DAO 的自定义查询方法
        List<GasSupplyResponse> records = baseMapper.selectPageList(
                page,
                request.getStartTime(),
                request.getEndTime(),
                request.getStatus(),
                null // supplyType 参数，如果查询条件中有的话可以传入
        );

        // 创建响应分页对象
        IPage<GasSupplyResponse> responsePage = new Page<>();
        responsePage.setRecords(records);
        responsePage.setTotal(page.getTotal());
        responsePage.setCurrent(page.getCurrent());
        responsePage.setSize(page.getSize());

        // 处理返回数据，补充枚举名称
        responsePage.getRecords().forEach(this::enrichResponse);

        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> add(GasSupplyRequest request) {
        try {
            if (request.getStartTime().after(request.getEndTime())) {
                return BaseResult.failure("计划供气开始时间不能晚于结束时间");
            }

            GasSupplyPo po = new GasSupplyPo();
            BeanUtils.copyProperties(request, po);

            po.setStatus(GasSupplyEnum.STATUS_UNSUBMITTED.getCode());
            po.setDeleted(0);

            // 设置组织ID（根据实际业务获取）
            po.setOrgId(27); // 示例值

            Integer currentUserId = getCurrentUserId();
            Date now = new Date();
            po.setCreateId(currentUserId);
            po.setUpdateId(currentUserId);
            po.setCreateTime(now);
            po.setUpdateTime(now);

            boolean result = this.save(po);
            return result ? BaseResult.success("新增成功") : BaseResult.failure("新增失败");
        } catch (Exception e) {
            log.error("新增供气计划失败", e);
            return BaseResult.failure("新增失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> update(GasSupplyRequest request) {
        try {
            if (request.getId() == null) {
                return BaseResult.failure("ID不能为空");
            }

            GasSupplyPo existPo = this.getById(request.getId());
            if (existPo == null) {
                return BaseResult.failure("供气计划不存在");
            }

            if (request.getStartTime().after(request.getEndTime())) {
                return BaseResult.failure("计划供气开始时间不能晚于结束时间");
            }

            GasSupplyPo po = new GasSupplyPo();
            BeanUtils.copyProperties(request, po);
            po.setUpdateId(getCurrentUserId());
            po.setUpdateTime(new Date());

            boolean result = this.updateById(po);
            return result ? BaseResult.success("更新成功") : BaseResult.failure("更新失败");
        } catch (Exception e) {
            log.error("更新供气计划失败", e);
            return BaseResult.failure("更新失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> delete(Integer id) {
        try {
            GasSupplyPo po = new GasSupplyPo();
            po.setId(id);
            po.setDeleted(1);
            boolean result = this.updateById(po);
            return result ? BaseResult.success("删除成功") : BaseResult.failure("删除失败");
        } catch (Exception e) {
            log.error("删除供气计划失败", e);
            return BaseResult.failure("删除失败：" + e.getMessage());
        }
    }

    @Override
    public GasSupplyResponse getDetail(Integer id) {
        GasSupplyResponse response = baseMapper.selectDetailById(id);
        if (response != null) {
            enrichResponse(response);
        }
        return response;
    }

    /**
     * 丰富响应数据（补充枚举名称等）
     */
    private void enrichResponse(GasSupplyResponse response) {
        if (response == null) return;

        // 状态名称
        response.setSupplyStatusName(getStatusName(response.getSupplyStatus()));

        // 供气类型名称
        response.setSupplyTypeName(getSupplyTypeName(response.getSupplyType()));

        // 气源类型（固定值）
        response.setGasType("27");
        response.setGasTypeName("天然气");

        // 如果 supplyArea 为空，设置默认值
        if (response.getSupplyArea() == null || response.getSupplyArea().isEmpty()) {
            response.setSupplyArea("-");
        }
    }

    /**
     * 获取供气类型名称
     */
    private String getSupplyTypeName(String supplyType) {
        if (supplyType == null) {
            return "";
        }
        switch (supplyType) {
            case "NEW":
                return "新建供气";
            case "RESTORE":
                return "恢复供气";
            default:
                return supplyType;
        }
    }

    /**
     * 获取状态名称
     */
    private String getStatusName(String status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case "UNSUBMITTED":
                return "未提交";
            case "PENDING":
                return "待执行";
            case "EXECUTING":
                return "执行中";
            case "COMPLETED":
                return "已完成";
            case "CANCELLED":
                return "已取消";
            default:
                return status;
        }
    }

    /**
     * 获取当前用户ID
     * TODO: 从登录用户获取
     */
    private Integer getCurrentUserId() {
        return 22;
    }
}