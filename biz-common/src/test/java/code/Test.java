package code;

import com.google.common.collect.Lists;
import com.zds.biz.util.GenerationUtil;

import java.util.Arrays;
import java.util.List;

public class Test {
    /**
     * 生成所有服务的po、dao、xml
     */
    public static void main(String[] args) {
        buildProjectByMybatisPlus();
    }

    public static void buildProjectByMybatisPlus() {
        System.setProperty("user.dir", "D:\\project\\gas\\server");
        //事故处置服务模块
//        buildServiceDispose();
        //文件服务模块
        //buildServiceFile();
        //燃气信息服务模块
        //buildServiceInfo();
        //用户服务模块
        buildServiceUser();
        //流程引擎服务模块
        //buildServiceFlow();
        //设备管理服务
//        buildServiceDevice();
        //从业资格考核服务
        //buildServiceExamine();
        //抄表信息化服务
//        buildServiceMeter();
    }

    public static void buildServiceDispose() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-dispose")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_dispose")
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceFile() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-file")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_file")
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceInfo() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-info")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_info")
                .ctablePrefixs(Arrays.asList("rqaq_resident_plan"))
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceUser() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-user")
                .ip("10.253.9.137")
                .userName("root")
                .userPassword("12345678")
                .dataBaseName("gas_data_user")
                .ctablePrefixs(List.of("gs_cutoff_plan","gs_cutoff_area_node","gs_cutoff_operation"))
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceFlow() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-flow")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_flow")
                .tablePrefixs(Lists.newArrayList("ACT_"))
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceDevice() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-device")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_device")
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceExamine() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-examine")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_examine")
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }

    public static void buildServiceMeter() {
        GenerationUtil.GenerateNeed generateNeed = GenerationUtil.GenerateNeed.builder()
                .moduleName("service-meter")
                .ip("36.140.200.252")
                .userName("zdsoft")
                .userPassword("B2jh#mCZeVqXxG&ALW1p")
                .dataBaseName("gas_data_meter")
                .build();
        GenerationUtil.quickGeneration(generateNeed);
    }
}
