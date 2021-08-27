package com.project.nmt.dataSetting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.repository.StockInfoRepository;
import com.project.nmt.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

//http://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=02&p_startday=2015-10-01&p_endday=2015-12-01&p_itemcategorycode=200&p_itemcode=231&p_kindcode=01&p_productrankcode=04&p_countrycode=1101&p_convert_kg_yn=Y&p_cert_key=111&p_cert_id=222&p_returntype=json

@RestController
public class DataSetting {
	String API = "ef02eb73-7007-4884-8401-e1bee7361066";
	String keyword[] = { "고구마", "감자", "대파", "시금치", "양파", "깻잎", "고추", "파프리카", "호박", "미나리" };
	String categoryCode[] = { "100", "100", "200", "200", "200", "200", "200", "200", "200", "200" };
	String item[] = { "sweet potato", "potato", "spring onion", "spinach","onion" ,"perilla leaf", "chili", "Paprika",
			"Pumpkin", "water parsley" };
	String itemCode[] = { "151", "152", "246", "213", "245", "253", "243", "256", "224", "252" };
	String kindCode[] = { "00", "01", "00", "00", "00", "00", "00", "00", "01", "00" };

	@Autowired
	StockRepository stockRepository;
	@Autowired
	StockInfoRepository stockInfoRepository;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("/setStockInfo")
	public List<Map<String, Object>> getString() throws IOException {
		List<Map<String, Object>> prices = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String end_dt = format.format(now);
		now.setYear(now.getYear() - 1);
		String start_dt = format.format(now);

		// 3년치 이상 데이터를 고른 것들
		
		JsonParser jsonParser = JsonParserFactory.getJsonParser();
		for (int idx = 0; idx < 10; idx++) {
			System.out.println(idx);
			// 요청 URL
			URL url = null;
			try {
				url = new URL(// url설정
						"https://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=02&"
								+ "p_startday=" + start_dt + "+&p_endday=" + end_dt + "&"
								+ "p_itemcategorycode=" + categoryCode[idx] + "&p_itemcode=" + itemCode[idx]
								+ "&p_kindcode=" + kindCode[idx] + "&"
								+ "p_productrankcode=04&p_countrycode=1101&p_convert_kg_yn=Y&" + "p_cert_key=" + API
								+ "&p_cert_id=222&p_returntype=json");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// url.openStream()으로 요청 결과를 stream으로 받음.
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

			// JsonParser를 사용하기 위해 stream을 하나의 String 객체로 묶음.
			String line = br.readLine();
			StringBuilder json = new StringBuilder();
			while (line != null && !line.equals("")) {
				json.append(line);

				line = br.readLine();
			}

			// JsonParser를 통해 요청 결과를 Map<String, Object>로 변환하여 반환.
			Map<String, Object> map = jsonParser.parseMap(json.toString());
			map = (Map<String, Object>) map.get("data");
			prices = (List<Map<String, Object>>) map.get("item");
			
			
			Stock stock = stockRepository.findByKeyword(keyword[idx]);

			System.out.println(stock.getId());

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
		return prices;
	}
	
	@GetMapping("/setStock")
	public void setStock() {
		for (int idx = 0; idx < 10; idx++) {
		Stock stock=Stock.builder()
				.quantity(1000)
				.name(item[idx])
				.keyword(keyword[idx])
				.build();
		stockRepository.save(stock);
		}
		
	}
}