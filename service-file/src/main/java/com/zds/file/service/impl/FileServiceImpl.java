package com.zds.file.service.impl;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.BasicDataKeyEnum;
import com.zds.biz.util.HttpUtil;
import com.zds.biz.util.IDGenerator;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.request.file.*;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.biz.vo.request.file.Base64FileRequest;
import com.zds.biz.vo.request.file.ByteFileRequest;
import com.zds.biz.vo.request.file.FileDownloadRequest;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.response.file.Base64FileResponse;
import com.zds.biz.vo.response.file.FileUploadResponse;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.file.config.FileConfig;
import com.zds.file.dao.TblFileInfoDao;
import com.zds.file.feign.UserService;
import com.zds.file.po.TblFileInfo;
import com.zds.file.service.FileService;
import com.zds.file.util.Base64ToMultipartUtil;
import com.zds.file.util.DatabaseBackupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private TblFileInfoDao fileInfoDao;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private DatabaseBackupUtil databaseBackupUtil;

    // 不安全文件后缀名黑名单
    private static final Set<String> UNSAFE_EXTENSIONS = new HashSet<>(Arrays.asList(
            "exe", "bat", "cmd", "sh", "php", "asp", "aspx", "jsp", "jar",
            "war", "dll", "so", "vbs", "js", "html", "htm", "ps1"
    ));

    // 安全文件后缀名白名单
    private static final Set<String> SAFE_EXTENSIONS = new HashSet<>(Arrays.asList(
            "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx",
            "ppt", "pptx", "txt", "csv", "zip", "rar", "mp3", "mp4"
    ));

    @Override
    public FileUploadResponse uploadFile(MultipartFile uploadfile) {
        return uploadFile(uploadfile, true);
    }

    /**
     * 上传文件
     * @param uploadfile
     * @param isUpdate
     */
    public FileUploadResponse uploadFile(MultipartFile uploadfile, boolean isUpdate) {
        checkFileSize(uploadfile);
        //文件后缀名校验
        validateFileExtension(uploadfile);
        FileUploadResponse uploadResponse = new FileUploadResponse();
        try {
            //保存文件
            checkPath();
            String originalFilename = uploadfile.getOriginalFilename();
            String fileName = isUpdate ? createFileName() + "." + StringUtils.getFilenameExtension(originalFilename) : originalFilename;
            String targetDir = getSystemPath() + File.separator;
            File targetfile = new File(targetDir, fileName);
            uploadfile.transferTo(targetfile);
            fileInfoDao.insert(TblFileInfo.builder()
                    .fileName(originalFilename)
                    .realFileName(fileName)
                    .realFileSize(getSizeOfKb(uploadfile))
                    .build());
            uploadResponse.setFileName(originalFilename);
            uploadResponse.setRealFileName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResponse;
    }

    // 文件后缀名校验方法
    private void validateFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new BaseException("文件名无效");
        }
        // 获取文件扩展名
        String extension = FilenameUtils.getExtension(originalFilename).toLowerCase();
        if (extension.isEmpty()) {
            throw new BaseException("文件缺少扩展名");
        }
        // 检查是否在不安全后缀名黑名单中
        if (UNSAFE_EXTENSIONS.contains(extension)) {
            throw new BaseException("禁止上传不安全文件类型: " + extension);
        }
        // 检查是否在安全后缀名白名单中
        if (!SAFE_EXTENSIONS.contains(extension)) {
            throw new BaseException("不支持的文件类型: " + extension);
        }
    }

    @Override
    public FileUploadResponse fileUploadUnchange(Base64FileRequest uploadfile) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(uploadfile.getFileName())) {
            throw new BaseException("源文件名不能为空");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(uploadfile.getFielBase64())) {
            throw new BaseException("文件Base64数据不能为空");
        }
        return saveBase64File(uploadfile.getFielBase64(), getFileType(uploadfile.getFileName()), uploadfile.getFileName(), false);
    }

    @Override
    public FileUploadResponse uploadFile(Base64FileRequest uploadfile) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(uploadfile.getFileName())) {
            throw new BaseException("源文件名不能为空");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(uploadfile.getFielBase64())) {
            throw new BaseException("文件Base64数据不能为空");
        }
        return saveBase64File(uploadfile.getFielBase64(), getFileType(uploadfile.getFileName()), uploadfile.getFileName());
    }

    private String getFileType(String fileName) {
        String[] arr = fileName.split("\\.");
        if (arr.length < 2) {
            throw new BaseException("源文件名格式错误");
        } else {
            return arr[arr.length - 1];
        }
    }

    /**
     * 获取文件保存路径
     */
    private String getSystemPath() {
        String sys = System.getProperty("os.name").toLowerCase();
        if (sys.contains("windows")) {
            return fileConfig.getFileUploadPathOfWindows();
        } else {
            return fileConfig.getFileUploadPathOfLinux();
        }
    }

    /**
     * 获取文件下载路径
     */
    private String getSystemLoadPath() {
        String sys = System.getProperty("os.name").toLowerCase();
        String path;
        if (sys.contains("windows")) {
            path = fileConfig.getFileLoadPathOfWindows();
        } else {
            path = fileConfig.getFileLoadPathOfLinux();
        }
        String ip = threadLocalUtil.getIpAddr();
        boolean flag = HttpUtil.isLanVisit(ip);
        if (!flag) {
            path = fileConfig.getFileLoadPathOfExtranet();
        }
        log.info("file host = " + threadLocalUtil.getHost());
        if ("http://219.153.117.95".equals(threadLocalUtil.getHost())) {
            path = "https://219.153.117.95:8083/gasapi/uploadfile/";
        }
        return path;
    }

    /**
     * 生成随机文件名
     */
    private String createFileName() {
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        return time + IDGenerator.generate().substring(0, 5);
    }

    /**
     * 检查路径是否存在
     */
    private void checkPath() {
        String fileUploadPath = getSystemPath();
        File file = new File(fileUploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private Long getSizeOfKb(MultipartFile uploadfile) {
        long len = uploadfile.getSize();
        return len / 1024;//KB
    }

    /**
     * 检查文件大小是否超过限制
     */
    private void checkFileSize(MultipartFile uploadfile) {
        long len = uploadfile.getSize();
        double fileSizeOfMB = (double) len / 1048576;//MB
        int max = getFileMbMaxSize();
        if (fileSizeOfMB > (double) max) {
            throw new BaseException("上传文件大小不能超过" + max + "MB");
        }
    }

    @Override
    public String getFileLoadUrl() {
        return getSystemLoadPath();
    }

    @Override
    public Integer getFileMbMaxSize() {
        //文件上传大小参数,优先级：数据库配置>yml配置>java枚举
        int max = Integer.parseInt(BasicDataKeyEnum.fileMbMaxSize.getTitle());
        BaseResult<BasicDataResponse> result = userService.selectByKey(BasicDataRequest.builder().dataKey(BasicDataKeyEnum.fileMbMaxSize.getKey()).build());
        if (result != null && org.apache.commons.lang3.StringUtils.isNotEmpty(result.getData().getDataValue())) {
            max = Integer.parseInt(result.getData().getDataValue());
        } else if (fileConfig.getFileMbMaxSize() != null && fileConfig.getFileMbMaxSize() > 0) {
            max = fileConfig.getFileMbMaxSize();
        }
        return max;
    }

    @Override
    public FileUploadResponse saveBase64File(String base64, String contentType, String fileName) {
        return saveBase64File(base64, contentType, fileName, true);
    }

    public FileUploadResponse saveBase64File(String base64, String contentType, String fileName, boolean isUpdate) {
        base64 = base64.replace("\\r\\n", "");
        MultipartFile multipartFile = base64ToMultipartFile(base64, contentType, fileName);
        return uploadFile(multipartFile, isUpdate);
    }

    /**
     * 将base64转为MultipartFile
     */
    private MultipartFile base64ToMultipartFile(String base64, String contentType, String fileName) {
        if (base64.contains("data:image")) {
            final String[] base64Array = base64.split(",");
            String dataUir = base64Array[0];
            String data = base64Array[1];
            return new Base64ToMultipartUtil(data, dataUir, fileName);
        }
        return new Base64ToMultipartUtil(base64, "", "." + contentType, fileName);
    }

    @Override
    public FileUploadResponse uploadByte(ByteFileRequest request){
        FileUploadResponse uploadResponse = new FileUploadResponse();
        //创建一个字节输出流
        OutputStream outputStream = null;
        try {
            checkPath();
            //解析源地址的格式类型
            String url = request.getUrl();
            String format = getFileType(url);
            String fileName = createFileName() + "."+format;
            String targetDir = getSystemPath() + File.separator;
            outputStream = new FileOutputStream(targetDir+fileName);
            outputStream.write(request.getFile());
            outputStream.flush();
            uploadResponse.setRealFileName(fileName);
            uploadResponse.setFileName(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(!Objects.isNull(outputStream)){
                    outputStream.close();
                }
                return uploadResponse;
            }catch (IOException e) {
                e.printStackTrace();
                return uploadResponse;
            }

        }
    }

    @Override
    public void fileDownload(FileDownloadRequest request, HttpServletResponse response) {
        try {
            String filename = request.getFileName();
            String targetDir = getSystemPath() + File.separator;
            String fileUrl = targetDir + request.getRealFileName();
            //以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(fileUrl));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            String fileName = new String(filename.getBytes("gb2312"), "iso8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.write(buffer);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载出现异常", e);
        }
    }

    @Override
    public Base64FileResponse fileDownloadBase64(FileDownloadRequest request) {
        Base64FileResponse fileResponse = new Base64FileResponse();
        try {
            String filename = request.getFileName();
            String targetDir = getSystemPath() + File.separator;
            String fileUrl = targetDir + request.getRealFileName();
            File file = new File(fileUrl);
            String base64 = "";
            if (file.exists()) {
                base64 = convertFileToBase64(file);
            }
            fileResponse.setFileName(filename);
            fileResponse.setFielBase64(base64);
            return fileResponse;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载出现异常", e);
        }
        return fileResponse;
    }

    public static String convertFileToBase64(File file) {
        // 创建字节数组输出流，用于存储文件的字节数据
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             // 创建文件输入流，读取文件内容
             FileInputStream inputStream = new FileInputStream(file)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // 读取文件内容并写入字节数组输出流
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 将字节数组转换为Base64编码的字符串
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());

        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FileInfoVo findByRealName(FileRealNameRequest request) {
        TblFileInfo fileInfo = fileInfoDao.selectOne(TblFileInfo.getWrapper()
                .last("limit 1")
                .eq(TblFileInfo::getDeleted, false)
                .eq(TblFileInfo::getRealFileName, request.getRealFileName()));
        FileInfoVo vo = FileInfoVo.builder().build();
        BeanUtils.copyProperties(fileInfo, vo);
        return vo;
    }

    @Override
    public boolean DatabaseBackup(String token) {
        boolean flag = "bbtZy46Ee3".equals(token);
        if (flag){
            try {
                databaseBackupUtil.backupAllDatabases();
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean DatabaseBackupByName(List<BackupTableRequest> request) {
        try {
            databaseBackupUtil.backupSpecificTables(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
