<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt"%> 

<!DOCTYPE html>
<html>
  <head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="keywords" content="共享雨伞">
	<meta name="description" content="共享式公益服务。">
    <title>首页</title>
	
	<link rel="stylesheet" href="resources/css/sccl.css">
	<link rel="stylesheet" type="text/css" href="resources/skin/qingxin/skin.css" id="layout-skin"/>
    
  </head>
  
  <body>
  <c_rt:choose>
  <c_rt:when test="${status}">
	<div class="layout-admin">
		<header class="layout-header">
			<span class="header-logo">共享雨伞</span> 
			<a class="header-menu-btn" href="javascript:;"><i class="icon-font">&#xe600;</i></a>
			<ul class="header-bar">
				<li class="header-bar-role"><a href="javascript:;">您好，超级管理员</a></li>
				<li class="header-bar-nav">
				
					<a href="javascript:;">${admin}<i class="icon-font" style="margin-left:5px;">&#xe60c;</i></a>
					<ul class="header-dropdown-menu">
						<li><a href="javascript:;">个人信息</a></li>
						<li><a href="javascript:;">切换账户</a></li>
						<li><a href="login-out">退出</a></li>
					</ul>
				</li>
				<li class="header-bar-nav"> 
					<a href="javascript:;" title="换肤"><i class="icon-font">&#xe608;</i></a>
					<ul class="header-dropdown-menu right dropdown-skin">
						<li><a href="javascript:;" data-val="qingxin" title="清新">清新</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="molv" title="墨绿">墨绿</a></li>
						
					</ul>
				</li>
			</ul>
		</header>
		
		<aside class="layout-side">
		
			<ul class="side-menu">
			  
			</ul>
		</aside>
		
		
		<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font">&#xe60d;</i></div></div>
		
		<section class="layout-main">
			<div class="layout-main-tab">
				<button class="tab-btn btn-left"><i class="icon-font">&#xe60e;</i></button>
                <nav class="tab-nav">
                   <div class="tab-nav-content">
                      <!--    <a href="javascript:;" class="content-tab active" data-id="home">首页</a>-->
                    </div> 
                </nav>
                <button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
			</div>
			<div class="layout-main-body">
			
				<iframe class="body-iframe" name="iframe0" width="100%" height="100%" src="firstPage" frameborder="0" data-id="firstPage" seamless></iframe>
			</div>
		</section>
		<div class="layout-footer">@Share Umbrella</div>
	</div>
	<script type="text/javascript" src="resources/lib/jquery-1.9.0.min.js"></script>
	<script type="text/javascript" src="resources/js/sccl.js"></script>
	<script type="text/javascript" src="resources/js/sccl-util.js"></script>
  	 </c_rt:when>
  
     <c_rt:otherwise>
     <jsp:forward page="login.jsp"></jsp:forward> 
  	</c_rt:otherwise>
  	</c_rt:choose>
  </body>
</html>
    