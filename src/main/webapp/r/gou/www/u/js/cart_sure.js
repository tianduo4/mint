
	jQuery(document).ready(function(){
		totalDeliveryFee();
		init();
	});
	
	//d:优惠券id；m:店铺id
	function ajaxmemberCoupon(d,m,s){
		$.post(URLPrefix.url+"/coupon/ajaxmemberCoupon.jspx", {
			'couponId':d,
			'storeId':m,
			'itemStr':s
		}, function(data) {
			if(data.status==0) {
	 			location.href='${loginUrl}';
	 		} else if(data.status==1){
				var couponPrice=data.couponPrice;
				if(m==''){
					$("#coupon_price_0").html(couponPrice);
					totalDeliveryFee();
				}else{
					$("#coupon_price_"+m).html(couponPrice);
					totalDeliveryFee();
				}
			}else if(data.status==2){
				var couponPrice=data.couponPrice;
				if(m==''){
					$("#coupon_price_0").html(couponPrice);
					totalDeliveryFee();
				}else{
					$("#coupon_price_"+m).html(couponPrice);
					totalDeliveryFee();
				}
			}
		},'json');		
	}

	function totalDeliveryFee(){
		var weightprice=0.0;
		var deduceAmountPrice = 0.0;
		$("#[id^='weight_price_']").each(function(){
			weightprice+=parseFloat(jQuery(this).val());
			$("#totalDeliveryFee").html(weightprice);
		});
		$("#[id^='coupon_price_']").each(function() {
			deduceAmountPrice += parseFloat(jQuery(this).html());
			$("#deduceAmount").html(deduceAmountPrice);
		});
		init();
	}

	function init(){
		var price=0.0;
		var weight=0.0;
		var price=0.0;
		var spid;
			$("#[id^='cart_weight_']").each(function(){
			var w=parseFloat(jQuery(this).html());	
			weight =accAdd(weight,w);	
			$("#cart_weight").html(weight);
			});
			$("#[id^='cart_price_']").each(function(){
				price+=parseFloat(jQuery(this).html());
				$("#cart_price").html(price);
			});
			var cart_price= $("#cart_price").html();
			var popularityPrice=$("#popularityPrice").html(); 
			var weightprice=$("#totalDeliveryFee").html(); 
			var deduceAmount = $("#deduceAmount").html();
			$("#amount_payable").html(parseFloat(cart_price)+parseFloat(weightprice)-parseFloat(deduceAmount)-parseFloat(popularityPrice));
	}


	function accAdd(arg1,arg2){ 
		var r1,r2,m; 
		try{
			r1=arg1.toString().split(".")[1].length;
		}catch(e){
			r1=0;
		} 
		try{
			r2=arg2.toString().split(".")[1].length;
		}catch(e){
			r2=0;
		} 
		m=Math.pow(10,Math.max(r1,r2)); 
		return (arg1*m+arg2*m)/m; 
	} 

	$(function() {
		$("#jvForm").validate();
		var obj = $("select[name='deliveryMethodSelector']");
		for (var i = 0; i < obj.length; i++) {
			if (window.event) {
				obj[i].fireEvent("onchange");
			} else {
				var e = document.createEvent('HTMLEvents');
				e.initEvent("change", false, false);
				obj[i].dispatchEvent(e);
			}
		}
	});
	
	//获得城市
	function getResultCity(d){
		$.post(URLPrefix.url+"/shopMemberAddress/findAllCity.jspx", {"id":d}, 
			function(data) {
						if(data.success){
							$("#itemcity").html("");
							 var select="<select name='cityId' onchange='getResultCountry(this.value)'>";
							for(var i=0;i<data.ids.length;i++){
								select+="<option value='"+data.ids[i]+"'>&nbsp;"+data.citys[i]+"&nbsp;</option>";
							}
							select+="</select>";
							getResultCountry(data.ids[0]);
							$("#itemcity").append(select);
						} 
		 },"json");
	}
	
	//获得县、区
	function getResultCountry(a){
		$.post(URLPrefix.url+"/shopMemberAddress/findAllCountry.jspx", {"id":a},
			 function(data) {
					if(data.success){
						$("#itemarea").html("");
						 var select="<select name='countryId'>";
						  for(var i=0;i<data.ids.length;i++){
								select+="<option value='"+data.ids[i]+"'>&nbsp;"+data.areas[i]+"&nbsp;</option>";
						 }
						select+="</select>";
						$("#itemarea").append(select);
					} 
			},"json");
	}
	
	function showRemark(chk) {
		if (chk.checked) {
			$("#remark").fadeIn("500");
		} else {
			$("#remark").fadeOut("500");
		}
	}