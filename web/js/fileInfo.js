var current_page =1;
var page_size =10;
function buttonquery(num) {
    var fileName =$('#filename').val();
    var fileContent =$('#filename').val();
    var from = num;//page from num
    var tbody = document.getElementsByClassName("file-info-list");
    tbody =$(tbody);
    tbody.empty();
    $.ajax({
        type:"post",
        async:false,
        url:'/query/fileQuery',
        data:{
            "fileName":fileName,
            "fileContent":fileContent,
            "size":2,
            "from":from
        },
        dataType:"json",
        success:function(result){
            if(result){
                var data =result.records;
                var count =result.count;
                console.log(count);
                if(data.length == 0){
                    return;
                }

                renderPage(count,data);


                for(var i=0;i<data.length;i++){
                    var tr =renderFile(data[i]);
                    tbody.append(tr);
                }



            }
        }
    });

}

function renderFile(row){
    var tr = $("<tr class='mainrow'></tr>");
    tr.show();
    var td=$("<td style='padding:20px 0px'>"+row['filename']+"</td>");

    var td2=$("<td style='padding:20px 0px' class='filetype'>"+row['filetype']+"</td>");

    var td3=$("<td style='padding:20px 0px' class='group'>"+row['group']+"</td>");
    var td4=$("<td style='padding:20px 0px' class='path'>"+row['path']+"</td>");
    var td6=$("<td style='padding:20px 0px' ></td>");

    var tr4=$('<div type="button" class="btn-download" ><a href="/upload/downloadFile?dfsGroup='
        +row['group']+'&dfsPath='+row['path']+'&dfsName='+row['filename']+'&dfsType='
        +row['filetype']+'">下载</a></div><br/>');

    td6.append(tr4);

    tr.append(td);
    tr.append(td2);
    tr.append(td3);
    tr.append(td4);
    tr.append(td6);

    return tr;
}
function renderPage(count,data) {
    layui.use('laypage', function(){
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'file-info-list-table', //注意，这里的 test1 是 ID，不用加 # 号
            count: count ,//数据总数，从服务端得到
            limit: 2,
            curr: current_page,
            layout: ['count', 'prev', 'page', 'next', 'skip'],
            jump:function (obj, first) {
                //第一次不执行,一定要记住,这个必须有,要不然就是死循环
                var curr = obj.curr;
                //更改存储变量容器中的数据,是之随之更新数据
                current_page = obj.curr;
                page_size= obj.limit;
                if(!first) {

                    //回调该展示数据的方法,数据展示
                    // renderFileList()
                    buttonquery(current_page);
                }

            }

        });
    });

}


function testDownload() {
    var tr = $("<tr class='mainrow'></tr>");
    tr.show();
    var temp ="file.test";

    var td=$("<td style='padding:20px 0px'>file.test</td>");

    var td2=$("<td style='padding:20px 0px' class='filetype'>png</td>");

    var td3=$("<td style='padding:20px 0px' class='group'>group1</td>");
    var td4=$("<td style='padding:20px 0px' class='path'>M00/00/31/ChQBNF1LuJSASqjHAAJ4GoSY2Ug171.png</td>");
    var td6=$("<td style='padding:20px 0px' ></td>");

    var tr4=$('<div type="button" class="btn-download" ><a href="/upload/downloadtest">下载</a></div>');
    // var tr5=$("<button style='padding:20px 0px'  onclick='testDownload()'>"+"删除"+"</button>");



    td6.append(tr4);
    // td6.append(tr5);

    tr.append(td);
    tr.append(td2);
    tr.append(td3);
    tr.append(td4);
    tr.append(td6);

    return tr;
}










