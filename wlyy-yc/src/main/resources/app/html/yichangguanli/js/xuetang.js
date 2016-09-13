  function queryList1(page,pageSize) {
	//显示进度条
	//	plus.nativeUI.showWaiting();
	//发送请求
	queryListByType(1,page, pageSize, queryListSuccesss);
}
  /**
    * 血糖查询列表
 	* 健康指标图表查询成功处理方法
 	*/
	function queryListSuccesss(res) {
		if (res.status == 200) {
			d.close();
			if (res.list.length > 0) {
				//成功
				showListXue(res.list);
			}else{
				//无更多数据
				document.querySelector("#view_more1").innerText = "已无更多数据";				
			}
		} else {
			//非200则为失败
			queryListFailed(res);
		}
	}
	/**
	 * 显示查询结果
	 * @param {Object} list
	 */
	function showListXue(list) {
		page = page+1;
		for (var i = 0; i < list.length; i++) {
			var data = list[i];
			if (!data) {
				continue;
			}
			addRow(data.date.substr(5, 5), toIntValue(data.value1), toIntValue(data.value2), toIntValue(data.value3), toIntValue(data.value4), toIntValue(data.value5), toIntValue(data.value6), toIntValue(data.value7));			
		}
		//表格宽度调整
		//修改活动表格子格子的宽度
		$('.date').css('width','69px');
		$('.bb').css('width','69px');
		$('.ba').css('width','43px');
		$('.lb').css('width','42px');
		$('.la').css('width','43px');
		$('.db').css('width','44px');
		$('.da').css('width','43px');						
		$('.sb').css('width','70px');
	}
	

	/**
	 * 血糖
	 * tbody添加一行tr
	 * @param {Object} dateStr
	 * @param {Object} value1
	 * @param {Object} value2
	 */
	function addRow(dateStr, value1, value2, value3, value4, value5, value6, value7) {
		if(dateStr.length > 5){
			dateStr = dateStr.substr(5, 5);
		}
		if(dateStr){
			dateStr = dateStr.replace("-",".");
		}
		var tb = document.querySelector("#item");
		var tr = document.createElement("tr");
		var html = '<td class="date">'+dateStr+'</td>';
		html += getTD(value1, 6.1, 3.9, "bb");
		html += getTD(value2, 7.8, 4.4, "ba");
		html += getTD(value3, 6.1, 3.9, "lb");
		html += getTD(value4, 7.8, 4.4, "la");
		html += getTD(value5, 6.1, 3.9, "db");
		html += getTD(value6, 7.8, 4.4, "da");
		html += getTD(value7, 6.1, 3.9, "sb");
		tr.innerHTML = html;
		tb.appendChild(tr);
	}
	
	
	function getTD(value, max, min, styleName){
		if(value == 0|| isNaN(value)){
			return '<td class="'+styleName+'"></td>';
		}
		if(value > max){
			return '<td class="'+styleName+' c-f00">'+value+'</td>';
		}else if(value < min){
			return '<td class="'+styleName+' c-007cd9">'+value+'</td>';
		}else{
			return '<td class="'+styleName+'">'+value+'</td>';
		}
	}

	