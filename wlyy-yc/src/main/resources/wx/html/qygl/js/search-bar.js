$.fn.searchBar = function() {
	var $input = this.find('input[type=search]'),
	$cancelBtn = this.find('a.searchbar-cancel'),
	$ele = this;
	$input.off('click.searchinput').on('click.searchinput',function() {
		$ele.addClass('searchbar-active');
		$cancelBtn.css({
			display: 'block',
			'margin-right': 0
		});
	});
	$cancelBtn.off('click.searchbtn').on('click.searchbtn',function() {
		$ele.removeClass('searchbar-active');
		$cancelBtn.css({

			'margin-right': (function(){
				return -$cancelBtn[0].offsetWidth + 'px'
			})()
		})
	});
}
