package com.example.interview_test.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="coin")
@Entity
public class Coin {
	@Id
	private String coinName;
	@Column(name="chinese_name")
	private String chineseName;
	@Column(name="rate")
	private float rate;
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "Coin [coinName=" + coinName + ", chineseName=" + chineseName + ", rate=" + rate + "]";
	}
	
	
}
