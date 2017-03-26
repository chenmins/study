// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.


function click(e) {
  //chrome.tabs.executeScript(null,
  //    {code:"document.body.style.backgroundColor='" + e.target.id + "'"});
  //chrome.tabs.executeScript(null,
   //   {code:"alert('" + e.target.id + "')"});
  //chrome.tabs.executeScript(null,
  //    {code:"alert(document.title);window.showAuto();"});
  chrome.tabs.executeScript(null,
      {file:"run.js"});
  window.close();
}

document.addEventListener('DOMContentLoaded', function () {
  //var divs = document.querySelectorAll('div');
  //for (var i = 0; i < divs.length; i++) {
  //  divs[i].addEventListener('click', click);
  //}
  chrome.tabs.executeScript(null,
      {file:"run.js"});
});
 