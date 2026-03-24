package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowAddOutsideFormRequest;
import com.zds.biz.vo.request.flow.FlowOutsideFormRequest;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.flow.FlowOutsideFormResponse;

import java.util.List;

public interface FormService {

    boolean saveForm(FlowAddOutsideFormRequest request);

    List<FlowFormTypeReponse> findFormType();

    List<FlowFormTypeReponse> findFlowFormType();

    List<FlowFormTypeReponse> findNoFlowFormType();

    String getOutsideForm(String type);

    IPage<FlowOutsideFormResponse> getOutsideFormList(FlowOutsideFormRequest request);

    boolean outsideFormDelete(IdRequest request);
}
