package com.project.nmt.dataSetting;

import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.repository.StockInfoRepository;
import com.project.nmt.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class DataSetter {
    private final String API = "ef02eb73-7007-4884-8401-e1bee7361066";

    private final StockRepository stockRepository;
    private final StockInfoRepository stockInfoRepository;

    private final String[] keyword = {"고구마", "감자", "대파", "시금치", "양파", "깻잎", "고추", "파프리카", "호박", "미나리"};
    private final String[] categoryCode = {"100", "100", "200", "200", "200", "200", "200", "200", "200", "200"};
    private final String[] item = {"sweet potato", "potato", "spring onion", "spinach", "onion", "perilla leaf", "chili", "Paprika", "Pumpkin", "water parsley"};
    private final String[] itemCode = {"151", "152", "246", "213", "245", "253", "243", "256", "224", "252"};
    private final String[] kindCode = {"00", "01", "00", "00", "00", "00", "00", "00", "01", "00"};

    public void getTodayProductData() {
        LocalDate today = LocalDate.now();
        String format = today.format(DateTimeFormatter.ISO_DATE);

        saveProductData(format, format);
    }

    public void getAllProductData() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusYears(1);

        saveProductData(start.format(DateTimeFormatter.ISO_DATE), end.format(DateTimeFormatter.ISO_DATE));
    }

   
    public void saveProductData(String startDate, String endDate) {
        List<Map<String, Object>> prices;

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        for (int idx = 0; idx < 10; idx++) {
            // 요청 URL
            URL url = null;
            try {
                url = new URL(// url설정
                        "https://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=02&"
                                + "p_startday=" + startDate + "+&p_endday=" + endDate + "&"
//					+ "p_itemcategorycode=200&p_itemcode=211&p_kindcode=01&"
                                + "p_itemcategorycode=" + categoryCode[idx] + "&p_itemcode=" + itemCode[idx]
                                + "&p_kindcode=" + kindCode[idx] + "&"
                                + "p_productrankcode=04&p_countrycode=1101&p_convert_kg_yn=Y&" + "p_cert_key=" + API
                                + "&p_cert_id=222&p_returntype=json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            StringBuilder json = null;

            try {
                // url.openStream()으로 요청 결과를 stream으로 받음.
                assert url != null;
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

                // JsonParser를 사용하기 위해 stream을 하나의 String 객체로 묶음.
                String line = br.readLine();
                json = new StringBuilder();
                while (line != null && !line.equals("")) {
                    json.append(line);

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // JsonParser를 통해 요청 결과를 Map<String, Object>로 변환하여 반환.
            Map<String, Object> map = jsonParser.parseMap(json.toString());
            map = (Map<String, Object>) map.get("data");
            prices = (List<Map<String, Object>>) map.get("item");
            Stock stock = stockRepository.findByKeyword(keyword[idx]);

            for (Map<String, Object> price : prices) {// 각 품목뵬 받아온 데이터 db저장
                if (price.get("marketname").toString().equals("가락도매")) {//평균, 평년->itemname=[], 서울 3가지 값으로 받아와서 itemname=서울값만 넣기 위해
                    String[] date = price.get("regday").toString().split("/");

                    StockInfo stockInfo = StockInfo.builder()
                            .price(Integer.parseInt(((String) (price.get("price"))).replace(",", "")))
                            .infoDate(LocalDate.of(Integer.parseInt((String) price.get("yyyy")),
                                    Integer.parseInt(date[0]), Integer.parseInt(date[1])))
                            .stock(stock)
                            .build();

                    stockInfoRepository.save(stockInfo);
                }
            }
        }
    }
}