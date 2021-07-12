<%@ page import="java.util.UUID" %>
<%@ page import="com.wxx.conference.model.common.UploadFile" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.commons.lang.StringUtils" %><%--
  Created by IntelliJ IDEA.
  User: wxx
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    String localid = UUID.randomUUID().toString().replace("-","");
    String path = request.getContextPath();
    String foreignid = request.getParameter("foreignid");
    String wmtext = request.getParameter("wmtext");
    String isShowTime = request.getParameter("isShowTime");
    String transparency = request.getParameter("transparency");
    String wFontSize = request.getParameter("wFontSize");
    String category = request.getParameter("category");
    if(category == null){
        category = "";
    }
    String multiple = request.getParameter("multiple");
    String allowedExtensions = request.getParameter("allowedExtensions");
    if(allowedExtensions == null || allowedExtensions.length() == 0){
        allowedExtensions = "[]";
    }
    String sizeLimit = request.getParameter("sizeLimit");
    if(sizeLimit == null){
        sizeLimit = "20480000"; //20M
    }
    List<UploadFile> list = (List<UploadFile>)request.getAttribute("list");

    String ids = "";
    if(list != null){
        for(int i=0; i< list.size(); i++){
            if(category.equals(list.get(i).getCategory())) {
                ids += list.get(i).getFileId() + ",";
            }
        }
    } else {
        list = new ArrayList<UploadFile>();
    }
    String showFlag = request.getParameter("showFlag");
    if(StringUtils.isEmpty(showFlag)){
        showFlag = "0";
    }
    String onComplete = request.getParameter("onComplete");
    if(onComplete != null  && onComplete.length()>0 ) {
        if("0".equals(showFlag)){
            onComplete = onComplete + "(id, fileName, data);";
        } else {
            onComplete = onComplete + "(id, fileName, data); return;";
        }
    }
    String onDelete = request.getParameter("onDelete");
    if(onDelete != null  && onDelete.length()>0 ) {
        onDelete = onDelete + "();";
    }
%>

<input type="hidden" id="fileids_<%=localid%>" data-foreignid="<%=foreignid%>" data-category="<%=category%>" value="<%=ids%>" />
<div id="fine-uploader_<%=localid%>"></div>

<script type="text/template" id="qq-template_<%=localid%>">
    <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="">
        <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
            <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
        </div>
        <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
            <span class="qq-upload-drop-area-text-selector"></span>
        </div>
        <div class="qq-upload-button-selector qq-upload-button">
            <div>浏览上传</div>
        </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>处理拖动文件...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
        <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals" style="display:none">
            <li>
                <div class="qq-progress-bar-container-selector">
                    <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                </div>
                <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                <span class="qq-upload-file-selector qq-upload-file"></span>
                <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                <span class="qq-upload-size-selector qq-upload-size"></span>
                <button type="button" class="qq-btn qq-upload-cancel-selector qq-upload-cancel">取消</button>
                <button type="button" class="qq-btn qq-upload-retry-selector qq-upload-retry">重试</button>
                <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">删除</button>
                <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
            </li>
        </ul>

        <dialog class="qq-alert-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">关闭</button>
            </div>
        </dialog>

        <dialog class="qq-confirm-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">否</button>
                <button type="button" class="qq-ok-button-selector">是</button>
            </div>
        </dialog>

        <dialog class="qq-prompt-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <input type="text">
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">取消</button>
                <button type="button" class="qq-ok-button-selector">确定</button>
            </div>
        </dialog>
    </div>
</script>

<ul id="upload-file-list_<%=localid%>" class="upload-file-list">
    <%
        for(int i=0; i< list.size(); i++){
            if(category.equals(list.get(i).getCategory())){
    %>
    <li id="li_<%=list.get(i).getFileId()%>">
        <a href="<%=path%>/upload/download/<%=list.get(i).getFileId()%>" target="_blank" class="filename">
            <span><%=list.get(i).getFileName()%><%=list.get(i).getExtension()%></span>
            <span>[<%=list.get(i).getFileSize()%>]</span>
        </a>
        <a href="javascript:void(0)" onclick="deleteFile_<%=localid%>('<%=list.get(i).getFileId()%>')">删除</a>
    </li>
    <%
            }
        }
    %>
