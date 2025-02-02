package com.idealstudy.mvp.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebUrlHelper {

    @Value("${spring.cloud.aws.cloudfront.domain-name}")
    private String cdnDomainName;

    public String makeCdnLink(String key) {
        return "https://" + cdnDomainName + "/" + key;
    }

    public String getKey(String url) {
        String[] temp = url.split("/");
        return temp[3] + "/" + temp[4];
    }
}
