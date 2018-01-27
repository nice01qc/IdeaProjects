<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  	<%@ include file="../head.jsp" %>
  	<link href="${ctx }/Public/css/fileinput.min.css" rel="stylesheet">
  	<script src="${ctx }/Public/js/myFormValidate.js"></script>
  	<script src="${ctx }/Public/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
  </head>
    
  <body style="background-color:#ecf0f5;">
    <!--物流配置 css -start-->
    <style>ul.group-list { width: 96%;min-width: 1000px; margin: auto 5px;list-style: disc outside none; } ul.group-list li { white-space: nowrap;float: left; width: 150px; height: 25px; padding: 3px 5px;list-style-type: none; list-style-position: outside;border: 0px;margin: 0px; }</style>
    <!--物流配置 css -end-->
    <!--以下是在线编辑器 代码 -->
    <script type="text/javascript">/*
	 * 在线编辑器相 关配置 js 
	 *  参考 地址 http://fex.baidu.com/ueditor/
	 */
      window.UEDITOR_Admin_URL = "${ctx}/Public/plugins/Ueditor/";
      var URL_upload = "/index/Admin/Ueditor/imageUp/savepath/goods";
      var URL_fileUp = "/index/Admin/Ueditor/fileUp/savepath/article";
      var URL_scrawlUp = "/index/Admin/Ueditor/scrawlUp/savepath/article";
      var URL_getRemoteImage = "/index/Admin/Ueditor/getRemoteImage/savepath/article";
      var URL_imageManager = "/index/Admin/Ueditor/imageManager/savepath/article";
      var URL_imageUp = "/index/Admin/Ueditor/imageUp/savepath/article";
      var URL_getMovie = "/index/Admin/Ueditor/getMovie/savepath/article";
      var URL_home = "";</script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/Public/plugins/Ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/Public/plugins/Ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/Public/plugins/Ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
    	var editor;
      $(function() {
        //具体参数配置在  editor_config.js  中
        var options = {
          //表示在哪一层
          zIndex: 999,
          initialFrameWidth: "95%",
          //初化宽度
          initialFrameHeight: 400,
          //初化高度
          focus: false,
          //初始化时，是否让编辑器获得焦点true或false
          
          //允许的最大字符数
          maximumWords: 99999,
          removeFormatAttributes: 'class,style,lang,width,height,align,hspace,valign',
          pasteplain: false,
          //是否默认为纯文本粘贴。false为不使用纯文本粘贴，true为使用纯文本粘贴
          autoHeightEnabled: true,
          toolbars: [['fullscreen', 'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|', 'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|', 'directionalityltr', 'directionalityrtl', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'insertimage', 'emotion', 'insertvideo', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|', 'horizontal', 'date', 'time', 'spechars', 'wordimage', '|', 'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|', 'print', 'preview', 'searchreplace']]
          //展示的工具
        };
        editor = new UE.ui.Editor(options);
        editor.render("goods_content"); //  指定 textarea 的  id 为 goods_content
      });
      </script>
    <!--以上是在线编辑器 代码 end-->
    <div class="wrapper">
      <div class="breadcrumbs" id="breadcrumbs">
        <ol class="breadcrumb">
          <li>
            <a href="javascript:void();">
              <i class="fa fa-home"></i>&nbsp;&nbsp;后台首页</a>
          </li>
          <li>
            <a href="javascript:void();">商品管理</a></li>
          <li>
            <a href="javascript:void();">添加修改商品</a></li>
        </ol>
      </div>
      <section class="content">
        <!-- Main content -->
        <div class="container-fluid">
          <div class="pull-right">
            <a href="javascript:history.go(-1)" data-toggle="tooltip" title="" class="btn btn-default" data-original-title="返回">
              <i class="fa fa-reply"></i>
            </a>
            <a href="javascript:;" class="btn btn-default" data-url="http://www.ego.cn/Doc/Index/article/id/1007/developer/user.html" onclick="get_help(this)">
              <i class="fa fa-question-circle"></i>帮助</a>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h3 class="panel-title">
                <i class="fa fa-list"></i>商品详情</h3>
            </div>
            <div class="panel-body">
              <ul class="nav nav-tabs">
                <li class="active">
                  <a href="#tab_tongyong" data-toggle="tab">通用信息</a></li>
                <!-- <li><a href="#tab_goods_desc" data-toggle="tab">描述信息</a></li>-->
                <li>
                  <a href="#tab_goods_images" data-toggle="tab">商品相册</a></li>
                <li>
                  <a href="#tab_goods_spec" data-toggle="tab">商品模型</a></li>
                <li>
                  <a href="#tab_goods_shipping" data-toggle="tab">商品物流</a></li>
              </ul>
              <!--表单数据-->
              <form method="post" id="addEditGoodsForm">
                <!--通用信息-->
                <div class="tab-content">
                  <div class="tab-pane active" id="tab_tongyong">
                    <table class="table table-bordered">
                      <tbody>
                        <tr>
                          <td>商品名称:</td>
                          <td>
                            <input type="text" value="" name="goodsName" class="form-control" style="width:550px;" />
                            <span id="err_goods_name" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>商品简介:</td>
                          <td>
                            <textarea rows="3" cols="80" name="goodsRemark"></textarea>
                            <span id="err_goods_remark" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>商品货号</td>
                          <td>
                            <input type="text" value="" name="goodsSn" class="form-control" style="width:350px;" />
                            <span id="err_goods_sn" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>SPU</td>
                          <td>
                            <input type="text" value="" name="spu" class="form-control" style="width:350px;" />
                            <span id="err_goods_spu" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>SKU</td>
                          <td>
                            <input type="text" value="" name="sku" class="form-control" style="width:350px;" />
                            <span id="err_goods_sku" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>商品分类:</td>
                          <td>
                          	<input type="hidden" name="catId" id="catId" value="" />
                          	<br/>
                            <div class="col-xs-3">
                              <select name="cat_id" id="cat_id" onchange="get_category(this.value,'cat_id_2','0','catId');" class="form-control" style="width:250px;margin-left:-15px;">
                                <option value="0">请选择商品分类</option>
                                <c:forEach var="gc" items="${gcList }">
                                	<option value="${gc.id }">${gc.name }</option>
                                </c:forEach>
                               </select>
                            </div>
                            <div class="col-xs-3">
                              <select name="cat_id_2" id="cat_id_2" onchange="get_category(this.value,'cat_id_3','0','catId');" class="form-control" style="width:250px;margin-left:-15px;">
                                <option value="0">请选择商品分类</option></select>
                            </div>
                            <div class="col-xs-3">
                              <select name="cat_id_3" id="cat_id_3" class="form-control" onchange="set_category_hidden(this.value,'catId')" style="width:250px;margin-left:-15px;">
                                <option value="0">请选择商品分类</option></select>
                            </div>
                            <span id="err_cat_id" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>扩展分类:</td>
                          <td>
					        <input type="hidden" name="extendCatId" id="extendCatId" value="" />
                            <div class="col-xs-3">
                              <select name="extend_cat_id" id="extend_cat_id" onchange="get_category(this.value,'extend_cat_id_2','0','extendCatId');" class="form-control" style="width:250px;margin-left:-15px;">
                              	<option value="0">请选择商品分类</option>
                                <c:forEach var="gc" items="${gcList }">
                                	<option value="${gc.id }">${gc.name }</option>
                                </c:forEach>
                              </select>
                            </div>
                            <div class="col-xs-3">
                              <select name="extend_cat_id_2" id="extend_cat_id_2" onchange="get_category(this.value,'extend_cat_id_3','0','extendCatId');" class="form-control" style="width:250px;margin-left:-15px;">
                                <option value="0">请选择商品分类</option></select>
                            </div>
                            <div class="col-xs-3">
                              <select name="extend_cat_id_3" id="extend_cat_id_3" onchange="set_category_hidden(this.value,'extendCatId')" class="form-control" style="width:250px;margin-left:-15px;">
                                <option value="0">请选择商品分类</option></select>
                            </div>
                            <span id="err_cat_id" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>商品品牌:</td>
                          <td>
                            <select name="brand_id" id="brandId" class="form-control" style="width:250px;">
                              <option value="">所有品牌</option>
                              <option value="224">-- 361°</option>
                              <option value="80">-- 倩碧/CLINIQUE</option>
                              <option value="194">-- 宏碁/acer</option>
                              <option value="27">-- 怡达/yida</option>
                              <option value="310">-- 斐利比/Philippi</option>
                              <option value="118">-- 榄菊</option>
                              <option value="258">-- 缪缪/MIU MIU</option>
                              <option value="280">-- 蔻驰/COACH</option>
                              <option value="214">A -- A21</option>
                              <option value="266">A -- Amii</option>
                              <option value="308">A -- 奥迪双钻/AULDEY</option>
                              <option value="64">A -- 安佳/Anchor</option>
                              <option value="135">A -- 安佳/Anchor</option>
                              <option value="207">A -- 安踏/ANTA ( 男装、女装、童装、内衣 )</option>
                              <option value="231">A -- 安踏/ANTA ( 运动户外 )</option>
                              <option value="237">A -- 昂立/Onlly</option>
                              <option value="345">A -- 澳佳宝/BLACKMORES</option>
                              <option value="155">A -- 澳西奴</option>
                              <option value="309">A -- 澳贝/auby</option>
                              <option value="263">A -- 爱华仕/Oiwas</option>
                              <option value="328">A -- 爱安德/AND</option>
                              <option value="327">A -- 爱科来/arkray</option>
                              <option value="73">A -- 艾美/Emmi</option>
                              <option value="41">A -- 阿华田/Ovaltine</option>
                              <option value="21">A -- 阿尔卑斯/Alpenliebe</option>
                              <option value="268">A -- 阿札/A-ZA</option>
                              <option value="264">A -- 阿玛尼/EMPORIO ARMANI ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="286">A -- 阿玛尼/EMPORIO ARMANI ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="201">A -- 阿迪达斯/adidas ( 男装、女装、童装、内衣 )</option>
                              <option value="221">A -- 阿迪达斯/adidas ( 运动户外 )</option>
                              <option value="289">B -- BUREI</option>
                              <option value="167">B -- 倍轻松/bero</option>
                              <option value="315">B -- 兵兵</option>
                              <option value="171">B -- 北欧欧慕/nathome</option>
                              <option value="275">B -- 变形金刚/Transformers</option>
                              <option value="208">B -- 宝娜斯</option>
                              <option value="336">B -- 宝氏/Post</option>
                              <option value="257">B -- 宝缇嘉/Bottega Veneta</option>
                              <option value="301">B -- 宝视达</option>
                              <option value="274">B -- 巴宝莉/Burberry</option>
                              <option value="233">B -- 彪马/Puma</option>
                              <option value="83">B -- 泊美/PUREMILD</option>
                              <option value="25">B -- 波力/PO-LI</option>
                              <option value="202">B -- 波司登/Bosideng</option>
                              <option value="267">B -- 波斯丹顿/Bostanten</option>
                              <option value="33">B -- 百味林</option>
                              <option value="79">B -- 碧欧泉/BIOTHERM</option>
                              <option value="243">B -- 碧生源/Besunyen</option>
                              <option value="296">B -- 秉信</option>
                              <option value="96">B -- 贝因美/BEINGMATE</option>
                              <option value="70">B -- 贝尔/BEIER</option>
                              <option value="265">B -- 贝尔/BEIER ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="177">B -- 贝尔斯顿/BESTDAY</option>
                              <option value="311">B -- 邦迪/BAND-AID</option>
                              <option value="246">C -- 初元</option>
                              <option value="210">C -- 初语/TOYOUTH</option>
                              <option value="218">C -- 川崎/kawasaki</option>
                              <option value="22">C -- 春光/chun guang</option>
                              <option value="173">C -- 春笑</option>
                              <option value="42">C -- 晨光/MG</option>
                              <option value="298">C -- 灿坤/EUPA</option>
                              <option value="121">C -- 超能</option>
                              <option value="270">D -- DOODOO</option>
                              <option value="12">D -- 东信/EASTCOM</option>
                              <option value="85">D -- 丹姿/DANZ</option>
                              <option value="276">D -- 哆啦A梦/Doraemon</option>
                              <option value="31">D -- 多力多滋/Doritos</option>
                              <option value="92">D -- 多美滋/Dumex</option>
                              <option value="68">D -- 多美鲜/Suki</option>
                              <option value="142">D -- 多美鲜/Suki</option>
                              <option value="76">D -- 大宝/Dabao</option>
                              <option value="295">D -- 大富翁/Uncle Wang</option>
                              <option value="338">D -- 大王/GOO.N</option>
                              <option value="176">D -- 德国博朗/BRAUN</option>
                              <option value="52">D -- 德运/Devondale</option>
                              <option value="66">D -- 德运/Devondale</option>
                              <option value="137">D -- 德运/Devondale</option>
                              <option value="337">D -- 德运/Devondale</option>
                              <option value="102">D -- 德运/Devondale ( 母婴用品、儿童玩具 )</option>
                              <option value="183">D -- 戴尔/DELL</option>
                              <option value="288">D -- 杜嘉班纳/DG</option>
                              <option value="112">D -- 滴露/Dettol</option>
                              <option value="222">D -- 迪士尼/Disney ( 运动户外 )</option>
                              <option value="282">D -- 迪士尼/Disney ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="115">D -- 雕牌</option>
                              <option value="44">D -- 顶好/Soyfresh</option>
                              <option value="56">D -- 顶好/Soyfresh</option>
                              <option value="254">E -- ELLE ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="272">E -- ELLE ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="18">E -- E人E本</option>
                              <option value="297">F -- 福库/Cuckoo</option>
                              <option value="81">F -- 芳珂/Fancl</option>
                              <option value="54">F -- 风行</option>
                              <option value="175">F -- 飞利浦/PHILIPS ( 家用电器 )</option>
                              <option value="7">F -- 飞利浦/PHILIPS ( 手机 、 数码 、 通信 )</option>
                              <option value="278">G -- GUESS</option>
                              <option value="129">G -- 光明</option>
                              <option value="165">G -- 光明 ( 家用电器 )</option>
                              <option value="256">G -- 古驰/Gucci</option>
                              <option value="232">G -- 哥伦比亚/Columbia</option>
                              <option value="252">G -- 广元堂</option>
                              <option value="162">G -- 格致诚品</option>
                              <option value="38">G -- 桂格/QUAKER</option>
                              <option value="61">G -- 葛兰纳诺/GRANAROLO</option>
                              <option value="334">G -- 钙尔奇/Caltrate</option>
                              <option value="101">G -- 高培/GlodMax</option>
                              <option value="10">H -- HTC</option>
                              <option value="343">H -- 亨氏/Heinz</option>
                              <option value="1">H -- 华为/HUAWEI</option>
                              <option value="146">H -- 华乐/huale</option>
                              <option value="37">H -- 华味亨</option>
                              <option value="186">H -- 华硕/ASUS</option>
                              <option value="340">H -- 和光堂/WaKODO</option>
                              <option value="225">H -- 回力/Warrior</option>
                              <option value="147">H -- 好伴侣/hao ban lv</option>
                              <option value="248">H -- 好医生</option>
                              <option value="238">H -- 恒寿堂/HENG SHOU TANG</option>
                              <option value="182">H -- 惠普/hp</option>
                              <option value="93">H -- 惠氏/Wyeth</option>
                              <option value="19">H -- 海信/Hisense</option>
                              <option value="143">H -- 海天下</option>
                              <option value="14">H -- 海尔/Haier</option>
                              <option value="314">H -- 海氏海诺/HAINUO</option>
                              <option value="281">H -- 海鸥表/Sea-Gull</option>
                              <option value="24">H -- 皇冠/Danisa</option>
                              <option value="247">H -- 红桃K</option>
                              <option value="331">H -- 花王/Merries</option>
                              <option value="200">H -- 花花公子/PLAYBOY</option>
                              <option value="131">H -- 鸿福堂/HUNG FOOK TONG</option>
                              <option value="307">J -- 九阳/Joyoung</option>
                              <option value="89">J -- 京润珍珠</option>
                              <option value="91">J -- 娇兰/Guerlain</option>
                              <option value="110">J -- 娇妍/JOLLY</option>
                              <option value="126">J -- 洁霸/Attack</option>
                              <option value="109">J -- 绝对宝贝/JUST BABY</option>
                              <option value="84">J -- 近江蔓莎</option>
                              <option value="189">J -- 金士顿/Kingston</option>
                              <option value="321">J -- 金奥力</option>
                              <option value="269">K -- 克路驰/CLUCI</option>
                              <option value="174">K -- 凯仕乐/KASRROW</option>
                              <option value="229">K -- 凯速</option>
                              <option value="251">K -- 凯镛</option>
                              <option value="226">K -- 匡威/Converse</option>
                              <option value="39">K -- 卡夫/KRAFT</option>
                              <option value="330">K -- 卡夫/KRAFT</option>
                              <option value="74">K -- 卡姿兰/CARSLAN</option>
                              <option value="108">K -- 卡瑞特兹/Karivita</option>
                              <option value="341">K -- 可瑞康/Karicare</option>
                              <option value="104">K -- 可瑞康/Karicare ( 母婴用品、儿童玩具 )</option>
                              <option value="138">K -- 坎诺拉/Canola</option>
                              <option value="179">K -- 康夫/kangfu</option>
                              <option value="150">K -- 康尔馨/Canasin</option>
                              <option value="32">K -- 康师傅/Master Kong</option>
                              <option value="319">K -- 康扉/KANGFEI</option>
                              <option value="67">K -- 康维多/Primavita</option>
                              <option value="103">K -- 康维多/Primavita ( 母婴用品、儿童玩具 )</option>
                              <option value="116">K -- 开米/Kami</option>
                              <option value="300">K -- 开馨宝/KAI XIN BAO</option>
                              <option value="191">K -- 开馨宝/KAI XIN BAO ( 电脑、办公 )</option>
                              <option value="141">K -- 科尔沁/KERCHIN</option>
                              <option value="180">K -- 酷力</option>
                              <option value="333">L -- LG</option>
                              <option value="30">L -- 乐事/Lay s</option>
                              <option value="294">L -- 乐比比/LEBIBI</option>
                              <option value="140">L -- 乐芝牛/The Laughing Cow</option>
                              <option value="163">L -- 六朝家居/luc life</option>
                              <option value="145">L -- 兰皇</option>
                              <option value="82">L -- 兰芝/LANEIGE</option>
                              <option value="78">L -- 兰蔻/LANCOME</option>
                              <option value="71">L -- 兰诺斯/Lemnos</option>
                              <option value="299">L -- 利仁/LIVEN</option>
                              <option value="271">L -- 拉菲斯汀/Lafestin</option>
                              <option value="46">L -- 李子园</option>
                              <option value="285">L -- 李维斯/Levi s</option>
                              <option value="157">L -- 来赉</option>
                              <option value="98">L -- 林贝儿/IMPERIAL.XO</option>
                              <option value="273">L -- 浪琴/Longines</option>
                              <option value="36">L -- 立丰/lifefun</option>
                              <option value="119">L -- 立白/Liby</option>
                              <option value="124">L -- 绿伞/EVER GREEN</option>
                              <option value="34">L -- 绿帝/green king</option>
                              <option value="75">L -- 老中医</option>
                              <option value="193">L -- 联想/Lenovo</option>
                              <option value="154">L -- 莱薇/Lavie</option>
                              <option value="123">L -- 蓝月亮</option>
                              <option value="158">L -- 路途乐</option>
                              <option value="90">L -- 隆力奇/LONGLIQI</option>
                              <option value="318">L -- 零听</option>
                              <option value="153">L -- 零听 ( 家居、家具、家装、厨具 )</option>
                              <option value="181">L -- 雷瓦/RIWA</option>
                              <option value="227">L -- 骆驼/CAMEL</option>
                              <option value="234">L -- 骆驼牌</option>
                              <option value="159">L -- 龙之涵</option>
                              <option value="325">L -- 龙贝儿/Loboor</option>
                              <option value="113">M -- 妙管家/MAGIC AMAH</option>
                              <option value="2">M -- 摩托罗拉/MOTOROLA</option>
                              <option value="106">M -- 明一/wissun</option>
                              <option value="199">M -- 明基/BenQ</option>
                              <option value="287">M -- 梅花/Titoni</option>
                              <option value="213">M -- 梦娜/MengNa</option>
                              <option value="152">M -- 梦特娇/MONTAGUT ( 家居、家具、家装、厨具 )</option>
                              <option value="259">M -- 梦特娇/MONTAGUT ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="28">M -- 母亲</option>
                              <option value="178">M -- 美克斯/MKS</option>
                              <option value="344">M -- 谜尚/MISSHA</option>
                              <option value="326">M -- 迈克大夫/microlife</option>
                              <option value="230">N -- NIKKO</option>
                              <option value="139">N -- NPG</option>
                              <option value="303">N -- 内野/UCHINO</option>
                              <option value="62">N -- 南阳</option>
                              <option value="107">N -- 牛奶客/Neolac</option>
                              <option value="13">N -- 纽曼/Newsmy</option>
                              <option value="250">N -- 纽曼思/Nemans</option>
                              <option value="63">N -- 纽瑞滋/Nouriz</option>
                              <option value="100">N -- 纽瑞滋/Nouriz ( 母婴用品、儿童玩具 )</option>
                              <option value="220">N -- 耐克/NIKE</option>
                              <option value="149">N -- 诺伊曼/noyoke</option>
                              <option value="346">N -- 诺优能/Nutrilon</option>
                              <option value="5">N -- 诺基亚/NOKIA ( 手机 、 数码 、 通信 )</option>
                              <option value="198">N -- 诺基亚/NOKIA ( 电脑、办公 )</option>
                              <option value="209">O -- ONEBUYE</option>
                              <option value="8">O -- OPPO</option>
                              <option value="215">O -- OopsCiah</option>
                              <option value="322">O -- 欧姆龙/Omron</option>
                              <option value="51">O -- 欧德堡/OLDENBURGER</option>
                              <option value="65">O -- 欧德堡/OLDENBURGER</option>
                              <option value="136">O -- 欧德堡/OLDENBURGER</option>
                              <option value="88">O -- 欧莱雅/L OREAL</option>
                              <option value="29">P -- 品客/Pringles</option>
                              <option value="49">P -- 帕斯卡/PASCUAL</option>
                              <option value="60">P -- 帕斯卡/PASCUAL</option>
                              <option value="151">P -- 普拉达/PULADA</option>
                              <option value="23">P -- 潘高寿</option>
                              <option value="236">P -- 潘高寿</option>
                              <option value="9">P -- 苹果/Apple ( 手机 、 数码 、 通信 )</option>
                              <option value="184">P -- 苹果/Apple ( 电脑、办公 )</option>
                              <option value="260">P -- 苹果/Apple ( 鞋、箱包、珠宝、手表 )</option>
                              <option value="317">Q -- 强生/Johnson</option>
                              <option value="188">Q -- 清华同方</option>
                              <option value="206">Q -- 秋水伊人</option>
                              <option value="55">Q -- 雀巢/Nestle</option>
                              <option value="339">Q -- 雀巢/Nestle</option>
                              <option value="160">R -- 日光生活</option>
                              <option value="166">R -- 日立/HITACHI</option>
                              <option value="255">R -- 瑞士军刀/SWISSGEAR</option>
                              <option value="335">R -- 瑞士莲/Lindt</option>
                              <option value="72">R -- 瑞慕/swiss mooh</option>
                              <option value="216">R -- 若美/nomi</option>
                              <option value="219">R -- 锐步/Reebok</option>
                              <option value="169">S -- SKG</option>
                              <option value="99">S -- 三元</option>
                              <option value="240">S -- 三叶</option>
                              <option value="57">S -- 三得利/SUNTORY</option>
                              <option value="15">S -- 三星/SAMSUNG</option>
                              <option value="196">S -- 三星/SAMSUNG ( 电脑、办公 )</option>
                              <option value="249">S -- 三金</option>
                              <option value="35">S -- 上好佳/Oishi</option>
                              <option value="130">S -- 双汇/shineway</option>
                              <option value="172">S -- 双鸟/twinbird</option>
                              <option value="332">S -- 善存/Centrum</option>
                              <option value="134">S -- 思念/SYNEAR</option>
                              <option value="122">S -- 扇牌</option>
                              <option value="277">S -- 施华洛世奇/Swarovski</option>
                              <option value="217">S -- 森马/Semir</option>
                              <option value="164">S -- 沙宣/VS</option>
                              <option value="69">S -- 深蓝健康/DEEP BLUE HEALTH</option>
                              <option value="120">S -- 狮王/LION</option>
                              <option value="48">S -- 生机谷/LIVING PLANET</option>
                              <option value="59">S -- 生机谷/LIVING PLANET</option>
                              <option value="4">S -- 索尼/SONY ( 手机 、 数码 、 通信 )</option>
                              <option value="197">S -- 索尼/SONY ( 电脑、办公 )</option>
                              <option value="262">S -- 赛琳/CELINE</option>
                              <option value="11">S -- 首信/capital</option>
                              <option value="6">T -- TCL</option>
                              <option value="187">T -- ThinkPad</option>
                              <option value="203">T -- 唐狮/Tonlion</option>
                              <option value="161">T -- 图强/TU QIANF TOWEL</option>
                              <option value="283">T -- 天梭/Tissot</option>
                              <option value="284">T -- 天王表/TIAN WANG</option>
                              <option value="53">T -- 天香</option>
                              <option value="228">T -- 探路者/Toread</option>
                              <option value="242">T -- 泰尔</option>
                              <option value="223">T -- 特步/Xtep</option>
                              <option value="105">T -- 特福芬/Topfer</option>
                              <option value="47">T -- 田园/COUNTRY GOODNESS</option>
                              <option value="58">T -- 田园/COUNTRY GOODNESS</option>
                              <option value="292">T -- 贴贴</option>
                              <option value="253">U -- UTU</option>
                              <option value="16">W -- 万利达/malata</option>
                              <option value="279">W -- 万宝龙/Montblanc</option>
                              <option value="156">W -- 吾家元素</option>
                              <option value="132">W -- 味千拉面/AJISEN RAMEN</option>
                              <option value="114">W -- 威洁士/Walex</option>
                              <option value="111">W -- 威露士/Walch</option>
                              <option value="190">W -- 微星/MSI</option>
                              <option value="185">W -- 微软/Microsoft</option>
                              <option value="144">W -- 湾仔码头</option>
                              <option value="40">W -- 维维</option>
                              <option value="320">X -- 仙鹤牌</option>
                              <option value="313">X -- 信乐/SINO</option>
                              <option value="20">X -- 喜之郎/STRONG</option>
                              <option value="50">X -- 喜乐</option>
                              <option value="342">X -- 夏依/summer eve</option>
                              <option value="127">X -- 小林</option>
                              <option value="306">X -- 小熊/Bear</option>
                              <option value="349">X -- 小米</option>
                              <option value="3">X -- 小辣椒</option>
                              <option value="128">X -- 新雅/sunya</option>
                              <option value="329">X -- 星巴克/STARBUCKS</option>
                              <option value="17">X -- 现代/HYUNDAI</option>
                              <option value="77">X -- 相宜本草/INOHERB</option>
                              <option value="195">X -- 西部数据/WD</option>
                              <option value="316">X -- 西门子/SIEMENS</option>
                              <option value="261">X -- 香奈儿/CHANEL</option>
                              <option value="302">X -- 香百年/Carori</option>
                              <option value="291">Y -- 一生一信/Infeel.Me</option>
                              <option value="245">Y -- 云南白药/YunnanBaiyao</option>
                              <option value="312">Y -- 云南白药/YunnanBaiyao</option>
                              <option value="43">Y -- 伊利</option>
                              <option value="94">Y -- 伊利 ( 母婴用品、儿童玩具 )</option>
                              <option value="293">Y -- 伊莱克斯/Electrolux</option>
                              <option value="168">Y -- 优冠</option>
                              <option value="192">Y -- 优派/ViewSonic</option>
                              <option value="239">Y -- 养生堂</option>
                              <option value="148">Y -- 养鹅人</option>
                              <option value="290">Y -- 友邦</option>
                              <option value="212">Y -- 幽迷/Youmi</option>
                              <option value="241">Y -- 御生堂</option>
                              <option value="304">Y -- 悠嘻猴</option>
                              <option value="170">Y -- 易简/Yijan</option>
                              <option value="244">Y -- 益力健</option>
                              <option value="97">Y -- 益力健 ( 母婴用品、儿童玩具 )</option>
                              <option value="125">Y -- 裕华</option>
                              <option value="86">Y -- 郁美净</option>
                              <option value="45">Y -- 银鹭</option>
                              <option value="211">Y -- 雅可希/YAKEXI</option>
                              <option value="324">Y -- 雅培/Abbott</option>
                              <option value="95">Y -- 雅士利/YASHILY</option>
                              <option value="204">Y -- 雅鹿</option>
                              <option value="133">Y -- 雨润/Yurun</option>
                              <option value="323">Y -- 鱼跃/yuyue</option>
                              <option value="305">Z -- 卓朗/ZoomLand</option>
                              <option value="26">Z -- 张二嘎</option>
                              <option value="87">Z -- 昭贵</option>
                              <option value="117">Z -- 正章</option>
                              <option value="205">Z -- 真维斯/Jeanswest</option>
                              <option value="235">Z -- 自由兵/FREE SOLDIER</option></select>
                          </td>
                        </tr>
                        <tr>
                          <td>供应商:</td>
                          <td>
                            <select name="suppliers_id" id="suppliersId" class="form-control" style="width:250px;">
                              <option value="0">不指定供应商属于本店商品</option></select>
                          </td>
                        </tr>
                        <tr>
                          <td>本店售价:</td>
                          <td>
                            <input type="text" value="" name="shopPrice" class="form-control" style="width:150px;" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_shop_price" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>市场价:</td>
                          <td>
                            <input type="text" value="" name="marketPrice" class="form-control" style="width:150px;" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_market_price" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>成本价:</td>
                          <td>
                            <input type="text" value="" name="costPrice" class="form-control" style="width:150px;" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_cost_price" style="color:#F00; display:none"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>佣金:</td>
                          <td>
                            <input type="text" value="" name="commission" class="form-control" style="width:150px;" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />用于分销的分成金额</td></tr>
                        <tr>
                          <td>上传商品图片:</td>
                          <td>
                          	<input type="hidden" id="originalImg" name="originalImg" value=""/>
                            <form enctype="multipart/form-data">
			                    <input id="file-product" class="file" name="file" type="file" data-min-file-count="1">
			                    <br>
			                </form>
                          </td>
                        </tr>
                        <tr>
                          <td>商品重量:</td>
                          <td>
                            <input type="text" class="form-control" style="width:150px;" value="" name="weight" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />&nbsp;克 (以克为单位)
                            <span id="err_weight" style="color:#F00; display:none;"></span></td>
                        </tr>
                        <tr>
                          <td>是否包邮:</td>
                          <td>是:
                            <input type="radio" value="1" name="isFreeShipping" />否:
                            <input type="radio" checked="checked" value="0" name="isFreeShipping" /></td></tr>
                        <tr>
                          <td>库存数量:</td>
                          <td>
                            <input type="text" value="1" class="form-control" style="width:150px;" name="storeCount" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_store_count" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>赠送积分:</td>
                          <td>
                            <input type="text" class="form-control" style="width:150px;" value="" name="giveIntegral" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_give_integral" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <tr>
                          <td>兑换积分:</td>
                          <td>
                            <input type="text" class="form-control" style="width:150px;" value="" name="exchangeIntegral" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
                            <span id="err_exchange_integral" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                        <!-- <tr>
                        <td>设置:</td>
                        <td>
                        <input type="checkbox" checked="checked" value="" name="is_on_sale"/> 上架&nbsp;&nbsp;
                        <input type="checkbox" checked="checked" value="" name="is_free_shipping"/> 包邮&nbsp;&nbsp;
                        <input type="checkbox" checked="checked" value="" name="is_recommend"/>推荐&nbsp;&nbsp;
                        <input type="checkbox" checked="checked" value="" name="is_new"/>新品&nbsp;&nbsp;
                        </td>
                        </tr>
                        -->
                        <tr>
                          <td>商品关键词:</td>
                          <td>
                            <input type="text" class="form-control" style="width:550px;" value="" name="keywords" />用空格分隔
                            <span id="err_keywords" style="color:#F00; display:none;"></span></td>
                        </tr>
                        <tr>
                          <td>商品详情描述:</td>
                          <td width="85%">
                            <textarea class="span12 ckeditor" id="goods_content" name="goodsContent" title=""></textarea>
                            <span id="err_goods_content" style="color:#F00; display:none;"></span>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <!--其他信息-->
                  <!-- 商品相册-->
                  <div class="tab-pane" id="tab_goods_images">
                    <table class="table table-bordered">
                      <tbody>
                        <tr>
                          <td>
                            <form enctype="multipart/form-data">
			                    <input id="file-goods-images" class="file" name="goodsImage" type="file" multiple data-min-file-count="1">
			                    <br>
			                </form>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <!--商品相册-->
                  <!-- 商品规格-->
                  <div class="tab-pane" id="tab_goods_spec">
                    <table class="table table-bordered" id="goods_spec_table">
                      <tr>
                        <td>商品模型:</td>
                        <td>
                          <select name="goods_type" id="spec_type" class="form-control" style="width:250px;">
                            <option value="0">选择商品模型</option>
                            <option value="33">运营商</option>
                            <option value="32">相机</option>
                            <option value="4">手机</option>
                            <option value="31">电池、电源、充电器</option>
                            <option value="8">化妆品</option>
                            <option value="9">精品手机</option>
                            <option value="30">洗衣机</option>
                            <option value="29">冰箱</option>
                            <option value="16">路由器</option>
                            <option value="15">平板电脑</option>
                            <option value="13">衣服</option>
                            <option value="17">网络盒子</option>
                            <option value="18">电视</option>
                            <option value="19">家纺</option>
                            <option value="20">吸顶灯</option>
                            <option value="21">床</option>
                            <option value="22">雨伞</option>
                            <option value="23">餐具</option>
                            <option value="24">毛呢大衣</option>
                            <option value="25">针织衫</option>
                            <option value="26">文胸</option>
                            <option value="27">香水</option>
                            <option value="28">珠宝</option></select>
                        </td>
                      </tr>
                    </table>
                    <div class="row">
                      <!-- ajax 返回规格-->
                      <div id="ajax_spec_data" class="col-xs-8" style="border:1px solid #ddd;"></div>
                      <div id="" class="col-xs-4" style="border:1px solid #ddd;">
                        <table class="table table-bordered" id="goods_attr_table">
                          <tr>
                            <td>
                              <b>商品属性</b>：</td>
                          </tr>
                        </table>
                      </div>
                    </div>
                  </div>
                  <!-- 商品规格-->
                  <!-- 商品属性-->
                  <!-- 商品属性-->
                  <!-- 商品物流-->
                  <div class="tab-pane" id="tab_goods_shipping">
                    <h4>
                      <b>物流配送：</b>
                      <input type="checkbox" onclick="choosebox(this)">全选</h4>
                    <table class="table table-bordered table-striped dataTable" id="goods_shipping_table">
                      <tr>
                        <td class="title left" style="padding-right:50px;">
                          <b>申通物流：</b>
                          <label class="right">
                            <input type="checkbox" value="1" cka="mod-0">全选</label></td>
                      </tr>
                      <tr>
                        <td>
                          <ul class="group-list">
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="24" ck="mod-0">全国其他地区</label></li>
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="37" ck="mod-0">珠三角地区</label></li>
                            <div class="clear-both"></div>
                          </ul>
                        </td>
                      </tr>
                      <tr>
                        <td class="title left" style="padding-right:50px;">
                          <b>顺丰物流：</b>
                          <label class="right">
                            <input type="checkbox" value="1" cka="mod-1">全选</label></td>
                      </tr>
                      <tr>
                        <td>
                          <ul class="group-list">
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="25" ck="mod-1">全国其他地区</label></li>
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="35" ck="mod-1">珠三角地区</label></li>
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="36" ck="mod-1">长三角</label></li>
                            <li>
                              <label>
                                <input type="checkbox" name="shipping_area_ids[]" value="38" ck="mod-1">全国一线城市</label></li>
                            <div class="clear-both"></div>
                          </ul>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <!-- 商品物流--></div>
                <div class="pull-right">
                  <input type="hidden" name="goodsId" id="goodsId" value="">
                  <button class="btn btn-primary" onclick="ajax_submit_form('addEditGoodsForm','${ctx}/product/save','','goodsId');" title="" data-toggle="tooltip" type="button" data-original-title="保存">保存</button></div>
                <input type="hidden" name="__hash__" value="4215372d5f63004790e1b9f4ed8df8b8_e74f2c0a9d3eff6e5b0e2ac8545e8177" /></form>
              <!--表单数据--></div>
          </div>
        </div>
        <!-- /.content --></section>
    </div>
<script src="${ctx }/Public/js/fileinput.js" type="text/javascript"></script>
<script src="${ctx }/Public/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script>
	//初始设置 language指定语言 uploadUrl指定文件上传的后台地址 allowedPreviewTypes允许上传文件的类型
    $('#file-product').fileinput({
        language: 'zh',
        uploadUrl: '${ctx}/fileUpload/save',
        allowedPreviewTypes : ['image', 'html', 'text', 'video', 'audio', 'flash'],
    });
	//上传文件失败后 调用的方法（回调函数）
    $('#file-product').on('fileuploaderror', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        console.log(data);
        console.log('File upload error');
    });
	//文件错误 比如文件类型错误 会调用此函数（回调函数）
    $('#file-product').on('fileerror', function(event, data) {
        console.log(data.id);
        console.log(data.index);
        console.log(data.file);
        console.log(data.reader);
        console.log(data.files);
    });
	//文件上传成功之后 回调的函数
    $('#file-product').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        $("#originalImg").val("http://"+data.response.fileUrl);
        console.log('File uploaded triggered');
    });
