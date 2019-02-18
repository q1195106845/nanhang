package com.nanhang.ad;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author yangjianghe
 * @Date 2018/12/26 09:12
 **/
public class MainTest {
    public static void main(String[] args) {
        String str = "/User/app/cc.png";
        System.out.println(FilenameUtils.getName(str));
        List<Object> lst = new ArrayList<>();
        lst.add(null);
        System.out.println(CollectionUtils.isEmpty(lst));
        System.out.println(!(lst.stream().filter(x-> !Objects.isNull(x)).count()>0));

    }
}
