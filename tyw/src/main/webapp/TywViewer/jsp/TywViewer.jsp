<!doctype html>


<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>FlexPaper AdaptiveUI JSP Example</title>
  
		<script type="text/javascript" src="../aserts/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../aserts/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="../aserts/flexpaper.js"></script>
		<script type="text/javascript" src="../aserts/flexpaper_handlers.js"></script>
    </head>
    <body>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;left:10px;top:10px;width:770px;height:500px"></div>
		<script type="text/javascript">


			$('#documentViewer').FlexPaperViewer({
				 config : {
					 
                                         SwfFile : '../TestSwf/14.swf',
					 Scale : 0.8, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : true, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : false,
					 MinZoomSize : 0.5,
					 MaxZoomSize : 3,
					 SearchMatchAll : false,
					 

					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,

					
					 jsDirectory : '../aserts/',
					 

					 JSONDataType : 'jsonp',
					 
					 WMode : 'window',
					 localeChain: 'zh_CN'
					 }
			});
		</script>
   </body>
</html>