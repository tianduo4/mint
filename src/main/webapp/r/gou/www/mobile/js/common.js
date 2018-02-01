
$(function(){
    var tab_menu_li = $('.tab_menu li');
    $('.tab_con .common:gt(0)').hide();

    tab_menu_li.click(function(){
        var index = $(this).index()+1;
        $(this).addClass('selected')
            .siblings().removeClass('selected');

        tab_menu_li.each(function(){
            var i = $(this).index()+1;

        })

        var tab_menu_li_index = tab_menu_li.index(this);
        $('.tab_con .common').eq(tab_menu_li_index).show()
            .siblings().hide();
    }).click(function(){
        $(this).addClass('hover');
    },function(){
        $(this).removeClass('hover');
    });

});

$(function(){
    var tab_menu_li = $('.tab_menu_category li');
    $('.tab_con .common:gt(0)').hide();

    tab_menu_li.click(function(){
        var index = $(this).index()+1;
        $(this).addClass('selected')
            .siblings().removeClass('selected');

        tab_menu_li.each(function(){
            var i = $(this).index()+1;

        })

        var tab_menu_li_index = tab_menu_li.index(this);
        $('.tab_con .common').eq(tab_menu_li_index).show()
            .siblings().hide();
    }).click(function(){
            $(this).addClass('hover');
        },function(){
            $(this).removeClass('hover');
        });

});


//加载提示
function loading(target){
    $("#"+target).html("<div style='text-align: center; margin: 0px auto; padding: 150px 50px 50px 0px;'><img src='/public/img/loading.gif'></div>");
}




$(function(){
    //菜单折叠和隐藏
    $(".menubtn").click(function(){
        $(".searchBox").hide();
        $(".menu").slideToggle();
    });
    getLoginStatus();
});
/**
 * 登录状态判断
 */
function getLoginStatus(){
    if ($("li").hasClass('info_url'))
    {
        $.post("/login/keep_login",{r:Math.random()},function(data){
            objData=jQuery.parseJSON(data);
            if(objData.tkFlag==99){
                $('.info_url a img').hide();
                $('.info_url span').text(objData.user_name);
                $('.info_url a').attr('href',objData.info_url);

                $('.login_out span').text('退出');
                $('.login_out a').attr('href',objData.out_url);
            }
        })
    }
}