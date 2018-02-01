
$(function(){
	//商品分类
	$('.all-goods .fenlei').hover(function(){
		$(this).addClass('sysp').find('s').show();
		$(this).find('.chanpin-wrap').show();
	},function(){
		$(this).removeClass('sysp').find('s').show();
		$(this).find('.chanpin-wrap').hide();
	});
	});

