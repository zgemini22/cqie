package com.zds.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.KpiMainVo;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.BiDataExampleDetailResponse;
import com.zds.biz.vo.response.user.BiDataExampleResponse;
import com.zds.biz.vo.response.user.BiDataListResponse;
import com.zds.biz.vo.response.user.BiDataModelResponse;
import com.zds.user.dao.TblBiDataAllDao;
import com.zds.user.dao.TblBiDataDao;
import com.zds.user.dao.TblBiDataQyDao;
import com.zds.user.dao.TblBiDataZjDao;
import com.zds.user.po.TblBiData;
import com.zds.user.po.TblBiDataAll;
import com.zds.user.po.TblBiDataQy;
import com.zds.user.po.TblBiDataZj;
import com.zds.user.service.BiDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class BiDataServiceImpl implements BiDataService {

    @Autowired
    private TblBiDataDao biDataDao;

    @Autowired
    private TblBiDataAllDao biDataAllDao;

    @Autowired
    private TblBiDataQyDao biDataQyDao;

    @Autowired
    private TblBiDataZjDao biDataZjDao;

    @Override
    public List<BiDataListResponse> findList() {
        return biDataDao.selectList(TblBiData.getWrapper().orderByAsc(TblBiData::getBiDataName).orderByAsc(TblBiData::getBiDataKey).eq(TblBiData::getDeleted, false))
                .stream()
                .map(po -> {
                    BiDataListResponse vo = new BiDataListResponse();
                    BeanUtils.copyProperties(po, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(BiDataSaveRequest request) {
        Long aLong = biDataDao.selectCount(TblBiData.getWrapper()
                .eq(TblBiData::getDeleted, false)
                .ne(request.getId() != null, TblBiData::getId, request.getId())
                .eq(TblBiData::getBiDataKey, request.getBiDataKey()));
        if (aLong > 0) {
            throw new BaseException("数据标识已存在");
        }
        TblBiData po = request.getId() == null ? new TblBiData() : biDataDao.selectById(request.getId());
        BeanUtils.copyProperties(request, po);
        int count = request.getId() == null ? biDataDao.insert(po) : biDataDao.updateById(po);
        return count == 1;
    }

    @Override
    public boolean delete(IdRequest request) {
        return biDataDao.updateById(TblBiData.builder()
                .id(request.getId())
                .deleted(true)
                .build()) == 1;
    }

    @Override
    public BiDataModelResponse findModel(IdRequest request) {
        TblBiData po = biDataDao.selectById(request.getId());
        BiDataModelResponse vo = new BiDataModelResponse();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    public boolean saveModel(BiDataModelSaveRequest request) {
        return biDataDao.updateById(TblBiData.builder()
                .id(request.getId())
                .biDataJson(request.getBiDataJson())
                .biDataComment(request.getBiDataComment())
                .build()) == 1;
    }

    @Override
    public BiDataExampleResponse findExample(IdRequest request) {
        TblBiData po = biDataDao.selectById(request.getId());
        BiDataExampleResponse vo = new BiDataExampleResponse();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    public boolean saveExample(BiDataExampleSaveRequest request) {
        return biDataDao.updateById(TblBiData.builder()
                .id(request.getId())
                .biDataExample(request.getBiDataExample())
                .build()) == 1;
    }

    @Override
    public BiDataExampleDetailResponse selectExampleByKey(BiDataExampleFindRequest request) {
        TblBiData po = biDataDao.selectOne(TblBiData.getWrapper()
                .last("limit 1")
                .eq(TblBiData::getDeleted, false)
                .eq(TblBiData::getBiDataKey, request.getBiDataKey()));
        BiDataExampleDetailResponse vo = new BiDataExampleDetailResponse();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }


    @Override
    public List<TblBiDataAll> biDataAlls(BiDataAllsRequest request){
        return biDataAllDao.selectList(TblBiDataAll.getWrapper()
                .eq(ObjectUtils.isNotEmpty(request.getD()),TblBiDataAll::getD,request.getD())
                .eq(ObjectUtils.isNotEmpty(request.getO()),TblBiDataAll::getO,request.getO())
                .eq(ObjectUtils.isNotEmpty(request.getV()),TblBiDataAll::getV,request.getV()));
    }

    @Override
    public List<TblBiDataQy> biDataQys(BiDataQysRequest request){
        return biDataQyDao.selectList(TblBiDataQy.getWrapper()
                .eq(ObjectUtils.isNotEmpty(request.getA()),TblBiDataQy::getA,request.getA())
                .eq(ObjectUtils.isNotEmpty(request.getE()),TblBiDataQy::getE,request.getE()));
    }

    @Override
    public List<TblBiDataZj> biDataZjs(BiDataZjsRequest request){
        return biDataZjDao.selectList(TblBiDataZj.getWrapper()
                .eq(ObjectUtils.isNotEmpty(request.getA()),TblBiDataZj::getA,request.getA())
                .eq(ObjectUtils.isNotEmpty(request.getE()),TblBiDataZj::getE,request.getE()));
    }

    @Override
    public Boolean jcczInsert(String time) {
        //time = "2025-10-01"
        Map<String,Integer> trankMap = new HashMap<>();
        Map<String, KpiMainVo> kpiTimeMap = new HashMap<>();
        //创建决策处置小结(办结中)指定月的数据
        updateDate(time,"jcczxj",trankMap,kpiTimeMap);
        //创建决策处置小结(办结中)-巡检延期开展弹窗数据
        updateDate(time,"xjyqkzbjz",trankMap,kpiTimeMap);
        //创建决策处置小结(办结中)-隐患逾期处置开展弹窗数据
        updateDate(time,"yhyqczbjz",trankMap,kpiTimeMap);
        //创建决策处置小结(办结中)-检修工作延期弹窗数据
        updateDate(time,"jxgzyqbjz",trankMap,kpiTimeMap);
        //创建决策处置小结(办结中)-入户安检延期开展弹窗数据
        updateDate(time,"rhajwdbbjz",trankMap,kpiTimeMap);
        //创建决策处置小结(已办结)指定月的数据 jcczybj
        updateDate(time,"jcczybj",trankMap,kpiTimeMap);
        //创建决策处置小结(已办结)年小结数据 jgsjzdaktj
        updateDate(time,"jgsjzdaktj",trankMap,kpiTimeMap);
        //创建决策处置小结(已办结)-弹窗数据 dakybj
        updateDate(time,"dakybj",trankMap,kpiTimeMap);
        //查看轨迹下转 czgjybj
        updateDate(time,"czgjybj",trankMap,kpiTimeMap);
        return true;
    }

    @Transactional
    public void updateDate(String time,String key,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        TblBiData po = biDataDao.selectOne(TblBiData.getWrapper()
                .last("limit 1")
                .eq(TblBiData::getDeleted, false)
                .eq(TblBiData::getBiDataKey,key));
        String keyStr = po.getBiDataKey();
        String jsonArryStr =  po.getBiDataExample();
        String json = createExample(time,keyStr,jsonArryStr,trankMap,kpiTimeMap);
        po.setBiDataExample(json);
        biDataDao.updateById(po);
    }

    public String createExample(String time,String key,String json,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //time = "2025-10-01"
        JSONArray arrays = JSONArray.parseArray(json);

        //判断指定时间为多少天
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(time, formatter);
        LocalDate nowLocalDate = LocalDate.now();
        String month = "";
        int monthInt = date.getMonthValue();
        if(monthInt <10){
            month = "0"+monthInt;
        }else{
            month = ""+monthInt;
        }

        switch (key){
            case "jcczxj":
                //决策处置办结中4维 bb:检修 cc:入户 dd:巡检  ee:隐患
                arrays = createJcczyData(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "xjyqkzbjz" :
                //巡检延期开展弹窗
                arrays = createXjyqkzbjz(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "yhyqczbjz":
                //隐患逾期处置开展弹窗
                arrays = createYhyqczbjz(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "jxgzyqbjz":
                //检修工作延期弹窗
                arrays = createJxgzyqbjz(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "rhajwdbbjz":
                //入户安检延期开展弹窗
                arrays = createRhajwdbbjz(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "jcczybj":
                //决策处置已办结4维
                arrays = createJcczyData(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "jgsjzdaktj":
                //决策处置已办结年度小结 /d=检修工作延期 e=入户安检延期开展 f=巡检延期开展 g=隐患延期
                arrays = createJgsjzdaktjData(date,month,arrays,trankMap);
                break;
            case "dakybj":
                //dd=巡检延期开展弹窗 ee=隐患逾期处置开展 bb=检修工作延期 cc=入户安检延期开展
                arrays = createDakybj(date,month,arrays,trankMap,kpiTimeMap);
                break;
            case "czgjybj":
                //弹窗内查看轨迹
                arrays = ceateCzgjybj(date,month,arrays,nowLocalDate);
                break;
        }
        json = arrays.toJSONString();
        return json;
    }

    public JSONArray createJcczyData(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //指定时间月份每天都需，数据bb,cc,dd,ee随机0~5
        // 2. 调用内置方法获取当月天数（自动处理闰年、月份差异）
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = 0;
        if(date.getMonthValue() == calendar.get(Calendar.MONTH)+1){
            //只生成当前天数的
            LocalDate nowDate = LocalDate.now();
            day = nowDate.getDayOfMonth();
        }else{
            day =date.lengthOfMonth();
        }

        for(int i=0;i<day;i++){
            JSONObject object = new JSONObject();
            if(i>=9){
                object.put("aa",date.getYear()+"-"+month+"-"+(i+1));
            }else{
                object.put("aa",date.getYear()+"-"+month+"-0"+(i+1));
            }
            object.put("bb",ThreadLocalRandom.current().nextInt(0, 5));
            object.put("cc",ThreadLocalRandom.current().nextInt(0, 2));
            object.put("dd",ThreadLocalRandom.current().nextInt(0, 2));
            object.put("ee",ThreadLocalRandom.current().nextInt(0, 5));
            arrays.add(object);
            KpiMainVo vo = new KpiMainVo();
            vo.setBb(object.getInteger("bb"));
            kpiTimeMap.put(object.getString("aa"),KpiMainVo.builder()
                    .bb(object.getInteger("bb"))
                    .cc(object.getInteger("cc"))
                    .dd(object.getInteger("dd"))
                    .ee(object.getInteger("ee"))
                    .build());
        }

        int bb= 0;
        int cc= 0;
        int dd= 0;
        int ee= 0;
        for(int i=0;i<arrays.size();i++){
            JSONObject ob = arrays.getJSONObject(i);
            if(ob.getString("aa").contains(date.getYear()+"-"+month)){
                bb= bb+Integer.parseInt(ob.getString("bb"));
                cc= cc+Integer.parseInt(ob.getString("cc"));
                dd= dd+Integer.parseInt(ob.getString("dd"));
                ee= ee+Integer.parseInt(ob.getString("ee"));
            }
        }
        trankMap.put("bb",bb);
        trankMap.put("cc",cc);
        trankMap.put("dd",dd);
        trankMap.put("ee",ee);
        return arrays;
    }

    public JSONArray createXjyqkzbjz(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //dd 获取需要创建的条数
        int count = trankMap.get("dd");
        Set<String> keys = kpiTimeMap.keySet();
        for(String key : keys){
            KpiMainVo vo = kpiTimeMap.get(key);
            Integer dd = vo.getDd();
            if(dd>0){
                for(int i=0;i<dd;i++){
                    JSONObject object = new JSONObject();
                    String company = "";
                    int companyInt = ThreadLocalRandom.current().nextInt(1, 3);
                    switch (companyInt){
                        case 1:company = "重燃沙分司";
                            break;
                        case 2:company ="凯源";
                            break;
                        case 3:company = "渝能";
                            break;
                    }
                    object.put("a",company);
                    object.put("b",date.getYear()+"年"+date.getMonthValue()+"月管道及管网附属设备巡检");
                    int dayInt;
                    /*if(date.getMonthValue() != 2){
                        dayInt = ThreadLocalRandom.current().nextInt(1, 25);
                    }else{
                        dayInt = ThreadLocalRandom.current().nextInt(1, 23);
                    }
                    if(dayInt<10){
                        object.put("c",date.getYear()+"-"+month+"-0"+dayInt);
                    }else{
                        object.put("c",date.getYear()+"-"+month+"-"+dayInt);
                    }*/
                    object.put("c",key);
                    object.put("d","期间有更重要的工作开展");
                    object.put("e","首次提醒");
                    dayInt = ThreadLocalRandom.current().nextInt(5, 15);
                    int fDayInt = dayInt-ThreadLocalRandom.current().nextInt(1, 3);
                    if(fDayInt<10){
                        object.put("f",date.getYear()+"-"+month+"-0"+fDayInt);
                    }else{
                        object.put("f",date.getYear()+"-"+month+"-"+fDayInt);
                    }
                    object.put("g","尽快加派人手进行处理");
                    int hDayInt = fDayInt-ThreadLocalRandom.current().nextInt(1, 2);
                    if(hDayInt<10){
                        object.put("h",date.getYear()+"-"+month+"-0"+hDayInt);
                    }else{
                        object.put("h",date.getYear()+"-"+month+"-"+hDayInt);
                    }
                    arrays.add(object);
                }
            }
        }
        return arrays;
    }

    public JSONArray createYhyqczbjz(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //ee
        int count = trankMap.get("ee");
        Set<String> keys = kpiTimeMap.keySet();
        for(String key : keys){
            KpiMainVo vo = kpiTimeMap.get(key);
            Integer ee = vo.getEe();
            if(ee>0){
                for(int i=0;i<ee;i++){
                    JSONObject object = new JSONObject();
                    String company = "";
                    int companyInt = ThreadLocalRandom.current().nextInt(1, 3);
                    switch (companyInt){
                        case 1:company = "重燃沙分司";
                            break;
                        case 2:company ="凯源";
                            break;
                        case 3:company = "渝能";
                            break;
                    }
                    object.put("a",company);
                    String reason = "";
                    int reasonInt = ThreadLocalRandom.current().nextInt(1, 7);
                    switch (reasonInt){
                        case 1:reason = "燃气具泄漏";
                            break;
                        case 2:reason ="私拉乱接，私开用气点";
                            break;
                        case 3:reason = "使用燃气燃烧直接取暖的设备";
                            break;
                        case 4:reason = "燃气具无熄火保护装置、装置失效或超期使用";
                            break;
                        case 5:reason = "室内设置直排式燃气热水器";
                            break;
                        case 6:reason = "热水器未安装烟道、烟道口未伸出室外、穿越卧室客厅卫生间、破损、接头密封不严密、扭曲安装、接入公共排油烟道";
                            break;
                        case 7:reason = "未使用燃气专用软管";
                            break;
                    }
                    object.put("b",reason);
                    int fjno = ThreadLocalRandom.current().nextInt(100000, 999999);
                    object.put("c","非居用户号：Fj"+fjno);
                    object.put("d","多次督促其解决，客户未解决");
                    int dayInt;
                    /*if(date.getMonthValue() != 2){
                        dayInt = ThreadLocalRandom.current().nextInt(1, 25);
                    }else{
                        dayInt = ThreadLocalRandom.current().nextInt(1, 23);
                    }
                    if(dayInt<10){
                        object.put("e",date.getYear()+"-"+month+"-0"+dayInt);
                    }else{
                        object.put("e",date.getYear()+"-"+month+"-"+dayInt);
                    }*/
                    object.put("e",key);
                    object.put("f","加快协调力度");
                    object.put("g","首次提醒");
                    dayInt = ThreadLocalRandom.current().nextInt(5, 15);
                    int fDayInt = dayInt-ThreadLocalRandom.current().nextInt(1, 3);
                    if(fDayInt<10){
                        object.put("h",date.getYear()+"-"+month+"-0"+fDayInt);
                    }else{
                        object.put("h",date.getYear()+"-"+month+"-"+fDayInt);
                    }
                    object.put("i","加快协调力度");
                    int hDayInt = fDayInt-ThreadLocalRandom.current().nextInt(1, 2);
                    if(hDayInt<10){
                        object.put("j",date.getYear()+"-"+month+"-0"+hDayInt);
                    }else{
                        object.put("j",date.getYear()+"-"+month+"-"+hDayInt);
                    }
                    arrays.add(object);
                }
            }
        }
        return arrays;
    }

    public JSONArray createJxgzyqbjz(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //bb
        int count = trankMap.get("bb");
        Set<String> keys = kpiTimeMap.keySet();
        for(String key : keys){
            KpiMainVo vo = kpiTimeMap.get(key);
            Integer bb = vo.getBb();
            if(bb>0){
                for(int i=0;i<bb;i++){
                    JSONObject object = new JSONObject();
                    String company = "";
                    int companyInt = ThreadLocalRandom.current().nextInt(1, 3);
                    switch (companyInt){
                        case 1:company = "重燃沙分司";
                            break;
                        case 2:company ="凯源";
                            break;
                        case 3:company = "渝能";
                            break;
                    }
                    object.put("a",company);
                    int sbno = ThreadLocalRandom.current().nextInt(10000, 99999);
                    object.put("b","QS-B22-A"+sbno+"三级维护");
                    int dayInt;
                    dayInt = ThreadLocalRandom.current().nextInt(5, 15);
                    /*if(date.getMonthValue() != 2){
                        dayInt = ThreadLocalRandom.current().nextInt(1, 25);
                    }else{
                        dayInt = ThreadLocalRandom.current().nextInt(1, 23);
                    }
                    if(dayInt<10){
                        object.put("c",date.getYear()+"-"+month+"-0"+dayInt);
                    }else{
                        object.put("c",date.getYear()+"-"+month+"-"+dayInt);
                    }*/
                    object.put("c",key);
                    object.put("d","有优先级更高的工作事项耽误");
                    object.put("e","首次警告");
                    int fDayInt = dayInt-ThreadLocalRandom.current().nextInt(1, 3);
                    if(fDayInt<10){
                        object.put("f",date.getYear()+"-"+month+"-0"+fDayInt);
                    }else{
                        object.put("f",date.getYear()+"-"+month+"-"+fDayInt);
                    }
                    object.put("g","安排人员加快处置进度");
                    int hDayInt = fDayInt-ThreadLocalRandom.current().nextInt(1, 2);
                    if(hDayInt<10){
                        object.put("h",date.getYear()+"-"+month+"-0"+hDayInt);
                    }else{
                        object.put("h",date.getYear()+"-"+month+"-"+hDayInt);
                    }
                    arrays.add(object);
                }
            }
        }
        return arrays;
    }

    public JSONArray createRhajwdbbjz(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        //cc
        int count = trankMap.get("cc");
        Set<String> keys = kpiTimeMap.keySet();
        for(String key : keys){
            KpiMainVo vo = kpiTimeMap.get(key);
            Integer cc = vo.getCc();
            if(cc>0){
                for(int i=0;i<cc;i++){
                    JSONObject object = new JSONObject();
                    String company = "";
                    int companyInt = ThreadLocalRandom.current().nextInt(1, 3);
                    switch (companyInt){
                        case 1:company = "重燃沙分司";
                            break;
                        case 2:company ="凯源";
                            break;
                        case 3:company = "渝能";
                            break;
                    }
                    object.put("a",company);
                    int sbno = ThreadLocalRandom.current().nextInt(10000, 99999);
                    object.put("b",company+"居民巡检("+date.getYear()+"年"+date.getMonthValue()+"月)");
                    int dayInt;
                    dayInt = ThreadLocalRandom.current().nextInt(5, 15);
                    /*if(date.getMonthValue() != 2){

                    }else{
                        dayInt = ThreadLocalRandom.current().nextInt(1, 23);
                    }*/
                   /* if(dayInt<10){

                    }else{
                        object.put("c",date.getYear()+"-"+month+"-"+dayInt);
                    }*/
                    object.put("c",key);
                    object.put("d","期间有更重要的工作开展");
                    object.put("e","首次提醒");
                    int fDayInt = dayInt-ThreadLocalRandom.current().nextInt(1, 3);
                    if(fDayInt<10){
                        object.put("f",date.getYear()+"-"+month+"-0"+fDayInt);
                    }else{
                        object.put("f",date.getYear()+"-"+month+"-"+fDayInt);
                    }
                    object.put("g","尽快加派人手进行处理");
                    int hDayInt = fDayInt-ThreadLocalRandom.current().nextInt(1, 2);
                    if(hDayInt<10){
                        object.put("h",date.getYear()+"-"+month+"-0"+hDayInt);
                    }else{
                        object.put("h",date.getYear()+"-"+month+"-"+hDayInt);
                    }
                    arrays.add(object);
                }
            }
        }
        return arrays;
    }

    public JSONArray createDakybj(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap,Map<String, KpiMainVo> kpiTimeMap){
        int ddCount = trankMap.get("dd");//巡检延期开展
        int eeCount = trankMap.get("ee");//隐患逾期处置开展
        int bbCount = trankMap.get("bb");//检修工作延期
        int ccCount = trankMap.get("cc");//入户安检延期开展
        arrays = createbjData(date,month,arrays,ddCount,"dd",kpiTimeMap);
        arrays = createbjData(date,month,arrays,eeCount,"ee",kpiTimeMap);
        arrays = createbjData(date,month,arrays,bbCount,"bb",kpiTimeMap);
        arrays = createbjData(date,month,arrays,ccCount,"cc",kpiTimeMap);

        return arrays;
    }

    public JSONArray createbjData(LocalDate date,String month,JSONArray arrays,int count,String mapKey,Map<String, KpiMainVo> kpiTimeMap){
        Set<String> keys = kpiTimeMap.keySet();
        for(String key : keys){
            KpiMainVo vo = kpiTimeMap.get(key);
            Integer data = 0;
            if(mapKey.equals("dd")){
                data = vo.getDd();
            }else if(mapKey.equals("ee")){
                data = vo.getEe();
            }else if(mapKey.equals("bb")){
                data = vo.getBb();
            }else if(mapKey.equals("cc")){
                data=vo.getCc();
            }
            for(int i=0;i<data;i++){
                JSONObject object = new JSONObject();
                String company = "";
                int companyInt = ThreadLocalRandom.current().nextInt(1, 3);
                switch (companyInt){
                    case 1:company = "重燃沙分司";
                        break;
                    case 2:company ="凯源";
                        break;
                    case 3:company = "渝能";
                        break;
                }
                object.put("a",company);
                String type = "";
                String work = "";
                String user = "";
                String content = "";
                switch (mapKey){
                    case "dd":
                        type="巡检延期开展";
                        work = date.getYear()+"年"+date.getMonthValue()+"月管道及管网附属设备巡检";
                        user = "巡检责任人";
                        content = "按工作规范完成巡检工作";
                        break;
                    case "ee":
                        type="隐患逾期处置开展";
                        work = "燃气具无熄火保护装置、装置失效或超期使用";
                        user = "燃气公司责任人员";
                        content = "多方配合完成整改工作";
                        break;
                    case "bb":
                        type="检修工作延期";
                        work = "QS-B22-E"+ThreadLocalRandom.current().nextInt(10000, 99999)+"一级维护";
                        user = "检修责任人";
                        content = "按规范要求完成检修工作";
                        break;
                    case "cc":
                        type="入户安检延期开展";
                        work = "沙坪坝街道非民用户巡检("+date.getYear()+"年"+date.getMonthValue()+"月)";
                        user = "巡检责任人";
                        content = "按工作规范完成巡检工作";
                        break;
                }
                object.put("b",type);
                object.put("c",work);
                object.put("d",ThreadLocalRandom.current().nextInt(0, 3));
                object.put("e","首次提醒");
                object.put("f","逾期完成");
                object.put("g",key);
                object.put("h",user);
                object.put("i",content);
                arrays.add(object);
            }
        }

        return arrays;
    }

    public JSONArray ceateCzgjybj(LocalDate date,String month,JSONArray arrays,LocalDate nowDate){
        arrays = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("a","首次提醒");
        object.put("b",date.getYear()+"-"+month+"-01");
        object.put("c","燃气专员");
        object.put("d","《重燃沙分司-"+date.getYear()+"年"+date.getMonthValue()+"月渝碚路街道片区管道及管网附属设备巡检逾期完成首次提醒书》");
        object.put("g",date.getYear()+"-"+month+"-03");
        object.put("h","1");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","事件办结");
        object.put("b",date.getYear()+"-"+month+"-"+nowDate.getDayOfMonth());
        object.put("c","巡检责任人");
        object.put("h","1");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","首次提醒");
        object.put("b",date.getYear()+"-"+month+"-02");
        object.put("c","燃气专管员");
        object.put("d","《首次提醒书》");
        object.put("g",date.getYear()+"-"+month+"-04");
        object.put("h","2");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","事件办结");
        object.put("b",date.getYear()+"-"+month+"-"+nowDate.getDayOfMonth());
        object.put("c","巡检责任人");
        object.put("h","2");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","首次提醒");
        object.put("c","燃气专管员");
        object.put("d","《重燃沙分司管道（07-028-SZ-SPA00083-0.48-PE-63/110/160-2003-0.4-083)(压力管道未开展定期检验或定期检验超过有效时限)逾期处理首次提醒书》");
        object.put("g",date.getYear()+"-"+month+"-01");
        object.put("h","3");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","再次催告");
        object.put("c","燃气专管员");
        object.put("d","《重燃沙分司管道（07-028-SZ-SPA00083-0.48-PE-63/110/160-2003-0.4-083)(压力管道未开展定期检验或定期检验超过有效时限)逾期处理再次催告书》");
        object.put("g",date.getYear()+"-"+month+"-03");
        object.put("h","3");
        arrays.add(object);
        object.put("a","限期整改");
        object.put("b",date.getYear()+"-"+month+"-05");
        object.put("c","燃气专员");
        object.put("d","《重燃沙分司管道（07-028-SZ-SPA00083-0.48-PE-63/110/160-2003-0.4-083)(压力管道未开展定期检验或定期检验超过有效时限)逾期处理限期整改通知书》");
        object.put("g",date.getYear()+"-"+month+"-"+(nowDate.getDayOfMonth()-2));
        object.put("h","3");
        arrays.add(object);
        object.put("a","办结完成");
        object.put("b",date.getYear()+"-"+month+"-"+nowDate.getDayOfMonth());
        object.put("c","燃气公司工程专员");
        object.put("h","3");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","首次提醒");
        object.put("c","燃气专管员");
        object.put("d","《重燃沙分司QS-B22-A23662(沙坪坝区土湾模范)二级维护逾期处理首次提醒书》");
        object.put("g",date.getYear()+"-"+month+"-01");
        object.put("h","4");
        arrays.add(object);
        object = new JSONObject();
        object.put("a","再次催告");
        object.put("b",date.getYear()+"-"+month+"-03");
        object.put("c","燃气专管员");
        object.put("d","《重燃沙分司QS-B22-A23662(沙坪坝区土湾模范)二级维护逾期处理再次催告通知书》");
        object.put("g",date.getYear()+"-"+month+"-05");
        object.put("h","4");
        arrays.add(object);
        object.put("a","办结完成");
        object.put("b",date.getYear()+"-"+month+"-"+nowDate.getDayOfMonth());
        object.put("c","燃气公司工程专员");
        object.put("h","4");
        arrays.add(object);
        return arrays;
    }

    public JSONArray createJgsjzdaktjData(LocalDate date,String month,JSONArray arrays,Map<String,Integer> trankMap){
        //决策处置已办结年度小结  f=巡检延期开展 g=隐患延期 d=检修工作延期 e=入户安检延期开展
        int fcount = trankMap.get("bb");
        int gcount = trankMap.get("cc");
        int dcount = trankMap.get("dd");
        int ecount = trankMap.get("ee");

        JSONObject object = new JSONObject();
        object.put("a",date.getYear()+"年");
        String jidu = "";
        if(1<=date.getMonthValue() && date.getMonthValue()<=3){
            jidu = "第一季";
        }else if(4<=date.getMonthValue() && date.getMonthValue()<=6){
            jidu = "第二季";
        }else if(7<=date.getMonthValue() && date.getMonthValue()<=9){
            jidu = "第三季";
        }else{
            jidu = "第四季";
        }
        object.put("b",jidu);
        object.put("c",date.getMonthValue()+"月");
        object.put("f",dcount);
        object.put("g",ecount);
        object.put("d",fcount);
        object.put("e",gcount);
        arrays.add(object);
        return arrays;
    }
}
