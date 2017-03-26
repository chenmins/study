	var head = document.getElementsByTagName('head').item(0);
	var script1 = document.createElement('script');
    script1.setAttribute('type', 'text/javascript');
	script1.innerHTML = "showAuto();";
	head.appendChild(script1);