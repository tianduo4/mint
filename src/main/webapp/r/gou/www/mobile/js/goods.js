$(function(){
    //图片滚动
    TouchSlide({
        slideCell:"#fouse",
        titCell:".hd_img ul",
        mainCell:".bd_img ul",
        effect:"left",
        autoPlay:true,
        autoPage:true
    });

    //收藏或取消收藏
    $(".storegoods").click(function()
        {

            var goodsIdVal=$('#goods_id').val();
            var is_cancel=$('#is_cancel').val();
            $.post('/goods/storegoods',{goods_id:goodsIdVal,is_cancel:is_cancel},function(data)
            {
                if(data==99)
                {
                    $('.storegoods i').html('☆');
                    $('#is_cancel').val(0);//取消
                    $(".storegoods").removeClass('selected');
                    alert('取消收藏成功！');

                }
                else if(data==98)
                {

                    $('.storegoods i').html('★');
                    $('#is_cancel').val(1);//添加
                    $(".storegoods").addClass('selected');
                    alert('成功收藏！');
                    return false;
                }
                else if(data==-4)
                {
                    alert('请先登录！');
                    return false;
                }
                else if(data==-1)
                {
                    alert('该商品已收藏过','温馨提示');
                    return false;
                }
                else if(isNaN(data))
                {
                    alert('请先登录,再进行收藏！');
                    location.href=data;
                }
            });
        }
    );
    $(".list:first").show();
    $(".submenu1 li").each(function(index){
        $(this).click(function(){
            $(this).addClass("cur").siblings().removeClass("cur");
            $(".item>div").eq(index).show().siblings().hide();
        })
    });
    //查看是否收藏
    function is_store()
    {
        var goodsIdVal=$('#goods_id').val();
        $.post('/goods/is_store',{goods_id:goodsIdVal},function(data)
        {
            //objData=jQuery.parseJSON(data);
            if(data>0)
            {
                $('.storegoods i').html('★');
                $(".storegoods").addClass("selected");
                $('#is_cancel').val(1);

            }
            else
            {
                $('.storegoods i').html('☆');
                $(".storegoods").removeClass("selected");
                $('#is_cancel').val(0);
            }
        });
    }
    //动态取商品价格和销售分店信息
    function ajaxindex()
    {
        var goodsIdVal=$('#goods_id').val();
        $.post('/goods/ajaxindex',{goods_id:goodsIdVal},function(data)
        {
            var hrefval=''
            var saleprices='';
            var shopInfo='';
            objData=jQuery.parseJSON(data);

            if(objData.hasOwnProperty('shopNum') && parseInt(objData.shopNum)>0)
            {
                shopInfo=objData.shopNum.toString();/////
                hrefVal='/goods/getshop/'+goodsIdVal.toString();
                $('.merchants a span').text(shopInfo);
                $('.merchants a').attr('href',hrefVal);
            }
            else
            {
                shopInfo='暂无分店在销售此商品';
                hrefVal='javascript:void(0);'
                $('.merchants a').text(shopInfo);
                $('.merchants a').attr('href',hrefVal);
            }
            if(objData.hasOwnProperty('min_price'))
            {
                saleprice=objData.min_price.toString();
            }
            if(objData.hasOwnProperty('max_price'))
            {
                saleprice=saleprice+'~'+objData.max_price.toString();
            }
            //alert(saleprice);
            $('.sp .title span').text(saleprice);
        });
    }
    $(".submenu li:last").css({"border-right":"0","margin-right":"0","padding-right":"0"});
    is_store();
    ajaxindex();

})
