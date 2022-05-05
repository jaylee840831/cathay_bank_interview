package com.example.interview_test.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.repository.CoinRepository;
import com.example.interview_test.service.CoinService;

@RestController()
@RequestMapping("/api/v1")
public class ApiController {
	
	@Autowired
	CoinService coinService;
	
	//轉換後的資料
	@GetMapping("/transform/coin")
	public ResponseEntity<List<Coin>> transformCoin() {
		return new ResponseEntity<List<Coin>>(coinService.getAllCoin(), HttpStatus.OK);
	}
	
	//查詢資料
	@GetMapping("/get/coin/{name}")
	public ResponseEntity<Object> getCoin(@PathVariable("name")String name) {
		if(coinService.getCoin(name)!=null) {
			return new ResponseEntity<Object>(coinService.getCoin(name), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	//新增資料
	@PostMapping("/add/coin")
	public ResponseEntity<Object> addCoin(@RequestBody Coin coin){
		coinService.saveCoin(coin);
		if(coinService.getCoin(coin.getCoinName())!=null) {
			return new ResponseEntity<Object>(coinService.getCoin(coin.getCoinName()), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);	
	}
	
	//修改資料
	@PutMapping("/put/coin/{previous_name}")
	public ResponseEntity<Object> updateCoin(@RequestBody Coin c,@PathVariable("previous_name") String previous_name){
		coinService.updateCoin(c, previous_name);
		return new ResponseEntity<Object>(coinService.getCoin(c.getCoinName()), HttpStatus.OK);
	}
	
	//刪除資料
	@DeleteMapping("/delete/coin/{name}")
	public ResponseEntity<Void> deleteCoin(@PathVariable("name")String name) {
		coinService.deleteCoin(name);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/add/all/coin")
	public ResponseEntity<List<Coin>> addAllCoin(@RequestBody List<Coin> c) {
//		System.out.println("更新或新增幣名、中文名、匯率");
//		c.forEach(coin -> {
//			System.out.println(coin.toString());
//		});
		coinService.saveCoins(c);
		
		return new ResponseEntity<List<Coin>>(coinService.getAllCoin(), HttpStatus.OK);
	}
}
