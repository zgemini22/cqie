/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zds.biz.vo.response.flow;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@ApiModel(description = "我的待办返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowMyTaskResponse {

    @ApiModelPropertyCheck("流程id")
    private String id;

    @ApiModelPropertyCheck("流程节点名称")
    private String name;

    @ApiModelPropertyCheck("代办人")
    private String assignee;

    @ApiModelPropertyCheck("流程定义id")
    private String processDefinitionId;

    @ApiModelPropertyCheck("表单key")
    private String formKey;

    @ApiModelPropertyCheck("流程创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
