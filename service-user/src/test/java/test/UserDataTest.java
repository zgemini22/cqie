package test;

import com.alibaba.fastjson.JSON;
import com.zds.biz.vo.request.user.BiKpiNormRequest;
import com.zds.biz.vo.response.user.BiKpiNormVo;
import com.zds.user.UserApplication;
import com.zds.user.dao.*;
import com.zds.user.manager.KpiNormManager;
import com.zds.user.po.*;
import com.zds.user.service.KpiNormService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(classes = UserApplication.class)
public class UserDataTest {

    @Autowired
    private TblBiDataDao tblBiDataDao;

    @Autowired
    private KpiNormManager kpiNormManager;

    @Autowired
    private KpiNormService kpiNormService;

    @Autowired
    private KpiNormClassifyDao kpiNormClassifyDao;

    @Autowired
    private KpiNormInfoDao kpiNormInfoDao;

    @Autowired
    private KpiNormInfoFormulaDao kpiNormInfoFormulaDao;

    @Autowired
    private KpiDataDao kpiDataDao;

    @Autowired
    private KpiDataDetailDao kpiDataDetailDao;

    @Autowired
    private TblAreaNodeDao areaNodeDao;

    /**
     * 字符串替换
     */
    @Test
    public void convertString() {
        int count = 0;
        // 要替换的子字符串和替换后的子字符串
        String targetString = "中梁山渝能燃气公司";
        String replaceString = "重燃渝北分公司";
        //查询数据
        List<TblBiData> tblBiDataList = tblBiDataDao.selectList(TblBiData.getWrapper()
                .eq(TblBiData::getDeleted, false)
                .like(TblBiData::getBiDataExample, "中梁山渝能燃气公司"));
        System.out.println("待替换数量:"+tblBiDataList.size());
        for (TblBiData vo : tblBiDataList) {
            count++;
            // 使用 replace 方法进行替换
            String updatedString = vo.getBiDataExample().replace(targetString, replaceString);
            vo.setBiDataExample(updatedString);
            tblBiDataDao.updateById(vo);
        }
        System.out.println("已替换数量:"+count);
    }

    @Test
    public void test() {
        kpiNormManager.computeNormById(1L, null, null);
    }

    @Test
    public void findByCode() throws ParseException {
        BiKpiNormRequest request = new BiKpiNormRequest();
        request.setNormCode("bgfw-jmrhaj-wcl");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        request.setStartTime(sdf.parse("2024-10-01 00:00:00"));
        request.setEndTime(sdf.parse("2024-10-31 23:59:59"));
        request.setLastSearchFlag(true);
        request.setBenchmarkSearchFlag(true);
        request.setCompanySearchFlag(true);
        request.setStreetSearchFlag(true);
        BiKpiNormVo vo = kpiNormService.findByCode(request);
        System.out.println(JSON.toJSONString(vo));
    }

    @Test
    public void updateNormStreet() throws ParseException {
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(KpiNormClassify.getWrapper()
                .eq(KpiNormClassify::getDeleted, false)
                .in(KpiNormClassify::getClassifyName, Arrays.asList("感知设备监测率", "入户安检率")));
        List<KpiNormInfo> infoList = kpiNormInfoDao.selectList(KpiNormInfo.getWrapper()
                .eq(KpiNormInfo::getDeleted, false)
                .in(KpiNormInfo::getNormClassifyId, classifyList.stream().map(KpiNormClassify::getId).collect(Collectors.toList())));
        List<Long> ids = kpiDataDao.selectList(KpiData.getWrapper()
                .eq(KpiData::getDeleted, false)
                .eq(KpiData::getDataType, 2))
                .stream().map(KpiData::getId).collect(Collectors.toList());
        List<Long> dataIds = kpiNormInfoFormulaDao.selectList(KpiNormInfoFormula.getWrapper()
                .in(KpiNormInfoFormula::getNormId, infoList.stream().map(KpiNormInfo::getId).collect(Collectors.toList()))
                .notIn(KpiNormInfoFormula::getDataId, ids))
                .stream().map(KpiNormInfoFormula::getDataId).collect(Collectors.toList());
        List<KpiDataDetail> detailList = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                .in(KpiDataDetail::getKpiDataId, dataIds));
        List<TblAreaNode> nodeList = areaNodeDao.selectList(TblAreaNode.getWrapper().eq(TblAreaNode::getParentCode, "500106"));
        List<KpiDataDetail> addList = new ArrayList<>();
        Random rand = new Random();
        for (KpiDataDetail detail : detailList) {
            int intValue = new BigDecimal("100").subtract(detail.getValue()).intValue();
            int max = intValue > 0 ? Math.min(intValue, 5) : 1;
            int num = 0;
            for (TblAreaNode node : nodeList) {
                num = num == 0 ? rand.nextInt(max) + 1 : -num;
                addList.add(KpiDataDetail.builder()
                        .kpiDataId(detail.getKpiDataId())
                        .companyName(detail.getCompanyName())
                        .street(node.getAreaName())
                        .dataDate(detail.getDataDate())
                        .value(detail.getValue().compareTo(new BigDecimal("100")) == 0 ? detail.getValue() : detail.getValue().add(BigDecimal.valueOf(num)))
                        .build());
                num = Math.max(num, 0);
            }
        }
        kpiDataDetailDao.insertList(addList);
    }

    @Test
    public void updateNormDimensions() throws ParseException {
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(KpiNormClassify.getWrapper()
                .eq(KpiNormClassify::getDeleted, false)
                .in(KpiNormClassify::getClassifyName, Arrays.asList("感知设备监测率", "入户安检率")));
        kpiNormInfoDao.update(KpiNormInfo.builder().normDimensions("NORM_URBAN_COMPANY,NORM_STREET").build(), KpiNormInfo.getWrapper()
                .eq(KpiNormInfo::getDeleted, false)
                .in(KpiNormInfo::getNormClassifyId, classifyList.stream().map(KpiNormClassify::getId).collect(Collectors.toList())));
    }
}
