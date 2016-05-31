function ResourceViewer(objArray, x) {
    bool = true;
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var s = '<table class="' + x + '">';

    if (bool) {
		//initializing the table
        s += '<thead><tr>';
        for (var i in array[0]) {
            s += '<th scope="col">' + i + '</th>';
        }
        s += '</tr></thead>';
    }
 
    // body of the table
    s += '<tbody>';
    for (var i = 0; i < array.length; i++) {
        s += (i % 2 == 0) ? '<tr class="alt">' : '<tr>';
        for (var index in array[i]) {
            s += '<td>' + array[i][index] + '</td>';
        }
        s += '</tr>';
    }
    s += '</tbody>'
    s += '</table>';
    
	return s;
}