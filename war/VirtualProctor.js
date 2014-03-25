var lastLoad = new Date();
var totalCount = 0;
function processImages(data) {
	lastLoad = new Date();
	var xSpace = 250;
	var xBorder = 20;
	var ySpace = 225;
	var yBorder = 60;
	var columns = Math.floor( $(window).width() / xSpace);
	
	for ( var i = 0; i < data.images.length; i++) {
		var image = data.images[i];
		var id = "id" + image.fileName.hashCode();
		var element = $("#" + id);
		if (element.length == 0) {
			var html = '<div id="' + id	+ '" class="ui-widget-content">'
					+ '<img src="'
					+ image.imageUrl + '"><p contenteditable="true">'
					+ image.fileName + '</p></div> &nbsp;';
			$(".inner").append(html);
			var box = $("#" + id)[0];
			box.style.position = "absolute";
			var xPos = totalCount % columns;
			var yPos = Math.floor(totalCount / columns);
			box.style.left = (xBorder + (xPos * xSpace)) + 'px';
			box.style.top = (yBorder +(yPos * ySpace))+'px';
			totalCount++;
		} else {
			element.find("img").attr("src", image.imageUrl);
		}
	}
	$("div").draggable();
}
String.prototype.hashCode = function() {
	var hash = 0, i, char;
	if (this.length == 0)
		return hash;
	for (i = 0, l = this.length; i < l; i++) {
		char = this.charCodeAt(i);
		hash = ((hash << 5) - hash) + char;
		hash |= 0; // Convert to 32bit integer
	}
	return hash;
};
function updateTime() {
	var now = new Date();
	var seconds = Math.round((now.getTime() - lastLoad.getTime()) / 1000);
	$("#timer").html('last loaded ' + seconds + ' second ago');
}

