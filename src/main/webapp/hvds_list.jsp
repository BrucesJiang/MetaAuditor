<%--
  Created by IntelliJ IDEA.
  User: jd
  Date: 18-8-17
  Time: 上午12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <title>HvdsList</title>
    <script type="text/javascript">
        $(document).ready(function(){
            /** 新增   **/
            $("#addBtn").fancybox({
                'href'  : 'house_edit.html',
                'width' : 733,
                'height' : 530,
                'type' : 'iframe',
                'hideOnOverlayClick' : false,
                'showCloseButton' : false,
                'onClosed' : function() {
                    window.location.href = 'house_list.html';
                }
            });

            /**编辑   **/
            $("a.edit").fancybox({
                'width' : 733,
                'height' : 530,
                'type' : 'iframe',
                'hideOnOverlayClick' : false,
                'showCloseButton' : false,
                'onClosed' : function() {
                    window.location.href = 'house_list.html';
                }
            });
        });
        /** 用户角色   **/
        var userRole = '';

        /** 模糊查询来电用户  **/
        function search(){
            $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
        }

        /** 新增   **/
        function add(){
            $("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();
        }


        /** 删除 **/
        function del(fyID){
            // 非空判断
            if(fyID == '') return;
            if(confirm("您确定要删除吗？")){
                $("#submitForm").attr("action", "/xngzf/archives/delFangyuan.action?fyID=" + fyID).submit();
            }
        }

        /** 批量删除 **/
        function batchDel(){
            if($("input[name='IDCheck']:checked").size()<=0){
                art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'至少选择一条', ok:true,});
                return;
            }
            // 1）取出用户选中的checkbox放入字符串传给后台,form提交
            var allIDCheck = "";
            $("input[name='IDCheck']:checked").each(function(index, domEle){
                bjText = $(domEle).parent("td").parent("tr").last().children("td").last().prev().text();
// 			alert(bjText);
                // 用户选择的checkbox, 过滤掉“已审核”的，记住哦
                if($.trim(bjText)=="已审核"){
// 				$(domEle).removeAttr("checked");
                    $(domEle).parent("td").parent("tr").css({color:"red"});
                    $("#resultInfo").html("已审核的是不允许您删除的，请联系管理员删除！！！");
// 				return;
                }else{
                    allIDCheck += $(domEle).val() + ",";
                }
            });
            // 截掉最后一个","
            if(allIDCheck.length>0) {
                allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
                // 赋给隐藏域
                $("#allIDCheck").val(allIDCheck);
                if(confirm("您确定要批量删除这些记录吗？")){
                    // 提交form
                    $("#submitForm").attr("action", "/xngzf/archives/batchDelFangyuan.action").submit();
                }
            }
        }

        /** 普通跳转 **/
        function jumpNormalPage(page){
            $("#submitForm").attr("action", "house_list.html?page=" + page).submit();
        }

        /** 输入页跳转 **/
        function jumpInputPage(totalPage){
            // 如果“跳转页数”不为空
            if($("#jumpNumTxt").val() != ''){
                var pageNum = parseInt($("#jumpNumTxt").val());
                // 如果跳转页数在不合理范围内，则置为1
                if(pageNum<1 | pageNum>totalPage){
                    art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                    pageNum = 1;
                }
                $("#submitForm").attr("action", "house_list.html?page=" + pageNum).submit();
            }else{
                // “跳转页数”为空
                art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
            }
        }
    </script>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="submitForm" name="submitForm" action="" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_bottom">
                        <input type="button" value="Refresh" class="ui_input_btn01" onclick="search();" />
                        <input type="button" value="新增" class="ui_input_btn01" id="addBtn" />
                        <input type="button" value="Audit" class="ui_input_btn01" onclick="batchDel();" />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
                        </th>
                        <th>INDEX</th>
                        <th>HVD</th>
                        <th>SALT</th>
                        <th>SIZE</th>
                    </tr>
                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458579642011" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼1-111</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458625716623" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼1-112</td>
                        <td>67.47㎡</td>
                        <td>67.47㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458592537463" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼2-121</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458632171244" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼2-122</td>
                        <td>67.47㎡</td>
                        <td>67.47㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458599318263" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼3-131</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458638753194" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼3-132</td>
                        <td>67.47㎡</td>
                        <td>67.47㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458606278350" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼4-141</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458644830000" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼4-142</td>
                        <td>67.47㎡</td>
                        <td>67.47㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458612828548" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼5-151</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="14458619251417" class="acb" /></td>
                        <td>城中区</td>
                        <td>瑞景河畔16号楼6-161</td>
                        <td>65.97㎡</td>
                        <td>65.97㎡</td>
                    </tr>
                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_flt" style="height: 30px; line-height: 30px;">
                    共有
                    <span class="ui_txt_bold04">90</span>
                    条记录，当前第
                    <span class="ui_txt_bold04">1
						/
						9</span>
                    页
                </div>
                <div class="ui_frt">
                    <!--    如果是第一页，则只显示下一页、尾页 -->

                    <input type="button" value="首页" class="ui_input_btn01" />
                    <input type="button" value="上一页" class="ui_input_btn01" />
                    <input type="button" value="下一页" class="ui_input_btn01"
                           onclick="jumpNormalPage(2);" />
                    <input type="button" value="尾页" class="ui_input_btn01"
                           onclick="jumpNormalPage(9);" />



                    <!--     如果是最后一页，则只显示首页、上一页 -->

                    转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页
                    <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage(9);" />
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>

