function queryList2(page,pageSize) {
	//显示进度条
	//	plus.nativeUI.showWaiting();
	//发送请求
	queryListByType(2,page, pageSize, queryListSuccesss1,begindate,enddate);
}

/**
	 * 血压
	 * 健康指标图表查询成功处理方法
	 */
	function queryListSuccesss1(res) {
		if (res.status == 200) {
			d.close();
			if (res.list.length > 0) {
				showList1(res.list);
				if(res.list.length<10){
					$("#view_more2").hide();
				}else{
					$("#view_more2").show();
				}
			}else{
				$("#view_more2").hide();	
			}
		} else {
			//非200则为失败
			queryListFailed(res);
		}
	}
		
	/**
	 * 
	 * 显示查询结果
	 * @param {Object} list
	 */
	function showList1(list) {
		page = page+1;
		for (var i = 0; i < list.length; i++) {
			var data = list[i];
			if (!data){
				continue;
			}
			addRow1(data.date.substr(5, 11), toIntValue(data.value1), toIntValue(data.value2));
		}
		setTimeout(function () {
			scroller1.refresh();
		}, 1000);	
	}

	/**
	 * 血压
	 * tbody添加一行tr
	 * @param {Object} dateStr
	 * @param {Object} value1
	 * @param {Object} value2
	 */
	function addRow1(dateStr, value1, value2) {
		if (dateStr.length > 11) {
			dateStr = dateStr.substr(5, 11);
		}
		if(dateStr){
			dateStr = dateStr.replace("-",".");
		}
		var tb = document.querySelector("#item1");
		var tr = document.createElement("tr");
		var html = '<td class="width-40 c-color">' + dateStr + '</td>';
		html += getTD1(value1, 139, 90);
		html += getTD1(value2, 89, 60);
		tr.innerHTML = html;
		tb.appendChild(tr);
	}
	
	function getTD1(value, max, min) {
		if(value == 0 || isNaN(value)){
			return '<td class="width-20"></td>';
		}
		if (value > max) {
			return '<td class="width-20 c-f00">' + value + '</td>';
		} else if (value < min) {
			return '<td class="width-20 c-007cd9">' + value + '</td>';
		} else {
			return '<td class="width-20 c-color">' + value + '</td>';
		}
	}