</script>
<script>
	//初始设置 language指定语言 uploadUrl指定文件上传的后台地址 allowedPreviewTypes允许上传文件的类型
    $('#file-goods-images').fileinput({
        language: 'zh',
        uploadUrl: '${ctx}/product/goodsImages/save/',
        allowedPreviewTypes : ['image', 'html', 'text', 'video', 'audio', 'flash'],
        uploadExtraData: function() {//添加除file对象以外，额外的参数
            var obj = {"goodsId":$("#goodsId").val()};
            return obj;
        }
    });
	//上传文件失败后 调用的方法（回调函数）
    $('#file-goods-images').on('fileuploaderror', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        console.log(data);
        console.log('File upload error');
    });
	//文件错误 比如文件类型错误 会调用此函数（回调函数）
    $('#file-goods-images').on('fileerror', function(event, data) {
        console.log(data.id);
        console.log(data.index);
        console.log(data.file);
        console.log(data.reader);
        console.log(data.files);
    });
	//文件上传成功之后 回调的函数
    $('#file-goods-images').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        $("#originalImg").val("http://"+data.response.fileUrl);
        console.log('File uploaded triggered');
    });
</script>
    <script type="text/javascript">function delfunc(obj) {
        layer.confirm('确认删除？', {
          btn: ['确定', '取消'] //按钮
        },
        function() {
          $.ajax({
            type: 'post',
            url: $(obj).attr('data-url'),
            data: {
              act: 'del',
              del_id: $(obj).attr('data-id')
            },
            dataType: 'json',
            success: function(data) {
              if (data == 1) {
                layer.msg('操作成功', {
                  icon: 1
                });
                $(obj).parent().parent().remove();
              } else {
                layer.msg(data, {
                  icon: 2,
                  time: 2000
                });
              }
              layer.closeAll();
            }
          })
        },
        function(index) {
          layer.close(index);
          return false; // 取消
        });
      }

      //全选
      function selectAll(name, obj) {
        $('input[name*=' + name + ']').prop('checked', $(obj).checked);
      }

      function get_help(obj) {
        layer.open({
          type: 2,
          title: '帮助手册',
          shadeClose: true,
          shade: 0.3,
          area: ['90%', '90%'],
          content: $(obj).attr('data-url'),
        });
      }

      function delAll(obj, name) {
        var a = [];
        $('input[name*=' + name + ']').each(function(i, o) {
          if ($(o).is(':checked')) {
            a.push($(o).val());
          }
        }) if (a.length == 0) {
          layer.alert('请选择删除项', {
            icon: 2
          });
          return;
        }
        layer.confirm('确认删除？', {
          btn: ['确定', '取消']
        },
        function() {
          $.ajax({
            type: 'get',
            url: $(obj).attr('data-url'),
            data: {
              act: 'del',
              del_id: a
            },
            dataType: 'json',
            success: function(data) {
              if (data == 1) {
                layer.msg('操作成功', {
                  icon: 1
                });
                $('input[name*=' + name + ']').each(function(i, o) {
                  if ($(o).is(':checked')) {
                    $(o).parent().parent().remove();
                  }
                })
              } else {
                layer.msg(data, {
                  icon: 2,
                  time: 2000
                });
              }
              layer.closeAll();
            }
          })
        },
        function(index) {
          layer.close(index);
          return false; // 取消
        });
      }</script>
    <script>$(document).ready(function() {
        $(":checkbox[cka]").click(function() {
          var $cks = $(":checkbox[ck='" + $(this).attr("cka") + "']");
          if ($(this).is(':checked')) {
            $cks.each(function() {
              $(this).prop("checked", true);
            });
          } else {
            $cks.each(function() {
              $(this).removeAttr('checked');
            });
          }
        });
      });

      function choosebox(o) {
        var vt = $(o).is(':checked');
        if (vt) {
          $('input[type=checkbox]').prop('checked', vt);
        } else {
          $('input[type=checkbox]').removeAttr('checked');
        }
      }
      /*
     * 以下是图片上传方法
     */
      // 上传商品图片成功回调函数
      function call_back(fileurl_tmp) {
        $("#original_img").val(fileurl_tmp);
        $("#original_img2").attr('href', fileurl_tmp);
      }

      // 上传商品相册回调函数
      function call_back2(paths) {

        var last_div = $(".goods_xc:last").prop("outerHTML");
        for (i = 0; i < paths.length; i++) {
          $(".goods_xc:eq(0)").before(last_div); // 插入一个 新图片
          $(".goods_xc:eq(0)").find('a:eq(0)').attr('href', paths[i]).attr('onclick', '').attr('target', "_blank"); // 修改他的链接地址
          $(".goods_xc:eq(0)").find('img').attr('src', paths[i]); // 修改他的图片路径
          $(".goods_xc:eq(0)").find('a:eq(1)').attr('onclick', "ClearPicArr2(this,'" + paths[i] + "')").text('删除');
          $(".goods_xc:eq(0)").find('input').val(paths[i]); // 设置隐藏域 要提交的值
        }
      }
      /*
     * 上传之后删除组图input     
     * @access   public
     * @val      string  删除的图片input
     */
      function ClearPicArr2(obj, path) {
        $.ajax({
          type: 'GET',
          url: "/index/Admin/Uploadify/delupload",
          data: {
            action: "del",
            filename: path
          },
          success: function() {
            $(obj).parent().remove(); // 删除完服务器的, 再删除 html上的图片				 
          }
        });
        // 删除数据库记录
        $.ajax({
          type: 'GET',
          url: "/index/Admin/Goods/del_goods_images",
          data: {
            filename: path
          },
          success: function() {
            //		 
          }
        });
      }

      /** 以下 商品属性相关 js*/

      // 属性输入框的加减事件
      function addAttr(a) {
        var attr = $(a).parent().parent().prop("outerHTML");
        attr = attr.replace('addAttr', 'delAttr').replace('+', '-');
        $(a).parent().parent().after(attr);
      }
      // 属性输入框的加减事件
      function delAttr(a) {
        $(a).parent().parent().remove();
      }

      /** 以下 商品规格相关 js*/
      $(document).ready(function() {
        // 商品模型切换时 ajax 调用  返回不同的属性输入框
        $("#spec_type").change(function() {
          var goods_id = '';
          var spec_type = $(this).val();
          $.ajax({
            type: 'GET',
            data: {
              goods_id: goods_id,
              spec_type: spec_type
            },
            url: "/index/admin/Goods/ajaxGetSpecSelect",
            success: function(data) {
              $("#ajax_spec_data").html('') $("#ajax_spec_data").append(data);
              ajaxGetSpecInput(); // 触发完  马上触发 规格输入框
            }
          });
          //商品类型切换时 ajax 调用  返回不同的属性输入框     
          $.ajax({
            type: 'GET',
            data: {
              goods_id: goods_id,
              type_id: spec_type
            },
            url: "/index/admin/Goods/ajaxGetAttrInput",
            success: function(data) {
              $("#goods_attr_table tr:gt(0)").remove() $("#goods_attr_table").append(data);
            }
          });
        });
        // 触发商品规格
        $("#spec_type").trigger('change');

        $("input[name='exchange_integral']").blur(function() {
          var shop_price = parseInt($("input[name='shop_price']").val());
          var exchange_integral = parseInt($(this).val());
          if (shop_price * 100 < exchange_integral) {

}
        });
      });

      /** 以下是编辑时默认选中某个商品分类*/
      $(document).ready(function() {

        //  扩展分类
      });</script>
  </body>

</html>