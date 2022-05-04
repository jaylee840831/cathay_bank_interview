var coins = {};
coins["USD"] = "美金";
coins["GBP"] = "英鎊";
coins["EUR"] = "歐元";
//console.log(coins)

var coins2={}
var coins3=[]

$(document).ready(function() {
		let coindeskAPI='https://api.coindesk.com/v1/bpi/currentprice.json'
	
    	$.ajax({
	    type: "GET",
	    url: coindeskAPI,
	    dataType: "json",
	    success: function (response) {
	    	for(var key in response){
  				//console.log(key + ' - ' + response[key])
		
				if(typeof response[key] != 'object'){
				$('#coindeskAPI').append(
					"<div style='border-bottom: dashed blue;'>"
						+"<h4 style='color:blue;'>"+key+"</h4>"
						+response[key]
					+"</div>"
					+"<br>")
				}
				else{
					$('#coindeskAPI').append(
						"<div style='border-bottom: dashed blue;'>"
							+"<h4 style='color:blue;'>"+key+"</h4>"
							+ objectToString(response[key])
						+"</div>"
						+"<br>");
				}
				
			}
			
			saveCoins(coins2)	
			
	    },
	    error: function (thrownError) {
	      console.log(thrownError);
	    }
	  });

	$( ".select_btn" ).click(function() {
		select_btn($(this).val())
	});
	
	$( ".submit_btn" ).click(function() {
		submit_btn($(this).val())
	});


	$("#transform_coindesk").click(function(){
	   transform_coin()
	});
});

//切換crud表單
function select_btn(btn_status){
	if(btn_status=='P'){
		$('#post_update_data').show();
		$('#select_data').hide();
		$('#delete_data').hide();
	}
	else if(btn_status=='S'){
		$('#post_update_data').hide();
		$('#select_data').show()
		$('#delete_data').hide();
	}
	else if(btn_status=='D'){
		$('#post_update_data').hide();
		$('#select_data').hide();
		$('#delete_data').show();
	}
}

function submit_btn(btn_status){
	
	//按下新增或更新
	if(btn_status=='post'){
		if($('#coin_name').val()=='' || $('#chinese_name').val()=='' || $('#rate').val()==''){
			alert("請輸入完整資料")
		}
		else{
				Url='/api/v1/add/coin'
				let c = {};
				c["coinName"] = $('#coin_name').val();
				c["chineseName"] = $('#chinese_name').val();
				c["rate"] = $('#rate').val();
				$.ajax({
				  type: 'POST',
				  url: Url,
				  data: JSON.stringify(c),
				  dataType: "json",
				  contentType: "application/json;charset=utf-8",
				  success: function(returnData){
						if(returnData!=null){
							alert('成功')
						}
						else{
							alert('失敗')
						}
				    },
				    error: function(thrownError){
				        console.log(thrownError);
				    }
				});
		}
	}
	//按下查詢
	else if(btn_status=='search'){
		if($('#select').val()==''){
			showAlert()
		}
		else{
				Url='/api/v1/get/coin/'+$('#select').val().trim()
				$.ajax({
				  type: 'GET',
				  url: Url,
				  dataType: "json",
				  contentType: "application/json;charset=utf-8",
				  success: function(returnData){
						if(returnData!=null){
							alert('查詢結果\n'+'幣名: '+returnData.coinName+'\n中文幣名: '+returnData.chineseName+'\n匯率: '+returnData.rate)
						}
						else{
							alert('失敗')
						}
				    },
				    error: function(thrownError){
				        console.log(thrownError);
				    }
				});
		}
	}
	//按下刪除
	else if(btn_status=='delete'){
		if($('#delete').val()==''){
			showAlert()
		}
		else{
				Url='/api/v1/delete/coin/'+$('#delete').val().trim()
				$.ajax({
				  type: 'DELETE',
				  url: Url,
				  dataType: "json",
				  contentType: "application/json;charset=utf-8",
				  success: function(returnData){
						alert("刪除成功")
				    },
				    error: function(thrownError){
				        console.log(thrownError);
				    }
				});
		}
	}
}

function showAlert(){
	alert("請輸入幣名")
}

function saveCoins(obj){
	for(var key in coins){
		if(Object.keys(obj).includes(key)){
			postCoin(key,coins[key],obj[key])
		}
	}
	
	//console.log(JSON.stringify(coins3))
	
	postCoinUrl='/api/v1/add/all/coin'
	$.ajax({
	  type: 'POST',
	  url: postCoinUrl,
	  data: JSON.stringify(coins3),
	  dataType: "json",
	  contentType: "application/json;charset=utf-8",
	  success: function(returnData){
	        console.log(returnData);
	    },
	    error: function(thrownError){
	        console.log(thrownError);
	    }
	});
}

function postCoin(code,coin_chinese_name,rate){
	//console.log(code,coin_chinese_name,rate)
	
	var dataJSON = {};
	dataJSON["coinName"] = code;
	dataJSON["chineseName"] = coin_chinese_name;
	dataJSON["rate"] = rate;
	coins3.push(dataJSON)
}

function objectToString(obj){
	let str='';
	for(var key in obj){
		
			if(typeof obj[key] != 'object'){
				str+=key + ': ' + obj[key]+'<br/>'	
			}
			else{
				coins2[key]=obj[key].rate_float
				str+=key + ': ' + objectToString(obj[key])+'<br/>'
			}
	}
	
	return str;
}

function transform_coin(){
		var dateTime = getTime();
	
		$('#update_time').html('更新時間')
		$('#coindeskAPI2').html('')
	
		$('#update_time').text($('#update_time').text()+':'+dateTime).show()
	
		let transformAPI='/api/v1/transform/coin'
		
	   	$.ajax({
	    type: "GET",
	    url: transformAPI,
	    dataType: "json",
	    success: function (response) {
		
		$('#coindeskAPI2').append(
			"<thead>"
				+"<tr>"
				+"<th>"+"幣名"+"</th>"
				+"<th>"+"中文幣名"+"</th>"
				+"<th>"+"匯率"+"</th>"
				+"</tr>"
			+"</thead>"
			+"<tbody>"	
		)
		
		if(response!=null || response.length!=0){
			for(var i=0;i<response.length;i++){
				//console.log(response[i].coinName+' '+response[i].chineseName)
				$('#coindeskAPI2').append(
					"<tr>"
					+"<td>"+response[i].coinName+"</td>"
					+"<td>"+response[i].chineseName+"</td>"
					+"<td>"+response[i].rate+"</td>"
					+"<tr/>"
				)
			}
		}
		else{
			alert('no data')
		}
		
		
		$('#coindeskAPI2').append(
			"</tbody>"
		)
		
			
	    },
	    error: function (thrownError) {
	      console.log(thrownError);
	    }
	  });
}

function getTime(){
	var today = new Date();
	var year =today.getFullYear()
	var month=fill_zero(today.getMonth()+1)
	var day=fill_zero(today.getDate())
	
	var hour=fill_zero(today.getHours())
	var minute=fill_zero(today.getMinutes())
	var second=fill_zero(today.getMinutes())
	
	var date = year+'/'+month+'/'+day;
	var time = hour + ":" + minute + ":" + second;
	return date+' '+time;
}

function fill_zero(num){
	if(num<10){
		return'0'+num
	}
	return num
}