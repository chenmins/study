// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// Update the declarative rules on install or upgrade.
 
//监听所有请求  
chrome.browserAction.onClicked.addListener(function(tab) {  
     //chrome.tabs.executeScript(tab.id, {file: 'jquery.min.js'});  
     //chrome.tabs.executeScript(tab.id, {file: 'content.js'});  
	 chrome.tabs.executeScript(tab.id,{code:"alert(window.title);window.showAuto();"});
 });  