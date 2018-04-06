<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset='UTF-8'><meta name='viewport' content='width=device-width initial-scale=1'>
<title>index.md</title><link href='https://fonts.googleapis.com/css?family=PT+Serif:400,400italic,700,700italic&subset=latin,cyrillic-ext,cyrillic,latin-ext' rel='stylesheet' type='text/css' /><style type='text/css'>html {overflow-x: initial !important;}#write, body { height: auto; }
#write, #write h1, #write h2, #write h3, #write h4, #write h5, #write h6, #write ol, #write p, #write ul { position: relative; }
#write, #write h1, #write h2, #write h3, #write h4, #write h5, #write h6, #write p, #write pre { width: inherit; }
.CodeMirror, .md-fences, table { text-align: left; }
.md-reset, a:active, a:hover { outline: 0px; }
.md-reset, .md-toc-item a { text-decoration: none; }
.MathJax_SVG, .md-reset { float: none; direction: ltr; }
:root { --bg-color:#ffffff; --text-color:#333333; }
html { font-size: 14px; background-color: var(--bg-color); color: var(--text-color); font-family: "Helvetica Neue", Helvetica, Arial, sans-serif; -webkit-font-smoothing: antialiased; }
body { margin: 0px; padding: 0px; bottom: 0px; top: 0px; left: 0px; right: 0px; font-size: 1rem; line-height: 1.42857; overflow-x: hidden; background: inherit; }
.in-text-selection, ::selection { background: rgb(181, 214, 252); text-shadow: none; }
#write { margin: 0px auto; word-break: normal; word-wrap: break-word; padding-bottom: 70px; white-space: pre-wrap; overflow-x: visible; }
.for-image #write { padding-left: 8px; padding-right: 8px; }
body.typora-export { padding-left: 30px; padding-right: 30px; }
@media screen and (max-width: 500px) {
  body.typora-export { padding-left: 0px; padding-right: 0px; }
  .CodeMirror-sizer { margin-left: 0px !important; }
  .CodeMirror-gutters { display: none !important; }
}
.typora-export #write { margin: 0px auto; }
#write > blockquote:first-child, #write > div:first-child, #write > ol:first-child, #write > p:first-child, #write > pre:first-child, #write > table:first-child, #write > ul:first-child { margin-top: 30px; }
#write li > table:first-child { margin-top: -20px; }
img { max-width: 100%; vertical-align: middle; }
button, input, select, textarea { color: inherit; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; }
input[type="checkbox"], input[type="radio"] { line-height: normal; padding: 0px; }
*, ::after, ::before { box-sizing: border-box; }
h1 { font-size: 2rem; }
h2 { font-size: 1.8rem; }
h3 { font-size: 1.6rem; }
h4 { font-size: 1.4rem; }
h5 { font-size: 1.2rem; }
h6 { font-size: 1rem; }
p { -webkit-margin-before: 1rem; -webkit-margin-after: 1rem; -webkit-margin-start: 0px; -webkit-margin-end: 0px; }
.typora-export p { white-space: normal; }
.mathjax-block { margin-top: 0px; margin-bottom: 0px; -webkit-margin-before: 0px; -webkit-margin-after: 0px; }
.hidden { display: none; }
.md-blockmeta { color: rgb(204, 204, 204); font-weight: 700; font-style: italic; }
a { cursor: pointer; }
sup.md-footnote { padding: 2px 4px; background-color: rgba(238, 238, 238, 0.7); color: rgb(85, 85, 85); border-radius: 4px; }
#write input[type="checkbox"] { cursor: pointer; width: inherit; height: inherit; }
#write > figure:first-child { margin-top: 16px; }
figure { overflow-x: auto; margin: -8px 0px 0px -8px; max-width: calc(100% + 16px); padding: 8px; }
tr { break-inside: avoid; break-after: auto; }
thead { display: table-header-group; }
table { border-collapse: collapse; border-spacing: 0px; width: 100%; overflow: auto; break-inside: auto; }
.CodeMirror-line, .md-fences { break-inside: avoid; }
table.md-table td { min-width: 80px; }
.CodeMirror-gutters { border-right: 0px; background-color: inherit; margin-right: 4px; }
.CodeMirror-placeholder { opacity: 0.3; }
.CodeMirror pre { padding: 0px 4px; }
.CodeMirror-lines { padding: 0px; }
div.hr:focus { cursor: none; }
pre { white-space: pre-wrap; }
.md-fences { font-size: 0.9rem; display: block; overflow: visible; white-space: pre; background: inherit; position: relative !important; }
.md-diagram-panel { width: 100%; margin-top: 10px; text-align: center; padding-top: 0px; padding-bottom: 8px; overflow-x: auto; }
.md-fences .CodeMirror.CodeMirror-wrap { top: -1.6em; margin-bottom: -1.6em; }
.md-fences.mock-cm { white-space: pre-wrap; }
.show-fences-line-number .md-fences { padding-left: 0px; }
.show-fences-line-number .md-fences.mock-cm { padding-left: 40px; }
.footnotes { opacity: 0.8; font-size: 0.9rem; padding-top: 1em; padding-bottom: 1em; }
.footnotes + .footnotes { margin-top: -1em; }
.md-reset { margin: 0px; padding: 0px; border: 0px; vertical-align: top; background: 0px 0px; text-shadow: none; position: static; width: auto; height: auto; white-space: nowrap; cursor: inherit; -webkit-tap-highlight-color: transparent; line-height: normal; font-weight: 400; text-align: left; box-sizing: content-box; }
.md-toc-inner, a img, img a { cursor: pointer; }
li div { padding-top: 0px; }
blockquote { margin: 1rem 0px; }
li .mathjax-block, li p { margin: 0.5rem 0px; }
li { margin: 0px; position: relative; }
blockquote > :last-child { margin-bottom: 0px; }
blockquote > :first-child { margin-top: 0px; }
.footnotes-area { color: rgb(136, 136, 136); margin-top: 0.714rem; padding-bottom: 0.143rem; white-space: nowrap; }
@media print {
  body, html { border: 1px solid transparent; height: 99%; break-after: avoid; break-before: avoid; }
  .typora-export * { -webkit-print-color-adjust: exact; }
  h1, h2, h3, h4, h5, h6 { break-after: avoid-page; orphans: 2; }
  p { orphans: 4; }
  html.blink-to-pdf { font-size: 13px; }
  .typora-export #write { padding-left: 1cm; padding-right: 1cm; padding-bottom: 0px; break-after: avoid; }
  .typora-export #write::after { height: 0px; }
  @page { margin: 20mm 0px; }
}
.footnote-line { margin-top: 0.714em; font-size: 0.7em; }
pre.md-meta-block { font-size: 0.8rem; min-height: 0.8rem; white-space: pre-wrap; background: rgb(204, 204, 204); display: block; overflow-x: hidden; }
p > img:only-child { display: block; margin: auto; }
.md-line > .md-image:only-child, p > .md-image:only-child { display: inline-block; width: 100%; text-align: center; }
.mathjax-block:not(:empty)::after, .md-toc-content::after, .md-toc::after { display: none; }
#write .MathJax_Display { margin: 0.8em 0px 0px; }
.mathjax-block { white-space: pre; overflow: hidden; width: 100%; }
p + .mathjax-block { margin-top: -1.143rem; }
[contenteditable="true"]:active, [contenteditable="true"]:focus { outline: 0px; box-shadow: none; }
.md-task-list-item { position: relative; list-style-type: none; }
.task-list-item.md-task-list-item { padding-left: 0px; }
.md-task-list-item > input { position: absolute; top: 0px; left: 0px; margin-left: -1.2em; margin-top: calc(1em - 10px); }
.math { font-size: 1rem; }
.md-toc { min-height: 3.58rem; position: relative; font-size: 0.9rem; border-radius: 10px; }
.MathJax_SVG, .mathjax-block .MathJax_SVG_Display { text-indent: 0px; max-width: none; max-height: none; min-height: 0px; }
.md-toc-content { position: relative; margin-left: 0px; }
.md-toc-item { display: block; color: rgb(65, 131, 196); }
.md-toc-inner:hover { }
.md-toc-inner { display: inline-block; }
.md-toc-h1 .md-toc-inner { margin-left: 0px; font-weight: 700; }
.md-toc-h2 .md-toc-inner { margin-left: 2em; }
.md-toc-h3 .md-toc-inner { margin-left: 4em; }
.md-toc-h4 .md-toc-inner { margin-left: 6em; }
.md-toc-h5 .md-toc-inner { margin-left: 8em; }
.md-toc-h6 .md-toc-inner { margin-left: 10em; }
@media screen and (max-width: 48em) {
  .md-toc-h3 .md-toc-inner { margin-left: 3.5em; }
  .md-toc-h4 .md-toc-inner { margin-left: 5em; }
  .md-toc-h5 .md-toc-inner { margin-left: 6.5em; }
  .md-toc-h6 .md-toc-inner { margin-left: 8em; }
}
a.md-toc-inner { font-size: inherit; font-style: inherit; font-weight: inherit; line-height: inherit; }
.footnote-line a:not(.reversefootnote) { color: inherit; }
.md-attr { display: none; }
.md-fn-count::after { content: "."; }
.md-tag { opacity: 0.5; }
code, pre, tt { font-family: var(--monospace); }
.md-comment { color: rgb(162, 127, 3); opacity: 0.8; font-family: var(--monospace); }
code { text-align: left; }
h1 .md-tag, h2 .md-tag, h3 .md-tag, h4 .md-tag, h5 .md-tag, h6 .md-tag { font-weight: initial; opacity: 0.35; }
a.md-print-anchor { border-width: initial !important; border-style: none !important; border-color: initial !important; display: inline-block !important; position: absolute !important; width: 1px !important; right: 0px !important; outline: 0px !important; background: 0px 0px !important; text-decoration: initial !important; text-shadow: initial !important; }
.md-inline-math .MathJax_SVG .noError { display: none !important; }
.mathjax-block .MathJax_SVG_Display { text-align: center; margin: 1em 0px; position: relative; min-width: 100%; width: auto; display: block !important; }
.MathJax_SVG_Display, .md-inline-math .MathJax_SVG_Display { width: auto; margin: inherit; display: inline-block !important; }
.MathJax_SVG .MJX-monospace { font-family: monospace; }
.MathJax_SVG .MJX-sans-serif { font-family: sans-serif; }
.MathJax_SVG { display: inline; font-style: normal; font-weight: 400; line-height: normal; zoom: 90%; text-align: left; text-transform: none; letter-spacing: normal; word-spacing: normal; word-wrap: normal; white-space: nowrap; min-width: 0px; border: 0px; padding: 0px; margin: 0px; }
.MathJax_SVG * { transition: none; }
.md-diagram-panel > svg, [lang="flow"] svg, [lang="mermaid"] svg { max-width: 100%; }
[lang="mermaid"] .node text { font-size: 1rem; }
table tr th { border-bottom: 0px; }


@font-face { font-family: "PT Serif"; font-style: normal; font-weight: normal; src: local("PT Serif"), local("PTSerif-Regular"), url("./newsprint/PT_Serif-Web-Regular.ttf"); }
@font-face { font-family: "PT Serif"; font-style: italic; font-weight: normal; src: local("PT Serif"), local("PTSerif-Italic"), url("./newsprint/PT_Serif-Web-Italic.ttf"); }
@font-face { font-family: "PT Serif"; font-style: normal; font-weight: bold; src: local("PT Serif"), local("PTSerif-Bold"), url("./newsprint/PT_Serif-Web-Bold.ttf"); }
@font-face { font-family: "PT Serif"; font-style: italic; font-weight: bold; src: local("PT Serif"), local("PTSerif-BoldItalic"), url("./newsprint/PT_Serif-Web-BoldItalic.ttf"); }
:root { --active-file-bg-color: rgba(32, 43, 51, 0.63); --active-file-text-color: white; --bg-color: #f3f2ee; --text-color: #1f0909; }
html { font-size: 16px; }
html, body { background-color: rgb(243, 242, 238); font-family: "PT Serif", "Times New Roman", Times; color: rgb(31, 9, 9); line-height: 1.5em; }
#write { max-width: 40em; }
ol, ul { list-style: none; }
blockquote, q { quotes: none; }
blockquote::before, blockquote::after, q::before, q::after { content: none; }
table { border-collapse: collapse; border-spacing: 0px; }
h1, h2, h3, h4, h5, h6 { font-weight: bold; }
h1 { font-size: 1.875em; line-height: 1.6em; margin-top: 2em; }
h2, h3 { font-size: 1.3125em; line-height: 1.15; margin-top: 2.28571em; margin-bottom: 1.15em; }
h3 { font-weight: normal; }
h4 { font-size: 1.125em; margin-top: 2.67em; }
h5, h6 { font-size: 1em; }
h1 { border-bottom: 1px solid; margin-bottom: 1.875em; padding-bottom: 0.8125em; }
a { text-decoration: none; color: rgb(6, 85, 136); }
a:hover, a:active { text-decoration: underline; }
p, blockquote, .md-fences { margin-bottom: 1.5em; }
h1, h2, h3, h4, h5, h6 { margin-bottom: 1.5em; }
blockquote { font-style: italic; border-left: 5px solid; margin-left: 2em; padding-left: 1em; }
ul, ol { margin: 0px 0px 1.5em 1.5em; }
ol li { list-style-type: decimal; list-style-position: outside; }
ul li { list-style-type: disc; list-style-position: outside; }
.md-meta, .md-before, .md-after { color: rgb(153, 153, 153); }
table { margin-bottom: 1.5em; font-size: 1em; }
thead th, tfoot th { padding: 0.25em 0.25em 0.25em 0.4em; text-transform: uppercase; }
th { text-align: left; }
td { vertical-align: top; padding: 0.25em 0.25em 0.25em 0.4em; }
code, .md-fences { background-color: rgb(218, 218, 218); padding-left: 1ch; padding-right: 1ch; }
.md-fences { margin-left: 2em; margin-bottom: 3em; }
.md-fences .CodeMirror.CodeMirror-wrap { top: -0.8em; }
pre, code, tt { font-size: 0.875em; line-height: 1.71429em; }
h1 { line-height: 1.3em; font-weight: normal; margin-bottom: 0.5em; }
p + ul, p + ol { margin-top: -1em; }
h3 + ul, h4 + ul, h5 + ul, h6 + ul, h3 + ol, h4 + ol, h5 + ol, h6 + ol { margin-top: 0.5em; }
li > ul, li > ol { margin-top: inherit; }
h2, h3 { margin-bottom: 0.75em; }
hr { border-top: none; border-right: none; border-bottom: 1px solid; border-left: none; }
h1 { border-color: rgb(197, 197, 197); }
blockquote { border-color: rgb(186, 186, 186); color: rgb(101, 101, 101); }
thead.md-table-edit { background-color: transparent; }
thead { background-color: rgb(218, 218, 218); }
tr:nth-child(2n) { background: rgb(232, 231, 231); }
hr { border-color: rgb(197, 197, 197); }
.task-list { padding-left: 1rem; }
.md-task-list-item { padding-left: 1.5rem; list-style-type: none; }
.md-task-list-item > input::before { content: "√"; display: inline-block; width: 1.25rem; height: 1.6rem; vertical-align: middle; text-align: center; color: rgb(221, 221, 221); background-color: rgb(243, 242, 238); }
.md-task-list-item > input:checked::before, .md-task-list-item > input[checked]::before { color: inherit; }
#write pre.md-meta-block { min-height: 1.875rem; color: rgb(85, 85, 85); border: 0px; background: transparent; margin-left: 1em; margin-top: 1em; }
.md-image > .md-meta { color: rgb(155, 81, 70); }
.md-image > .md-meta { font-family: Menlo, "Ubuntu Mono", Consolas, "Courier New", "Microsoft Yahei", "Hiragino Sans GB", "WenQuanYi Micro Hei", sans-serif; }
#write > h3.md-focus::before { left: -1.5rem; color: rgb(153, 153, 153); border-color: rgb(153, 153, 153); }
#write > h4.md-focus::before { left: -1.5rem; top: 0.25rem; color: rgb(153, 153, 153); border-color: rgb(153, 153, 153); }
#write > h5.md-focus::before { left: -1.5rem; color: rgb(153, 153, 153); border-color: rgb(153, 153, 153); }
#write > h6.md-focus::before { left: -1.5rem; top: 0.3125rem; color: rgb(153, 153, 153); border-color: rgb(153, 153, 153); }
.md-toc:focus .md-toc-content { margin-top: 19px; }
.md-toc-content:empty::before { color: rgb(6, 85, 136); }
.md-toc-item { color: rgb(6, 85, 136); }
#write div.md-toc-tooltip { background-color: rgb(243, 242, 238); }
#typora-sidebar { background-color: rgb(243, 242, 238); box-shadow: rgba(0, 0, 0, 0.376) 0px 6px 12px; }
.pin-outline #typora-sidebar { background: inherit; box-shadow: none; border-right: 1px dashed; }
.pin-outline #typora-sidebar:hover .outline-title-wrapper { border-left: 1px dashed; }
.outline-item:hover { background-color: rgb(218, 218, 218); border-left: 28px solid rgb(218, 218, 218); border-right: 18px solid rgb(218, 218, 218); }
.typora-node .outline-item:hover { border-right: 28px solid rgb(218, 218, 218); }
.outline-expander::before { content: ""; font-family: FontAwesome; font-size: 14px; top: 1px; }
.outline-expander:hover::before, .outline-item-open > .outline-item > .outline-expander::before { content: ""; }
.modal-content { background-color: rgb(243, 242, 238); }
.auto-suggest-container ul li { list-style-type: none; }
.megamenu-menu, #top-titlebar, #top-titlebar *, .megamenu-content { background: rgb(243, 242, 238); color: rgb(31, 9, 9); }
.megamenu-menu-header { border-bottom: 1px dashed rgb(32, 43, 51); }
.megamenu-menu { box-shadow: none; border-right: 1px dashed; }
header, .context-menu, .megamenu-content, footer { font-family: "PT Serif", "Times New Roman", Times; color: rgb(31, 9, 9); }
#megamenu-back-btn { color: rgb(31, 9, 9); border-color: rgb(31, 9, 9); }
.megamenu-menu-header #megamenu-menu-header-title::before { color: rgb(31, 9, 9); }
.megamenu-menu-list li a:hover, .megamenu-menu-list li a.active { color: inherit; background-color: rgb(232, 231, 223); }
.long-btn:hover { background-color: rgb(232, 231, 223); }
#recent-file-panel tbody tr:nth-child(2n-1) { background-color: transparent !important; }
.megamenu-menu-panel tbody tr:hover td:nth-child(2) { color: inherit; }
.megamenu-menu-panel .btn { background-color: rgb(210, 209, 209); }
.btn-default { background-color: transparent; }
.typora-sourceview-on #toggle-sourceview-btn, .show-word-count #footer-word-count { background: rgb(199, 197, 197); }
#typora-quick-open { background-color: inherit; }
.md-diagram-panel { margin-left: -1ch; margin-top: 24px; }
.file-list-item-file-name { font-weight: initial; }
.file-list-item-summary { opacity: 1; }
.file-list-item { color: rgb(119, 119, 119); }
.file-list-item.active { background-color: inherit; color: black; }
.ty-side-sort-btn.active { background-color: inherit; }
.file-list-item.active .file-list-item-file-name { font-weight: bold; }
.file-list-item { opacity: 1 !important; }
.file-library-node.active > .file-node-background { background-color: var(--active-file-bg-color); }
.file-tree-node.active > .file-node-content { color: var(--active-file-text-color); }
.md-task-list-item > input { margin-left: -1.6em; margin-top: calc(1rem - 12px); }






</style>
</head>
<body class='typora-export' >
<div  id='write'  class = 'is-node show-fences-line-number'><h2><a name='header-n0' class='md-header-anchor '></a>向房间内推送图文的Chrome插件</h2><h3><a name='header-n2' class='md-header-anchor '></a>安装方法</h3><ol start='' ><li>最新插件<a href='https://github.com/nice01qc/chromeExtension/archive/master.zip'>点击这里</a></li><li>下载zip文件, 解压</li><li>将最里层的<strong>chrome</strong>文件夹复制到你常用的安装软件的目录</li><li>依次进行如下操作:</li></ol><ul><li>打开chrome, 进入<code>设置</code></li><li>选择<code>扩展程序</code></li><li>勾选<code>开发者模式</code></li><li>加载已解压的扩展程序, 选择<code>chrome</code>文件夹.</li></ul><ol start='5' ><li>至此已经全部安装完成, 若有不安全提示, 取消即可.</li></ol><h3><a name='header-n33' class='md-header-anchor '></a>使用方法</h3><h4><a name='header-n34' class='md-header-anchor '></a>发送</h4><ol start='' ><li>点击右上方月亮, 在面板内输入自定义的一个房间号, 然后点击<code>确认</code>进入房间</li><li>发送当前页面图片,按快捷键<code>ctrl</code> + <code>alt</code> + <code>z</code></li><li>发送的内容为鼠标指向的文字内容，然后按快捷键<code>Shift</code>+<code>A</code>或者<code>Ctrl</code>+<code>Shift</code>+<code>A</code></li></ol><h4><a name='header-n45' class='md-header-anchor '></a>接收</h4><ul><li><p>方法: 进入下列网址之一:</p><ul><li><a href='http://118.25.1.128:8080/pushtest/webSock.jsp'>site</a></li></ul></li></ul><h2><a name='header-n54' class='md-header-anchor '></a>全文依照<a href='https://github.com/wqwz111/Push-text'>https://github.com/wqwz111/Push-text</a>改造而成，在师兄的帮助下，最终成型</h2></div>
</body>
</html>