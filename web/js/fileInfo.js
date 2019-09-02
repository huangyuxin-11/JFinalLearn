

function buttonquery() {
    var fileName =$('#filename').val();
    var fileContent =$('#filename').val();

    var tbody = document.getElementsByClassName("file-info-list");
    tbody =$(tbody);
    tbody.empty();
    $.ajax({
        type:"post",
        async:false,
        url:'/query/fileQuery',
        data:{
            "fileName":fileName,
            "fileContent":fileContent
        },
        dataType:"json",
        success:function(result){
            if(result){
                var data =result.records;
                if(data.length == 0){
                    return;
                }
                for(var i=0;i<data.length;i++){
                    var tr =renderFile(data[i]);
                    tbody.append(tr);
                }
            }
            // window.location.reload();
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










