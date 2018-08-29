package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description //TODO $
 * @Date 19:51
 **/
@Service
public class IndexService {
    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_IMG_URL}")
    private String TAOTAO_IMG_URL;
    @Value("${TAOTAO_AD1_URL}")
    private String TAOTAO_AD1_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD1() {
        String url = TAOTAO_IMG_URL + TAOTAO_AD1_URL;
        try {
            String jsonDate = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonDate)){
                return null;
            }
            JsonNode jsonNode = MAPPER.readTree(jsonDate);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("srcB",row.get("pic").asText());
                map.put("height",240);
                map.put("alt",row.get("title").asText());
                map.put("width",670);
                map.put("src",row.get("pic").asText());
                map.put("widthB",550);
                map.put("href",row.get("url").asText());
                map.put("heightB",240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
