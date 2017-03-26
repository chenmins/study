	var head = document.getElementsByTagName('head').item(0);
	var css = document.createElement('link');
    css.setAttribute('type', 'text/css');
	css.setAttribute('rel', 'stylesheet');
    css.setAttribute('src', 'http://cmworker.duapp.com/auto/Auto.css');
    head.appendChild(css);
    var script = document.createElement('script');
    script.setAttribute('type', 'text/javascript');
    script.setAttribute('src', 'http://cmworker.duapp.com/auto/auto/auto.nocache.js');
    head.appendChild(script);

 