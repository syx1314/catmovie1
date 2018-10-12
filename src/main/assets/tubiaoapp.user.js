javascript:
function play() {
    'use strict';
    var curPlaySite = '';
    var curWords = '';
    var videoSite = window.location.href;
    var reYk = /youku/i;
    var reAqy = /iqiyi/i;
    var reLS = /le/i;
    var reTX = /qq/i;
    var reTD = /tudou/i;
    var reMG = /mgtv/i;
    var reSH = /film.sohu/i;
    var reSHTV = /tv.sohu/i;
    var reAF = /acfun/i;
    var reBL = /bilibili/i;
    var rePP = /pptv/i;
    var rem19 = /1905/i;
    var remigu = /migu/i;
    var reYYT = /yinyuetai/i;
    var repupudy = /pupudy/i;
    var vipBtn = '<a id="pupudyBtn" style="cursor:pointer;text-decoration:none;color:#FF6600;padding:0 5px;border:1px solid #FF6600;">VIP随心看-酷</a>';
    var mSearchBtn = '<a id="pupudySearchBtn" target="_blank" style="cursor:pointer;text-decoration:none;color:#FF6600;padding:0 5px;border:1px solid #FF6600;">去看最新大片</a>';
    // youku
    if(reYk.test(videoSite)){
        var youkuTitle = $('#playerBox');
        youkuTitle.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        curWords = youkuTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // IQIYI
    if(reAqy.test(videoSite)){
        var iqiyiTitle = $('#widget-videotitle');
        iqiyiTitle.parent('.mod-play-tit').append(vipBtn).append(mSearchBtn);
        $('#pupudyBtn').css({'font-size':'16px','display':'inline-block','height':'24px','line-height':'24px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-size':'16px','display':'inline-block','height':'24px','line-height':'24px','margin':'0 5px'});
        if($('#drama-series-title').length !== 0){
         curWords = $('#drama-series-title').find('a').text();
        }else{
         curWords = iqiyiTitle.text();
        }
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );

    }
    // LETV
    if(reLS.test(videoSite)){
        var lsTitle = $('.briefIntro_tit');
        lsTitle.after(mSearchBtn).after(vipBtn);
        lsTitle.css('float','left');
        $('#pupudyBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
       	if($('.Info').find('.title').find('h3').length !== 0){
        	curWords = $('.Info').find('.title').find('h3').text();
        }else{
        	curWords = lsTitle.text();
        }
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // QQ
    if(reTX.test(videoSite)){
        var qqTitle = $('.video_score');
        qqTitle.eq(0).after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        if($('.player_title').length !== 0 && $('.player_title').find('a').length === 0){
        	curWords = $('.player_title').text();
        }else{
        	curWords = $('._base_title').text();
        }
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // tudou
    if(reTD.test(videoSite)){
        var tdTitle = $('#videoKw');
        tdTitle.parent('.fix').append(vipBtn);
        $('#pupudyBtn').css({'font-size':'16px','display':'inline-block','height':'22px','line-height':'22px','margin':'14px 5px 0'});
    }
    // MGTV
    if(reMG.test(videoSite)){
        var mgTitle = $('.v-panel-title');
        mgTitle.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
    	curWords = mgTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // SOUHU
    if(reSH.test(videoSite)){
        var shTitle = $('.player-top-info-name');
        shTitle.append(vipBtn).append(mSearchBtn);
        shTitle.find('h2').css({'float':'left'});
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        curWords = shTitle.find('h2').text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // SOUHUTV
    if(reSHTV.test(videoSite)){
        var shtvTitle = $('.crumbs');
        shtvTitle.append(vipBtn).append(mSearchBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'10px','display':'inline-block','height':'20px','line-height':'20px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'10px','display':'inline-block','height':'20px','line-height':'20px','margin':'0 5px'});
        curWords = shtvTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // acfun
    if(reAF.test(videoSite)){
        var acTitle = $('.head').find('.title');
        acTitle.append(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'20px','line-height':'20px','margin':'0 5px'});
    }
    // bilibili
    if(reBL.test(videoSite)){
        var biliTitle = $('h1');
        biliTitle.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'28px','line-height':'28px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'28px','line-height':'28px','margin':'0 5px'});
        curWords = biliTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // pptv
    if(rePP.test(videoSite)){
        var pptvTitle = $('.g-1408-hd');
        pptvTitle.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        curWords = pptvTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // m19
    if(rem19.test(videoSite)){
        var m19Title = $('.nav-title');
        m19Title.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        curWords = m19Title.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // migu
    if(remigu.test(videoSite)){
        var miguTitle = $('.phone-look');
        miguTitle.after(mSearchBtn).after(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        $('#pupudySearchBtn').css({'font-weight':'bold','font-size':'16px','display':'inline-block','height':'30px','line-height':'30px','margin':'0 5px'});
        curWords = miguTitle.text();
        $('#pupudySearchBtn').attr('href','http://www.hui65.com/?kuuhui.com' );
    }
    // yinyuetai
    if(reYYT.test(videoSite)){
        var yytTitle = $('.videoName');
        yytTitle.append(vipBtn);
        $('#pupudyBtn').css({'font-weight':'bold','font-size':'14px','display':'inline-block','height':'28px','line-height':'28px','margin':'0 5px'});
    }
    $('#pupudyBtn').on('click',function(){
        curPlaySite = window.location.href;
        window.location.href = 'http://appapi.svipv.kuuhui.com/svipjx/liulanqichajian/browserplugin/qhjx/?id=' + curPlaySite;
    });
};