</ul>


<script type="text/javascript">

    $(function () {

        var uploader = new qq.FineUploader({
            debug: true,
            template: "qq-template_<%=localid%>",
            element: document.getElementById('fine-uploader_<%=localid%>'),
            request: {
                endpoint: '<%=path%>/upload/file',
                params: {
                    'foreignId': '<%=foreignid%>',
                    'category': '<%=category%>',
                    'allowedExtensions': <%=allowedExtensions%>,
                    'wmtext':'<%=wmtext%>',
                    'isShowTime':'<%=isShowTime%>',
                    'transparency':'<%=transparency%>',
                    'wFontSize':'<%=wFontSize%>'
                },
                paramsInBody: true
            },
            validation: {
                //allowedExtensions: ['jpeg', 'jpg', 'gif', 'png'],
                allowedExtensions: <%=allowedExtensions%>,
                //sizeLimit: 20480000 // 20M
                sizeLimit: <%=sizeLimit%>
            },
            deleteFile: {
                enabled: true,
                endpoint: '<%=path%>/upload/delete?qquuid=',
                forceConfirm: true
            },
            retry: {
                enableAuto: true
            },
            multiple: <%=multiple%>,
            callbacks: {
                onSubmit: function (id, fileName) {

                },
                onUpload: function (id, fileName) {

                },
                onComplete: function (id, fileName, data) {

                    if (data.errormsg != '') {
                        window.top.$.messager.alert('提示', data.errormsg);
                        return;
                    }

                    <%=onComplete%>

                    var ids = $('#fileids_<%=localid%>').val();
                    ids = ids + data.qquuid + ",";
                    $('#fileids_<%=localid%>').val(ids);

                    var html = "<li id=\"li_" + data.qquuid + "\">" +
                            <%--"   <a href=\"<%=path%>/upload/download/" + data.qquuid + "\" target=\"_blank\" class=\"filename\"><span>" + data.qqfilename + " [" + data.qqtotalfilesize + "]</span></a>" +--%>
                            "   <a href=\"<%=path%>/upload/download/" + data.qquuid + "\" target=\"_blank\" class=\"filename\" onclick=\"removeFile(this)\"><span>" + data.qqfilename + " [" + data.qqtotalfilesize + "]</span></a>" +
                            <%--"   <a href=\"javascript:void(0)\" onclick=\"deleteFile_<%=localid%>('" + data.qquuid + "')\">删除</a>" +--%>
                            "</li>";
                    $("#upload-file-list_<%=localid%>").append(html);
                }
            }
        });
    });
    function removeFile(obj){
//        alert($(obj).parent())
        $(obj).parent().remove();
    }

    function deleteFile_<%=localid%>(qquuid) {
        window.top.$.messager.confirm('提示', '确定删除吗?', function (value) {
            if (value) {

                var ids = $('#fileids_<%=localid%>').val();
                var idarray = ids.split(",");
                ids = "";
                for(i=0; i< idarray.length; i++){
                    if(idarray[i] == qquuid || idarray[i] == "") continue;
                    ids += idarray[i] + ",";
                }
                $('#fileids_<%=localid%>').val(ids);

                $('#li_' + qquuid).remove();
                <%=onDelete%>
                <%--$.ajax({--%>
                <%--url: '<%=path%>/upload/delete?fileid=' + qquuid,--%>
                <%--type: 'POST',--%>
                <%--success: function (data, status) {--%>
                <%--if (data.result) {--%>
                <%--$('#ul_' + qquuid).remove();--%>
                <%--}--%>
                <%--}--%>
                <%--});--%>

            }
        });
        return false;
    }

</script>
