oldVal = document.querySelector("#textarea").value;

function print() {
	var val = document.querySelector("#textarea").value;
	if (val != oldVal) {
		console.log(val);
		oldVal = val;
	}
}

var interval = setInterval(print, 5000);