package com.project.nmt.service;

import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChartService {

    private final StockInfoRepository sir;

    public ResponseEntity<JSONObject> json_get(Stock stock) {//구글차트를 위한 데이터 처리->json변환
        ResponseEntity<JSONObject> entity;
        List<StockInfo> list = sir.findAllByStock(stock);

        JSONObject data = new JSONObject();
        JSONObject col1 = new JSONObject();
        JSONObject col2 = new JSONObject();
        JSONArray title = new JSONArray();

        col1.put("label", "날짜");
        col1.put("type", "string");
        col2.put("label", "금액");
        col2.put("type", "number");

        title.add(col1);
        title.add(col2);

        data.put("cols", title);


        JSONArray body = new JSONArray();
        for (StockInfo dto : list) {
            JSONObject name = new JSONObject();
            name.put("v", dto.getInfoDate()); //일자 -> v 객체
            JSONObject price = new JSONObject();
            price.put("v", dto.getPrice()); //가격 ->v 객체

            //  v객체를 row 배열을 만든후 추가한다.
            JSONArray row = new JSONArray();
            row.add(name);
            row.add(price);

            //   c 객체 를 만든후 row 배열을 담는다.
            JSONObject c = new JSONObject();
            c.put("c", row);
            // c 객체를 배열 형태의 body 에 담는다.
            body.add(c);
        }
        // 배열 형태의 body 를 rows 키값으로 객체 data 에 담는다.
        data.put("rows", body);

        try {
            entity = new ResponseEntity<JSONObject>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(" 에러            -- ");
            entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
