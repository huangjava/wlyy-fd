package com.yihu.wlyy.services.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.configuration.GatewayConfig;
import com.yihu.wlyy.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead on 2016/9/13.
 */
@Service
public class GateWayService {
    private static Logger logger = LoggerFactory.getLogger(GateWayService.class);
    @Autowired
    private GatewayConfig gatewayConfig;

    private Map<String, Object> generateParam(String api, String param) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("AuthInfo", "{ \"ClientId\": " + gatewayConfig.getClientId() + " }");
        paramMap.put("SequenceNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        paramMap.put("Api", api);
        paramMap.put("Param", param);
        paramMap.put("ParamType", 0);
        paramMap.put("OutType", 0);

        return paramMap;
    }

    public String post(String api, Map<String, String> apiParam) {
        Map<String, Object> params;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String param = objectMapper.writeValueAsString(apiParam);
            params = generateParam(api, param);

            return HttpClientUtil.doPost(gatewayConfig.getUrl(), params, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return "{}";
    }
}
