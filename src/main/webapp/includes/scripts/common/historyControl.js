/**
 * Created by thinkpad on 2020-12-10.
 */
//--------------------  历史管理 -----------------------------------
function loadToDiv(url,title,usePush){
    $("#mycontent").load(url,function (e) {
        bindDivA();
    });
    if(title){
        $("#contentHeader").html(title);
    }
    var state = { 'pageurl':url,'pagetitle':title};

    if(usePush){
        console.log("push state of "+state);
        history.pushState(state, title, "/?child="+url+"&title="+$("#contentHeader").text());
    }
}
function bindDivA(){
    console.log("bindInframeA called");
    $(".inframe").bind("click",function(e){
        //var href = this.href;
        var href  = $(this).attr("href");
        alert(href)
        var title = $(this).text();
        console.log("visit href "+href);
        loadToDiv(href,title,true);
        return false;
    });
}
$(document).ready(function(){
    bindDivA();
});
if (history.pushState) {
    window.addEventListener("popstate", function() {
        console.log("popstate called ,href="+location.href +", state = "+history.state);
        if(! history.state) return;
        console.log(history.state);
        loadToDiv(history.state.pageurl,history.state.pagetitle,false);
    });
}
//--------------------  历史管理 结束 -----------------------------------