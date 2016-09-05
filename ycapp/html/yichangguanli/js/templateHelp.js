template.helper("setSex", function(s) {
	if(s == 1) {
		return "男"
	} else if(s == 2) {
		return "女"
	}
});

template.helper("setPhoto", function(p, sex) {
	if(!p || p.length == 0) {
		return sex==1? "../../../images/d-male.png" : "../../../images/d-female.png";
	} else {
		return p
	}
});

template.helper("setDocPhoto", setDocPhoto);

function setDocPhoto(p, sex){
	if (!p || p.length == 0) {
		return sex==2? "../../../images/d-female.png" : "../../../images/d-male.png";
	} else {
		return p;
	}
}
