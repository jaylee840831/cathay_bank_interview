package com.example.interview_test.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.interview_test.DAO.Coin;

public interface CoinRepository extends JpaRepository<Coin, String>{

	@Query("SELECT c FROM Coin c")
	List<Coin>getAllCoin();
	
	@Query(value="SELECT * FROM Coin c WHERE c.coin_name=:name",nativeQuery=true)
	Coin findByCoinName(@Param("name")String name); 
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO Coin (coin_name,chinese_name,rate) VALUES (:name,:chineseName,:rate)",nativeQuery=true)
	void addCoin(@Param("name")String name,@Param("chineseName")String chineseName,@Param("rate")float rate); 
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Coin SET coin_name = :newName,chinese_name = :chineseName,rate = :rate WHERE coin_name = :name",nativeQuery=true)
	void updateCoin(@Param("name")String name,@Param("newName")String newName,@Param("chineseName")String chineseName,@Param("rate")float rate);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM Coin c WHERE c.coin_name= :name",nativeQuery=true)
	void deleteByCoinName(@Param("name")String name);
}