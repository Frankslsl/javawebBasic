function accountDel(id){
    // 经过确认,把id通过get方式传到控制层
    if(confirm("Confirm to delete this record?")){
        window.location.href ='account.do?id='+id + '&operate=del';
    }
}
function page(pageNum,pageTotal){
    // 判断pageNum如果小于1,则赋值为1, 满足firstpage,prepage的正确性.大于pageTotal,赋值pageTotal,满足达到lastpage时nextpage的正确性
    if (pageNum<1){
        pageNum=1
    }else if (pageNum>pageTotal) {
        pageNum=pageTotal
    }
    window.location.href='account.do?pageNum='+pageNum;
}