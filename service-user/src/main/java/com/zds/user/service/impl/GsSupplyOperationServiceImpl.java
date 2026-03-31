// GsSupplyOperationServiceImpl.java
package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zds.biz.vo.GasSupplyOperationQueryRequest;
import com.zds.biz.vo.GasSupplyOperationResponse;
import com.zds.user.dao.GsSupplyOperationDao;
import com.zds.user.po.GsSupplyOperationPo;
import com.zds.user.service.GsSupplyOperationService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

@Service
public class GsSupplyOperationServiceImpl extends ServiceImpl<GsSupplyOperationDao, GsSupplyOperationPo>
        implements GsSupplyOperationService {

    @Override
    public IPage<GasSupplyOperationResponse> selectPage(GasSupplyOperationQueryRequest request) {
        Page<GsSupplyOperationPo> page = new Page<>(request.getPage(), request.getPageSize());

        LambdaQueryWrapper<GsSupplyOperationPo> wrapper = new LambdaQueryWrapper<>();

        // 明确指定使用 so 表的 deleted 字段
        wrapper.apply("so.deleted = 0");

        // 时间范围查询 - 使用实际开始时间 start_time
        if (StrUtil.isNotBlank(request.getPlanStartTime())) {
            LocalDateTime startOfDay = LocalDate.parse(request.getPlanStartTime()).atStartOfDay();
            wrapper.ge(GsSupplyOperationPo::getStartTime, startOfDay);
        }

        if (StrUtil.isNotBlank(request.getPlanEndTime())) {
            LocalDateTime endOfDay = LocalDate.parse(request.getPlanEndTime()).atTime(LocalTime.MAX);
            wrapper.le(GsSupplyOperationPo::getStartTime, endOfDay);
        }

        // 供气类型查询 - 通过 plan_id 判断
        if (StrUtil.isNotBlank(request.getSupplyType())) {
            if ("PLANNED".equals(request.getSupplyType())) {
                wrapper.gt(GsSupplyOperationPo::getPlanId, 50);
            } else if ("UNPLANNED".equals(request.getSupplyType())) {
                wrapper.le(GsSupplyOperationPo::getPlanId, 50);
            }
        }

        wrapper.orderByDesc(GsSupplyOperationPo::getCreateTime);

        // 使用关联查询方法
        IPage<GsSupplyOperationPo> poPage = baseMapper.selectPageWithRelation(page, wrapper);

        // 转换为响应对象
        IPage<GasSupplyOperationResponse> responsePage = new Page<>();
        BeanUtil.copyProperties(poPage, responsePage, "records");

        responsePage.setRecords(poPage.getRecords().stream()
                .map(this::convert)
                .collect(Collectors.toList()));

        return responsePage;
    }

    private GasSupplyOperationResponse convert(GsSupplyOperationPo po) {
        GasSupplyOperationResponse resp = new GasSupplyOperationResponse();
        resp.setId(po.getId());
        resp.setPlanStartTime(po.getStartTime());
        resp.setPlanEndTime(po.getEndTime());

        // 根据 plan_id 计算供气类型
        if (po.getPlanId() != null && po.getPlanId() > 50) {
            resp.setSupplyType("PLANNED");
        } else {
            resp.setSupplyType("UNPLANNED");
        }

        resp.setSupplyReason(po.getSupplyReason());
        resp.setAffectHouseholds(po.getAffectedHouseholds());
        resp.setRemarks(po.getRemark());
        resp.setEnterpriseName(po.getEnterpriseName());
        resp.setSupplyArea(po.getSupplyArea());

        return resp;
    }
}