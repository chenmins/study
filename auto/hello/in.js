	var head = document.getElementsByTagName('head').item(0);
	var hasAuto = document.getElementById('_auto');
	if(hasAuto){
		var script1 = document.createElement('script');
		script1.setAttribute('type', 'text/javascript');
		script1.innerHTML = "showAuto();";
		//script1.innerHTML = "alert('注入代码成功，请稍后');";
		head.appendChild(script1);
	}else{
		var css = document.createElement('link');
		css.setAttribute('type', 'text/css');
		css.setAttribute('rel', 'stylesheet');
		css.setAttribute('src', '//cmworker.duapp.com/auto/Auto.css');
	   
		var script = document.createElement('script');
		script.setAttribute('type', 'text/javascript');
		script.setAttribute('id', '_auto');
		script.setAttribute('src', '//cmworker.duapp.com/auto/auto/auto.nocache.js');
		
		head.appendChild(css);
		head.appendChild(script);
	}
	 