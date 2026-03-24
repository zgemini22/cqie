package test;

import com.zds.biz.util.ContrastUtil;
import com.zds.biz.vo.ContrastVo;
import com.zds.biz.vo.response.user.UserResponse;
import com.zds.user.UserApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @Test
    public void contrastList() {
        List<UserResponse> newList = new ArrayList<>();
        newList.add(UserResponse.builder().id(1L).roleId(2L).build());
        newList.add(UserResponse.builder().id(2L).build());
        newList.add(UserResponse.builder().id(3L).build());
        newList.add(UserResponse.builder().id(4L).build());

        List<UserResponse> oldList = new ArrayList<>();
        oldList.add(UserResponse.builder().id(5L).build());
        oldList.add(UserResponse.builder().id(2L).build());
        oldList.add(UserResponse.builder().id(3L).build());
        oldList.add(UserResponse.builder().id(4L).build());

        ContrastVo<UserResponse> contrastVo = ContrastUtil.contrastList(oldList, newList, UserResponse::getId);
        List<UserResponse> addList = contrastVo.getAddList();
        List<UserResponse> delList = contrastVo.getDelList();
        System.out.println(addList);
        System.out.println(delList);
    }
}
