//品牌确认控制
function smallChange(obj) {
	var smObj = document.getElementsByName("brand");
	var bigObj = document.getElementById("bigclassauthorize");
	if (obj.checked == true){
		bigObj.disabled=false;
	}else {
		b = true;
		for (var i = 0; i < smObj.length; i++) {
			if (smObj[i].checked == true){
				b = false;
			}				
		}
		if (b == true){
			bigObj.disabled=true;
		}
	}
}
//初始属性确认控制
function smallChange1(obj,x) {
	var smObj = document.getElementsByName("exendedsId");
	var bigObj = document.getElementById("bigclassauthorize_"+x);
	if (obj.checked == true){
		bigObj.disabled=false;
	}else {
		b = true;
		for (var i = 0; i < smObj.length; i++) {
			if (smObj[i].checked == true){
				b = false;
			}				
		}
		if (b == true){
			bigObj.disabled=true;
		}
	}
}
//选择后属性确认控制
function smallChange2(obj,x) {
	var smObj = document.getElementsByName("exendedsId");
	var bigObj = document.getElementById("bigclassauthorize1_"+x);
	if (obj.checked == true){
		bigObj.disabled=false;
	}else {
		b = true;
		for (var i = 0; i < smObj.length; i++) {
			if (smObj[i].checked == true){
				b = false;
			}				
		}
		if (b == true){
			bigObj.disabled=true;
		}
	}
}
//更多选项按钮
function clickOrderBy(a){
	$("#orderBy").val(a);
	var jvForm = getJvForm();
	jvForm.onsubmit=null;
	jvForm.submit();
}
//价格确认按钮
function searchPriceRange(){
	var d=$("#startPrice").val();
	var a=$("#endPrice").val();
   if((d.value=="")&&(a.value=="")){
	   alert("请输入价格区间");d.focus();return;
   }
   if((d.value!="")&&(a.value!="")){
	   if(Number(a.value)<Number(d.value)){
		   alert("输入价格区间 右值必须大于或等于左值");
		   a.focus();return;
		}
	}
	var jvForm = getJvForm();
	jvForm.onsubmit=null;
	jvForm.submit();
}
//价格清除按钮
function cleanPrice(){
	 document.getElementById("startPrice").value="￥";
	 document.getElementById("endPrice").value="￥";
}
//品牌多选
function MultiBrand() {  
    var bb = "";  
    var temp = "";  
    var a = document.getElementsByName("brand");  
	    for ( var i = 0; i < a.length; i++) {  
		    if (a[i].checked) {  
			    temp = a[i].value;  
			    bb = bb + "," +temp;  
		    }  
	    }  
    document.getElementById("brandId").value = bb.substring(1, bb.length); 
    var jvForm = getJvForm();
	jvForm.onsubmit=null;
	jvForm.submit();
}
//品牌全部
function AllBrand(b){
	$("#brandId").val(b);
    var jvForm = getJvForm();
	jvForm.onsubmit=null;
	jvForm.submit();
} 
//属性全部
function AllAttribute(a,b){
	$("#exendedss_"+a).val(b);
    var jvForm = getJvForm();
	jvForm.submit();
}  
//属性多选
function MultiAttribute(x) {  
    var bb = "";  
    var temp = "";  
    var a = document.getElementsByName("exendedsId");  
	    for ( var i = 0; i < a.length; i++) {  
		    if (a[i].checked) {  
			    temp = a[i].value;  
			    bb = bb + "&" +temp;  
		    }  
	    }  
    $("#exendedss_"+x).val(bb.substring(1, bb.length));
    var jvForm = getJvForm();
	jvForm.submit();
}  
//品牌更多，收起
function showMoreq(a,d){
	if(jQuery("#FacetBrandsMoreBtnq")[0]&&jQuery("#FacetBrandsBackBtnq")[0]){
		if(d&&d=="more"){
			jQuery("#FacetBrandsMoreBtnq")[0].style.display="none";
            jQuery("#FacetBrandsBackBtnq")[0].style.display="block";
        }else{
        	jQuery("#FacetBrandsMoreBtnq")[0].style.display="block";
        	jQuery("#FacetBrandsBackBtnq")[0].style.display="none";
        }
    }
	var c=9;
	if(typeof(cateType)!="undefined"&&cateType==1){c=9;}
	var b=$("#"+a).children();
	if(d&&d=="more"){
		b.each(function(e){$(this).show()})
	}else{
		b.each(function(e){if(e>c){$(this).hide()}})
	}
}
//选择后的属性更多，收起
function showMore1s(a,d,z,x){
	if(jQuery("#"+z)[0]&&jQuery("#"+x)[0]){
		if(d&&d=="more"){
			jQuery("#"+z)[0].style.display="none";
            jQuery("#"+x)[0].style.display="block";
        }else{
        	jQuery("#"+z)[0].style.display="block";
        	jQuery("#"+x)[0].style.display="none";
        }
    }
	var c=4;
	if(typeof(cateType)!="undefined"&&cateType==1){c=4;}
	var b=$("#"+a).children();
	if(d&&d=="more"){
		b.each(function(e){$(this).show()})
	}else{
		b.each(function(e){if(e>c){$(this).hide()}})
	}
}
//属性的更多，收起
function showMore2s(a,d,z,x){
	if(jQuery("#"+z)[0]&&jQuery("#"+x)[0]){
		if(d&&d=="more"){
			jQuery("#"+z)[0].style.display="none";
            jQuery("#"+x)[0].style.display="block";
        }else{
        	jQuery("#"+z)[0].style.display="block";
        	jQuery("#"+x)[0].style.display="none";
        }
    }
	var c=4;
	if(typeof(cateType)!="undefined"&&cateType==1){c=4;}
	var b=$("#"+a).children();
	if(d&&d=="more"){
		b.each(function(e){$(this).show()})
	}else{
		b.each(function(e){if(e>c){$(this).hide()}})
	}
}