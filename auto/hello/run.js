	var head = document.getElementsByTagName('head').item(0);
	var script1 = document.createElement('script');
    script1.setAttribute('type', 'text/javascript');
	script1.innerHTML = "showAuto();";
	//script1.innerHTML = "alert('注入代码成功，请稍后');";
	head.appendChild(script1);