function loadData() {
	var image = 'http://virtualproctor-tkp.appspot.com/Image?fileName=AMIfv97AeDHZ2uo5BmX_DwelGJAp8XWwJMsXq-US2F5pTSKpbfwCbBznV5cYH_m8eiC3uFE4ZP_B8nbNxrJAeDp1MUHyYPjDy-J0BZhabUlF7X6DO7wwh8a0zPlsGlF5NKNzMNYotXET2o2OG95iEzSzouA3y8tQFmoyBYzXfyEODMaJwQWUgik';

	processImages({
		images : [ {
			fileName : 'lynn',
			imageUrl : image
		}, {
			fileName : 'llewellyn',
			imageUrl : image
		} ]
	});
}
$(document).ready(loadData);
