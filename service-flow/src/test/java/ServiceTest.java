import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.info.AdminTwVideoRequest;
import com.zds.biz.vo.response.info.AdminPipelineAreaLineTypeNumResponse;
import com.zds.biz.vo.response.info.AdminTwVideoResponse;
import com.zds.flow.FlowApplication;
import com.zds.flow.feign.UserService;
import com.zds.flow.service.FlowTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

@Slf4j
@SpringBootTest(classes = FlowApplication.class)
public class ServiceTest {
    @Autowired
    private FlowTaskService flowTaskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Test
    public void findObjectByRange() throws ParseException {

    }
}
