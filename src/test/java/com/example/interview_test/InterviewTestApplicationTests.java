package com.example.interview_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.repository.CoinRepository;

@SpringBootTest
class InterviewTestApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Autowired
	CoinRepository coinRepository;
	
	@Autowired
	RestTemplate restTemplate;

//	@Test
//	void testAddAllCoin() {
//		fail("Not yet implemented");
//	}

	//依幣別查詢對應的資料
	@Test
	void testGetCoinByName() {
		Optional<Coin> coin=coinRepository.findById("USD");
		assertNotNull(coin);
		
		System.out.println("依幣別(USD)查詢對應的資料");
		System.out.println(coin.toString());
		System.out.println("----------------------------------------------------");
	}

	//新增幣別資料
	@Test
	void testAddCoin() {
		Coin coin=new Coin();
		coin.setCoinName("NTD");
		coin.setChineseName("新台幣");
		coin.setRate(30000.123f);
		
		coinRepository.save(coin);
		assertNotNull(coinRepository.getById("NTD"));
	}

	//更新幣別資料
	@Test
	void testUpdateCoin() {
		Optional<Coin> coin=coinRepository.findById("USD");
		System.out.println("幣別(USD)資料更新前");
		System.out.println(coin.toString());
		coin.get().setChineseName("美元");
		coinRepository.save(coin.get());
		
		System.out.println("幣別(USD)資料更新後");
		System.out.println(coin.get().toString());
		
		System.out.println("----------------------------------------------------");
		
		assertEquals("美元",coinRepository.findById("USD").get().getChineseName());
	}

	//刪除幣別資料
	@Test
	void testDeleteCoin() {
		coinRepository.deleteById("NTD");
		assertThat(coinRepository.existsById("NTD")).isFalse();
	}
	
	//資料轉換
	@Test
	void testTransformCoin() {
		List<Coin>coins=coinRepository.findAll();
		assertThat(coins).size().isGreaterThan(0);
		
		System.out.println("資料轉換");
		for(Coin c:coins) {
			System.out.println(c.toString());
		}
		System.out.println("----------------------------------------------------");
	}

	//coindesk api
	@Test
	void testCoinDeskAPI() {
		String url="https://api.coindesk.com/v1/bpi/currentprice.json";
		String coindesk=restTemplate.getForObject(url, String.class);
		
		assertNotNull(coindesk);
		
		System.out.println("coindesk api");
		System.out.println(coindesk.toString());
		System.out.println("----------------------------------------------------");
	}

}
