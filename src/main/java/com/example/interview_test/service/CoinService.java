package com.example.interview_test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.repository.CoinRepository;

@Service
public class CoinService {

	@Autowired
	CoinRepository coinRepository;
	
	public List<Coin>getAllCoin(){
		return coinRepository.findAll();
	}
	
	public Optional<Coin> getCoin(String name){
		return coinRepository.findById(name);
	}
	
	public void updateCoin(Coin coin,String name){
		coinRepository.save(coin);
	}

	public void saveCoin(Coin coin) {
		coinRepository.save(coin);
	}
	
	public void saveCoins(List<Coin> coins) {
		coinRepository.saveAll(coins);
	}
	
	public void deleteCoin(String name) {
		coinRepository.deleteById(name);
	}
}
