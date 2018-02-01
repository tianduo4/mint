function is_weixin(){ 
   var ua = navigator.userAgent.toLowerCase(); 
   if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
		return true; 
   }else{ 
		return false; 
   } 
 } 
 function rem(){
	 document.documentElement.style.fontSize = document.documentElement.clientWidth /7.5 + 'px';
     if(document.documentElement.clientWidth>=414){
     	document.documentElement.style.fontSize='57.5px';
     }
 }
window.onload = function () {
     rem();
	 var isWeiXin = is_weixin();
	
	 if(isWeiXin){ 
              
		$(".three-weChat").show();
	}    
};
window.onresize = function () {
	 rem();
	 var isWeiXin = is_weixin();
	 if(isWeiXin){  
		$(".three-weChat").show();
	 }	 
}