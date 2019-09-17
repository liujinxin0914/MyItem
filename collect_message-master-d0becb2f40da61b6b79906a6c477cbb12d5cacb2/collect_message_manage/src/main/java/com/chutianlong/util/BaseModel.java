package com.chutianlong.util;

import java.io.Serializable;

public class BaseModel implements Serializable {
    /**
     * sdf
     */
    private static final long serialVersionUID = 4994848560358696240L;
    // 查询数据库获取总个数
    private int totalCount;
    // 总的页数
    private int pageCount;
    // 每页的条数
    private int pageSize = 2;
    // 当前页数
    private int pageIndex = 1;
    // 开始位置
    private int startPos;
    // 结束位置
    private int endPos;
    // 开始时间
    private String beginTime;
    // 结束时间
    private String endTime;
    //关键字
    private String keyword;

    private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();


    public void calculatePage() {
        // 获取总页数
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }
        // 计算开始记录以及结束记录
        // pageindex == 1 则 startPos = 0；endPos = 3
        // pageindex == 1 则 startPos = 3；endPos = 6
        startPos = (pageIndex - 1) * pageSize;
        endPos = pageIndex * pageSize;
    }


    public int getTotalCount() {
        return totalCount;
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public int getPageCount() {
        return pageCount;
    }


    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getPageIndex() {
        return pageIndex;
    }


    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }


    public int getStartPos() {
        return startPos;
    }


    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }


    public int getEndPos() {
        return endPos;
    }


    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }


    public String getBeginTime() {
        return beginTime;
    }


    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getKeyword() {
        return keyword;
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPageStr() {
        StringBuffer sb = new StringBuffer();
        if(totalCount>0){
            if(pageIndex==1){
                sb.append("	<a>共<font color=red>"+totalCount+"</font>条</a>");
                sb.append("	<input type=\"number\" style=\"width:55px;\" value=\"\" id=\"toGoPage\"  placeholder=\"页码\"/>");
                sb.append("	<a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a>");
                sb.append("	<a>首页</a>\n");
                sb.append("	<a>上页</a>\n");
            }else{
                sb.append("	<a>共<font color=red>"+totalCount+"</font>条</a>\n");
                sb.append("	<input type=\"number\" style=\"width:55px;\" value=\"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left;margin-top:4px;\" placeholder=\"页码\"/>\n");
                sb.append("	<a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a>\n");
                sb.append("	<a onclick=\"nextPage(1)\">首页</a>\n");
                sb.append("	<a onclick=\"nextPage("+(pageIndex-1)+")\">上页</a>\n");
            }
            int startTag = 1;
            if(pageIndex>pageSize){
                startTag = pageIndex-1;
            }
            int endTag = startTag+pageSize-1;
            for(int i=startTag; i<=pageCount && i<=endTag; i++){
                if(pageIndex==i)
                    sb.append("<a><font color='#808080'>"+i+"</font></a>\n");
                else
                    sb.append("	<a onclick=\"nextPage("+i+")\">"+i+"</a>\n");
            }
            if(pageIndex==pageCount){
                sb.append("	<a>下页</a>\n");
                sb.append("	<a>尾页</a>\n");
            }else{
                sb.append("	<a onclick=\"nextPage("+(pageIndex+1)+")\">下页</a>\n");
                sb.append("	<a onclick=\"nextPage("+pageCount+")\">尾页</a>\n");
            }
            sb.append("	<a>第"+pageIndex+"页</a>\n");
            sb.append("	<a>共"+pageCount+"页</a>\n");


            sb.append("	<select title='显示条数' style=\"width:55px;\" onchange=\"changeCount(this.value)\">\n");

            for(int i=1;i<10;i++){
                int cn = 2*i;
                if(pageSize == cn){
                    sb.append("	<option value='"+cn+"' selected>"+cn+"</option>\n");
                } else{
                    sb.append("	<option value='"+cn+"'>"+cn+"</option>\n");
                }
            }
            sb.append("	</select>\n");
            sb.append("	\n");



            sb.append("<script type=\"text/javascript\">\n");

            //换页函数
            sb.append("function nextPage(page){");   //每点击分页得事件 触发得js函数 传参
            //sb.append(" top.jzts();");
            sb.append("	if(true && document.forms[0]){\n");   //判断是否有一个form
            sb.append("		var url = document.forms[0].getAttribute(\"action\");\n"); //获取action属性
            sb.append("		if(url.indexOf('?')>-1){url += \"&pageIndex=\";}\n");
            sb.append("		else{url += \"?pageIndex=\";}\n");
            sb.append("		url = url + page + \"&pageSize="+pageSize+"\";\n");
            sb.append("		document.forms[0].action = url;\n");  //拿到表单的提交路径
            sb.append("		document.forms[0].submit();\n");   //表单提交
            sb.append("	}else{\n");
            sb.append("		var url = document.location+'';\n");
            sb.append("		if(url.indexOf('?')>-1){\n");
            sb.append("			if(url.indexOf('pageIndex')>-1){\n");
            sb.append("				var reg = /pageIndex=\\d*/g;\n");
            sb.append("				url = url.replace(reg,'pageIndex=');\n");
            sb.append("			}else{\n");
            sb.append("				url += \"&pageIndex=\";\n");
            sb.append("			}\n");
            sb.append("		}else{url += \"?pageIndex=\";}\n");
            sb.append("		url = url + page + \"&pageSize="+pageSize+"\";\n");
            sb.append("		document.location = url;\n");
            sb.append("	}\n");
            sb.append("}\n");

            //调整每页显示条数
            sb.append("function changeCount(value){");
            //sb.append(" top.jzts();");
            sb.append("	if(true && document.forms[0]){\n");
            sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
            sb.append("		if(url.indexOf('?')>-1){url += \"&pageIndex=\";}\n");
            sb.append("		else{url += \"?pageIndex=\";}\n");
            sb.append("		url = url + \"1&pageSize=\"+value;\n");
            sb.append("		document.forms[0].action = url;\n");
            sb.append("		document.forms[0].submit();\n");
            sb.append("	}else{\n");
            sb.append("		var url = document.location+'';\n");
            sb.append("		if(url.indexOf('?')>-1){\n");
            sb.append("			if(url.indexOf('pageIndex')>-1){\n");
            sb.append("				var reg = /pageIndex=\\d*/g;\n");
            sb.append("				url = url.replace(reg,'pageIndex=');\n");
            sb.append("			}else{\n");
            sb.append("				url += \"1&pageIndex=\";\n");
            sb.append("			}\n");
            sb.append("		}else{url += \"?pageIndex=\";}\n");
            sb.append("		url = url + \"&pageSize=\"+value;\n");
            sb.append("		document.location = url;\n");
            sb.append("	}\n");
            sb.append("}\n");

            //跳转函数
            sb.append("function toTZ(){");
            sb.append("var toPaggeVlue = document.getElementById(\"toGoPage\").value;");
            sb.append("if(toPaggeVlue == ''){document.getElementById(\"toGoPage\").value=1;return;}");
            sb.append("if(isNaN(Number(toPaggeVlue))){document.getElementById(\"toGoPage\").value=1;return;}");
            sb.append("nextPage(toPaggeVlue);");
            sb.append("}\n");
            sb.append("</script>\n");
        }
        pageStr = sb.toString();
        return pageStr;
    }
}
